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
public class no14 {

	public static void main(String[] args) {
		String s = "MCMXCIV";
		// System.out.println(romanToInt(s));

		int num = 1994;
		System.out.println(intToRoman(num));
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

	/**
	 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
	 */
	public static String intToRoman(int num) {
		StringBuffer s = new StringBuffer();
		Map<Integer, String> map = new TreeMap<Integer, String>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}

		});
		map.put(1000, "M");
		map.put(900, "CM");
		map.put(500, "D");
		map.put(400, "CD");
		map.put(100, "C");
		map.put(90, "XC");
		map.put(50, "L");
		map.put(40, "XL");
		map.put(10, "X");
		map.put(9, "IX");
		map.put(5, "V");
		map.put(4, "IV");
		map.put(1, "I");

		int ratio = 0;
		int ret = num;
		for (Integer key : map.keySet()) {
			ratio = ret / key;
			ret = ret % key;
			for (int i = 0; i < ratio; i++) {
				s.append(map.get(key));
			}
		}

		return s.toString();
	}
}
