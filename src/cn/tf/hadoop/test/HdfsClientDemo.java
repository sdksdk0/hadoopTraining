package cn.tf.hadoop.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Before;
import org.junit.Test;

public class HdfsClientDemo {
	
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
	//上传文件
	@Test
	public void testUpload() throws IllegalArgumentException, IOException, InterruptedException{
		fileSystem.copyFromLocalFile(new Path("D:\\uploadPic\\pic\\a.docx"), new Path("/user/a.docx"));
		fileSystem.close();
	}
	
	//下载
	@Test
	public  void testDownload() throws IllegalArgumentException, IOException{
		fileSystem.copyToLocalFile(new Path("/user/root/testdata"), new Path("d:/"));
		fileSystem.close();
	}
	
	/**
	 * 删除
	 */
	@Test
	public void deleteTest() throws Exception{
		boolean delete = fileSystem.delete(new Path("/user/b.jpg"), true);//true， 递归删除
		System.out.println(delete);
		fileSystem.close();
	}
	
	//打印参数
	@Test
	public void testConf(){
		Iterator<Entry<String, String>> iterator = conf.iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			System.out.println(entry.getValue() + "--" + entry.getValue());//conf加载的内容
		}
	}
	
	
	
	//创建目录
	@Test
	public  void testMkdir() throws IllegalArgumentException, IOException{
		boolean mkdirs = fileSystem.mkdirs(new Path("/tesemkdirdir/aa/bb"));	
		System.out.println(mkdirs);
	}
	
	//查询
	//会递归找到所有的文件
	@Test
	public void testLs() throws FileNotFoundException, IllegalArgumentException, IOException{
		RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(new Path("/"), true);
		while(listFiles.hasNext()){
			LocatedFileStatus next = listFiles.next();
			System.out.println("BlockSize:"+next.getBlockSize());
			System.out.println("owner:"+next.getOwner());
			System.out.println("Replication:"+next.getReplication());
			System.out.println("path:"+next.getPath());
			System.out.println("*********************************");
		}
	}
	
	
	
	
	
	
	
	

}
