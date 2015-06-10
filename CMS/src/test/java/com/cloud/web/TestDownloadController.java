package com.cloud.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cloud.dao.ReportGameDayDownDao;
import com.cloud.service.ReportGameDayDownRunable;

public class TestDownloadController {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("testApplicationContext.xml");
		
//		ReportGameDayDownRunable reportGameDayDownRunable = (ReportGameDayDownRunable) ac.getBean("reportGameDayDownRunable");
		
		ReportGameDayDownDao reportGameDayDownDao = (ReportGameDayDownDao) ac.getBean("reportGameDayDownDao");
//		System.out.println(reportGameDayDownRunable);
//		System.out.println(reportGameDayDownRunable.getReportGameDayDownDao());
		System.out.println(reportGameDayDownDao);
	}
}
