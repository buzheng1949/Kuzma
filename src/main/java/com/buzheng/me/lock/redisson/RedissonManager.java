package com.buzheng.me.lock.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by buzheng on 18/1/27.
 */
@Component
@Slf4j
public class RedissonManager {

    private Config config = new Config();

    private Redisson redisson;

    //静态代码块初始化也行 但是既然是bean用post注解也是可以的
    @PostConstruct
    private void init() {
        try {
            //初始化配置
            config.useSingleServer().setAddress("127.0.0.1:6379");
            //使用配置初始化Redisson
            redisson = (Redisson) Redisson.create(config);
            log.info("init the Redisson success");
        } catch (Exception e) {
            log.error("init the Redisson error: {}", e);
        }
    }

    /**
     * 对外暴露Redisson
     *
     * @return
     */
    public Redisson getRedisson() {
        return redisson;
    }
}
