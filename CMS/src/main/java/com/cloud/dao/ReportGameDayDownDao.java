package com.cloud.dao;

import org.springframework.stereotype.Repository;

import com.cloud.valueobject.po.ReportGameDayDown;

@Repository
public interface ReportGameDayDownDao {

	public int getReportGameDayDownIdByIdentityFileAndTodayDate(String bucket,
			String prefix, String fileName);

	public void insertReportGameDayDown(ReportGameDayDown re);
	public void updateReportGameDayDown(ReportGameDayDown re);
	
}
