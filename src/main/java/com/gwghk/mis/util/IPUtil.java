package com.gwghk.mis.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 * 摘要：IP处理类
 * @author Gavin.guo
 * @date 2014-03-11
 */
public class IPUtil {

	/**
	 * 功能：获取客户端IP
	 * @param request  客户端request
	 * @return   客户端IP
	 */
	public static String getClientIP(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}else{
			ip=request.getRemoteAddr();
		}
		return ip;
    }
	
	/**
	 * 功能：获取真实本地的IP
	 * @return 本机IP
	 */
	public static String getRealIp(){
		String localip = "";												// 本地IP，如果没有配置外网IP则返回它
		String netip = "";													// 外网IP
		Enumeration<NetworkInterface> netInterfaces = null;
		try{
			 netInterfaces = NetworkInterface.getNetworkInterfaces();
			 InetAddress ip = null;
				boolean finded = false;										// 是否找到外网IP
				while (netInterfaces.hasMoreElements() && !finded) {
					NetworkInterface ni = netInterfaces.nextElement();
					Enumeration<InetAddress> address = ni.getInetAddresses();
					while (address.hasMoreElements()) {
						ip = address.nextElement();
						if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {		// 外网IP
							netip = ip.getHostAddress();
							finded = true;
							break;
						} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {	// 内网IP
							localip = ip.getHostAddress();
						}
					}
				}
				if (netip != null && !"".equals(netip)) {
					return netip;
				} else {
					return localip;
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
}
