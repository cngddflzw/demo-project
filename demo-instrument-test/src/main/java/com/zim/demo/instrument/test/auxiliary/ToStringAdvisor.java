package com.zim.demo.instrument.test.auxiliary;

import static net.bytebuddy.implementation.bytecode.assign.Assigner.Typing.DYNAMIC;

import net.bytebuddy.asm.Advice;

/**
 * @author zhenwei.liu
 * @since 2018-12-29
 */
public class ToStringAdvisor {

//	@Advice.OnMethodExit
//	public static void replaceValue(@Advice.Return(readOnly = false, typing = DYNAMIC) Object returned) {
//		System.out.println("hhhhhhhhhh");
//		returned = "hacked value";
//	}

	@Advice.OnMethodEnter
	public static void replaceValue() {
		System.out.println("hhhhhhhhhh");
//		returned = "hacked value";
	}
}
