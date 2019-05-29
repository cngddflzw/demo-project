package com.zim.demo.instrument.test;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.none;

import com.zim.demo.instrument.test.model.EmptyService;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.Listener.StreamWriting;
import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy;
import net.bytebuddy.agent.builder.AgentBuilder.TypeStrategy.Default;

/**
 * @author zhenwei.liu
 * @since 2019-02-21
 */
public class DynamicAddInterfaceTest {

	public static void main(String[] args) throws Exception {

		Instrumentation inst = InstrumentUtils.getInst();

		String className = "com.zim.demo.instrument.test.model.DefaultService";
		new AgentBuilder.Default()
				.with(StreamWriting.toSystemOut().withErrorsOnly())
				.with(StreamWriting.toSystemOut().withTransformationsOnly())
//					.with(RedefinitionStrategy.REDEFINITION)
				.with(RedefinitionStrategy.RETRANSFORMATION)
				.with(Default.REDEFINE)
				.ignore(none())
				.disableClassFormatChanges()
//				.type(is(Thread.class))
				.type(named(className))
				.transform((builder, typeDescription, classLoader, module) -> builder.defineField("test", Integer.class, Modifier.PUBLIC)
						.implement(EmptyService.class)
						)
				.installOn(inst);

		Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
		Field test = clazz.getField("test");
		System.out.println(test);
		System.out.println(Arrays.toString(clazz.getInterfaces()));
	}
}
