package org.darcy.package1;

public class JavaSamePackage {

	/**
	 * 2.Java同一个包下访问4种不同修饰符，只有private不可见
	 */

	public static void main(String[] args) {
		JavaObject record = new JavaObject();
		System.out.println("2.Java同一个包下访问4种不同修饰符，只有private不可见");
		// System.out.println(record.private_int);
		System.out.println(record.default_int);
		System.out.println(record.protected_int);
		System.out.println(record.public_int);

	}

}
