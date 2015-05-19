package com.cloud.web.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;



public class FetchDouBan {
	private static Logger logger = Logger.getLogger(FetchDouBan.class);
	
	public static String getHtmlStringFromUrl(String url) throws Exception{
		String htmlStr = null;
		HttpClient httpClient = new DefaultHttpClient();
//		httpClient.getParams().setParameter("User-Agent",
//				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1");
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1");
		httpGet.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.addHeader("Accept-Charset",
				"gbk,utf-8;q=0.7,*;q=0.3");
//		httpGet.addHeader("Accept-Encoding",
//				"gzip,deflate,sdch");
		
		httpGet.addHeader("Accept-Language",
				"zh-CN,zh;q=0.8");
		httpGet.addHeader("Cache-Control",
				"max-age=0");
		httpGet.addHeader("Connection",
				"keep-alive");
		HttpContext localContext = new BasicHttpContext();
		
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK
					|| statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY
					|| statusCode == HttpStatus.SC_SEE_OTHER
					|| statusCode == HttpStatus.SC_TEMPORARY_REDIRECT) {
				htmlStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (ClientProtocolException e) {
			logger.error(e);
			throw e;
		} catch (IOException e) {
			logger.error(e);
			throw e;
		}

		return htmlStr;
	}
}
