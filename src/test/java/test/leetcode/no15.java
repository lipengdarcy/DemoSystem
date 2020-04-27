package test.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0
 * ？找出所有满足条件且不重复的三元组。
 * 
 * 
 * 
 */
public class no15 {

	public static void main(String[] args) {

		int[] num = { -1, 0, 1, 2, -1, -4 };
		List<List<Integer>> result = threeSum(num);
		System.out.println(result);
	}

	public static List<List<Integer>> threeSum(int[] nums) {
		int[][] tmp = new int[nums.length][nums.length];
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		for (int i = 0; i < nums.length; i++) {
			int a = nums[i];
			for (int j = i + 1; j < nums.length; j++) {
				if(i==j)
					continue;
				int b = nums[j];
				tmp[i][j] = 0 - a - b;
			}
		}
		for (int i = 0; i < nums.length; i++) {
			int a = nums[i];
			for (int j = i + 1; j < nums.length; j++) {
				if (a == tmp[i][j]) {
					List<Integer> node = Arrays.asList(a, nums[i], nums[j]);
					result.add(node);
				}
			}
		}
		return result;

	}
}
