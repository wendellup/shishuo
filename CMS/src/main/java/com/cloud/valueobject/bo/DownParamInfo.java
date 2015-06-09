package com.cloud.valueobject.bo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.egame.common.servlet.WebUtils;

public class DownParamInfo extends BaseParamInfo {

    private static final long serialVersionUID = 1558252379336479745L;

    private String bucket;
    private String fileName;
    private String prefix;

    public DownParamInfo(HttpServletRequest req) {
        super(req);
        this.bucket = WebUtils.getString(req, "bucket", "");
        this.fileName = WebUtils.getString(req, "file_name", "");
        this.prefix = WebUtils.getString(req, "prefix", "");
    }

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
    
}
