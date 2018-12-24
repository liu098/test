package com.liucong.serviceImpl;

import com.liucong.inte.Resource;
import com.liucong.inte.Service;

@Service
public class TestServiceImpl {
    @Resource
    private TestBeaServiceImpl testBeaServiceImpl;

    public TestServiceImpl() {
        System.out.print("TestServiceImpl");
    }
    public void hello(){
        testBeaServiceImpl.hello();
    }
}
