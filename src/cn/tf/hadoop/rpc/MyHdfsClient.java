package cn.tf.hadoop.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;


public class MyHdfsClient {
	
	
	public static void main(String[] args) throws IOException {
		ClientNameProtocol namenode = RPC.getProxy(ClientNameProtocol.class, 1L, new InetSocketAddress("localhost",8020), new Configuration());
		String data = namenode.getMetaData("/tesemkdirdir/aa/bb/a.doc");
		System.out.println(data);
		
		
	}

}
