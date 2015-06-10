package com.cloud.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.util.Utils;

import com.cloud.service.ReportGameDayDownRunable;
import com.cloud.valueobject.bo.DownParamInfo;
import com.cloud.web.utils.ConstVar;




@Controller
@RequestMapping(value="/v2/dl")
public class DownloadController {
	
	private static Logger LOGGER = Logger.getLogger(DownloadController.class);
	
	@RequestMapping(value="/download")
	public static void downLoad(HttpServletRequest req, HttpServletResponse resp, String[] path, Object token)
            throws Exception {
		
        // 请求封装参数
        DownParamInfo downParamInfo = new DownParamInfo(req);
//        DownLoadResultInfo downLoadResultInfo = new DownLoadResultInfo();
        // 检查参数有效性
//        checkParamValid(resp, downParamInfo);
        // 760计费下载后, 判断该游戏针对该用户是否已经收过费调用的下载接口
        // 根据渠道或者机型获取游戏实体包
//        GameFileInfo gameFileInfo = getGameFileInfoByChannelAndTermindId(resp, downParamInfo);
        // 获取下载地址
//        String fileUrl = getDownFileUrl(req, downParamInfo, gameFileInfo);
        // 重定向游戏url开始下载
        String downloadUrl = null;
  
        if(downParamInfo.getBucket().equals("superman")){
        	downloadUrl = ConstVar.QINIU_HOST_SUPERMAN
        			+ downParamInfo.getPrefix() + "/" + downParamInfo.getFileName();
        }
        redirectGameUrl(req, resp, downloadUrl, downParamInfo);
        // 植入下载指定游戏事件
//        pubDownEvent(downParamInfo);
//        ModelAndView mav = new ModelAndView("dl/download");
//		return mav;
    }
	
	// 重定向游戏url开始下载
    private static void redirectGameUrl(HttpServletRequest req, HttpServletResponse resp, String fileUrl, DownParamInfo downParamInfo)
            throws IOException, ExceptionCommonBase {
        if (!Utils.stringIsNullOrEmpty(fileUrl)) {
            resp.sendRedirect(fileUrl);
            // 记录下载日志
            recordDownloadlog(downParamInfo.getBucket(), downParamInfo.getPrefix(), downParamInfo.getFileName());
        } else {
            LOGGER.info("down_url=" + fileUrl + "; " + downParamInfo.getIp()+"下载地址错误...");
            throw new ExceptionCommonBase(HttpServletResponse.SC_NOT_FOUND, "");
        }
    }
    
    private static void recordDownloadlog(String bucket, String prefix, String fileName) {
        Thread t = new Thread(new ReportGameDayDownRunable(bucket, prefix, fileName));
        t.start();
    }
}
