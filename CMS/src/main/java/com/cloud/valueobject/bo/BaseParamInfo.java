package com.cloud.valueobject.bo;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloud.web.utils.CommonUtils;

public class BaseParamInfo implements Serializable {
    private static final long serialVersionUID = 7411534878885452057L;
    private String ip;

    public BaseParamInfo(HttpServletRequest req) {
        this.ip = CommonUtils.getNetWork(req);
    }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
