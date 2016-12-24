package com.sina.util;

import java.util.ArrayList;

import java.util.List;

import org.apache.log4j.Logger;


import com.redis.MyJedisPool;
import com.sina.pojos.proxyPojo;

public class StaticValue {

	public static Logger logger = Logger.getLogger(StaticValue.class);
	
	public static ReadConfig readConfig=new ReadConfig("crawl.properties",true);
	public static String resourcePath="./resources/";
	/**
	 * 编码设置
	 */
	public static String default_encoding = "utf-8";
	
	/**
	 * 错误限制
	 */
	public static int page_no_item_max_times=Integer.valueOf(readConfig.getValue("page_no_item_max_times"));
	public static int network_error_max_times=Integer.valueOf(readConfig.getValue("page_no_item_max_times"));
	
	public static List<proxyPojo> proxyList=new ArrayList<proxyPojo>();
	public static int proxyIndex=0;
	public static boolean proxy_open = Boolean.parseBoolean(readConfig.getValue("proxy_open"));
	public static String ip_proxy_file_path=readConfig.getValue("ip_proxy_file_path");
	/**
	 * 用于登陆用户切换
	 */
	public static int count_of_login_user=Integer.valueOf(readConfig.getValue("count_of_login_user"));
	public static int login_user_id=0; 
	public static int login_user_index=0; 
	public static int cookie_index=0; //getCookieStore应该返回的cookieIdx
	public static final int COOKIE_STATU_UNUSABLE = 0;
	public static final int COOKIE_STATU_USABLE = 1;
	public static final int COOKIE_STATU_SUSPENDED = 2;//暂停的
	
	
	public static final int COOKIE_CONNECT_LIMIT = 100;//新的cookie限制的连接次数
	//进程休息时间控制
	public static final int SLEEP_TIME_CRAWL = 10*1000;
	public static final int SLEEP_TIME_PARSE_PAGE = 10*1000;
	public static final int SLEEP_TIME_UPDATE_COOKIE = 60*1000;
	
	//页面数量限制
	public static final int CRAWL_PAGE_LIMIT = 20; //爬取用户或者微博某一信息的页数限制（有的微博可能会有几千页）
	
	
	
	public static MyJedisPool JedisPool=new MyJedisPool();
	
//	public static String cookie="";

	/**
	 * 热门微博编码
	 */
    public static String hourCode="9999";
    public static String dayCode="99991";
    public static String weekCode="99992";
    public static String monthCode="99993";
    public static String sportCode="1399";
    public static String foodCode="2699";
    public static String entertainmentCode="1099";
    public static String emotionalCode="1999";
    public static String travelCode="2599";
    public static String economicsCode="1299";
    public static String technologyCode="2099";
    public static String cultureCode="1499";
    
    
    static {
		try {
			if(proxy_open){
				logger.info("proxy server has been used!");	
		    		ReadConfig rc=new ReadConfig(ip_proxy_file_path,false);
//		    		rc.getLineConfigTxt();
//		    		System.out.println(rc.getLineConfigTxt());
		    		String [] proxys=rc.getLineConfigTxt().split("\n");
		    		for(int i=0;i<proxys.length;i++){
		    			proxyPojo pp=new proxyPojo();
		    			String[] proxy=proxys[i].split(" ");
		    			pp.setIp(proxy[0]);
		    			pp.setPort(proxy[1]);
		    			proxyList.add(pp);
		    		}
			} else {
				logger.info("proxy server is forbidden!");
			}
		} catch (Exception e) {
			logger.info("读取代理服务器列表参数时抛出异常，请检查!");
			e.printStackTrace();
		}
    }
    
    public static void main(String arg[]){
    	List<proxyPojo>pl=StaticValue.proxyList;
    	while(StaticValue.proxyIndex<20){
    		System.out.println(StaticValue.proxyIndex+"---"+pl.get((StaticValue.proxyIndex++)%pl.size()).toString());
    	}
    	
    }
}
