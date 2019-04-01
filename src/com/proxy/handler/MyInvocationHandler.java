package com.proxy.handler;

import java.lang.reflect.Method;

/**
 * @Description: TODO
 * @ClassName MyInvication
 * @Auther: administer
 * @Date: 2019/4/1 11:17
 * @Version 1.0
 */
public interface MyInvocationHandler {
    Object invoke(Object proxy, Method method,Object[] args)throws Throwable;
}
