package cn.tf.hadoop.friends;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class SharedFriendsStrpTwo {

	static class SharedFriendsStepTwoMapper extends
			Mapper<LongWritable, Text, Text, Text> {

		// 拿到的数据是上一次输出的结果

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] friend_persons = line.split("\t");
			String friends = friend_persons[0];
			String[] persons = friend_persons[1].split(",");

			Arrays.sort(persons);
			for (int i = 0; i < persons.length - 2; i++) {
				for (int j = i + 1; j < persons.length - 1; j++) {
					// <人-人，好友>
					context.write(new Text(persons[i] + "-" + persons[j]),
							new Text(friends));
				}
			}

		}
	}

	static class SharedFriendsStepTwoReducer extends
			Reducer<Text, Text, Text, Text> {
		@Override
		protected void reduce(Text person_person, Iterable<Text> friends,
				Context context) throws IOException, InterruptedException {

			StringBuffer sb = new StringBuffer();

			for (Text friend : friends) {
				sb.append(friend).append(",");
			}
			context.write(person_person, new Text(sb.toString()));
		}
	}

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf);
		job.setJarByClass(SharedFriendsStepTwoMapper.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(SharedFriendsStepTwoMapper.class);
		job.setReducerClass(SharedFriendsStepTwoReducer.class);

		FileInputFormat.setInputPaths(job, new Path("D:/temp/out/part-r-00000"));
		FileOutputFormat.setOutputPath(job, new Path("D:/temp/out2"));


		job.waitForCompletion(true);

	}

}
