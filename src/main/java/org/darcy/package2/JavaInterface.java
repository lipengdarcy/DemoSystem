package org.darcy.package2;

/**
 * Java接口
 */
public interface JavaInterface {

	/**
	 * 接口可以有变量，但必须是public static final的，没有修饰符等价于public static final
	 */
	public static final String public_static_final_string = "public static final 变量";

	public String public_string = "public 变量";

	static String static_string = "static 变量";

	final String final_string = "final 变量";

	String default_string = "没有修饰符等价于public static final 变量";

	/**
	 * 接口可以有静态方法和抽象类重名，则调用抽象类的
	 */
	public static void staticMethod() {
		System.out.println("接口可以有静态方法和抽象类重名，则调用抽象类的");
	}
	
	/**
	 * 接口可以有静态方法和实现，其他的只能申明不能实现
	 */
	public static void interfaceStaticMethod() {
		System.out.println("接口可以有静态方法和实现，其他的只能申明不能实现");
	}

	/**
	 * 接口方法是public的，只申明不实现
	 */
	public void commonMethod1();

	/**
	 * 接口方法默认是public的
	 */
	void commonMethod2();

	public static void main(String[] args) {
		System.out.println("接口不能实例化，因此内部只能调用静态方法");
		System.out.println(public_string);
		System.out.println(default_string);
		interfaceStaticMethod();
	}

}
