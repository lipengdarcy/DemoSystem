package test.leetcode;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照逆序的方式存储的，
 * 
 * 并且它们的每个节点只能存储一位数字。
 * 
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 
 * 示例：
 * 
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4) 
 * 
 * 输出：7 -> 0 -> 8 原因：342 + 465 = 807
 * 
 * 
 */
public class no2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ListNode l1 = new ListNode(2);
		l1.next = new ListNode(4);
		l1.next.next = new ListNode(3);
		ListNode l2 = new ListNode(5);
		l2.next = new ListNode(6);
		l2.next.next = new ListNode(4);

		ListNode result = addTwoNumbers(l1, l2);
		System.out.println(result.val);

		ListNode l3 = new ListNode(5);
		ListNode l4 = new ListNode(5);
		ListNode result2 = addTwoNumbers(l3, l4);
		System.out.println(result2.val);

	}

	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode result = new ListNode(0);
		ListNode node1 = l1, node2 = l2, node3 = result;
		int exp = 0;// 进位
		while (true) {
			if (node1 == null && node2 == null && exp == 0) {
				break;
			}
			int val = exp + (node1 == null ? 0 : node1.val) + (node2 == null ? 0 : node2.val);
			if (exp > 0) {
				exp--;
			}
			if (val > 9) {
				node3.val = val - 10;
				exp++;
			} else {
				node3.val = val;
			}
			node1 = node1 == null ? null : node1.next;
			node2 = node2 == null ? null : node2.next;
			if (node1 == null && node2 == null && exp == 0) {
				node3.next = null;
			} else {
				node3.next = new ListNode(0);
			}
			node3 = node3.next;
		}
		return result;
	}

}

class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
	}
}
