package test.leetcode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 字符 数值
 * 
 * I 1
 * 
 * V 5
 * 
 * X 10
 * 
 * L 50
 * 
 * C 100
 * 
 * D 500
 * 
 * M 1000
 * 
 * 给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。
 * 
 * 
 */
public class no13 {

	public static void main(String[] args) {
		String s = "MCMXCIV";
		System.out.println(romanToInt(s));
	}

	public static int romanToInt(String s) {
		int result = 0;
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			char next = '0';
			if (i < s.length() - 1)
				next = s.charAt(i + 1);
			switch (ch) {
			case 'M':
				result += 1000;
				break;
			case 'D':
				result += 500;
				break;
			case 'C':
				if (next == 'D' || next == 'M')
					result -= 200;
				result += 100;
				break;
			case 'L':
				result += 50;
				break;
			case 'X':
				if (next == 'L' || next == 'C')
					result -= 20;
				result += 10;
				break;
			case 'V':
				result += 5;
				break;
			case 'I':
				if (next == 'V' || next == 'X')
					result -= 2;
				result += 1;
				break;
			default:
				break;
			}
		}
		return result;
	}

}
