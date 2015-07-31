package com.cloud.web.wechat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.egame.common.servlet.WebUtils;

import com.cloud.web.utils.EncoderHandler;

@Controller
@RequestMapping(value = "/v2/wechat")
public class WeChatController {

	private static Logger logger = Logger.getLogger(WeChatController.class);

	private static String TOKEN = "wendellup_wx_token";

	@ResponseBody
	@RequestMapping(value = "/test/json", method = RequestMethod.GET)
	public Object testJson(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		resp.setContentType("application/json");
		AOO aoo = new AOO();
		aoo.setId(100);
		aoo.setName("wendellup");
		return aoo;
	}

	/**
	 * @desc 微信公众平台接入方法,对应文章url:http://mp.weixin.qq.com/wiki/17/2d4265491f12608
	 *       cd170a95559800f2d.html
	 * 
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/access", method = RequestMethod.GET)
	public Object revert_time(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		logger.info(getIpAddr(req) + "," + req.getQueryString());
		String signature = WebUtils.getString(req, "signature", "");
		String timestamp = WebUtils.getString(req, "timestamp", "");// 时间戳
		String nonce = WebUtils.getString(req, "nonce", ""); // 随机数
		String echostr = WebUtils.getString(req, "echostr", "");// 随机字符串,需要返回

		List<String> paramList = new ArrayList<String>();
		boolean isValid = false;
		paramList.add(TOKEN);
		paramList.add(timestamp);
		paramList.add(nonce);
		Collections.sort(paramList);
		String catStr = "";
		for (String str : paramList) {
			catStr += str;
		}
		catStr = EncoderHandler.encode("SHA1", catStr);
		if (catStr.equals(signature)) {
			isValid = true;
		}

		return echostr;
		// if(isValid){
		// return echostr;
		// }else{
		// return false;
		// }
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
}
