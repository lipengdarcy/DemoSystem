package org.darcy.package1;

/**
 * Java抽象类
 */
public abstract class JavaAbstractClass {

	private int private_int = 1;

	int default_int = 2;

	protected int protected_int = 3;

	public int public_int = 4;

	/**
	 * 抽象类可以有静态方法
	 */
	public static void staticMethod() {
		System.out.println("抽象类可以有静态方法");
	}

	/**
	 * 抽象类可以有普通方法
	 */
	public void commonMethod() {
		System.out.println("抽象类可以有普通方法");
	}

	/**
	 * 抽象类可以有抽象方法，但不能有实现，由子类实现
	 */
	public abstract void abstractMethod();

	public static void main(String[] args) {
		// 抽象类不能实例化
		// JavaAbstractClass record = new JavaAbstractClass();
		System.out.println("抽象类不能实例化，因此内部只能调用静态方法");
		staticMethod();
	}

}
