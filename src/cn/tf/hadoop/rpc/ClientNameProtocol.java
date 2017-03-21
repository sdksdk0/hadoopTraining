package cn.tf.hadoop.rpc;

public interface ClientNameProtocol {
	public static  final long versionID=1L;
	public String getMetaData(String path);
		

}
