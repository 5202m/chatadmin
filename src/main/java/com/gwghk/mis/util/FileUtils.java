package com.gwghk.mis.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
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
	
	public static void main(String[] args) {
		//String imageFileType = "logo";
		//String cutedImagePath = FileUtils.getFilePrefix("e:\\2.jpg")+"_"+imageFileType+"."+FileUtils.getExtend("e:\\2.jpg");
		//System.out.println(cutedImagePath);
		String file ="E:\\hello.jpg";
	    System.out.println(FileUtils.getExtend(file));
	    System.out.println(FileUtils.getPrefix(file));
	    System.out.println(FileUtils.getBaseFileName(file));
	}
}
