package org.darcy.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 用来描述一个特定的作业 比如，该作业使用哪个类作为逻辑处理中的map，哪个作为reduce
 * 
 * 还可以指定该作业要处理的数据所在的路径 还可以指定改作业输出的结果放到哪个路径
 */
public class WCRunner {
	public static void main(String[] args) throws ClassNotFoundException, InterruptedException, IOException {

		Configuration conf = new Configuration();
		Job wcJob = Job.getInstance(conf);

		// 设置job所在的类在哪个jar包
		wcJob.setJarByClass(WCRunner.class);

		// 指定job所用的mappe类和reducer类
		wcJob.setMapperClass(WCMapper.class);
		wcJob.setReducerClass(WCReducer.class);

		// 指定mapper输出类型和reducer输出类型
		// 由于在wordcount中mapper和reducer的输出类型一致，
		// 所以使用setOutputKeyClass和setOutputValueClass方法可以同时设定mapper和reducer的输出类型
		// 如果mapper和reducer的输出类型不一致时，可以使用setMapOutputKeyClass和setMapOutputValueClass单独设置mapper的输出类型
		// wcJob.setMapOutputKeyClass(Text.class);
		// wcJob.setMapOutputValueClass(IntWritable.class);
		wcJob.setOutputKeyClass(Text.class);
		wcJob.setOutputValueClass(IntWritable.class);

		// 指定job处理的数据路径
		FileInputFormat.setInputPaths(wcJob, new Path("hdfs://master:9000/user/exe_mapreduce/wordcount/input"));
		// 指定job处理数据输出结果的路径
		FileOutputFormat.setOutputPath(wcJob, new Path("hdfs://master:9000/user/exe_mapreduce/wordcount/output"));

		// 将job提交给集群运行
		wcJob.waitForCompletion(true);
	}
}