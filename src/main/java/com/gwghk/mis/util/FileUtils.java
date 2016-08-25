package com.gwghk.mis.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.UploadFileInfo;
import com.gwghk.mis.enums.ResultCode;

/**
 * 摘要：文件工具类
 * @author  Gavin.guo
 * @date 2014-11-11
 */
public class FileUtils {

	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	/**
	 * 功能：获取文件扩展名(例如：E:/a.ext --> ext)
	 * @param filename   文件名
	 * @return  文件扩展名
	 */  
	public static String getExtend(String fileName) {
		return FilenameUtils.getExtension(fileName);
	}

	/**
	 * 功能： 获取文件前缀[不含后缀名](例如：E:/a.ext --> E:/a)
	 * @param  fileName  文件名(包含路径)
	 * @return String   文件名称
	 */
	public static String getPrefix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex);
	}
	
	/**
	 * 功能：获取文件名(例如：E:/hello.ext --> hello)
	 */
	public static String getBaseFileName(String fileName) {
		return FilenameUtils.getBaseName(fileName);
	}
	
	/**
	 * 功能：文件复制
	 * @param   inputFile    源文件
	 * @param   outputFile   目标文件
	 */
	public static void copyFile(String inputFile,String outputFile) throws FileNotFoundException{
		File sFile = new File(inputFile);
		File tFile = new File(outputFile);
		FileInputStream fis = new FileInputStream(sFile);
		FileOutputStream fos = new FileOutputStream(tFile);
		int temp = 0;  
		byte[] buf = new byte[10240];
        try {  
        	while((temp = fis.read(buf))!=-1){   
        		fos.write(buf, 0, temp);   
            }   
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally{
            try {
            	fis.close();
            	fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        } 
	}
	
	/**
	 * 功能：删除指定的文件
	 * @param strFileName   指定绝对路径的文件名
	 * @return 如果删除成功true  否则false
	 */
	public static boolean delete(String strFileName) {
		String deleteFilePath = ResourceUtil.getPmFilesPath()+"/"+strFileName;
		File fileDelete = new File(deleteFilePath);
		if (!fileDelete.exists() || !fileDelete.isFile()) {
			//LogUtil.info("错误: " + strFileName + "不存在!");
			return false;
		}
		LogUtil.info("--------成功删除文件---------"+strFileName);
		return fileDelete.delete();
	}
	
	/**
	 * 功能：删除指定的文件
	 * @param deleteFile   删除的文件
	 * @return 如果删除成功true  否则false
	 */
	public static boolean delete(File deleteFile) {
		if (!deleteFile.exists() || !deleteFile.isFile()) {
			return false;
		}
		LogUtil.info("--------成功删除文件---------"+deleteFile.getName());
		return deleteFile.delete();
	}
	
	/**
	 * 创建文件路径,并把路径，文件名、后缀名信息保存在UploadFileInfo对象中
	 * @param uploadFileInfo
	 */
	public static void createFilePath(UploadFileInfo uploadFileInfo){
		MultipartFile srcFile = uploadFileInfo.getSrcFile();
		String fileExtend = FileUtils.getExtend(srcFile.getOriginalFilename());
		//创建主目录
		String basePath = PropertiesUtil.getInstance().getProperty("uploadBasePath")+ "/"+uploadFileInfo.getSrcFileDirectory()+"/"+DateUtil.toYyyymm()+"/";
		String realPath = ResourceUtil.getPmFilesPath() + "/"+basePath ;			  // 文件的硬盘真实路径
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		if(System.getProperty("os.name").equals("Linux")){
			//文件夹授权，api会通过ftp方式上传，需要ftpuser的写权限
			try {
				Runtime.getRuntime().exec("chmod 777 " + realPath);
			} catch (IOException e) {
				logger.error("chmod error:" + e.getMessage());
			}
		}
		//重新命名新图片的名字(当前时间+随机8位数字+文件后缀名)
		String destBaseName = DateUtil.toYyyymmddHhmmss() +"_"+ StringUtil.randomNum(8);
		uploadFileInfo.setFileName(destBaseName);
		uploadFileInfo.setFilePath(basePath);
		uploadFileInfo.setFileAbsolutePath(realPath);
		uploadFileInfo.setFileExt(fileExtend);
	}
	
	/**
	 * 功能：上传图片处理
	 * @param uploadFileInfo 文件上传对象
	 * @return ApiResult 结果对象
	 */
	public static ApiResult uploadFile(UploadFileInfo uploadFileInfo){
		FileUtils.createFilePath(uploadFileInfo);
		String realDestFilePath = uploadFileInfo.getFileName()+"." + uploadFileInfo.getFileExt();
		String realPath=uploadFileInfo.getFileAbsolutePath();
		ApiResult result = new ApiResult();
		MultipartFile srcFile = uploadFileInfo.getSrcFile();
		try{
			//创建文件
			FileCopyUtils.copy(srcFile.getBytes(), new File(realPath + realDestFilePath));
			result.setCode(ResultCode.OK);
			result.setReturnObj(new Object[]{uploadFileInfo.getFilePath()+realDestFilePath});
		}catch(IOException e){
			result.setCode(ResultCode.Error1007);
		}
		return result;
	}
	
	/**
	 * 功能：上传图片到远程(单文件上传)
	 * @param  uploadFilePath  上传文件路径    
	 * @param  remoteApiUrl    上传的目录API URL 
	 * @param  type   		         上传目录名称
	 */
	public static String uploadFileToRemote(String uploadFilePath,String remoteApiUrl,String type){
		 DataOutputStream ds = null; 
		 String end ="\r\n",twoHyphens ="--",boundary ="*****",newName ="image."+FileUtils.getExtend(uploadFilePath);
		 try{
	         URL url = new URL(remoteApiUrl);
	         HttpURLConnection con = (HttpURLConnection)url.openConnection();
	         
	         //允许Input、Output，不使用Cache
	         con.setDoInput(true);            
	         con.setDoOutput(true);
	         con.setUseCaches(false);
	         
	         //设置传送的method=POST
	         con.setRequestMethod("POST");
	         
	         //设置请求的相关参数
	         con.setRequestProperty("Connection", "Keep-Alive");
	         con.setRequestProperty("Charset", "UTF-8");
	         con.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
	         
	         //设置DataOutputStream
	         ds = new DataOutputStream(con.getOutputStream());
	         
	         //设置固定的值--------start
	         ds.writeBytes(twoHyphens + boundary + end);
	         ds.writeBytes("Content-Disposition: form-data; "+"name=\"fileDir\"" + end + end + type + end);
	         //设置固定的值--------end
	         
	         //设置文件的值--------start(如果是多文件的话重复这里)
	         ds.writeBytes(twoHyphens + boundary + end);
	         ds.writeBytes("Content-Disposition: form-data; "+ "name=\"file1\";filename=\""+newName +"\""+ end+end);
	         FileInputStream fStream = new FileInputStream(uploadFilePath);
	         //设置每次写入1024bytes
	         int bufferSize =1024;  									
	         byte[] buffer = new byte[bufferSize];
	         int length =-1;
	         //从文件读取数据至缓冲区,并将资料写入DataOutputStream中
	         while((length = fStream.read(buffer)) !=-1){
	            ds.write(buffer, 0, length);
	         }
	         ds.writeBytes(end);
	         //设置文件的值--------end
	         
	         //post请求结束--------start
	         ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
	         //post请求结束--------end
	         
	         fStream.close();
	         ds.flush();
	         
	         //接口调用结束后取得返回值的内容
	         InputStream is = con.getInputStream();
	         int ch;
	         StringBuffer b =new StringBuffer();
	         while((ch = is.read()) !=-1 ){
	        	 b.append((char)ch);
	         }
	         return b.toString();
	     }catch(Exception e){
	    	 e.printStackTrace();
	     }finally{
	    	 try{
	    		 ds.close();
	    	 }catch(Exception e){
	    		 e.printStackTrace();
	    	 }
	     }
		 return null;
	}
	
	public static void main(String[] args) {
		//String imageFileType = "logo";
		//String cutedImagePath = FileUtils.getFilePrefix("e:\\2.jpg")+"_"+imageFileType+"."+FileUtils.getExtend("e:\\2.jpg");
		//System.out.println(cutedImagePath);
		String file ="E:\\hello.jpg";
	    System.out.println(file.substring(file.indexOf(".")));
		//System.out.println(FileUtils.getExtend(file));
	    //System.out.println(FileUtils.getPrefix(file));
	    //System.out.println(FileUtils.getBaseFileName(file));
	}
}
