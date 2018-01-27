package com.buzheng.me.lock.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * Created by buzheng on 18/1/27.
 */
@Slf4j
public class RedissonLock {

    @Autowired
    private RedissonManager redissonManager;

    /**
     * 获取锁
     *
     * @param lockKey   锁的key
     * @param waitTime  最大等待锁时间 最好设置成0  不然踩坑呀呀呀
     * @param leaseTime 锁有效期
     * @return
     */
    private boolean lock(String lockKey, Long waitTime, Long leaseTime) {
        boolean result = Boolean.FALSE;
        Redisson redisson = redissonManager.getRedisson();
        RLock lock = redisson.getLock(lockKey);
        //尝试获取锁
        try {
            result = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if (result) {
                //获取分布式锁成功
                log.info("Redisson get lock success");
            } else {
                log.info("Redisson get lock failed");
            }
        } catch (InterruptedException e) {
            log.error("Redisson get lock error:{}", e);
        }
        return result;
    }

}
