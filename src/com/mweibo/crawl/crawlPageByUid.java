package com.mweibo.crawl;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;

import com.sina.pojos.CookieStorePojo;
import com.sina.util.JsoupUtil;
import com.sina.util.RadisUtil;
import com.sina.util.StaticValue;
import com.sina.util.getPageUtil;

public class crawlPageByUid {
	public static Logger logger = Logger.getLogger(crawlPageByUid.class);
	RadisUtil ru = new RadisUtil();
//	getPageUtil gpu = new getPageUtil();
	getPageUtil gpu = getPageUtil.getPageUtil();
	private final int crawlPageLimit = StaticValue.CRAWL_PAGE_LIMIT; 
	public void crawlPage() {
		//队列不为空，任务不停止，为空则该函数结束执行，调用的线程会暂时休息
		while(!ru.isQueEmpty("mws.uid.crawlQue")){
			String uid = "";
			try {
				uid = ru.popUidFromCrawlQue();	//这里也可能出错！
				if(StringUtil.isBlank(uid))
					continue;
				logger.info("开始爬取uid="+uid+"的相关页面！");
				crawlUserInfoPage(uid);
				crawlUserWeiboInfoPage(uid);
				crawlUserFansInfoPage(uid);
				crawlUserFollowInfoPage(uid);
				logger.info("成功爬取uid="+uid+"的相关页面！");
			} catch (Exception e) {
				logger.error("抓取uid="+uid+"的页面发生错误，暂停该用户的抓取！");
//				ru.addUidToCrawlQue(uid);	//抓取失败不放回
				e.printStackTrace();
				continue;
			}
		}
		
	}
	public void crawlUserInfoPage(String uid) {
		String url = "http://weibo.cn/"+uid+"/info";
		try {
			String page = gpu.getPageSourceOfMW(url);
			if(StringUtil.isBlank(page)){
				logger.error("获取uid="+uid+" 用户信息 页面为空！");
				throw new Exception("获取 用户信息 页面为空！");
			}
			ru.addUserInfoPage(page);
			logger.info("抓取uid="+uid+" 用户信息页面 完成");
		} catch (Exception e) {
			logger.info("爬取UserInfo页面失败！");
			e.printStackTrace();
		}
	}

	public void crawlUserWeiboInfoPage(String uid) throws Exception {
		String url = "http://weibo.cn/"+uid;
		String startPage = gpu.getPageSourceOfMW(url);
		if(StringUtil.isBlank( startPage) ){
			logger.error("获取uid="+uid+" 用户微博信息 起始页面为空！");
			throw new Exception("获取 用户微博信息 起始页面为空！");
		}
		int pageNum = JsoupUtil.parsePagelistPaNum( startPage );//1获取页码
		if( pageNum==0 )
			throw new Exception("从uid="+uid+" 用户微博信息页面 的起始页面中 没有解析到页码！");
		if(pageNum>crawlPageLimit)
			pageNum = crawlPageLimit;//页面限制
		
		for(int pi = 1;pi<=pageNum;pi++){//2开始page循环，逐页抓取
			String pageUrl = url+"?page="+pi;
			String referer = url+"?page="+(pi-1);
			String page = gpu.getPageSourceOfMW(pageUrl,referer,null);
			if(StringUtil.isBlank(page))
				logger.error("获取uid="+uid+"第"+pi+"页 用户微博页面 为空！");
			else
				ru.addUserWeiboPage(page);//3爬取结果存入radis数据库
		}
		logger.info("抓取uid="+uid+" 用户微博页面 完成，抓取页数："+pageNum);
	}
	public void crawlUserFansInfoPage(String uid) throws Exception{
		String url = "http://weibo.cn/"+uid+"/fans";
		String startPage = gpu.getPageSourceOfMW(url);
		if(StringUtil.isBlank( startPage) ){
			logger.error("获取uid="+uid+" 用户粉丝信息 起始页面为空！");
			throw new Exception("获取 用户粉丝信息 起始页面为空！");
		}
		int pageNum = JsoupUtil.parsePagelistPaNum( startPage );//1获取页码
		for(int pi = 1;pi<=pageNum;pi++){//2开始page循环，逐页抓取
			String pageUrl = url+"?page="+pi;
			String referer = url+"?page="+(pi-1);
			String page = gpu.getPageSourceOfMW(pageUrl,referer,null);
			if(StringUtil.isBlank(page))
				logger.error("获取uid="+uid+"第"+pi+"页 用户粉丝页面 为空！");
			else
				ru.addUserFansPage(page);//3爬取结果存入radis数据库
		}
		logger.info("抓取uid="+uid+" 用户粉丝页面 完成，抓取页数："+pageNum);
	}
	public void crawlUserFollowInfoPage(String uid) throws Exception{
//		http://weibo.cn/1618051664/fans
		String url = "http://weibo.cn/"+uid+"/follow";		
		String startPage = gpu.getPageSourceOfMW(url);
		if(StringUtil.isBlank( startPage) ){
			logger.error("获取uid="+uid+" 用户关注信息 起始页面为空！");
			throw new Exception("获取 用户关注信息 起始页面为空！");
		}
		int pageNum = JsoupUtil.parsePagelistPaNum( startPage );//1获取页码
		for(int pi = 1;pi<=pageNum;pi++){//2开始page循环，逐页抓取
			String pageUrl = url+"?page="+pi;
			String referer = url+"?page="+(pi-1);
			String page = gpu.getPageSourceOfMW(pageUrl,referer,null);
			if(StringUtil.isBlank(page))
				logger.error("获取uid="+uid+"第"+pi+"页 用户关注页面 为空！");
			else
				ru.addUserFollowPage(page);//3爬取结果存入radis数据库
		}
		logger.info("抓取uid="+uid+" 用户关注页面 完成，抓取页数："+pageNum);
	}
	
	
	public static void main(String[] args) {
		crawlPageByUid cpbu = new crawlPageByUid();
		cpbu.crawlPage();

	}

}
