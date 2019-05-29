package com.zim.demo.instrument.test;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.none;

import com.google.common.base.Splitter;
import com.sun.tools.attach.VirtualMachine;
import com.zim.demo.instrument.test.auxiliary.ThreadAdvisor;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
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
 * @author zhenwei.liu
 * @since 2019-01-25
 */
public class ThreadInstrumentTest {

	public static void main(String[] args) throws Exception {

		System.out.println("#### 1 " + Thread.currentThread().getContextClassLoader());

		Instrumentation inst = InstrumentUtils.getInst();

		File temp = null;
		try {
			temp = Files.createTempDirectory("test_dir").toFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		UsingInstrumentation
				.of(temp, Target.BOOTSTRAP, inst)
				.inject(Collections.singletonMap(
						new ForLoadedType(ThreadAdvisor.class),
						ForClassLoader.read(ThreadAdvisor.class)
				));

		new AgentBuilder.Default()
				.with(StreamWriting.toSystemOut().withErrorsOnly())
				.with(StreamWriting.toSystemOut().withTransformationsOnly())
//					.with(RedefinitionStrategy.REDEFINITION)
				.with(RedefinitionStrategy.RETRANSFORMATION)
				.with(Default.REDEFINE)
				.ignore(none())
				.enableBootstrapInjection(inst, temp)
				.disableClassFormatChanges()
//				.type(is(Thread.class))
				.type(named("java.lang.Thread"))
				.transform((builder, typeDescription, classLoader, module) ->
						builder.visit(Advice.to(ThreadAdvisor.class)
								.on(ElementMatchers.named("getContextClassLoader"))))
				.installOn(inst);

		// this code would print "test hacked" instrumented by ThreadAdvisor.class
		// while failed in Debug mode
		System.out.println("### 2 " + Thread.currentThread().getContextClassLoader());

		System.out.println(new Thread().getContextClassLoader());
	}

}
