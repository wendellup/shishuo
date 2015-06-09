package com.cloud.web.utils;

import java.util.Properties;

import cn.egame.common.util.Utils;

public class ConstVar {
	public static String QINIU_HOST_SUPERMAN;
	public static int REPEAT_READ_NUM;
	
	static{
		Properties properties = getProperties();
		
		QINIU_HOST_SUPERMAN = getPropertiesValueByKey(properties, "qiniu_host_superman", QINIU_HOST_SUPERMAN);
		System.out.println("qiniu_host_superman========"+QINIU_HOST_SUPERMAN);
		
		REPEAT_READ_NUM = getPropertiesIntValueByKey(properties, "repeat_read_num", REPEAT_READ_NUM);
		System.out.println("repeat_read_num========"+REPEAT_READ_NUM);
	}
	
	private static String getPropertiesValueByKey(Properties properties,
			String key, String defaultResult) {
		/* download_url 获取 */
		String value = properties.getProperty(key);
		if (value != null && !"".equals(value.trim())) {
			return value;
		}
		return defaultResult;
	}

	private static int getPropertiesIntValueByKey(Properties properties,
			String key, int defaultResult) {
		/* download_url 获取 */
		String value = properties.getProperty(key);
		if (value != null && !"".equals(value.trim()) && value.matches("\\d+")) {
			return Integer.parseInt(value);
		}
		return defaultResult;
	}
	
	private static Properties getProperties() {
		Properties properties = new Properties();
		try {
			properties = Utils.getProperties("common.properties");
		} catch (Exception e) {
			System.out.println("common.properties not find");
		}
		return properties;
	}
}
