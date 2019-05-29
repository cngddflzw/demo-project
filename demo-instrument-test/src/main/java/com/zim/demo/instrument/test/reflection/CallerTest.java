package com.zim.demo.instrument.test.reflection;

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

/**
 * @author zhenwei.liu
 * @since 2019-02-13
 */
public class CallerTest {

	@CallerSensitive
	public Class<?> getCallerClass() {
		return Reflection.getCallerClass();
	}

	@CallerSensitive
	public void getCallerClass(long count) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			Reflection.getCallerClass();
		}
		System.out.println(String.format("getCallerClass %s elapse %s ms", count, System.currentTimeMillis() - start));
	}

	public void getCallerClass2(long count) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			Reflection.getCallerClass(0);
		}
		System.out.println(String.format("getCallerClass2 %s elapse %s ms", count, System.currentTimeMillis() - start));
	}

	public void getCallerClass3(long count) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			Reflection.getCallerClass(1);
		}
		System.out.println(String.format("getCallerClass3 %s elapse %s ms", count, System.currentTimeMillis() - start));
	}

	public void getCallerClass4(long count) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			Reflection.getCallerClass(2);
		}
		System.out.println(String.format("getCallerClass4 %s elapse %s ms", count, System.currentTimeMillis() - start));
	}
}
