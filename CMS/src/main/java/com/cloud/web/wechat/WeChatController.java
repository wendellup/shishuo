package com.cloud.web.wechat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.egame.common.servlet.WebUtils;
import cn.egame.common.util.Utils;

import com.cloud.valueobject.vo.Image;
import com.cloud.valueobject.vo.TextMessage;
import com.cloud.web.utils.EncoderHandler;
import com.cloud.web.utils.XmlUtil;

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
	
	/*
	@ResponseBody
	@RequestMapping(value = "/access", method = RequestMethod.POST)
	public Object msgHandler(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(req.getInputStream());
            Element users = document.getRootElement();
            for (Iterator i = users.elementIterator(); i.hasNext();) {
                Element user = (Element) i.next();
                logger.info(user.getName() + ":" + user.getText());
            }
        } catch (DocumentException e) {
        	logger.error(e);
        }
		return "";
	}
	*/
	
	@ResponseBody
	@RequestMapping(value = "/access", method = RequestMethod.POST)
	public Object msgHandler(HttpServletRequest req,
			HttpServletResponse response,@RequestBody String body) throws Exception {
		logger.info("reqest body:\n"+body);
		TextMessage requestMessage = XmlUtil.toTextMessage(body);
//      String msgType = requestMessage.getMsgType();
//      String toUserName = requestMessage.getToUserName();
//      String fromUserName = requestMessage.getFromUserName();
      //判断消息类型，如果是event，且事件类型为subscribe，则新建一个文本消息
//      if (MessageType.event.name().equals(msgType)) {
//          if (EventType.subscribe.name().equals(requestMessage.getEvent())) {
//              String message = "感谢您关注我的公众账号[愉快]";
//              textMessage = new TextMessage(toUserName, fromUserName,
//                      MessageType.text.name(), message, TimeUtil.currentSeconds());
//          }
//      }

		TextMessage respMsg = new TextMessage();
		respMsg.setContent(requestMessage.getContent()+System.currentTimeMillis());
		respMsg.setCreateTime(System.currentTimeMillis());
		respMsg.setFromUserName(requestMessage.getToUserName());
		respMsg.setToUserName(requestMessage.getFromUserName());
		respMsg.setMsgType(requestMessage.getMsgType());
		if(requestMessage.getMsgType()!=null && requestMessage.getMsgType().equals("image")){
			//如果是图片消息
			Image image = new Image();
			image.setMediaId(requestMessage.getMediaId());
			respMsg.setImage(image);
		}
      
      //将文本消息转换为xml文本
      String responseMessage = XmlUtil.toXml(respMsg);
      logger.info(String.format("response message: \n", responseMessage));
      logger.info("receive message finish");
      HttpHeaders responseHeaders = new HttpHeaders();
//      //设置返回实体的编码，不设置的话可能会变成乱码
      responseHeaders.add("Content-Type", "text/html; charset=utf-8");
      return new ResponseEntity<String>(responseMessage, responseHeaders, HttpStatus.OK);
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
