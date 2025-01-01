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

        // 创建一个 ClassPool
        ClassPool pool = ClassPool.getDefault();

        // 创建一个新的类
        CtClass cc = pool.makeClass("com.example.MyDynamicClass");

        // 创建一个新的方法
        CtMethod method = CtMethod.make(
                "public String sayHello(String name) { " +
                        "return \"Hello, \" + name;" +
                        "}", cc);

        // 将方法添加到类中
        cc.addMethod(method);

        // 将类写入文件
        cc.writeFile("path/to/output/directory"); // 指定输出目录

        // 加载并实例化动态生成的类
        Class<?> dynamicClass = cc.toClass();
        Object instance = dynamicClass.getDeclaredConstructor().newInstance();

        // 调用动态方法
        String result = (String) dynamicClass.getMethod("sayHello", String.class)
                .invoke(instance, "World");

        // 输出 "Hello, World"
        System.out.println(result);
    }

}