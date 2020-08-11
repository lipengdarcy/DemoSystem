package org.darcy.spark;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;

public class SparkSQL {
	
  public static void main(String[] args) {
    String logFile = "D:/Java/Apache/spark-2.4.5/README.md"; 
    // 1.SparkSQL 的入口：申明SparkSession  
    SparkSession spark = SparkSession.builder().appName("SparkSQL Application Demo").master("local").getOrCreate();
    
    Dataset<String> logData = spark.read().textFile(logFile).cache();

   // long numAs = logData.filter(s -> s.contains("a")).count();
   // long numBs = logData.filter(s -> s.contains("b")).count();

   // System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);

    spark.stop();
  }
}