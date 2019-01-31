package com.zim.demo.instrument.test.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;

/**
 * @author zhenwei.liu
 * @since 2019-01-29
 */
public class HelloInterceptor {

	public static String hello(String name, @This Object thiz) {
//	public static String hello(String name) {
		try {
			System.out.println(thiz);
			System.out.println(thiz.getClass());
			Field df = thiz.getClass().getDeclaredField("delegate");
			df.setAccessible(true);
			Object delegate = df.get(thiz);
			System.out.println(delegate);
			Method mf = delegate.getClass().getDeclaredMethod("hello", String.class);
			mf.setAccessible(true);
			return (String) mf.invoke(delegate, name);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
//		return "hello " + name;
	}
}
