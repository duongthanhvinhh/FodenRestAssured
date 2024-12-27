package org.foden.tests;

import org.foden.utils.DatabaseUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.lang.reflect.Method;

public class BaseTest {

    @BeforeSuite
    public void getDataFromDB(){
        DatabaseUtils.loadUserCredentials();
    }

    @BeforeMethod
    public void beforeMethod(Method m){
        System.out.println("STARTING TEST: " + m.getName());
        System.out.println("THREAD ID: " + Thread.currentThread().threadId());
    }
}
