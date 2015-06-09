package com.cloud.valueobject.po;

import java.io.Serializable;
import java.sql.Date;

public class ReportGameDayDown implements Serializable {
    private static final long serialVersionUID = -6940117068986988709L;

    private Integer id;
    private int gId;
    private String bucket;
    private String prefix;
    private String fileName;
    private Date date;
    private long dayCountsTotal;
    private long dayCountsIncr;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getgId() {
		return gId;
	}
	public void setgId(int gId) {
		this.gId = gId;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getDayCountsTotal() {
		return dayCountsTotal;
	}
	public void setDayCountsTotal(long dayCountsTotal) {
		this.dayCountsTotal = dayCountsTotal;
	}
	public long getDayCountsIncr() {
		return dayCountsIncr;
	}
	public void setDayCountsIncr(long dayCountsIncr) {
		this.dayCountsIncr = dayCountsIncr;
	}

    
}
