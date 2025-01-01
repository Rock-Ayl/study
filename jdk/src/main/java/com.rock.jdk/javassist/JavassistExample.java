package com.rock.jdk.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @Author ayl
 * @Date 2025-01-01
 */
public class JavassistExample {

    public static void main(String[] args) throws Exception {

        /**
         * 将类加载到内存中
         */

        //创建一个 ClassPool
        ClassPool pool = ClassPool.getDefault();
        //创建一个新的类
        CtClass cc = pool.makeClass("com.rock.jdk.javassist.out.MyDynamicClass");
        //创建一个新的方法
        CtMethod method = CtMethod.make("public String sayHello(String name) { return \"Hello, \" + name;}", cc);
        //将方法添加到类中
        cc.addMethod(method);

        /**
         * 如果有需要,将类写入指定目录
         * cc.writeFile("javassist/out");
         */

        /**
         * 反射出对应实例、调用方法
         */

        //加载类
        Class<?> clazz = cc.toClass();
        //反射-初始化势力
        Object instance = clazz.getDeclaredConstructor().newInstance();
        //反射-调用实例方法
        String result = (String) clazz
                //指定方法、参数类型
                .getMethod("sayHello", String.class)
                //调用方法、传入参数
                .invoke(instance, "World");

        //输出 "Hello, World"
        System.out.println(result);
    }

}