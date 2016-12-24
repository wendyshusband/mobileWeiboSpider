package com.mweibo.crawl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.cookie.Cookie;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import redis.clients.jedis.Jedis;

import com.redis.MyJedisPool;
import com.sina.pojos.UserWeiboPojo;
import com.sina.util.FileUtil;
//import com.sina.util.HbaseUtil;
import com.sina.util.MyHttpConnectionManager;
import com.sina.util.RadisUtil;
import com.sina.util.ReadConfig;
import com.sina.util.StaticValue;
import com.sina.util.getCookieUtil;
import com.sina.util.getPageUtil;
import com.sina.util.parseUtil;

import net.sf.json.JSONObject;
 
public class crawlHotWeibos {
	public static Logger logger = Logger.getLogger(crawlHotWeibos.class);

	MyJedisPool pool = new MyJedisPool();
	Jedis jedis = pool.getJedis();
	static int crawlHotWeiboPageNum = 30;
	//加载剖析、页面、cookie、文件工具
	parseUtil pu = new parseUtil();
	getPageUtil gpu = new getPageUtil();	//该gpu直接构造，不会自动生成一个可用的cookieStore绑定gpu
//	getPageUtil gpu = getPageUtil.getPageUtil();
//	getCookieUtil gcu = new getCookieUtil();
	getCookieUtil gcu = getCookieUtil.getCookieUtil();
	FileUtil fu = new FileUtil();
	
	
	
	//get到结果如果出错（如不是json格式），则作标记为true，则会重新执行循环
	boolean wrongPage = false;	
	//程序会不断的翻页，指定最大页数（一般热门微博也就两三页）
	int maxPage = 100;

	/**
	 * 获得各时间段的热门评论（如1小时，24小时等）
	 * 
	 * @param timeCode
	 *            时间段的编码
	 * @return
	 * @throws Exception
	 */

	//新方法//////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**新方法：从手机web端获取微博信息
	 */
	public List<UserWeiboPojo> getHotWeibosFromWebMobile() throws Exception{
		logger.info("开始从手机web版爬取微博：");
		List<UserWeiboPojo> weibos = new ArrayList<UserWeiboPojo>();
		
//		String cookie = gcu.getMWCookieFromFile();
//		String cookie = "";
		int page = 1;
		for(page = 1;page<= crawlHotWeiboPageNum ;page++){
			String content = gpu.getAPage("http://weibo.cn/pub/topmblog?page="+page);//没有使用cookie
//			System.out.println(content);
			weibos = parseMobileWeiboInfo(content,weibos);//解析一页并且将所有微博加入结果集合
//			System.out.println("page"+page+content);
		}
		logger.info("成功从手机web端爬取热门微博"+(page-1)+"页");
		storeInfo(weibos);
		return weibos;
	}
	
	/**解析一页并且将所有微博加入结果集合，返回解析好的微博数组
	 */
	public List<UserWeiboPojo> parseMobileWeiboInfo(String content,List<UserWeiboPojo> weibos){
		int weiboNum = 0;
		Document doc = Jsoup.parse(content);// 解析html
		Elements esClassC = doc.select("div.c");
		//对每一个微博节点进行解析
		for (Element eClassC : esClassC) {
			UserWeiboPojo wpj = new UserWeiboPojo();
			
			//微博id和用户id
			String wid = eClassC.attr("id");
			if(StringUtils.isBlank(wid))//不一定所有class=c的div都是包含微博信息的，没有的就不是微博节点
				continue;
			wpj.setWid(wid);

			Element childNode;
			String eClassCText = eClassC.text();
			if(eClassCText.contains("该微博已被删除")) //异常微博
				continue;
			//1.用戶id:uid
			childNode = eClassC.select("a.nk").first();
			if( childNode != null ){
				String homePageLink = childNode.attr("href");
				String userId = pu.matchStringGroup1(homePageLink, "http://weibo.cn/u/(\\d+)");//http://weibo.cn/u/的开头可以直接获取微博ID
				if(StringUtils.isBlank(userId))	//情况异常，不能直接获取用户ID
					userId = getPageUtil.getUserIdFromHomePageLink(homePageLink);
				wpj.setUid(userId);
//				System.out.println(childNode.text()+":"+userId);
			}
			//2微博内容信息:content
			childNode = eClassC.select("span.ctt").first();
			if( childNode != null){
				String wbContent = childNode.text().trim();
				if(StringUtils.isNotBlank(wbContent)){
					wbContent = wbContent.substring(1, wbContent.length());//去掉冒号
					wpj.setContent(wbContent);
//					System.out.println(wbContent);
				}
				else
					continue;//空白微博
			}
			//2.2获取媒体地址
			String thumbnailPic =eClassC.select("div").select("a").select("img.ib").attr("src");
			wpj.setMediaSrc(thumbnailPic);
			//3.设置时间
			childNode = eClassC.select("span.ct").first();//时间戳
			if( childNode!=null ){
				String timeStr = childNode.text();
				//System.out.println("time="+timeStr);
				String wbTime = "";
//				System.out.println("timeStr"+timeStr);
				if(pu.isMatch(timeStr, "分钟前")){//1： x分钟前格式时间戳
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(new Date());
					calendar.add(Calendar.MINUTE, +1);//+1今天的时间加一天		
					Date date = calendar.getTime();		
					wbTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
					//System.out.println("分钟前"+timeStr);
				}
				else if(pu.isMatch(timeStr, "刚刚")){//2： “刚刚” 格式时间戳
					wbTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
					//System.out.println("刚刚"+timeStr);
				}
				else //3: XXXX年XX月XX日 或者 XX月XX日 或者 今天 XX:XX 
				{
					if(pu.isMatch(timeStr, "今天")){
						wbTime = new SimpleDateFormat("yyyy-MM-dd ").format(new Date());
						//System.out.println("今天"+timeStr);
					}
					else if(pu.isMatch(timeStr, "\\d{4}-\\d{2}-\\d{2}")){
						wbTime += pu.matchStringGroup1(timeStr, "(\\d+)-")+"-";
						wbTime += pu.matchStringGroup1(timeStr, "-(\\d\\d)-")+"-";
						wbTime += pu.matchStringGroup1(timeStr, "-(\\d\\d) ")+" ";
						//System.out.println("年份"+timeStr);
					}
					else if(pu.isMatch(timeStr, "\\d{2}月\\d{2}日")){
						wbTime += new SimpleDateFormat("yyyy-").format(new Date());
						wbTime += pu.matchStringGroup1(timeStr, "(\\d\\d)月")+"-";
						wbTime += pu.matchStringGroup1(timeStr, "(\\d\\d)日")+" ";
						//System.out.println("几月几日"+timeStr);
					}
					else
						wbTime = "";
					//获取时间
					//System.out.println("wbtimefirst="+wbTime);
					//System.out.println("timestr="+timeStr);
					wbTime += pu.matchStringGroup1(timeStr, "(\\d\\d:\\d\\d)");
					
				}
				String wbSource = pu.matchStringGroup1(timeStr, "来自(.+)");
			//	System.out.println("微博发布时间="+wbTime);
			//	System.out.println("微博来源="+wbSource);
				wpj.setTime(wbTime);
				wpj.setSource(wbSource);
			}			
			//4.收集 赞、转发、评论数
			String likeCountStr = pu.matchStringGroup1(eClassCText, "赞\\[(\\d+)\\]") ;
			String repostCountStr = pu.matchStringGroup1(eClassCText, "转发\\[(\\d+)\\]");
			String commentCountStr = pu.matchStringGroup1(eClassCText, "评论\\[(\\d+)\\]");
			wpj.setLikeCount(likeCountStr);
			wpj.setRepostCount(repostCountStr);
			wpj.setCommentCount(commentCountStr);
//			System.out.println("likeCountStr:"+likeCountStr);
//			System.out.println("repostCountStr:"+repostCountStr);
//			System.out.println("commentCountStr:"+commentCountStr);
			//5.获取微博ID 和 用户ID 或者 转发的原始用户ID和微博ID  如果有两种a.cc,
			wpj.setIsOriginal(true);
			for(int i = 0;i<eClassC.select("a.cc").size();i++ ){
				childNode = eClassC.select("a.cc").get(i);
				if(pu.isMatch(childNode.text(), "原文评论")){ //非原创
					String commentLink = childNode.attr("href");
					String owid = pu.matchStringGroup1(commentLink, "http://weibo.cn/comment/(\\w+)\\?");
//					String ouid = pu.matchStringGroup1(commentLink, "http://weibo.cn/comment/\\w+\\?uid=(\\d+)");
					String ouHomePageLink = eClassC.select("span.cmt").first().select("a").first().attr("href");
					String ouid = getPageUtil.getUserIdFromHomePageLink(ouHomePageLink);
					String rcmt = pu.matchStringGroup1(eClassCText, "转发理由:(.+?)赞");
//					System.out.println(owid);
//					System.out.println(ouid);
//					System.out.println(commentLink);
			//		System.out.println(rcmt);
					wpj.setIsOriginal(false);
//					wpj.setOwid(owid);
//					wpj.setOuid(ouid);
//					wpj.setRcmt(rcmt);//转发评论
				}
				else if(pu.isMatch(childNode.text(), "评论")){//原创
					String commentLink = childNode.attr("href");
					wid = pu.matchStringGroup1(commentLink, "http://weibo.cn/comment/(\\w+)\\?");
					String uid = pu.matchStringGroup1(commentLink, "http://weibo.cn/comment/\\w+\\?uid=(\\d+)");
//					System.out.println(wid);
//					System.out.println(uid);
					wpj.setWid(wid);
					wpj.setUid(uid);
				}
			}
			System.out.println(wpj);
			System.out.println("解析到热门微博如上！");
			weibos.add(wpj);
		}
		return weibos;
	}
	
	public boolean storeInfo(List<UserWeiboPojo> weibos) throws IOException{
		RadisUtil ru = new RadisUtil();
		if(weibos == null || weibos.size()==0){
			logger.info("没有获得热门微博，存储失败！");
			return false;
		}
		for(UserWeiboPojo weibo:weibos){
			//1存储所有的uid 和 wid到radis
//			System.out.println(weibo.toString());
			ru.addUid(weibo.getUid());//存储uid到radis
			ru.addWid(weibo.getWid());//存储wid到radis
			//test
			//ru.addUidToCrawlQue(weibo.getUid());
			//ru.addWidToCrawlQue(weibo.getWid());
			//2.存储爬取的weiboInfo到文件
			System.out.println(weibo.toJson());
			FileUtil fu = new FileUtil();
			if(weibo.getUid() !=null && weibo.getUid().hashCode() != "".hashCode()){
				fu.writeFileAppend(weibo.toJson(), "./weiboUser/hot/"+weibo.getUid()+"/weibo/weibo.json" );
			}else{
				System.err.println("由于用户ID缺失，不储存该热门微博！");
			}
		}
		logger.info("存储热门微博成功！");
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		crawlHotWeibos chw = new crawlHotWeibos();
		chw.getHotWeibosFromWebMobile();
	}

}

	