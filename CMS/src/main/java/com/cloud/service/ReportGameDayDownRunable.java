package com.cloud.service;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.egame.common.util.Utils;

import com.cloud.dao.ReportGameDayDownDao;
import com.cloud.valueobject.po.ReportGameDayDown;

public class ReportGameDayDownRunable implements Runnable {
	private ReportGameDayDownDao reportGameDayDownDao;
	
    Logger log = Logger.getLogger(ReportGameDayDownRunable.class);
    
    private String bucket;
    private String prefix;
    private String fileName;

    public ReportGameDayDownRunable() {
		super();
	}

	public ReportGameDayDownRunable(ServletContext sc, String bucket, String prefix, String fileName) {
//		ApplicationContext ac = new ClassPathXmlApplicationContext("testApplicationContext.xml");
//		reportGameDayDownDao = (ReportGameDayDownDao) ac.getBean("reportGameDayDownDao");;
		
//		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sc);
//		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		ApplicationContext ac=(ApplicationContext)sc.getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.spring");
		reportGameDayDownDao = (ReportGameDayDownDao) ac.getBean("reportGameDayDownDao");; 
		
        this.bucket = bucket;
        this.prefix = prefix;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            ReportGameDayDown re = new ReportGameDayDown();
            Integer id = reportGameDayDownDao.getReportGameDayDownIdByIdentityFileAndTodayDate(bucket,
            		prefix, fileName);
            id = Utils.toInt(id, 0);
            if (id > 0) {
                re.setId(id);
            } else {
                re.setId(null);
            }

            re.setBucket(bucket);
            re.setPrefix(prefix);
            re.setFile_name(fileName);
            if(id>0){
            	reportGameDayDownDao.incrReportGameDayDown(re);
            }else{
            	reportGameDayDownDao.insertReportGameDayDown(re);
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    
}
