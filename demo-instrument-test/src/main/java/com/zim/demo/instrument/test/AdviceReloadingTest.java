package com.zim.demo.instrument.test;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static net.bytebuddy.matcher.ElementMatchers.none;

import com.zim.demo.instrument.client.InstrumentTask;
import com.zim.demo.instrument.client.Instruments;
import com.zim.demo.instrument.test.auxiliary.ValueReplaceAdvisor;
import com.zim.demo.instrument.test.model.Foo;
import java.lang.instrument.Instrumentation;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.Identified.Extendable;
import net.bytebuddy.agent.builder.AgentBuilder.Listener.StreamWriting;
import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy;
import net.bytebuddy.agent.builder.AgentBuilder.Transformer;
import net.bytebuddy.agent.builder.AgentBuilder.TypeStrategy.Default;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

/**
 * 使用 Advice 实现动态 reload
 *
 * @author zhenwei.liu
 * @since 2018-12-29
 */
public class AdviceReloadingTest {

	public static void main(String[] args) {

		// 使用原始输出
		Foo foo = new Foo();
		System.out.println(foo.test("abc"));

		final Extendable extendable = new AgentBuilder.Default()
				.with(StreamWriting.toSystemOut().withErrorsOnly())
				.with(StreamWriting.toSystemOut().withTransformationsOnly())
				.with(Default.REDEFINE) // 要 runtime reload 必须使用 redefine, 因为 rebase 会生成新方法
				.with(RedefinitionStrategy.REDEFINITION) // redifine 和 retransform 貌似没什么区别
				.disableClassFormatChanges() // 要动态 reload 一定要开, 否则 ByteBuddy 会加一段初始化的 static 代码
				.ignore(none()) // 这里其实可以不开, ByteBuddy 默认会忽略掉 bootstrap 的类, 设置 none() 则所有类都可以被扫描
				.type(is(Foo.class))
				.transform(new Transformer() {
					public Builder<?> transform(Builder<?> builder, TypeDescription typeDescription,
							ClassLoader classLoader, JavaModule module) {
						return builder.visit(Advice.to(ValueReplaceAdvisor.class).on(ElementMatchers.named("test")));
					}
				});

		Instruments.start(new InstrumentTask() {
			public void run(Instrumentation instrumentation) {
				extendable.installOn(instrumentation);
			}
		});

		// 使用 advisor 后的输出
		System.out.println(foo.test("abc"));
	}
}
