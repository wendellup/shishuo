package com.cloud.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.dao.ReportGameDayDownDao;
import com.cloud.valueobject.po.ReportGameDayDown;

@Service
public class ReportGameDayDownRunable implements Runnable {
	@Autowired
	private ReportGameDayDownDao reportGameDayDownDao;
	
    Logger log = Logger.getLogger(ReportGameDayDownRunable.class);
    
    private String bucket;
    private String prefix;
    private String fileName;

    public ReportGameDayDownRunable() {
		super();
	}

	public ReportGameDayDownRunable(String bucket, String prefix, String fileName) {
        this.bucket = bucket;
        this.prefix = prefix;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            ReportGameDayDown re = new ReportGameDayDown();
            int id = reportGameDayDownDao.getReportGameDayDownIdByIdentityFileAndTodayDate(bucket,
            		prefix, fileName);
            if (id > 0) {
                re.setId(id);
            } else {
                re.setId(null);
            }

            re.setBucket(bucket);
            re.setPrefix(prefix);
            re.setFileName(fileName);
            if(id>0){
            	reportGameDayDownDao.updateReportGameDayDown(re);
            }else{
            	reportGameDayDownDao.insertReportGameDayDown(re);
            }
        } catch (Exception e) {
            log.error("", e);
        }

    }

}
