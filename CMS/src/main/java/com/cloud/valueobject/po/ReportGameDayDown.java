package com.cloud.valueobject.po;

import java.io.Serializable;
import java.sql.Date;

public class ReportGameDayDown implements Serializable {
    private static final long serialVersionUID = -6940117068986988709L;

    private Integer id;
    private int g_id;
    private String bucket;
    private String prefix;
    private String file_name;
    private Date date;
    private long day_counts_total;
    private long day_counts_incr;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getG_id() {
		return g_id;
	}
	public void setG_id(int g_id) {
		this.g_id = g_id;
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
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getDay_counts_total() {
		return day_counts_total;
	}
	public void setDay_counts_total(long day_counts_total) {
		this.day_counts_total = day_counts_total;
	}
	public long getDay_counts_incr() {
		return day_counts_incr;
	}
	public void setDay_counts_incr(long day_counts_incr) {
		this.day_counts_incr = day_counts_incr;
	}
    
    
    
}
