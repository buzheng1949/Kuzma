package com.buzheng.me.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by buzheng on 18/1/21.
 * 使用Redis实现分布式锁服务
 */
public class RedisLock {


    private Logger logger = LoggerFactory.getLogger(RedisLock.class);

    /**
     * redis
     */
    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 尝试获取锁
     *
     * @param redisConnection
     * @param lockSeconds
     * @return
     */
    private boolean lock(RedisConnection redisConnection, int lockSeconds, byte[] lockKey) {
        long nowTime = System.currentTimeMillis();
        long expireTime = nowTime + lockSeconds * 1000;
        if (redisConnection.setNX(lockKey, longToBytes(expireTime))) {
            redisConnection.expire(lockKey, lockSeconds);
            return true;
        } else {
            byte[] oldValue = redisConnection.get(lockKey);
            if (oldValue != null && bytesToLong(oldValue) < nowTime) {
                //说明锁已经过期了  可以获得锁
                byte[] oldValueRetry = redisConnection.getSet(lockKey, longToBytes(expireTime));
                if (Arrays.equals(oldValue, oldValueRetry)) {
                    //双重验证标明可以获得锁
                    redisConnection.expire(lockKey, lockSeconds);
                    return true;
                } else {
                    //标明此时已经有人先获取锁了
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 通过轮询的方式获取分布式锁
     *
     * @param lockSeconds      锁时间
     * @param waitTimeMillions 休眠时间
     * @param exeCounts        最大尝试次数
     * @param lockKey          锁的key
     * @return
     */
    public boolean tryLock(final int lockSeconds, final long waitTimeMillions, final long exeCounts, byte[] lockKey) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                int tryCount = 0;
                while (true) {
                    tryCount++;
                    if (tryCount > exeCounts) {
                        return false;
                    }
                    try {
                        if (tryLock(lockSeconds, lockKey)) {
                            return true;
                        }
                    } catch (Exception e) {
                        logger.error("try get lock error", e);
                    }
                    try {
                        Thread.sleep(waitTimeMillions);
                    } catch (Exception e) {
                        logger.error("tryLock interrupted", e);
                        return false;
                    }
                }
            }
        });
    }

    /**
     * 释放锁
     */
    private void unLock(byte[] lockKey) {
        String lock = new String(lockKey);
        redisTemplate.delete(lock);
    }

    /**
     * 尝试一次性获取锁
     *
     * @param lockSeconds
     * @param lockKey
     * @return
     */
    public boolean tryLock(final int lockSeconds, byte[] lockKey) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                try {
                    return lock(redisConnection, lockSeconds, lockKey);
                } catch (Exception e) {
                    logger.error("try lock is failed", e);
                    return false;
                }
            }
        });
    }


    public byte[] longToBytes(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);
        buffer.putLong(value);
        return buffer.array();
    }

    public long bytesToLong(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong();
    }


}
