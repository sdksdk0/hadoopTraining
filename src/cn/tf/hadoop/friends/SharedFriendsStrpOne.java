package cn.tf.hadoop.friends;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class SharedFriendsStrpOne {
	
	
	static class SharedFriendsStepOneMapper  extends  Mapper<LongWritable,Text,Text,Text>{
		
		@Override
		protected void map(LongWritable key, Text value,
				Context context)
				throws IOException, InterruptedException {
			String line =value.toString();
			String[]  persion_friends=line.split(":");
			String persion=persion_friends[0];
			String friends=persion_friends[1];
			
			
			
			for (String friend : friends.split(",")) {
				context.write(new Text(friend),new Text(persion));
			}
			
			
		}
	}
	
	
	
	static class SharedFriendsStepOneReducer extends  Reducer<Text, Text, Text, Text>{
		@Override
		protected void reduce(Text friend, Iterable<Text> persons,
				Context context)
				throws IOException, InterruptedException {
			
			StringBuffer  sb=new StringBuffer();
			
			for (Text person : persons) {
				sb.append(person).append(",");

			}
			context.write(friend, new Text(sb.toString()));
		}
	}
	
	
	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf);
		job.setJarByClass(SharedFriendsStepOneMapper.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		job.setMapperClass(SharedFriendsStepOneMapper.class);
		job.setReducerClass(SharedFriendsStepOneReducer.class);

		FileInputFormat.setInputPaths(job, new Path("D:/srcdata/friends"));
		FileOutputFormat.setOutputPath(job, new Path("D:/temp/out"));

		job.waitForCompletion(true);

	}
	
	

}
