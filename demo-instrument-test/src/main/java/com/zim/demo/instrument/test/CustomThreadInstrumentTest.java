package com.zim.demo.instrument.test;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static net.bytebuddy.matcher.ElementMatchers.isSubTypeOf;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.none;

import com.zim.demo.instrument.test.model.Bar;
import com.zim.demo.instrument.test.model.Foo;
import java.lang.instrument.Instrumentation;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.Listener.StreamWriting;
import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy;
import net.bytebuddy.agent.builder.AgentBuilder.TypeStrategy.Default;
import net.bytebuddy.implementation.FixedValue;

/**
 * @author zhenwei.liu
 * @since 2019-01-25
 */
public class CustomThreadInstrumentTest {

	public static void main(String[] args) throws Exception {

		Instrumentation inst = InstrumentUtils.getInst();

		new AgentBuilder.Default()
				.with(StreamWriting.toSystemOut().withErrorsOnly())
				.with(StreamWriting.toSystemOut().withTransformationsOnly())
//					.with(RedefinitionStrategy.REDEFINITION)
				.with(RedefinitionStrategy.RETRANSFORMATION)
				.with(Default.REDEFINE)
				.ignore(none())
				.disableClassFormatChanges()
//				.type(is(Thread.class))
				.type(isSubTypeOf(Foo.class).or(is(String.class)))
				.transform((builder, typeDescription, classLoader, module) -> builder.method(named("test"))
						.intercept(FixedValue.value("abc")))
				.installOn(inst);

		System.out.println("---------- start");
		Bar bar = new Bar();
		System.out.println(bar.test("gggg"));

	}

}
