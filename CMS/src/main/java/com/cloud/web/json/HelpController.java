package com.cloud.web.json;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/v2/help")
public class HelpController {
	
	private static Logger logger = Logger.getLogger(HelpController.class);
	
	@ResponseBody
	@RequestMapping(value = "/revert_time/time/{time}/format/{format}", method =RequestMethod.GET)
	public Object revert_time(@PathVariable("time") Long time
			, @PathVariable("format") String format, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dateStr = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = new Date(time);
			dateStr = sdf.format(date);
		} catch (Exception e) {
			logger.error("", e);
			dateStr = "格式为:"+format+",无法转换.";
		}
		
		return dateStr;
	}
	
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
}
