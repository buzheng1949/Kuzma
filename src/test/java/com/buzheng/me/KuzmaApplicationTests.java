package com.buzheng.me;

import com.buzheng.me.holder.UserHolder;
import com.buzheng.me.proxy.cglib.CGLibMethodInteceptor;
import com.buzheng.me.proxy.common.RealSubject;
import com.buzheng.me.proxy.common.Subject;
import com.buzheng.me.proxy.dynamic.DynamicProxyHandler;
import com.buzheng.me.service.StudentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KuzmaApplicationTests {

    @Autowired
    private StudentService studentService;


    @Test
    public void contextLoads() {
    }

    @Test(expected = Exception.class)
    public void tesKuzma() {
        UserHolder.set("admin");
        studentService.deleteStudentInformation();
    }

    @Test
    public void testProxyDynamic() {
        Subject subject = new RealSubject();
        DynamicProxyHandler dynamicProxyHandler = new DynamicProxyHandler(subject);
        Subject s = dynamicProxyHandler.getProxy();
        s.exec();
    }

    @Test
    public void testCGLib() {
        Subject subject = new RealSubject();
        CGLibMethodInteceptor cgLibMethodInteceptor = new CGLibMethodInteceptor(subject);
        Subject s = cgLibMethodInteceptor.getProxy();
        s.exec();
    }

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private Jedis jedis;

    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("name", "jaychou");
    }

    @Test
    public void testJedis() {
        jedis.set("age", "12");
        String age = jedis.get("age");
        jedis.close();
        Assert.assertEquals(age, "12");
    }



}
