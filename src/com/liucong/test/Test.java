package com.liucong.test;

import com.liucong.conf.ClassPathApplication;
import com.liucong.serviceImpl.TestServiceImpl;

public class Test {
    public static void main(String[] args) {
        ClassPathApplication classPathApplication = new ClassPathApplication("com.liucong.serviceImpl");
        try {
            TestServiceImpl testService = (TestServiceImpl) classPathApplication.getBean("testServiceImpl");
        testService.hello();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
