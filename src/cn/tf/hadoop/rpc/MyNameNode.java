package cn.tf.hadoop.rpc;

import org.apache.hadoop.hdfs.protocolPB.ClientNamenodeProtocolPB;

public class MyNameNode implements ClientNameProtocol{
	
	
	public String getMetaData(String path){
		return path+": 3 - {BLK_1,BLK_2} ....";
	}

}
