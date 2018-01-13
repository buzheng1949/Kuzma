package com.buzheng.me;

import com.buzheng.me.holder.UserHolder;
import com.buzheng.me.proxy.cglib.CGLibMethodInteceptor;
import com.buzheng.me.proxy.common.RealSubject;
import com.buzheng.me.proxy.common.Subject;
import com.buzheng.me.proxy.dynamic.DynamicProxyHandler;
import com.buzheng.me.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

//    @Test
//    public void testFindById() {
//        ActionLog actionLog = mapper.findById(1);
//        return;
//    }

}
