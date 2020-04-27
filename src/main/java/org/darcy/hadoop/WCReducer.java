package org.darcy.hadoop;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Reducer<KEYIN,VALUEIN,KEYOUT,VALUEOUT>
 * 
 * 经过mapper处理后的数据会被reducer拉取过来，所以reducer的KEYIN、VALUEIN和mapper的KEYOUT、VALUEOUT一致
 * 
 * 经过reducer处理后的数据格式为<单词，频数>,所以KEYOUT为Text，VALUEOUT为IntWritable
 */

public class WCReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	
	/**
	 * 日志对象
	 */
	protected final Logger log = LogManager.getLogger(this.getClass());

	// 当mapper框架将相同的key的数据处理完成后，reducer框架会将mapper框架输出的数据<key,value>变成<key,values{}>。
	// 例如，在wordcount中会将mapper框架输出的所有<hello,1>变为<hello,{1,1,1...}>，即这里的<k2，v2s>，然后将<k2，v2s>作为reduce函数的输入
	@Override
	protected void reduce(Text k2, Iterable<IntWritable> v2s,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		int count = 0;
		// 遍历v2的list，进行累加求和
		for (IntWritable v2 : v2s) {
			log.info("Reducer阶段：" + v2.get());
			count += v2.get();
		}
		// 输出这一个单词的统计结果
		context.write(k2, new IntWritable(count));
	}

}
