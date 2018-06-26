package com.zj.boot_web.common.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GetObjectRequest;

/**
 * 阿里云对象存储OSS
 *TypesName(类名)：AliOSS
 *Description(描述)：TODO 阿里云对象存储OSS
 * @author Adger
 * @date 2018年3月26日上午9:44:49
 *
 */
public class AliOSS {
	
	//private static final String UPLOAD_PATH = ConfConfig.getString("UPLOAD_PATH2");
	
	// endpoint以杭州为例，其它region请按实际情况填写
	static String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
	// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
	static String accessKeyId = "LTAIFZBnZd0VbLxc"; // 速保网络科技子账号用户 AliOSS
	static String accessKeySecret = "le1Tx30MUNCggnrGlgETaF7XnFZk31"; // 速保网络科技子账号用户 AliOSS
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*String fileName = UPLOAD_PATH + "enterprise\\download\\asdasf.xlsx";
		InputStream inputstream = null;
		try {
			inputstream = new  FileInputStream(fileName);
			// 上传指定文件流
			//String result = downloadFile("subao", "document/upload/lister/201804/RY(EPUB)_d653c322.xlsx", fileName);
			//上传网络流
			//String result = uploadFlowNetwork("subao", "document/upload/policy/20180319/0180017150.pdf", "http://tste.gwcslife.com/cms/wwwroot/ng/downLoad/018005910420180320174159.pdf");
			
			String result = deleteFile("subao", "document/upload/lister/201804/FP_9854c8fa.docx");
			System.out.println("上传结果：" + result);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("路径资源不存在！");
		}*/
		
		
	}
	
	/**
	 * 上传字符串
	*MethodsName(方法名)：uploadString
	*Description(描述)：TODO 
	* @param  @return bucketName：bucket名称；key：上传文件夹（路径）及名称，例：file/policy/aaaa.pdf；content：字符串
	* @param  @throws 
	* @return String SUCCESS：成功，FAIL：失败
	* @author Adger
	* @date 2018年3月23日下午4:42:20
	*
	 */
	public static String uploadString(String bucketName, String key, String content){
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		String return_code = "FAIL";
		try {
			ossClient.putObject(bucketName, key, new ByteArrayInputStream(content.getBytes()));
			return_code = "SUCCESS";
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
            return_code = "FAIL";
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
            return_code = "FAIL";
        } catch (Throwable e) {
            e.printStackTrace();
            return_code = "FAIL";
        } finally {
        	// 关闭client
            ossClient.shutdown();
        }
		
		return return_code;
	}
	
	/**
	 * 上传byte数组
	*MethodsName(方法名)：uploadByte
	*Description(描述)：TODO 
	* @param  @return bucketName：bucket名称；key：上传文件夹（路径）及名称，例：file/policy/aaaa.pdf；content：字节数组
	* @param  @throws 
	* @return String SUCCESS：成功，FAIL：失败
	* @author Adger
	* @date 2018年3月23日下午4:59:01
	*
	 */
	public static String uploadByte(String bucketName, String key, byte[] content){
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		String return_code = "FAIL";
		try {
			ossClient.putObject(bucketName, key, new ByteArrayInputStream(content));
			return_code = "SUCCESS";
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
            return_code = "FAIL";
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
            return_code = "FAIL";
        } catch (Throwable e) {
            e.printStackTrace();
            return_code = "FAIL";
        } finally {
        	// 关闭client
            ossClient.shutdown();
        }
		
		return return_code;
	}
	
	/**
	 * 上传网络流
	*MethodsName(方法名)：uploadByte
	*Description(描述)：TODO 
	* @param  @return bucketName：bucket名称；key：上传文件夹（路径）及名称，例：file/policy/aaaa.pdf；url：网络地址
	* @param  @throws 
	* @return String SUCCESS：成功，FAIL：失败
	* @author Adger
	 * @throws IOException 
	 * @throws MalformedURLException 
	* @date 2018年3月23日下午4:59:28
	*
	 */
	public static String uploadFlowNetwork(String bucketName, String key, String url) {
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		String return_code = "FAIL";
		try {
			// 上传
			InputStream inputStream = new URL(url).openStream();
			ossClient.putObject(bucketName, key, inputStream);
			return_code = "SUCCESS";
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
            return_code = "FAIL";
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
            return_code = "FAIL";
        } catch (Throwable e) {
            e.printStackTrace();
            return_code = "FAIL";
        } finally {
        	// 关闭client
            ossClient.shutdown();
        }
		
		return return_code;
	}
	
	/**
	 * 上传文件流
	*MethodsName(方法名)：uploadFileIO
	*Description(描述)：TODO 
	* @param  @return bucketName：bucket名称；key：上传文件夹（路径）及名称，例：file/policy/aaaa.pdf；inputStream：文件流
	* 调用此方法时需抛出路径文件资源不存在异常进行异常处理
	* @param  @throws FileNotFoundException
	* @return String SUCCESS：成功，FAIL：失败
	* @author Adger
	* @date 2018年3月23日下午5:06:17
	*
	 */
	public static String uploadFileIO(String bucketName, String key, InputStream inputStream){
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		String return_code = "FAIL";
		try {
			// 上传
			ossClient.putObject(bucketName, key, inputStream);
			return_code = "SUCCESS";
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
            return_code = "FAIL";
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
            return_code = "FAIL";
        } catch (Throwable e) {
            e.printStackTrace();
            return_code = "FAIL";
        } finally {
        	// 关闭client
            ossClient.shutdown();
        }
		
		return return_code;
	}
	
	/**
	 * 下载文件到本地
	*MethodsName(方法名)：downloadFile
	*Description(描述)：TODO 下载文件到本地
	* @param  @return bucketName：bucket名称；key：需要下载文件路径（路径）及名称，例：file/policy/aaaa.pdf；localFile：下载的文件保存地址
	* @param  @throws 
	* @return String SUCCESS：成功，FAIL：失败
	* @author Adger
	* @date 2018年3月26日下午12:24:29
	*
	 */
	public static String downloadFile(String bucketName, String key, String localFile){
		
		 // 创建OSSClient实例  
		   OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);  
		   // 下载object到文件  new File("<yourLocalFile>"这个file对象需要给定一个本地目录，文件会下载到该目录中  
		   ossClient.getObject(new GetObjectRequest(bucketName, key), new File(localFile));  
		   // 关闭client  
		   ossClient.shutdown();
		   String return_code = "SUCCESS";
		return return_code;  
		
		/*
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		String return_code = "FAIL";
		
		try {
			// 下载object到文件
			ossClient.getObject(new GetObjectRequest(bucketName, key), new File(localFile));
			return_code = "SUCCESS";
        } catch (OSSException oe) { 
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
            return_code = "FAIL";
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
            return_code = "FAIL";
        } catch (Throwable e) {
            e.printStackTrace();
            return_code = "FAIL";
        } finally {
        	// 关闭client
            ossClient.shutdown();
        }*/
		//return return_code;
	}
	
	
	
	
	/**
	 * 删除指定单个文件
	*MethodsName(方法名)：deleteFile
	*Description(描述)：TODO 删除指定单个文件
	* @param  @return bucketName：bucket名称；key：需要删除的文件路径（路径）及名称
	* @param  @throws 
	* @return String SUCCESS：成功，FAIL：失败
	* @author Adger
	* @date 2018年3月26日下午12:24:29
	*
	 */
	public static String deleteFile(String bucketName, String key){
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		String return_code = "FAIL";
		
		try {
			// 删除Object
			ossClient.deleteObject(bucketName, key);
			return_code = "SUCCESS";
        } catch (OSSException oe) { 
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
            return_code = "FAIL";
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
            return_code = "FAIL";
        } catch (Throwable e) {
            e.printStackTrace();
            return_code = "FAIL";
        } finally {
        	// 关闭client
            ossClient.shutdown();
        }
		return return_code;
	}
	
	
	/**
	 * 删除指定多个个文件
	*MethodsName(方法名)：deleteFiles
	*Description(描述)：TODO 删除指定多个文件
	* @param  @return bucketName：bucket名称；keys：需要删除的文件路径（路径）及名称数组：List<String> keys = new ArrayList<String>();
	* @param  @throws 
	* @return String SUCCESS：成功，FAIL：失败
	* @author Adger
	* @date 2018年3月26日下午12:24:29
	*
	 */
	public static String deleteFiles(String bucketName, List<String> keys){
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		String return_code = "FAIL";
		
		try {
			// 删除Objects
			DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
			List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
			for (String object : deletedObjects) {
                System.out.println("\t" + object);
            }
			return_code = "SUCCESS";
		} catch (OSSException oe) { 
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			System.out.println("Error Message: " + oe.getErrorMessage());
			System.out.println("Error Code:       " + oe.getErrorCode());
			System.out.println("Request ID:      " + oe.getRequestId());
			System.out.println("Host ID:           " + oe.getHostId());
			return_code = "FAIL";
		} catch (ClientException ce) {
			System.out.println("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ce.getMessage());
			return_code = "FAIL";
		} catch (Throwable e) {
			e.printStackTrace();
			return_code = "FAIL";
		} finally {
			// 关闭client
			ossClient.shutdown();
		}
		return return_code;
	}
}
