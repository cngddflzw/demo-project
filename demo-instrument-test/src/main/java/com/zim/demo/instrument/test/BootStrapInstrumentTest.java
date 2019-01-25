package com.zim.demo.instrument.test;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static net.bytebuddy.matcher.ElementMatchers.none;

import com.zim.demo.instrument.client.InstrumentTask;
import com.zim.demo.instrument.client.Instruments;
import com.zim.demo.instrument.test.auxiliary.ToStringAdvisor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.Listener.StreamWriting;
import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy;
import net.bytebuddy.agent.builder.AgentBuilder.TypeStrategy.Default;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription.ForLoadedType;
import net.bytebuddy.dynamic.ClassFileLocator.ForClassLoader;
import net.bytebuddy.dynamic.loading.ClassInjector.UsingInstrumentation;
import net.bytebuddy.dynamic.loading.ClassInjector.UsingInstrumentation.Target;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * 测试动态修改 java.lang 包的类
 *
 * @author zhenwei.liu
 * @since 2018-12-29
 */
public class BootStrapInstrumentTest {

	public static void main(String[] args) throws Exception {

		Class<BootStrapInstrumentTest> clz = BootStrapInstrumentTest.class;
		System.out.println(clz.toString());

		InstrumentTask task = inst -> {
			File temp = null;
			try {
				temp = Files.createTempDirectory("test_dir").toFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			UsingInstrumentation
					.of(temp, Target.BOOTSTRAP, inst)
					.inject(Collections.singletonMap(
							new ForLoadedType(ToStringAdvisor.class),
							ForClassLoader.read(ToStringAdvisor.class)
					));

			new AgentBuilder.Default()
					.with(StreamWriting.toSystemOut().withErrorsOnly())
					.with(StreamWriting.toSystemOut().withTransformationsOnly())
					.with(RedefinitionStrategy.REDEFINITION)
					.with(Default.REDEFINE)
					.ignore(none())
					// 由于 bootstrap classloader 没有 ToStringAdvisor, 所以要将 Advisor 类注入进去
					.enableBootstrapInjection(inst, temp)
					.disableClassFormatChanges()
					.type(is(Class.class))
					.transform((builder, typeDescription, classLoader, module) ->
							builder.visit(Advice.to(ToStringAdvisor.class)
									.on(ElementMatchers.named("toString"))))
					.installOn(inst);
		};

		Instruments.start(task);

		System.out.println(clz.toString());
	}
}