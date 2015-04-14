package com.gwghk.mis.util;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * 摘要：日志工具类
 * @author Gavin
 * @date 2014-10-17
 */
public class LogUtil {

	private static Logger objLog;
	private static Logger getLogger() {
		if (objLog == null) {
			objLog = LogManager.getLogger(LogUtil.class);
		}
		return objLog;
	}

    /**
     * 功能：记录info信息
     * @param message
     * @param exception
     */
	public static void info(String message, Exception exception) {
		log("INFO", message, exception);
	}

	/**
     * 功能：记录info信息
     * @param message
     */
	public static void info(Object message) {
		log("INFO", message);
	}

	/**
     * 功能：记录trace信息
     * @param message
     */
	public static void trace(String message) {
		log("TRACE", message);
	}

	/**
     * 功能：记录trace信息
     * @param message
     * @param exception
     */
	public static void trace(String message, Exception exception) {
		log("TRACE", message, exception);
	}

	/**
     * 功能：记录error信息
     * @param message
     * @param exception
     */
	public static void error(String message, Exception exception) {
		log("ERROR", message, exception);
	}

	/**
     * 功能：记录error信息
     * @param message
     */
	public static void error(String message) {
		log("ERROR", message);
	}

	/**
     * 功能：记录warn信息
     * @param message
     * @param exception
     */
	public static void warning(String message, Exception exception) {
		log("WARN", message, exception);
	}

	/**
     * 功能：记录warn信息
     * @param message
     */
	public static void warning(String message) {
		log("WARN", message);
	}

	/**
     * 功能：记录fatal信息
     * @param message
     * @param exception
     */
	public static void fatal(String message, Exception exception) {
		log("FATAL", message, exception);
	}

	/**
     * 功能：记录fatal信息
     * @param message
     */
	public static void fatal(String message) {
		log("FATAL", message);
	}

	/**
     * 功能：记录debug信息
     * @param message
     * @param exception
     */
	public static void debug(String message, Exception exception) {
		log("DEBUG", message, exception);
	}

	/**
     * 功能：记录fatal信息
     * @param message
     */
	public static void debug(String message) {
		log("DEBUG", message);
	}

	/**
     * 功能：记录log信息
     * @param level
     * @param msg
     */
	public static void log(String level, Object msg) {
		log(level, msg, null);
	}

	/**
     * 功能：记录log信息
     * @param level
     * @param e
     */
	public static void log(String level, Throwable e) {
		log(level, null, e);
	}

	/**
     * 功能：记录log信息
     * @param level
     * @param msg
     * @param e
     */
	public static void log(String level, Object msg, Throwable e) {
		try {
			StringBuilder sb = new StringBuilder();
			Throwable t = new Throwable();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			String input = sw.getBuffer().toString();
			StringReader sr = new StringReader(input);
			BufferedReader br = new BufferedReader(sr);
			for (int i = 0; i < 4; i++){
				br.readLine();
			}
			String line = br.readLine();
			int paren = line.indexOf("at ");
			line = line.substring(paren + 3);
			paren = line.indexOf('(');
			String invokeInfo = line.substring(0, paren);
			int period = invokeInfo.lastIndexOf('.');
			sb.append('[');
			sb.append(invokeInfo.substring(0, period));
			sb.append(':');
			sb.append(invokeInfo.substring(period + 1));
			sb.append("():");
			paren = line.indexOf(':');
			period = line.lastIndexOf(')');
			sb.append(line.substring(paren + 1, period));
			sb.append(']');
			sb.append(" - ");
			sb.append(msg);
			getLogger().log((Priority) Level.toLevel(level), sb.toString(), e);
		} catch (Exception ex) {
			LogUtil.error("日志写入出错!");
		}
	}
}
