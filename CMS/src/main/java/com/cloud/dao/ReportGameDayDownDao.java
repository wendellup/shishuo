package com.cloud.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cloud.valueobject.po.ReportGameDayDown;

@Repository
public interface ReportGameDayDownDao {

	public Integer getReportGameDayDownIdByIdentityFileAndTodayDate(
			@Param("bucket") String bucket
			, @Param("prefix") String prefix
			, @Param("file_name") String fileName);

	public void insertReportGameDayDown(ReportGameDayDown re);
	public void incrReportGameDayDown(ReportGameDayDown re);
	public void updateReportGameDayDown(ReportGameDayDown re);
	
}
