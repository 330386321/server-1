package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ClientParams;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDFSClient {

	private static TrackerClient trackerClient = null;
	private static TrackerServer trackerServer = null;
	private static StorageServer storageServer = null;
	private static StorageClient1 storageClient = null;

	private static FastDFSClient client;
	
	public static FastDFSClient getInstance(ClientParams param) throws Exception{
		if(client == null){
			client=new FastDFSClient(param);
		}
		return client;
	}
	
	private FastDFSClient(ClientParams param) throws Exception {
		ClientGlobal.init(param);
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageServer = null;
		storageClient = new StorageClient1(trackerServer, storageServer);
	}
	
	
	/**
	 * 上传文件方法
	 * <p>
	 * Title: uploadFile
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param fileName
	 *            文件全路径
	 * @param extName
	 *            文件扩展名，不包含（.）
	 * @param metas
	 *            文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(String fileName, String extName, NameValuePair[] metas) {
		String result = null;
		try {
			result = storageClient.upload_file1(fileName, extName, metas);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 上传文件,传fileName
	 * 
	 * @param fileName
	 *            文件的磁盘路径名称 如：D:/image/aaa.jpg
	 * @return null为失败
	 */
	public String uploadFile(String fileName) {
		return uploadFile(fileName, null, null);
	}

	/**
	 * 
	 * @param fileName
	 *            文件的磁盘路径名称 如：D:/image/aaa.jpg
	 * @param extName
	 *            文件的扩展名 如 txt jpg等
	 * @return null为失败
	 */
	public String uploadFile(String fileName, String extName) {
		return uploadFile(fileName, extName, null);
	}

	/**
	 * 上传文件方法
	 * <p>
	 * Title: uploadFile
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param fileContent
	 *            文件的内容，字节数组
	 * @param extName
	 *            文件扩展名
	 * @param metas
	 *            文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) {
		String result = null;
		try {
			result = storageClient.upload_file1(fileContent, extName, metas);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 上传文件
	 * 
	 * @param fileContent
	 *            文件的字节数组
	 * @return null为失败
	 * @throws Exception
	 */
	public String uploadFile(byte[] fileContent) throws Exception {
		return uploadFile(fileContent, null, null);
	}

	/**
	 * 上传文件
	 * 
	 * @param fileContent
	 *            文件的字节数组
	 * @param extName
	 *            文件的扩展名 如 txt jpg png 等
	 * @return null为失败
	 */
	public String uploadFile(byte[] fileContent, String extName) {
		return uploadFile(fileContent, extName, null);
	}

	/**
	 * 文件下载到磁盘
	 * 
	 * @param path
	 *            图片路径
	 * @param output
	 *            输出流 中包含要输出到磁盘的路径
	 * @return -1失败,0成功
	 */
	public int download_file(String path, BufferedOutputStream output) {
		// byte[] b = storageClient.download_file(group, path);
		int result = -1;
		try {
			byte[] b = storageClient.download_file1(path);
			try {
				if (b != null) {
					output.write(b);
					result = 0;
				}
			} catch (Exception e) {
			} // 用户可能取消了下载
			finally {
				if (output != null)
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取文件数组
	 * 
	 * @param path
	 *            文件的路径 如group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
	 * @return
	 */
	public byte[] download_bytes(String path) {
		byte[] b = null;
		try {
			b = storageClient.download_file1(path);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 删除文件
	 * 
	 * @param group
	 *            组名 如：group1
	 * @param storagePath
	 *            不带组名的路径名称 如：M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
	 * @return -1失败,0成功
	 */
	public Integer delete_file(String group, String storagePath) {
		int result = -1;
		try {
			result = storageClient.delete_file(group, storagePath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param storagePath
	 *            文件的全部路径 如：group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
	 * @return -1失败,0成功
	 * @throws IOException
	 * @throws Exception
	 */
	public Integer delete_file(String storagePath) {
		int result = -1;
		try {
			result = storageClient.delete_file1(storagePath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		ClientParams param =new ClientParams();
		/**
		 * 连接超时2s
		 */
		param.setConnectTimeout(2);
		/**
		 * 网络超时30s
		 */
		param.setNetworkTimeout(30);
		/**
		 * 设置字符编码
		 */
		param.setCharset("ISO8859-1");
		/**
		 * 设置http访问端口
		 */
		param.setTrackerTttpPport(80);
		/**
		 * 开启token
		 */
		param.setAntiStealToken(false);
		/**
		 * 秘钥
		 */
		param.setSecretKey("FastDFS1234567890");
		/**
		 * tracker 和storage 服务地址以及端口逗号隔开
		 */
		param.setTrackerServer("192.168.1.180:22122,192.168.1.181:22122");
//		FastDFSClient client = new FastDFSClient(param);
		File file =new File("c:/2012011410364885.gif");
		String filePath = FastDFSClient.getInstance(param).uploadFile(getFileBuffer(new FileInputStream(file),file.length()), "gif");
		System.out.println(filePath);
//		String uploadFile = client.uploadFile("c:/2012011410364885.gif");
//		FastDFSClient client1 = new FastDFSClient("c:/fdfs_client.conf");
//		Integer delete_file = client.delete_file("group1/M00/00/00/wKgBtFl1Xz6AWbPgAAGbl4-V9UI015.gif");
//		System.out.println(delete_file);
		
	}
	/**
	 * InputStream 转byte[]
	 * @param inStream
	 * @param fileLength
	 * @return
	 * @throws IOException
	 */
	public static byte[] getFileBuffer(InputStream inStream, long fileLength) throws IOException {

		byte[] buffer = new byte[256 * 1024];
		byte[] fileBuffer = new byte[(int) fileLength];

		int count = 0;
		int length = 0;

		while ((length = inStream.read(buffer)) != -1) {
			for (int i = 0; i < length; ++i) {
				fileBuffer[count + i] = buffer[i];
			}
			count += length;
		}
		return fileBuffer;
	}
}
