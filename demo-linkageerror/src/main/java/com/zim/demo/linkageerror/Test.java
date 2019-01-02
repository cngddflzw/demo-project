package com.zim.demo.linkageerror;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhenwei.liu
 * @since 2019-01-02
 */
public class Test {

	/**
	 * A self-first delegating classloader. It only loads specified classes self-first; other
	 * classes are loaded from the parent.
	 */
	private static class CustomCL extends ClassLoader {

		private Set<String> selfFirstClasses;
		private String label;

		public CustomCL(String name, ClassLoader parent, String... selfFirsNames) {
			super(parent);
			this.label = name;
			this.selfFirstClasses = new HashSet<String>(Arrays.asList(selfFirsNames));
		}

		public Class<?> findClass(String name) throws ClassNotFoundException {
			if (selfFirstClasses.contains(name)) {
				try {
					byte[] buf = new byte[100000];
					String loc = name.replace('.', '/') + ".class";
					InputStream inp = Test.class.getClassLoader().getResourceAsStream(loc);
					int n = inp.read(buf);
					inp.close();
					System.out.println(label + ": Loading " + name + " in custom classloader");
					return defineClass(name, buf, 0, n);
				} catch (Exception e) {
					throw new ClassNotFoundException(name, e);
				}
			}

			// Is never executed in this test
			throw new ClassNotFoundException(name);
		}

		public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
			if (findLoadedClass(name) != null) {
				System.out.println(label + ": already loaded(" + name + ")");
				return findLoadedClass(name);
			}

			// Override parent-first behavior into self-first only for specified classes
			if (selfFirstClasses.contains(name)) {
				return findClass(name);
			} else {
				System.out.println(label + ": super.loadclass(" + name + ")");
				return super.loadClass(name, resolve);
			}
		}

		public String toString() {
			return label;
		}
	}


	public static class User {

	}

	public static class LoginEJB {

		static {
			System.out.println("LoginEJB loaded");
		}

		public static void login(User u) {
		}
	}

	public static class Servlet {

		public static void doGet() {
			System.out.println("doGet CL " + Thread.currentThread().getContextClassLoader());
			User u = new User();
			System.out.println("Logging in with User loaded in " + u.getClass().getClassLoader());
			LoginEJB.login(u);
		}
	}

	private static class EjbCL extends CustomCL {

		public EjbCL(ClassLoader parent, String... selfFirsNames) {
			super("Ejb", parent, selfFirsNames);
		}
	}

	private static class SfWebCL1 extends CustomCL {

		public SfWebCL1(ClassLoader parent, String... selfFirsNames) {
			super("SF111", parent, selfFirsNames);
		}
	}

	private static class SfWebCL2 extends CustomCL {

		public SfWebCL2(ClassLoader parent, String... selfFirsNames) {
			super("SF222", parent, selfFirsNames);
		}
	}

	public static void test1() throws Exception {
		CustomCL ejbCL = new EjbCL(Test.class.getClassLoader(), "com.test.zim.Demo$User",
				"com.test.zim.Demo$LoginEJB");
		CustomCL sf1 = new SfWebCL1(ejbCL, "com.test.zim.Demo$User",
				"com.test.zim.Demo$Servlet");
		CustomCL sf2 = new SfWebCL1(ejbCL, "com.test.zim.Demo$User",
				"com.test.zim.Demo$Servlet");

		System.out.println("Logging in, self-first");
		sf1.loadClass("com.test.zim.Demo$Servlet", false).getMethod("doGet").invoke(null);
//		sfWebCL.loadClass("com.test.zim.Demo$Servlet", false).getMethod("doGet");
//		sfWebCL.loadClass("com.test.zim.Demo$User", false);
//		sfWebCL.loadClass("com.test.zim.Demo$LoginEJB", false).getMethods();

		/*
		 * 出现异常:
		 * Ejb: Loading com.test.zim.Demo$User in custom classloader
		 * java.lang.LinkageError: loader constraint violation: loader (instance of com/test/zim/Demo$EjbCL) previously initiated loading for a different type with name "com/test/zim/Demo$User"
		 *
		 * 这是因为:
		 * 1. ejbCL 和 sfWebCl 加载的 LoginEJB.class 是同一个
		 * 2. 前面 efWebCl 调用 doGet 的时候触发了对 LoginEJB.login(User u) 的参数 linking, 此处的 User linking 的是 sfWebCl 加载的 User
		 * 3. 这里 ejbCL 的 getMethods() 也会触发 LoginEJB.login(User u), 这里又想将 ejbCl 的 User linking 到参数, 所以这里报错了.
		 */
		System.out.println("Examining methods of LoginEJB");
		sf2.loadClass("com.test.zim.Demo$LoginEJB", false).getMethods();
//		sf2.loadClass("com.test.zim.Demo$User", false);
//		sf2.loadClass("com.test.zim.Demo$LoginEJB", false);

//		ejbCL.loadClass("com.test.zim.Demo$User");
	}

	public static void test2() throws Exception {
		CustomCL ejbCL = new EjbCL(Test.class.getClassLoader(), "com.test.zim.Demo$User",
				"com.test.zim.Demo$LoginEJB");
		CustomCL sf1 = new SfWebCL1(ejbCL, "com.test.zim.Demo$User",
				"com.test.zim.Demo$Servlet");

		System.out.println("Logging in, self-first");
		sf1.loadClass("com.test.zim.Demo$Servlet", false).getMethod("doGet").invoke(null);

		System.out.println("Examining methods of LoginEJB");
//		ejbCL.loadClass("com.test.zim.Demo$LoginEJB", false).getMethods();
		ejbCL.loadClass("com.test.zim.Demo$User", false);
	}

	public static void main(String[] args) {
		try {
//			test1();
			test2();
		} catch (Throwable e) {
			e.printStackTrace(System.out);
		}
	}
}
