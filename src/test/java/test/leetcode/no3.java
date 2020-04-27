package test.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * 
 * 示例 1:
 * 
 * 输入: "abcabcbb" 输出: 3
 * 
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 
 * 
 */
public class no3 {

	public static void main(String[] args) {
		String s = "dvdf";
		System.out.println(lengthOfLongestSubstring(s));
	}

	public static int lengthOfLongestSubstring(String s) {
		int max = 0, length = 0;
		Set<Character> set = new HashSet<Character>();
		for (int i = 0; i < s.length(); i++) {
			Character c = s.charAt(i);
			if (set.contains(c)) {
				//重新计算
				set.clear();
				for (int j = 0; j < i; j++) {
					set.add(s.charAt(i));
				}
			}
			set.add(c);
			length = set.size();
			if (max < length) {
				max = length;
			}
		}
		return max;
	}

}
