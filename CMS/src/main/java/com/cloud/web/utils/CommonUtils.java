package com.cloud.web.utils;

import javax.servlet.http.HttpServletRequest;

import cn.egame.common.servlet.WebUtils;

public class CommonUtils {

	public static int getClientVersion(HttpServletRequest request) {
	    int version = WebUtils.getInt(request, "vc", 0);
        return version;
    }
	
    /**  
     * 获取网络IP号
     * 
     * @param request
     * 
     * @return String netWork
     */
	public static String getNetWork(HttpServletRequest request) {
		String netWork = request.getHeader("x-forwarded-for");
		if (netWork == null || netWork.length() == 0
				|| "unknown".equalsIgnoreCase(netWork)) {
			netWork = request.getHeader("Proxy-Client-IP");
		}
		if (netWork == null || netWork.length() == 0
				|| "unknown".equalsIgnoreCase(netWork)) {
			netWork = request.getHeader("WL-Proxy-Client-IP");
		}
		if (netWork == null || netWork.length() == 0
				|| "unknown".equalsIgnoreCase(netWork)) {
			netWork = request.getRemoteAddr();
		}
		// 多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
		if (netWork != null && netWork.contains(",")) {
			String ip_array[] = netWork.split(",");
			netWork = ip_array[0];
		}
		return netWork;
	}
    
	
	public static void main(String[] args) {
//		String imsi = WebUtils.getString(null, "imsi", "0");
		// String imsi ="460010781826881";
		// System.out.println(DivideFromImsi(null, imsi).getMessage());
	}
}
