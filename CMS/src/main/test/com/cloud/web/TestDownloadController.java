package com.cloud.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cloud.service.ReportGameDayDownRunable;

public class TestDownloadController {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		
//		ReportGameDayDownRunable reportGameDayDownRunable = (ReportGameDayDownRunable) ac.getBean("reportGameDayDownRunable");
//		System.out.println(reportGameDayDownRunable);
	}
}
