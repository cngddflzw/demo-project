package com.zim.demo.instrument.test.reflection;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static net.bytebuddy.matcher.ElementMatchers.none;

import com.google.common.base.Splitter;
import com.sun.tools.attach.VirtualMachine;
import com.zim.demo.instrument.test.auxiliary.ThreadAdvisor;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
public class GetCallerTest {

	public static void main(String[] args) throws Exception {

		String name = ManagementFactory.getRuntimeMXBean().getName();
		String pid = Splitter.on('@').split(name).iterator().next();
		VirtualMachine vm = VirtualMachine.attach(pid);
		vm.loadAgent(GetCallerTest.class.getClassLoader().getResource("instrument-agent.jar").getPath());

		Class<?> agentClazz = Class.forName("com.zim.demo.instrument.agent.AgentMain");
		Field instField = agentClazz.getDeclaredField("inst");
		instField.setAccessible(true);
		Instrumentation inst = (Instrumentation) instField.get(agentClazz);

		File temp = null;
		try {
			temp = Files.createTempDirectory("test_dir").toFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		UsingInstrumentation
				.of(temp, Target.BOOTSTRAP, inst)
				.inject(Collections.singletonMap(
						new ForLoadedType(CallerTest.class),
						ForClassLoader.read(CallerTest.class)
				));

		ClassLoader classLoader = getExtClassLoader();
		Class<?> cls = classLoader.loadClass("com.zim.demo.instrument.test.reflection.CallerTest");
		Object o = cls.newInstance();

		Method md = cls.getDeclaredMethod("getCallerClass");
		Object invoke = md.invoke(o);
		System.out.println(invoke);

		long count = 10_000_000;

		long start = System.currentTimeMillis();
		long s = 0;
		for (int i = 0; i < count; i++) {
			s += i;
		}
		System.out.println(s);
		System.out.println("sum elp " + (System.currentTimeMillis() - start) + " ms");

		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			Integer.toString(i);
		}
		System.out.println("Integer.toString elp " + (System.currentTimeMillis() - start) + " ms");

		MySecureManager msm = new MySecureManager();
		for (int i = 0; i < 5; i++) {
			start = System.currentTimeMillis();
			for (int j = 0; j < count; j++) {
				msm.getClassContext1();
			}
			System.out.println("MSM elp " + (System.currentTimeMillis() - start) + " ms");
		}

		Method md2 = cls.getDeclaredMethod("getCallerClass", long.class);
		for (int i = 0; i < 5; i++) {
			md2.invoke(o, count);
		}

		Method md3 = cls.getDeclaredMethod("getCallerClass2", long.class);
		for (int i = 0; i < 5; i++) {
			md3.invoke(o, count);
		}

		Method md4 = cls.getDeclaredMethod("getCallerClass3", long.class);
		for (int i = 0; i < 5; i++) {
			md4.invoke(o, count);
		}

		Method md5 = cls.getDeclaredMethod("getCallerClass4", long.class);
		for (int i = 0; i < 5; i++) {
			md5.invoke(o, count);
		}
	}

	private static ClassLoader getExtClassLoader() {
		ClassLoader extClassLoader = ClassLoader.getSystemClassLoader();
		while (extClassLoader.getParent() != null) {
			extClassLoader = extClassLoader.getParent();
		}
		return extClassLoader;
	}

	private static class MySecureManager extends SecurityManager {

		Class[] getClassContext1() {
			return getClassContext();
		}
	}
}
