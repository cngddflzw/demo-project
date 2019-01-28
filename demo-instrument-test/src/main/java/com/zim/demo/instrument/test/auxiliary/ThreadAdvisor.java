package com.zim.demo.instrument.test.auxiliary;

import net.bytebuddy.asm.Advice;

/**
 * @author zhenwei.liu
 * @since 2019-01-25
 */
public class ThreadAdvisor {

	public static String name = "abc";

	@Advice.OnMethodEnter
	public static void replaceValue() {
		System.out.println("test hacked " + name);
	}
}
