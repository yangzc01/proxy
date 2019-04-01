package com.proxy.handler;

import com.proxy.Person;

import java.lang.reflect.Method;

/**
 * @Description: TODO
 * @ClassName MyHandler
 * @Auther: administer
 * @Date: 2019/4/1 11:19
 * @Version 1.0
 */
public class MyHandler implements MyInvocationHandler {
    private Person person;
    public MyHandler(Person person){
        this.person = person;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(person,args);
        after();
        return obj;
    }
    public void before(){
        System.out.println("before..............");
    }
    public void after(){
        System.out.println("after................");
    }
}
