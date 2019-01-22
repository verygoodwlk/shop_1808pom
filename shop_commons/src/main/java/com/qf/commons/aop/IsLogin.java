package com.qf.commons.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 元注解：注解注解的注解
 * @Retention: 表示当前注解的有效范围
 *  RetentionPolicy.SOURCE 表示当前注解的有效范围在源文件中，编译后丢失
 *  RetentionPolicy.CLASS 表示当前注解有效范围在编译文件中，运行时丢失
 *  RetentionPolicy.RUNTIME 表示当前注解有效范围在运行时，如果你的注解需要被反射操作，则必须选择这个范围
 *
 * @Target:表示当前注解的可标记位置
 *  ElementType.ANNOTATION_TYPE 表示当前注解可以标记其他的注解
 *  ElementType.CONSTRUCTOR 表示当前注解可以标记构造方法
 *  ElementType.FIELD 表示当前注解标记属性（成员变量、全局变量）
 *  ElementType.LOCAL_VARIABLE 表示当前注解可以标记局部变量
 *  ElementType.METHOD 方法
 *  ElementType.PACKAGE 包
 *  ElementType.PARAMETER 方法形参
 *  ElementType.TYPE 类、接口、枚举
 *
 *
 * @Author ken
 * @Date 2019/1/22
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IsLogin {

    boolean tologin() default false;
}
