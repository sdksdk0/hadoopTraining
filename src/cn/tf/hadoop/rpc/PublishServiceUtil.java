package cn.tf.hadoop.rpc;

import java.io.IOException;

import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.Server;


public class PublishServiceUtil {
	
	public static void main(String[] args) throws HadoopIllegalArgumentException, IOException {
		Builder builder=new RPC.Builder(new Configuration());
	
		builder.setBindAddress("localhost")
		.setPort(8020)
		.setProtocol(ClientNameProtocol.class)
		.setInstance(new MyNameNode());
		
		
		Server server=builder.build();
		server.start();
	}

}
