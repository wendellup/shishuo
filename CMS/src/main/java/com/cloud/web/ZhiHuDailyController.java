package com.cloud.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cloud.valueobject.vo.NewsInfo;
import com.cloud.web.utils.FetchDouBan;

@Controller
@RequestMapping(value="/v2/zhihu/daily")
public class ZhiHuDailyController {
	
//	@RequestMapping(value="/list", produces = {"application/json;charset=UTF-8"})
//	@ResponseBody
	@RequestMapping(value="/list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse
			response) throws Exception{
		String dateStr = request.getParameter("date");
		
		//url---> 20140507
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat showSdf = new SimpleDateFormat("yyyy月MM日dd");
		Date date = new Date();
		if(dateStr!=null){
			date = sdf.parse(dateStr);
		}
		//展现的时间
		String currentDateStrShow = showSdf.format(date);
		Calendar calendar=Calendar.getInstance();
		
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		String nextDateStr = sdf.format(calendar.getTime());
		String url = "http://news-at.zhihu.com/api/2/news/before/"+nextDateStr;
		String content = FetchDouBan.getHtmlStringFromUrl(url);
		
		//解析某日的日报list
		ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode jn = jsonMapper.readTree(content);
        List<NewsInfo> newsInfoList = new ArrayList<NewsInfo>();
        if(jn.get("news")!=null){
            Iterator it = jn.get("news").iterator();
            while(it.hasNext()){
                JsonNode jn2 = (JsonNode) it.next();
                NewsInfo newsInfo = new NewsInfo();
                newsInfo.setTitle(jn2.get("title").getTextValue());
                newsInfo.setUrl(jn2.get("url").getTextValue());
                newsInfo.setImage(jn2.get("image").getTextValue());
                newsInfo.setShare_url(jn2.get("share_url").getTextValue());
                newsInfo.setThumbnail(jn2.get("thumbnail").getTextValue());
                newsInfo.setGa_prefix(jn2.get("ga_prefix").getTextValue());
                newsInfo.setId(jn2.get("id").getIntValue());
                
                newsInfoList.add(newsInfo);
            }
        }
        
        calendar.add(Calendar.DAY_OF_YEAR, -2);
		String preDateStr = sdf.format(calendar.getTime());
		
		String todayDateStr = sdf.format(new Date());
		
		
		if(todayDateStr.equals(dateStr) || dateStr==null){
			nextDateStr = null;
		}
        
        
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("currentDateStrShow", currentDateStrShow);
		model.put("preDateStr", preDateStr);
		model.put("nextDateStr", nextDateStr);
		model.put("newsInfoList", newsInfoList);
		ModelAndView mav = new ModelAndView("zhihu/daily/list", model);
//		ModelAndView mav = new ModelAndView("list", model);
		return mav;
	}
}
