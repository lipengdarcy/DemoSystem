package org.darcy.package1;

import org.darcy.package2.JavaInterface;

/**
 * Java普通类，继承抽象类，实现接口
 */
public class JavaCommonClass extends JavaAbstractClass implements JavaInterface{

	public static void main(String[] args) {
		JavaCommonClass record = new JavaCommonClass();
		System.out.println("抽象类可以有变量，子类可以访问（private不行），default_int=" + record.default_int);
		record.abstractMethod();
		record.commonMethod();
		
		System.out.println("接口可以有变量，子类可以访问，default_string=" + JavaInterface.default_string);
		record.commonMethod1();
		record.commonMethod2();
		
		staticMethod();//等价于JavaAbstractClass.staticMethod
		JavaInterface.interfaceStaticMethod();//JavaInterface不可省略
		
	}

	@Override
	public void abstractMethod() {
		System.out.println("抽象类可以有抽象方法，但不能有实现，由子类实现");
	}

	@Override
	public void commonMethod1() {
		System.out.println("实现接口的commonMethod1");
	}

	@Override
	public void commonMethod2() {
		System.out.println("实现接口的commonMethod2");	
	}

}
