package org.darcy.spark;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.SparkSession;

import java.util.*;

/**
 * mapPartition
 * 
 * 将List分区然后map。这里将1，2，3，4，5分为2个区，然后对每个分区进行累加。
 * 
 * 结果是1+2，3+4+5.如果是分3个区，则是1，2+3，4+5.
 */
public class SimpleMapPartition {

	public static void main(String[] args) {

		SparkSession sparkSession = SparkSession.builder().appName("JavaWordCount").master("local").getOrCreate();
		// spark对普通List的reduce操作
		JavaSparkContext javaSparkContext = new JavaSparkContext(sparkSession.sparkContext());

		// 使用map将key变成key-value，添加value
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
		// 分为2个分区
		JavaRDD<Integer> stringRDD = javaSparkContext.parallelize(list, 2);
		// 与map方法类似，map是对rdd中的每一个元素进行操作，而mapPartitions(foreachPartition)则是对rdd中的每个分区的迭代器进行操作。
		// 如果在map过程中需要频繁创建额外的对象,(例如将rdd中的数据通过jdbc写入数据库,map需要为每个元素创建一个链接而mapPartition为每个partition创建一个链接),
		// 则mapPartitions效率比map高的多。
		JavaRDD<?> rdd = stringRDD.mapPartitions(new FlatMapFunction<Iterator<Integer>, Integer>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<Integer> call(Iterator<Integer> integerIterator) throws Exception {
				int sum = 0;
				while (integerIterator.hasNext()) {
					sum += integerIterator.next();
				}
				List<Integer> list1 = new LinkedList<>();
				list1.add(sum);
				return list1.iterator();
			}
		});

		List<?> list1 = rdd.collect();
		// [3, 12]
		System.out.println(list1);
		
		

		List<String> data = Arrays.asList("hello world", "java spark", "hello spark");
		JavaRDD<String> originRDD = javaSparkContext.parallelize(data);
		// flatmap()是将函数应用于RDD中的每个元素，将返回的迭代器的所有内容构成新的RDD
		// RDD经过map后元素数量不变，经过flatmap后，一个元素可以变成多个元素
		JavaRDD<String> flatMap = originRDD.flatMap(s -> Arrays.asList(s.split(" ")).iterator());

		System.out.println(flatMap.collect());

	}
}
