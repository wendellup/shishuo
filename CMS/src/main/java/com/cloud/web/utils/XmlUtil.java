package com.cloud.web.utils;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.cloud.valueobject.vo.TextMessage;

public class XmlUtil {
	private static Logger log = Logger.getLogger(XmlUtil.class);
	
	public static void main(String[] args) throws Exception {
//		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("xmlTest.xml")));
		String xmlStr = "<xml><ToUserName>toUser</ToUserName><FromUserName>fromUser</FromUserName> <CreateTime>1348831860</CreateTime><MsgType>text</MsgType><Content>this is a test</Content><MsgId>1234567890123456</MsgId></xml>";
//		TextMessage textMessage = null;
		TextMessage requestMessage = XmlUtil.toTextMessage(xmlStr);
//        String msgType = requestMessage.getMsgType();
//        String toUserName = requestMessage.getToUserName();
//        String fromUserName = requestMessage.getFromUserName();
        //判断消息类型，如果是event，且事件类型为subscribe，则新建一个文本消息
//        if (MessageType.event.name().equals(msgType)) {
//            if (EventType.subscribe.name().equals(requestMessage.getEvent())) {
//                String message = "感谢您关注我的公众账号[愉快]";
//                textMessage = new TextMessage(toUserName, fromUserName,
//                        MessageType.text.name(), message, TimeUtil.currentSeconds());
//            }
//        }

        
        //将文本消息转换为xml文本
        String responseMessage = XmlUtil.toXml(requestMessage);
//        HttpHeaders responseHeaders = new HttpHeaders();
//        //设置返回实体的编码，不设置的话可能会变成乱码
//        responseHeaders.add("Content-Type", "text/html; charset=utf-8");
//        log.info(String.format("response message: %s", responseMessage));
//        log.info("receive message finish");
//        return new ResponseEntity<String>(responseMessage, responseHeaders, HttpStatus.OK);
        System.out.println(responseMessage);
	}
	

    public static String toXml(TextMessage textMessage) throws Exception {
        if (textMessage == null) return "";

        JAXBContext context = JAXBContext.newInstance(TextMessage.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_FRAGMENT, true);

        StringWriter sw = new StringWriter();
        m.marshal(textMessage, sw);
        return sw.toString();
    }

    public static TextMessage toTextMessage(String xml) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(TextMessage.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        TextMessage textMessage = (TextMessage) jaxbUnmarshaller.unmarshal(reader);
        IOUtils.closeQuietly(reader);
        return textMessage;
    }
}