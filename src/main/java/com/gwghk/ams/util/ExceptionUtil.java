package com.gwghk.ams.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 摘要：Exception工具类
 * @author  Gavin
 * @date 2014-10-29
 */
public class ExceptionUtil {

   /**
	* 功能：返回错误信息字符串
	* @param ex
	* @return 错误信息字符串
	*/
	public static String getExceptionMessage(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}
}
