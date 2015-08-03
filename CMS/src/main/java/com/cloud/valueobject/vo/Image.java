package com.cloud.valueobject.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class Image {
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	@XmlElement(name = "MediaId")
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	
	
	
}
