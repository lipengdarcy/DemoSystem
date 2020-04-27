package org.darcy.hadoop;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
 * 
 * 4个泛型中，前两个是指定mapper输入数据的类型，KEYIN是输入的key的类型，VALUEIN是输入的value的类型
 * 
 * map 和 reduce 的数据输入输出都是以 key-value对的形式封装的
 * 
 * 默认情况下，Map框架传递给我们的mapper的输入数据中，key是要处理的文本中一行的起始偏移量（选用LongWritable），
 * 
 * value是这一行的内容（VALUEIN选用Text）
 * 
 * 在wordcount中，经过mapper处理数据后，得到的是<单词，1>这样的结果，所以KEYOUT选用Text，VAULEOUT选用IntWritable
 */

public class WCMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	/**
	 * 日志对象
	 */
	protected final Logger log = LogManager.getLogger(this.getClass());

	// MapReduce框架每读一行数据就调用一次map方法
	@Override
	protected void map(LongWritable k1, Text v1, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		// 将这一行的内容转换成string类型
		String line = v1.toString();
		// 对这一行的文本按特定分隔符切分
		// String[] words = line.split(" ");
		String[] words = StringUtils.split(line, " ");
		// 遍历这个单词数组,输出为key-value形式 key：单词 value ： 1
		for (String word : words) {
			log.info("Mapper阶段：" + word);
			context.write(new Text(word), new IntWritable(1));
		}

	}

}
