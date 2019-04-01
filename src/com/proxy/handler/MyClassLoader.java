package com.proxy.handler;

import com.proxy.utils.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Description: 自定义类加载器MyClassLoader
 * @ClassName MyClassLoader
 * @Auther: administer
 * @Date: 2019/4/1 11:23
 * @Version 1.0
 */
public class MyClassLoader extends ClassLoader {
    //生成的代理类加载路径
    private File dir;
    //代理引用包
    private String proxyClassPackage;

    public File getDir() {
        return dir;
    }

    public String getProxyClassPackage() {
        return proxyClassPackage;
    }

    public MyClassLoader(String path,String proxyClassPackage) {
        this.dir = new File(path);
        this.proxyClassPackage = proxyClassPackage;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if(null != dir){
            File classFile = new File(dir,name+".class");
            if(classFile.exists()){
                try {
                    //生产class的二进制字节流
                    byte[] classBytes = FileUtils.copyToByteArray(classFile);
                    return defineClass(proxyClassPackage+"."+name,classBytes,0,classBytes.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //默认加载方式
        //指定路径下加载指定的字节码文件
        return super.findClass(name);
    }
}
