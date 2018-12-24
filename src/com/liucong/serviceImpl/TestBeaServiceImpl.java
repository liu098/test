package com.liucong.serviceImpl;

import com.liucong.inte.Service;

@Service
public class TestBeaServiceImpl {

 public TestBeaServiceImpl(){
     System.out.print("TestBeaServiceImpl");
 }

 public void hello(){
     System.out.print("依赖注入");
 }
}
