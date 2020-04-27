package test.leetcode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 将非负整数转换为其对应的英文表示。可以保证给定输入小于 231 - 1 。
 * 
 * 输入: 123
 * 
 * 输出: "One Hundred Twenty Three"
 * 
 * 输入: 1234567891
 * 
 * 输出: "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven
 * Thousand Eight Hundred Ninety One"
 * 
 * 
 */
public class no273 {

	public static void main(String[] args) {
		String num = "0";
		System.out.println(num + ": " + numberToWords(num));
		num = "123";
		System.out.println(num + ": " + numberToWords(num));
		num = "12345";
		// System.out.println(num + ": " + numberToWords(num));
		num = "1234567891";
		// System.out.println(num + ": " + numberToWords(num));
		num = "50868";
		// System.out.println(num + ": " + numberToWords(num));
		num = "1234567891234567891234567891";
		System.out.println(num + ": " + numberToWords(num));

		num = "1000000000"; // One Billion
		System.out.println(num + ": " + numberToWords(num));
		num = "1000000000000"; // One Thousand Billion
		System.out.println(num + ": " + numberToWords(num));
		num = "1000000000000000"; // One Million Billion
		System.out.println(num + ": " + numberToWords(num));
		num = "1000000000000000000"; // One Billion Billion
		System.out.println(num + ": " + numberToWords(num));
		num = "1222333444500600750842"; // One Thousand Billion Billion
		System.out.println(num + ": " + numberToWords(num));

	}

	public static String getNumLess1000(int num) {
		if (num == 0)
			return "Zero";
		if (num < 0)
			return "Negative";

		String[] to19 = "One Two Three Four Five Six Seven Eight Nine Ten Eleven Twelve Thirteen Fourteen Fifteen Sixteen Seventeen Eighteen Nineteen"
				.split(" ");
		String[] tens = "Twenty Thirty Forty Fifty Sixty Seventy Eighty Ninety".split(" ");

		StringBuffer s = new StringBuffer();
		int h = num / 100;
		if (h > 0) {
			s.append(to19[h - 1] + " Hundred ");
		}
		int t = num % 100;
		if (t == 0) {
			s.deleteCharAt(s.length() - 1);
			return s.toString();
		}
		if (t >= 20) {
			t = num % 100 - num % 10;
			s.append(tens[t / 10 - 2]);
			if (num % 10 > 0) {
				s.append(" " + to19[num % 10 - 1]);
			}
		} else {
			s.append(to19[num - 1]);
		}
		return s.toString();
	}

	/**
	 * 支持无限位整数，如：1000位
	 */
	public static String numberToWords(String sNum) {
		String[] q = { "Billion", "Million", "Thousand" };
		StringBuffer s = new StringBuffer();
		int count = sNum.length();
		int ret = count % 3;
		if (ret == 0 && count >= 3) {
			ret = 3;
		}
		String header = sNum.substring(0, ret);
		List<Integer> list = new ArrayList<Integer>();
		list.add(Integer.parseInt(header));
		for (int i = ret; i < count;) {
			String tmp = sNum.substring(i, i + 3);
			list.add(Integer.parseInt(tmp));
			i = i + 3;
		}

		int index = 2;
		for (int i = list.size() - 1; i >= 0; i--) {
			int k = list.get(i);
			if (i == list.size() - 1) {
				s.insert(0, getNumLess1000(k) + " ");
			} else {
				s.insert(0, getNumLess1000(k) + " " + q[index] + " ");
				index--;
				if (index < 0)
					index = 2;
			}
		}

		if (s.length() > 0)
			s.deleteCharAt(s.length() - 1);
		else
			return "Zero";

		String result = s.toString();
		// 去除多余的0
		String zeroPart1 = "Zero Billion Zero Million Zero Thousand";
		result = result.replace(zeroPart1, "Billion");
		String zeroPart2 = " Zero Million Zero Thousand";
		result = result.replace(zeroPart2, "");
		String zeroPart3 = " Zero Thousand";
		result = result.replace(zeroPart3, "");
		result = result.replace(" Zero", "");
		return result;
	}

	public static String numberToWords_bak(int num) {
		StringBuffer s = new StringBuffer();
		Map<Integer, String> map = new TreeMap<Integer, String>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}

		});
		map.put(1000000000, "Billion");
		map.put(1000000, "Million");
		map.put(1000, "Thousand");
		map.put(100, "Hundred");

		map.put(90, "Ninety");
		map.put(80, "Eighty");
		map.put(70, "Seventy");
		map.put(60, "Sixty");
		map.put(50, "Fifty");
		map.put(40, "Forty");
		map.put(30, "Thirty");
		map.put(20, "Twenty");

		map.put(19, "Nineteen");
		map.put(18, "Eighteen");
		map.put(17, "Seventeen");
		map.put(16, "Sixteen");
		map.put(15, "Fifteen");
		map.put(14, "Fourteen");
		map.put(13, "Thirteen");
		map.put(12, "Twelve");
		map.put(11, "Eleven");

		map.put(10, "Ten");
		map.put(9, "Nine");
		map.put(8, "Eight");
		map.put(7, "Seven");
		map.put(6, "Six");
		map.put(5, "Five");
		map.put(4, "Four");
		map.put(3, "Three");
		map.put(2, "Two");
		map.put(1, "One");

		int ratio = 0;
		int ret = num;
		for (Integer key : map.keySet()) {
			ratio = ret / key;
			ret = ret % key;
			if (ratio == 0)
				continue;
			String r = map.get(key);
			if (r.equals("Billion") || r.equals("Million") || r.equals("Thousand") || r.equals("Hundred")) {
				int h = ratio / 100;
				int t = ratio % 100 - ratio % 10;
				int other = ratio % 10;
				if (h > 0) {
					s.append(map.get(h) + " Hundred ");
				}
				if (t > 0) {
					if (t == 10) {
						s.append(map.get(t + other) + " ");
					} else {
						s.append(map.get(t) + " ");
						if (other > 0) {
							s.append(map.get(other) + " ");
						}
					}

				} else {
					if (other > 0) {
						s.append(map.get(other) + " ");
					}
				}

			}
			s.append(map.get(key) + " ");
		}
		if (s.length() > 0)
			s.deleteCharAt(s.length() - 1);
		else
			return "Zero";

		return s.toString();
	}

}
