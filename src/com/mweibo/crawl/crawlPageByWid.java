package com.mweibo.crawl;

import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;

import com.redis.MyJedisPool;
import com.sina.util.JsoupUtil;
import com.sina.util.RadisUtil;
import com.sina.util.getPageUtil;

import redis.clients.jedis.Jedis;

public class crawlPageByWid {
	public static Logger logger = Logger.getLogger(crawlPageByWid.class);
	RadisUtil ru = new RadisUtil();
//	getPageUtil gpu = new getPageUtil();
	getPageUtil gpu = getPageUtil.getPageUtil();
	private final int crawlPageLimit = 20; 
	public void crawlPage() {
		//队列不为空，任务不停止，为空则该函数结束执行，调用的线程会暂时休息
		while(!ru.isQueEmpty("mws.wid.crawlQue")){
			String wid = "";
			try {
				wid = ru.popWidFromCrawlQue();	//这里也可能出错！
				if(StringUtil.isBlank(wid))
					continue;
				logger.info("开始爬取wid="+wid+"的相关 微博评论、转发 页面！");
				crawlWeiboCommentInfoPage(wid);
				crawlWeiboRepostInfoPage(wid);
				logger.info("成功爬取wid="+wid+"的 微博评论、转发 页面！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("抓取wid="+wid+"的页面发生错误，暂停该微博的抓取！");
//				ru.addWidToCrawlQue(wid);  //抓取失败不放回
				e.printStackTrace();
				continue;
			}
		}
		
	}
	public void crawlWeiboCommentInfoPage(String wid) throws Exception{
//		http://weibo.cn/comment/DiBcBuGFn?page=3
		String url = "http://weibo.cn/comment/"+wid;		
		String startPage = gpu.getPageSourceOfMW(url);
		if(StringUtil.isBlank( startPage) ){
			logger.error("获取wid="+wid+" 微博评论 起始页面为空！");
			throw new Exception("获取wid="+wid+" 微博评论 起始页面为空！");
		}
		int pageNum = JsoupUtil.parsePagelistPaNum( startPage );//1获取页码
		if( pageNum==0 )
			pageNum = 1; //尝试解析第一页
//			throw new Exception("从wid="+wid+" 微博评论页面 的起始页面中 没有解析到页码！");//可能只有一页
		if(pageNum>crawlPageLimit)
			pageNum = crawlPageLimit;//页面限制
		for(int pi = 1;pi<=pageNum;pi++){//2开始page循环，逐页抓取
			String pageUrl = url+"?page="+pi;
			String page = gpu.getPageSourceOfMW(pageUrl);
			if(StringUtil.isBlank(page))
				logger.error("获取wid="+wid+"第"+pi+"页 微博评论页面 为空！");
			else
				ru.addWeiboCommentPage(page);//3爬取结果存入radis数据库
		}
		logger.info("抓取wid="+wid+" 微博评论页面 完成，抓取页数："+pageNum);
	}

	public void crawlWeiboRepostInfoPage(String wid) throws Exception{
//		http://weibo.cn/repost/DiBcBuGFn
		String url = "http://weibo.cn/repost/"+wid;		
		String startPage = gpu.getPageSourceOfMW(url);
		if(StringUtil.isBlank( startPage) ){
			logger.error("获取wid="+wid+" 微博转发 起始页面为空！");
			throw new Exception("获取wid="+wid+" 微博转发 起始页面为空！");
		}
		int pageNum = JsoupUtil.parsePagelistPaNum( startPage );//1获取页码
		if( pageNum==0 )
			pageNum = 1; //尝试解析第一页
//			throw new Exception("从wid="+wid+" 微博评论页面 的起始页面中 没有解析到页码！");//可能只有一页
		if(pageNum>crawlPageLimit)
			pageNum = crawlPageLimit;//页面限制
		for(int pi = 1;pi<=pageNum;pi++){//2开始page循环，逐页抓取
			String pageUrl = url+"?page="+pi;
			String page = gpu.getPageSourceOfMW(pageUrl);
			if(StringUtil.isBlank(page))
				logger.error("获取wid="+wid+"第"+pi+"页 微博转发页面 为空！");
			else
				ru.addWeiboRepostPage(page);//3爬取结果存入radis数据库
		}
		logger.info("抓取wid="+wid+" 微博评论转发 完成，抓取页数："+pageNum);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		crawlPageByWid crawlPageByWid = new crawlPageByWid();
		crawlPageByWid.crawlPage();
	}

}
