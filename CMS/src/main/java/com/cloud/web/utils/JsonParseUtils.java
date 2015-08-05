package com.cloud.web.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonParseUtils {
	
	public static String getAccessToken(String treeStr) throws JsonProcessingException, IOException{
		ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode jn = jsonMapper.readTree(treeStr);
        if (jn.get("access_token") != null) {
        	return jn.get("access_token").getTextValue();
        }
		return null;
	}
	
	public static String getMediaId(String treeStr) throws JsonProcessingException, IOException{
		ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode jn = jsonMapper.readTree(treeStr);
        if (jn.get("media_id") != null) {
        	return jn.get("media_id").getTextValue();
        }
		return null;
	}
	
}

