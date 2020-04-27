package org.darcy.package2;

import org.darcy.package1.JavaObject;

public class JavaDiffPackage {

	/**
	 * 4.Java不同包下访问4种不同修饰符，只有public可见
	 */

	public static void main(String[] args) {
		JavaObject record = new JavaObject();
		System.out.println("4.Java不同包下访问4种不同修饰符，只有public可见");
		// System.out.println(record.private_int);
		// System.out.println(record.default_int);
		// System.out.println(record.protected_int);
		System.out.println(record.public_int);

	}

}
