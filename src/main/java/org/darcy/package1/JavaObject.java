package org.darcy.package1;

/**
 * Java访问4种不同修饰符的方法
 */
public class JavaObject {

	private int private_int = 0x7FFFFFFF;

	int default_int = 2;

	protected int protected_int = 3;

	public int public_int = 4;

	/**
	 * 1.Java类内部，可以访问4种不同修饰符
	 */

	public static void main(String[] args) {
		JavaObject record = new JavaObject();
		System.out.println("1.Java类内部，可以访问4种不同修饰符");
		System.out.println(Integer.MAX_VALUE);
		System.out.println(0x80000000);
		
		System.out.println(record.private_int);
		System.out.println(record.default_int);
		System.out.println(record.protected_int);
		System.out.println(record.public_int);
	}

}
