package test.leetcode;

/**
 * 给定一个未排序的整数数组，找出其中没有出现的最小的正整数。
 * 
 * 示例 1:
 * 
 * 输入: [1,2,0] 输出: 3
 * 
 * 示例 2:
 * 
 * 输入: [3,4,-1,1] 输出: 2
 * 
 * 示例 3:
 * 
 * 输入: [7,8,9,11,12] 输出: 1
 * 
 * 
 */
public class no41 {

	public static void main(String[] args) {
		int[] nums1 = { 1, 2, 0 };
		int[] nums2 = { 3, 4, -1, 1 };
		int[] nums3 = { 7, 8, 9, 11, 12 };
		int[] nums4 = { 8, 7, 6, 5, 4, 3, 2, 1 };
		System.out.println(firstMissingPositive(nums1));
		System.out.println(firstMissingPositive(nums2));
		System.out.println(firstMissingPositive(nums3));
		System.out.println(firstMissingPositive_bak(nums4));
	}

	public static int firstMissingPositive_bak(int[] nums) {
		int v = 1;
		for (int i = 0; i < nums.length; i++) {
			// 负数和0跳过
			if (v > nums[i]) {
				continue;
			}
			if (v == nums[i]) {
				v++;
			}
		}
		for (int i = 0; i < nums.length; i++) {
			if (v == nums[i]) {
				v++;
			}
		}
		return v;
	}

	public static int firstMissingPositive(int[] nums) {
		int n = nums.length;

		// 基本情况
		int contains = 0;
		for (int i = 0; i < n; i++)
			if (nums[i] == 1) {
				contains++;
				break;
			}

		if (contains == 0)
			return 1;

		// nums = [1]
		if (n == 1)
			return 2;

		// 用 1 替换负数，0，
		// 和大于 n 的数
		// 在转换以后，nums 只会包含
		// 正数
		for (int i = 0; i < n; i++)
			if ((nums[i] <= 0) || (nums[i] > n))
				nums[i] = 1;

		// 使用索引和数字符号作为检查器
		// 例如，如果 nums[1] 是负数表示在数组中出现了数字 `1`
		// 如果 nums[2] 是正数 表示数字 2 没有出现
		for (int i = 0; i < n; i++) {
			int a = Math.abs(nums[i]);
			// 如果发现了一个数字 a - 改变第 a 个元素的符号
			// 注意重复元素只需操作一次
			if (a == n)
				nums[0] = -Math.abs(nums[0]);
			else
				nums[a] = -Math.abs(nums[a]);
		}

		// 现在第一个正数的下标
		// 就是第一个缺失的数
		for (int i = 1; i < n; i++) {
			if (nums[i] > 0)
				return i;
		}

		if (nums[0] > 0)
			return n;

		return n + 1;
	}

}
