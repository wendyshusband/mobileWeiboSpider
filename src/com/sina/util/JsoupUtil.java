package com.sina.util;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtil {
	public static Logger logger = Logger.getLogger(JsoupUtil.class);
	//解析pagelist中的页码数量
	public static int parsePagelistPaNum(String page){
		Document doc = Jsoup.parse(page);// 解析html
		Element ePagelist = doc.select("div.pa#pagelist").first();
//		System.out.println("page:"+page);
//		System.out.println("ePagelist.text:"+ePagelist.text());
		if(ePagelist!=null)
			return Integer.valueOf( parseUtil.matchStringGroup1(ePagelist.text(), "/(\\d+)页") );
		else{
			logger.warn("没有从页面中解析到页码!");
			if(StringUtil.isBlank(page))
				logger.warn("因为待解析页面为空");
			return 0;
		}
	}
	public static Element getBody(String page){
		Document doc = Jsoup.parse(page);// 解析html
		return doc.body();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
