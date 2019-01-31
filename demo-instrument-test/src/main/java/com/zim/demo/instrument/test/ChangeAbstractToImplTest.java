package com.zim.demo.instrument.test;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.isDefaultConstructor;
import static net.bytebuddy.matcher.ElementMatchers.named;

import com.zim.demo.instrument.client.Instruments;
import com.zim.demo.instrument.test.model.Delegate;
import com.zim.demo.instrument.test.model.HelloInterceptor;
import com.zim.demo.instrument.test.model.Model;
import java.lang.reflect.Modifier;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.Identified.Extendable;
import net.bytebuddy.agent.builder.AgentBuilder.Listener.StreamWriting;
import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy;
import net.bytebuddy.agent.builder.AgentBuilder.Transformer;
import net.bytebuddy.agent.builder.AgentBuilder.TypeStrategy.Default;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.utility.JavaModule;

/**
 * @author zhenwei.liu
 * @since 2019-01-29
 */
public class ChangeAbstractToImplTest {

	public static void main(String[] args) throws Exception {
		String clazzName = "com.zim.demo.instrument.test.model.AbstractModel";
		final Extendable extendable = new AgentBuilder.Default()
				.with(StreamWriting.toSystemOut().withErrorsOnly())
				.with(StreamWriting.toSystemOut().withTransformationsOnly())
				.with(Default.REDEFINE) // 要 runtime reload 必须使用 redefine, 因为 rebase 会生成新方法
				.with(RedefinitionStrategy.REDEFINITION) // redifine 和 retransform 貌似没什么区别
				.type(named(clazzName))
				.transform(new Transformer() {
					public Builder<?> transform(Builder<?> builder, TypeDescription typeDescription,
							ClassLoader classLoader, JavaModule module) {
						return builder
								.modifiers(Modifier.PUBLIC)
								.defineField("delegate", Delegate.class, Modifier.PRIVATE)
								.defineMethod("hello", String.class, Modifier.PUBLIC)
								.withParameters(String.class)
								.intercept(MethodDelegation.to(HelloInterceptor.class))
								.constructor(any())
								.intercept(
										MethodCall.invoke(isDefaultConstructor())
												.andThen(FieldAccessor.ofField("delegate").setsValue(new Delegate()))
								);
					}
				});

		Instruments.start(extendable::installOn);

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Class<?> clazz = cl.loadClass(clazzName);
		Model dynamic = (Model) clazz.newInstance();
		System.out.println(dynamic.hello("zim"));
	}
}
