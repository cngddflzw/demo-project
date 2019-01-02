package com.zim.demo.instrument.test.model;

/**
 * @author zhenwei.liu
 * @since 2018-12-29
 */
public class Foo {

	// 这个变量用来测试 class reload 以后实例的变量值是否还保持
	// 事实证明 static 和非 static 都可以保留
	private static int staticCount = 0;
	private int counter = 0;

	public String test(String name) {
		counter += 10;
		staticCount += 10;
		System.out.println("static count: " + staticCount + " | counter " + counter);
		return "Hello " + name;
	}
}
