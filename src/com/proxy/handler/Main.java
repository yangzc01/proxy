package com.proxy.handler;

import com.proxy.Person;
import com.proxy.ZhangSan;
public class Main {

    public static void main(String[] args) {
        Person person = new ZhangSan();

        MyHandler myHandler = new MyHandler(person);

        Person proxyPerson = (Person) MyProxy.newProxyInstance(new MyClassLoader("D:\\yto_works\\proxy\\src\\com\\proxy\\handler","com.proxy.handler"),Person.class,myHandler);

        System.out.println(proxyPerson.getClass().getName());

        try {
            proxyPerson.findJob();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
}
