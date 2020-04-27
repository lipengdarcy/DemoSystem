package cn.smarthse.framework.algs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Java 常用数据结构
 * 
 */
public class JavaCollection {

	/**
	 * 1.列表
	 */
	List<Integer> arrayList = new ArrayList<Integer>();
	List<Integer> linkedList = new LinkedList<Integer>();

	/**
	 * 2.队列
	 */
	Queue<Integer> priorityQueue = new PriorityQueue<Integer>();
	Queue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(10);

	/**
	 * 3.集合
	 */
	Set<Integer> hashSet = new HashSet<Integer>();
	Set<Integer> treeSet = new TreeSet<Integer>();

	/**
	 * 4.散列表
	 */
	Map<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
	Map<Integer, Integer> treeMap = new TreeMap<Integer, Integer>();

	/**
	 * 5.堆栈，基于Vector，线程安全
	 */
	Stack<Integer> stack = new Stack<Integer>();

	public static void main(String[] args) {
		JavaCollection demo = new JavaCollection();
		demo.stack.add(1);
		demo.stack.add(5);
		demo.stack.add(9);
		System.out.println(demo.stack.pop());
		System.out.println(demo.stack.pop());
		System.out.println(demo.stack.pop());
	}

}
