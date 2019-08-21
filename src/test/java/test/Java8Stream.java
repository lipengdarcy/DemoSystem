package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Java8Stream {

	public static void main(String[] args) {
		Java8Stream demo = new Java8Stream();
		demo.streamTest();
		
		demo.print_java7();
		demo.print_java8();

		demo.count_java7(demo.getPersonList());
		demo.count_java8(demo.getPersonList());
	}

	/**
	 * stream 初体验
	 */
	public void streamTest() {
		// stream() − 为集合创建串行流。
		List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
		List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
		// parallelStream() − 为集合创建并行流。
		List<Integer> intList = Arrays.asList(1, 2, 5, 6, 3, 7, 4);
		intList.parallelStream().filter(a -> a > 3).forEach(System.out::println);
		;

		// Stream 提供了新的方法 'forEach' 来迭代流中的每个数据。以下代码片段使用 forEach 输出了10个随机数：
		Random random = new Random();
		random.ints().limit(10).forEach(System.out::println);
	}

	/**
	 * java 7 传统方式打印
	 */
	public void print_java7() {
		List<String> list = Arrays.asList("a", "b", "c", "c", "c");
		Map<String, Long> map = new HashMap<String, Long>();
		for (String a : list) {
			if (map.get(a) == null)
				map.put(a, 1L);
			else
				map.put(a, map.get(a) + 1);
		}
		for (Map.Entry<String, Long> e : map.entrySet()) {
			System.out.println(e.getKey() + ": " + e.getValue());
		}
	}

	/**
	 * java 8 stream 方式打印
	 */
	public void print_java8() {
		// 代码效果：[a,b,c,c,c] -> a:1,b:1,c:3
		Map<String, Long> map = Arrays.asList("a", "b", "c", "c", "c").stream()
				.collect(Collectors.groupingBy(str -> str, Collectors.counting()));
		for (Map.Entry<String, Long> e : map.entrySet()) {
			System.out.println(e.getKey() + ": " + e.getValue());
		}
	}

	/**
	 * java 7 传统方式统计
	 * 
	 * 按性别统计不同的级别的人员
	 */
	public void count_java7(List<Person> list) {
		Map<String, Map<String, Integer>> resultMap = new HashMap<>();
		Map<String, Integer> maleMap = new HashMap<>();
		Map<String, Integer> femaleMap = new HashMap<>();

		resultMap.put("male", maleMap);
		resultMap.put("female", femaleMap);

		for (int i = 0; i < list.size(); i++) {
			Person person = list.get(i);
			String gender = person.getGender();
			String level = person.getLevel();
			switch (gender) {
			case "male":
				Integer maleCount;
				if ("gold".equals(level)) {
					maleCount = maleMap.get("gold");
					maleMap.put("gold", null != maleCount ? maleCount + 1 : 1);
				} else if ("soliver".equals(level)) {
					maleCount = maleMap.get("soliver");
					maleMap.put("soliver", null != maleCount ? maleCount + 1 : 1);
				}
				break;

			case "female":
				Integer femaleCount;
				if ("gold".equals(level)) {
					femaleCount = femaleMap.get("gold");
					femaleMap.put("gold", null != femaleCount ? femaleCount + 1 : 1);
				} else if ("soliver".equals(level)) {
					femaleCount = femaleMap.get("soliver");
					femaleMap.put("soliver", null != femaleCount ? femaleCount + 1 : 1);
				}
				break;

			}
		}
		System.out.println("java 7 传统方式统计,按性别统计不同的级别的人员");
		System.out.println("male gold: " + resultMap.get("male").get("gold"));
		System.out.println("male soliver: " + resultMap.get("male").get("soliver"));
		System.out.println("female gold: " + resultMap.get("female").get("gold"));
		System.out.println("female soliver: " + resultMap.get("female").get("soliver"));
	}

	/**
	 * java 8 stream 方式统计
	 * 
	 * 按性别统计不同的级别的人员
	 */
	public void count_java8(List<Person> list) {
		Map<String, Map<String, Integer>> resultMap = list.stream()
				.collect(Collectors.groupingBy(Person::getGender, Collectors.toMap(person -> person.getLevel(),
						person -> 1, (existValue, newValue) -> existValue + newValue)));

		System.out.println("java 8 stream方式统计,按性别统计不同的级别的人员");
		System.out.println("male gold: " + resultMap.get("male").get("gold"));
		System.out.println("male soliver: " + resultMap.get("male").get("soliver"));
		System.out.println("female gold: " + resultMap.get("female").get("gold"));
		System.out.println("female soliver: " + resultMap.get("female").get("soliver"));
	}

	// 测试数据
	public List<Person> getPersonList() {
		List<Person> list = new ArrayList<Person>();
		list.add(new Person("male", "gold"));
		list.add(new Person("male", "soliver"));
		list.add(new Person("male", "gold"));
		list.add(new Person("male", "soliver"));
		list.add(new Person("female", "gold"));
		list.add(new Person("female", "soliver"));
		list.add(new Person("female", "gold"));
		list.add(new Person("female", "soliver"));
		list.add(new Person("female", "gold"));
		list.add(new Person("female", "soliver"));

		return list;
	}

	class Person {
		String gender;
		String level;

		public String getGender() {
			return gender;
		}

		public String getLevel() {
			return level;
		}

		public Person(String gender, String level) {
			this.gender = gender;
			this.level = level;
		}

	}

}
