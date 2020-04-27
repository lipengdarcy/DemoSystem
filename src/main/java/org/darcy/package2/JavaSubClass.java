package org.darcy.package2;

import org.darcy.package1.JavaObject;

public class JavaSubClass extends JavaObject {

	/**
	 * 3.Java子类下访问4种不同修饰符，跟父类不同包，只有public、protected_int可见
	 */

	public static void main(String[] args) {
		JavaSubClass record = new JavaSubClass();
		System.out.println("3.Java子类下访问4种不同修饰符，跟父类不同包，只有public、protected_int可见");
		// System.out.println(record.private_int);
		// System.out.println(record.default_int);
		System.out.println(record.protected_int);
		System.out.println(record.public_int);

	}

}
