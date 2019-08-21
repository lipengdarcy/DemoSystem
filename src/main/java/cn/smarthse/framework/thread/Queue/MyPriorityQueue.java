package cn.smarthse.framework.thread.Queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * PriorityQueue是一种无界的，线程不安全的，并拥有优先级的队列
 */
public class MyPriorityQueue {
	public static void main(String[] args) {
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>(5, new NumComparator());
		queue.add(5);
		queue.add(1);
		queue.add(4);
		queue.add(4);
		queue.add(3);
		Arrays.asList();
		while (queue.size() > 0) {
			System.out.println(queue.remove());
		}
	}

	static class NumComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			if (o1 > o2) {
				return 1;
			} else if (o1 < o2) {
				return -1;
			} else
				return 0;
		}
	}
}
