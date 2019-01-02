package com.zim.demo.instrument.test.auxiliary;

import static net.bytebuddy.implementation.bytecode.assign.Assigner.Typing.DYNAMIC;

import net.bytebuddy.asm.Advice;

/**
 * <pre>
 * 使用 Advice 的优点是 advice 不需要生成新方法和变量
 * 这样可以不违背 HotSwap 的规则, 即已经 loaded 的 class 在 reload 的时候
 * 不能增加新的成员和方法及 static 块
 *
 * 此处使用 Advice 实现对方法逻辑的值替换
 * </pre>
 *
 * @author zhenwei.liu
 * @since 2018-12-29
 */
public class ValueReplaceAdvisor {

	/**
	 * 方法执行前的预处理
	 *
 	 * @param name 原方法参数
	 * @return enter 返回值, 如果返回 null, 则会跳过原方法执行
	 */
	@Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
	public static Object shouldReplaceValue(@Advice.Argument(0) String name) {
		if (name.equals("abc")) {
			return "replace value"; // 返回不为 null 时跳过方法执行
		} else {
			return null;
		}
	}

	@Advice.OnMethodExit
	public static Object replaceValue(
			@Advice.Return(readOnly = false, typing = DYNAMIC) Object returned,
			@Advice.Enter Object enter) {
		if (enter != null) {
			returned = enter;
		}
		return returned;
	}
}
