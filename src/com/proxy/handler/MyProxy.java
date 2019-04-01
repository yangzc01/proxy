package com.proxy.handler;

import com.proxy.utils.FileUtils;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
/**
 * @Description: TODO
 * @ClassName MyProxy
 * @Auther: administer
 * @Date: 2019/4/1 13:26
 * @Version 1.0
 */
public class MyProxy {
    /*
    *第一：需要根据interfaces接口构造出动态代理类需要的方法。（其实就是利用反射获取）
     第二：把动态生成的代理类（即.java文件）进行编译，生成字节码文件（即.class文件），然后利用类加载进行加载
     第三：动态代理类进行加载后，利用反射机制，通过构造方法进行实例化，并在实例化时，初始化业务Hanlder
    */
    private static final String rt = "\r";
    public static Object newProxyInstance(MyClassLoader loader,Class<?> interfaces,MyInvocationHandler invocationHandler){
        if(invocationHandler == null){
            throw  new NullPointerException();
        }
        //根据接口构造代理类：$Proxy0
        Method[] methods = interfaces.getMethods();
        StringBuffer proxyClassString = new StringBuffer();
        proxyClassString.append("package ")
                .append(loader.getProxyClassPackage()).append(";").append(rt)
                .append("import java.lang.reflect.Method;").append(rt)
                .append("public class $MyProxy0 implements ").append(interfaces.getName()).append("{").append(rt)
                .append("public MyInvocationHandler h;").append(rt)
                .append("public $MyProxy0(MyInvocationHandler h){").append(rt).append("this.h = h;}").append(rt)
                .append(getMethodString(methods,interfaces)).append("}");
        //写入Java文件,进行编译
        String fileName = loader.getDir()+File.separator+"$MyProxy0.java";
        File myProxyFile = new File(fileName);
        try {
            compile(proxyClassString,myProxyFile);
            //利用自定义的ClassLoader加载
            Class $myProxy0 = loader.findClass("$MyProxy0");
            //$MyProxy0初始化
            //获得构造方法
            //根据java虚拟机，每一个构造方法也相当于一个对象
            Constructor constructor = $myProxy0.getConstructor(MyInvocationHandler.class);
            //产生新对象
            Object obj = constructor.newInstance(invocationHandler);
            return obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static void compile(StringBuffer proxyClassString, File myProxyFile) throws IOException {
        FileUtils.copy(proxyClassString.toString().getBytes(),myProxyFile);
        //进行编译
        //首先获得编译器
        //compiler 为java编译器  即javac
        //获得编译器对象
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        //参数含义 （编译诊断，locale,charset）
        //管理动态生成的文件的StandardJavaFileManager对象
        StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null,null,null);
        //根据参数获取多个java文件   返回java文件对象集
        Iterable<? extends JavaFileObject> javaFileObjects =  standardJavaFileManager.getJavaFileObjects(myProxyFile);
        //“编译任务”对象
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null,standardJavaFileManager,null,null,null,javaFileObjects);
        //调用
        task.call();
        //关闭
        standardJavaFileManager.close();

    }
    private static String getMethodString(Method[] methods, Class<?> interfaces) {
        StringBuffer methodStringBuffer = new StringBuffer();
        for (Method method:methods){
            methodStringBuffer.append("public void ").append(method.getName())
                    .append("()").append("throws Throwable{ ")
                    .append("Method method1 = ").append(interfaces.getName())
                    .append(".class.getMethod(\"").append(method.getName())
                    .append("\",new Class[]{});")
                    .append("this.h.invoke(this,method1,null);}").append(rt);
        }
        return methodStringBuffer.toString();
    }
}
