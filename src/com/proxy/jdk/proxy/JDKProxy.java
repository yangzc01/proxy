package com.proxy.jdk.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description: TODO
 * @ClassName PersonHandler
 * @Auther: administer
 * @Date: 2019/4/1 10:27
 * @Version 1.0
 */
public class JDKProxy implements InvocationHandler {
    private Object target;
    /**
     * 获得代理的类的接口的实例
     * @param target 代理的类
     * @return 代理的类接口的实例
     */
    public Object getInstance(Object target) {
        this.target = target;
        Class clz = this.target.getClass();
        return Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(), this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(target, args);
        after();
        return obj;
    }
    public void before(){
        System.out.println("找到之前,投简历，面试");
    }
    public void after(){
        System.out.println("入职之后996");
    }
}
