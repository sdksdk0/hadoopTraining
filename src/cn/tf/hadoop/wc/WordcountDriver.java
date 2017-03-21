package cn.tf.hadoop.wc;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordcountDriver {
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
	/*	if (args == null || args.length == 0) {
			args = new String[2];
			args[0] = "hdfs://node1:8020/wordcount/input/wordcount.txt";
			args[1] = "hdfs://node1:8020/wordcount/output8";
		}*/
		
		
		Configuration  conf=new Configuration();
		
		conf.set("mapreduce.framework.name","localhost");
		
		//本地模式运行mr程序事，输入输出的数据可以在本地，也可以在hdfs上。
		
		
		conf.set("fs.defaults", "file:///");
		
		
		
		
		
		
		Job  job=Job.getInstance(conf);
		
		
		job.setJarByClass(WordcountDriver.class);
		
		
		job.setMapperClass(WordcountMapper.class);
		job.setReducerClass(WordcountReducer.class);
		
		//指定mapper输出数据的kv类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		boolean res = job.waitForCompletion(true);
		System.exit(res?0:1);
		
	}
	

}
