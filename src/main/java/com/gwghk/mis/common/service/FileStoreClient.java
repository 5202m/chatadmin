package com.gwghk.mis.common.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.gwghk.mis.common.model.FileBean;
import com.gwghk.mis.util.HttpClientUtils;
import com.gwghk.mis.util.PropertiesUtil;

/**
 * 摘要：文件上传
 * @author  Gavin.guo
 * @date 2015-03-18
 */
public class FileStoreClient {

	private static final Logger logger = Logger.getLogger(FileStoreClient.class);
	
	private String baseUrl = PropertiesUtil.getInstance().getProperty("fileStore.baseUrl");
	
	/**
	 * Save file to specified path
	 * @param filePath 
	 * @param type 
	 * @param file file bytes
	 * @param overwrite overwrite or not if file already existed
	 * @return if the specified file already existed
	 * @throws HttpException
	 * @throws IOException
	 */
	public boolean upload2FilePath(String filePath, String type, byte[] file, boolean overwrite) throws HttpException, IOException{
		Map<String, String> params = new HashMap<String, String>();
		params.put("filePath", filePath);
		params.put("type", type);
		params.put("overwrite", String.valueOf(overwrite));
		String filename = FilenameUtils.getName(filePath);
		Map<String, FileBean> fileBytes = new HashMap<String, FileBean>();
		if(file != null){
			fileBytes.put("file", new FileBean(filename, file));
		}
		String json = HttpClientUtils.httpPostMultipart(baseUrl + "/Upload", params, null, fileBytes, "UTF8");
		JSONObject jsonObject = JSONObject.fromObject(json);
		String code = jsonObject.getString("code");
		if("SUCCESS".equals(code)){
			return jsonObject.getBoolean("alreadyExisted");
		}else{
			String error = jsonObject.getString("error");
			throw new IOException("Update file fail " + filename + ". Error: " + error);
		}
	}
	
	/**
	 * Upload file
	 * @param filename 
	 * @param file data
	 * @param type file type
	 * @return file path
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public String upload(String filename, String type, byte[] file) throws HttpException, IOException{
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", type);
		Map<String, FileBean> fileBytes = new HashMap<String, FileBean>();
		if(file != null){
			fileBytes.put("file", new FileBean(filename, file));
		}
		String json = HttpClientUtils.httpPostMultipart(baseUrl + "/Upload", params, null, fileBytes, "UTF8");
		JSONObject jsonObject = JSONObject.fromObject(json);
		String code = jsonObject.getString("code");
		if("SUCCESS".equals(code)){
			JSONArray pathsJsonArray = jsonObject.getJSONArray("path");
			List<String> pathList = new ArrayList<String>();
			for(int i = 0; i < pathsJsonArray.size(); i++){
				pathList.add(pathsJsonArray.getString(i));
			}
			return pathList.get(0);
		}else{
			String error = jsonObject.getString("error");
			throw new IOException("Update file fail " + filename + ". Error: " + error);
		}
	}
	
	/**
	 * Download file
	 * @param filePath
	 * @return
	 * @throws HttpException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public byte[] download(String filePath, String type) throws HttpException, UnsupportedEncodingException, IOException{
		String filePathEncoded = null;
		try{
			filePathEncoded = URLEncoder.encode(filePath, "UTF8");
		}catch(UnsupportedEncodingException e){
			logger.error("never throw", e);
		}
		return HttpClientUtils.httpGetBytes(baseUrl + 
				"/Download?filePath=" + filePathEncoded + "&type=" + type );	
	}
}
