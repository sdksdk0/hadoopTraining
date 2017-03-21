package cn.tf.hadoop.wc;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


/**
 * 
 * @author asus
 *KEYIN:默认情况下，是mr框架所读到的一行文本的起始偏移量  long
 *VALUEIN：默认情况下是mr框架所读到的一行文本的内容  string
 *KEYOUT：是用户自定义逻辑处理完之后输出数据的key，在此处是单词  string
 *VALUEOUT:是用户自定义逻辑处完之后输出数据中的value，在此处是单词次数  Integer
 */
public class WordcountMapper  extends  Mapper<LongWritable, Text, Text, IntWritable>{
	
	
	//map阶段的业务逻辑就写在自定义的map()方法中
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
	    //将maptask传给我们的文本内容先转换为string
		String   line=value.toString();
		//根据空格将这一行切分成单词
		String[]  words=line.split(" ");
		
		//将单词输出为《单词，1>
		for (String word : words) {
			context.write(new Text(word), new IntWritable(1));
		}
		
		
		
	}
	
	

}
