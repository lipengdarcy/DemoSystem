package org.darcy.spark;

import java.util.Arrays;
import java.util.List;

//spark通常引入的jar包
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;

public class FirstSpark {

	public static void main(String[] args) {

		/**
		 * 1.Initializing Spark
		 * 
		 * The first thing a Spark program must do is to create a JavaSparkContext
		 * object, which tells Spark how to access a cluster. To create a SparkContext
		 * you first need to build a SparkConf object that contains information about
		 * your application.
		 * 
		 * the appName parameter is a name for your application to show on the cluster
		 * UI. masterNode is a Spark, Mesos or YARN cluster URL, or a special “local”
		 * string to run in local mode. In practice, when running on a cluster, you will
		 * not want to hardcode master in the program, but rather launch the application
		 * with spark-submit and receive it there. However, for local testing and unit
		 * tests, you can pass “local” to run Spark in-process.
		 */
		String appName = "First Spark Application", masterNode = "local";
		SparkConf conf = new SparkConf().setAppName(appName).setMaster(masterNode);
		JavaSparkContext sc = new JavaSparkContext(conf);

		/**
		 * 2.RDD
		 *
		 * Spark revolves around the concept of a resilient distributed dataset (RDD),
		 * which is a fault-tolerant collection of elements that can be operated on in
		 * parallel. There are two ways to create RDDs: parallelizing an existing
		 * collection in your driver program, or referencing a dataset in an external
		 * storage system, such as a shared filesystem, HDFS, HBase, or any data source
		 * offering a Hadoop InputFormat.
		 * 
		 */
		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
		// 驱动程序的数据，
		JavaRDD<Integer> distData = sc.parallelize(data);
		// 外部数据，来自文件
		JavaRDD<String> distFile = sc.textFile("README.md");

		/**
		 * 3.RDD Operations
		 * 
		 * RDDs support two types of operations: transformations, which create a new
		 * dataset from an existing one, and actions, which return a value to the driver
		 * program after running a computation on the dataset. For example, map is a
		 * transformation that passes each dataset element through a function and
		 * returns a new RDD representing the results. On the other hand, reduce is an
		 * action that aggregates all the elements of the RDD using some function and
		 * returns the final result to the driver program (although there is also a
		 * parallel reduceByKey that returns a distributed dataset).
		 */

		// map 操作是transformations，即：转换
		JavaRDD<Integer> lineLengths = distFile.map(s -> s.length());
		// reduce 操作是actions，即：计算
		int totalLength = lineLengths.reduce((a, b) -> a + b);
		// 计算结果保存，持久化到内存，以便之后调用
		lineLengths.persist(StorageLevel.MEMORY_ONLY());

		// 计算：通过传入 function
		JavaRDD<Integer> lineLengths2 = distFile.map(new GetLength());
		int totalLength2 = lineLengths2.reduce(new Sum());

		/**
		 * 4. closure
		 * 
		 * To execute jobs, Spark breaks up the processing of RDD operations into
		 * tasks, each of which is executed by an executor. Prior to execution, Spark
		 * computes the task’s closure. The closure is those variables and methods which
		 * must be visible for the executor to perform its computations on the RDD (in
		 * this case foreach()). This closure is serialized and sent to each executor.
		 */
		int counter = 0;
		JavaRDD<Integer> rdd = sc.parallelize(data);

		// Wrong: Don't do this!!
		//rdd.foreach(x -> counter += x);
		rdd.take(100).forEach(x->System.out.println(x));

		System.out.println("Counter value: " + counter);
	}

}

class GetLength implements Function<String, Integer> {
	public Integer call(String s) {
		return s.length();
	}
}

class Sum implements Function2<Integer, Integer, Integer> {
	public Integer call(Integer a, Integer b) {
		return a + b;
	}
}