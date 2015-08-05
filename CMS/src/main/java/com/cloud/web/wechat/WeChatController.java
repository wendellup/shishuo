package com.cloud.web.wechat;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.servlet.WebUtils;

import com.cloud.valueobject.vo.Image;
import com.cloud.valueobject.vo.TextMessage;
import com.cloud.web.utils.EncoderHandler;
import com.cloud.web.utils.ImageUtil;
import com.cloud.web.utils.JsonParseUtils;
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
			try {
				//如果是图片消息
				//1.下载mediaId对应图片
				String fileName = testGetStream(requestMessage.getMediaId());
				File file = new File(fileName);
				//2.将图片变成黑白
				if(file.exists()){
					ImageUtil.changeImge(file);
				}
				//3.上传黑白图片获取mediaId
				String mediaId = getMediaId(file);
				
				//4.返回黑白图片
				Image image = new Image();
				image.setMediaId(mediaId);
				respMsg.setImage(image);
			} catch (Exception e) {
				//如果报错则返回错误信息
				respMsg.setMsgType("text");
				respMsg.setContent("图片处理出错,请稍后再试~");
			}
			
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

	private static String APPID = "wx1141594d3ec678a7";
	
	private static String APPSECRET = "09d861ca7bed9151422b37adb0898761";
	
	public String getMediaId(File file) throws ExceptionCommonBase{
		String accessToken = getAccessToken();
		String postUrl = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type=image";
		String retStr = cn.egame.common.web.WebUtils.uploadFile(postUrl, file, "media");
		try {
			return JsonParseUtils.getMediaId(retStr);
		} catch (Exception e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		}
	}
	
	public static String getAccessToken() throws ExceptionCommonBase{
		String str = null;
		try {
			String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
			str = cn.egame.common.web.WebUtils.http(tokenUrl, "GET", "UTF-8", null, null, null);
			str = JsonParseUtils.getAccessToken(str);
		} catch (Exception e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		}
		return str;
	}
	
	public String testGetStream(String media_id) throws ExceptionCommonBase{
		String accessToken = getAccessToken();
		String getUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token="+accessToken+"&media_id="+media_id;
		
		HashMap<String, String> response = new HashMap<String, String>();
		
		FileOutputStream fos = null;
		String fileName = "output"+System.currentTimeMillis()+".jpg";
		try {
			ByteArrayOutputStream baos = cn.egame.common.web.WebUtils.httpStreamNew(getUrl, "GET", "utf-8", null, null, response);
			String val = response.get("Content-disposition");
			if(val!=null
					&& val.indexOf("filename=\"")!=-1){
				//获取文件的名称
				int beginIndex = val.indexOf("filename=\"")+"filename=\"".length();
				int endIndex = val.indexOf("\"", beginIndex);
				fileName = val.substring(beginIndex, endIndex);
			}
			fos = new FileOutputStream(new File(fileName));
			baos.writeTo(fos);
		} catch (IOException ioe) {
			throw ExceptionCommonBase.throwExceptionCommonBase(ioe);
		} finally {
			if(fos!=null){
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileName;

	}
	
	public static String uploadFile(String actionUrl, File file, String postFileName)
			throws ExceptionCommonBase {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		// String newName = "image.jpg";
		/* 设置DataOutputStream */
		DataOutputStream ds = null;
		FileInputStream fStream = null;
		HttpURLConnection con = null;
		StringBuffer b = null;
		try {
			URL url = new URL(actionUrl);
			con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file1\";filename=\"" + file.getName() + "\""
					+ end);
			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			fStream = new FileInputStream(file);
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

			/* 取得Response内容 */
			InputStream is = con.getInputStream();
			int ch;
			b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
		} catch (Exception e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			/* close streams */
			if (fStream != null) {
				try {
					fStream.close();
				} catch (IOException e) {
					throw ExceptionCommonBase.throwExceptionCommonBase(e);
				}
			}
			if (ds != null) {
				try {
					ds.flush();
					ds.close();
				} catch (IOException e) {
					throw ExceptionCommonBase.throwExceptionCommonBase(e);
				}
			}
		}

		if (b != null) {
			return b.toString();
		}
		return null;
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
