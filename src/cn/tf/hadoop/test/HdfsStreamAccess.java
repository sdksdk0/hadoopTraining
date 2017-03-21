package cn.tf.hadoop.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Before;
import org.junit.Test;

public class HdfsStreamAccess {
	
	FileSystem fileSystem;
	Configuration conf = null;
	@Before
	public  void   init() throws IOException, InterruptedException, URISyntaxException{
		 conf = new Configuration();
		//conf.set("fs.defaultFS","hdfs://node1:8020");
		
		//拿到一个文件系统操作的客户端实例对象
		//通过参数传递，最后一个参数为用户名
		fileSystem= FileSystem.get(new URI("hdfs://node1:8020"),conf,"hadoop");
		
	}
	
	//上传
	@Test
	public void testupload() throws IllegalArgumentException, IOException{
		FSDataOutputStream outputStream = fileSystem.create(new Path("/tesemkdirdir/aa/bb/a.doc"),true);
	    FileInputStream inputStream = new FileInputStream("C:\\Users\\asus\\Desktop\\201661122101087.doc");
	    IOUtils.copy(inputStream, outputStream);
	}
	
	//下载
	@Test
	public  void testDownload() throws IllegalArgumentException, IOException{
		FSDataInputStream inputStream = fileSystem.open(new Path("/tesemkdirdir/aa/bb/a.doc"));
	
	   FileOutputStream outputStream = new FileOutputStream("d:/b.doc");
	   IOUtils.copy(inputStream, outputStream);
	}
	
	//随机读
	@Test
	public void   testRandomAccess() throws IOException{
		FSDataInputStream inputStream = fileSystem.open(new Path("/tesemkdirdir/aa/bb/a.doc"));
		
		//从第40个字节开始读
		inputStream.seek(400);
		

		FileOutputStream outputStream = new FileOutputStream("d:/b.doc");
		IOUtils.copy(inputStream, outputStream);
		
	}
	

}
