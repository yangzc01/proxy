package com.proxy.jdk.proxy;

import com.proxy.Person;
import com.proxy.ZhangSan;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * @Description: TODO
 * @ClassName JDKProxyTest
 * @Auther: administer
 * @Date: 2019/4/1 10:33
 * @Version 1.0
 */
public class JDKProxyTest {
    public static void main(String[] args) throws Throwable {
        Person person = new ZhangSan();
        JDKProxyHandler jdkProxyHandler = new JDKProxyHandler(person);
        Person proxy = (Person) Proxy.newProxyInstance(person.getClass().getClassLoader(),person.getClass().getInterfaces(),jdkProxyHandler);
        try {
            proxy.findJob();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println(proxy.getClass().getName());
        person.findJob();
    }
}
