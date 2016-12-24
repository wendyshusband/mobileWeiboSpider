package com.sina.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.tools.JavaFileManager.Location;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.mweibo.crawl.crawlHotWeibos;
import com.sina.pojos.CookieStorePojo;
import com.sina.pojos.proxyPojo;

public class getPageUtil {
	public static Logger logger = Logger.getLogger(getPageUtil.class);
//	public static DefaultHttpClient gpuStaticHttpClient = new DefaultHttpClient();//静态客户端(不保存cookie)
	//工具函数	
	/**流转换成字符串，不添加回车符号，方便解析
	 */
	public static String streamToString(InputStream is, String charset) {
		if(charset == null)
			charset = "utf-8";///////////////////////////
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName(charset)));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line );
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	public static HttpGet pretendBrowserHeaderGet(HttpGet httpGet){
		httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0");
		//如果使用多cookie策略  可以实行不同的登录方法，可以改进
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		httpGet.setHeader("Accept-Encoding", "identity");//编码方式！！
		httpGet.setHeader("Connection", "keep-alive");
		return httpGet;
	}
	public static HttpPost pretendBrowserHeaderPost(HttpPost httpPost){
		httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64;rv:43.0) Gecko/20100101 Firefox/43.0");
		httpPost.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpPost.addHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate");
		httpPost.addHeader("Connection", "keep-alive");
		return httpPost;
		
	}
	public String getAPage(String url){
		String content="";
		HttpGet get = new HttpGet(url);
		try {
			MyHttpConnectionManager.setHandleRedirect(httpClient, true);
			HttpResponse response = httpClient.execute(get);
//			checkHttpResponse(response);
			HttpEntity entity = response.getEntity();
			if(entity!=null)
				content = EntityUtils.toString(entity);
			get.abort();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
	public  String getAPage(String url,String cookieStr,boolean ifPretendBrowse){
		String content="";
		HttpGet get = new HttpGet(url);
		if(StringUtils.isNotBlank(cookieStr))
			get.setHeader("Cookie", cookieStr);
		if(ifPretendBrowse)
			get = pretendBrowserHeaderGet(get);
		try {
			MyHttpConnectionManager.setHandleRedirect(httpClient, false);
			HttpResponse response = httpClient.execute(get);
			checkHttpResponse(response);
			HttpEntity entity = response.getEntity();
			if(entity!=null)
				content = EntityUtils.toString(entity);
			get.abort();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
//	private static String checkMwCookieUseableUrl = "http://weibo.cn/2803301701/info";//人民日报的用户信息页面
//	public  boolean checkMwCookieUseable(String cookieStr){
////		if(1<2)return true;
////		System.out.println("cookieStr:"+cookieStr);
//		HttpGet get = new HttpGet(checkMwCookieUseableUrl);
//		get.setHeader("Host", "weibo.cn");//主机名，可能是http://或者https://开头
//		pretendBrowserHeaderGet(get);
//		get.setHeader("Cookie", cookieStr);
//		try {
//			MyHttpConnectionManager.setHandleRedirect(httpClient, false);
//			HttpResponse response = httpClient.execute(get);
//			get.abort();
////			checkHttpResponse(response);
//			Header headerLocation = response.getFirstHeader("Location");
//			if(headerLocation != null){//跳转到登录界面
//				String location = headerLocation.getValue();
//				if(location.contains("login.weibo.cn/login")){
//					logger.warn("校验cookie时发生 登录跳转 ，cookie可能失效，尝试重新获取cookie");
//					return false;//先返回空				
//				}
//				else if(location.contains("weibo.cn/pub/")){
//					logger.warn("校验cookie时发生 weibo.cn/pub/ 跳转");
//					return false;
//				}
//				else{
//					logger.warn("校验cookie时发生跳转 location:"+location);
//					return false;
//				}
//			}
//			logger.info("校验cookie可用性成功！");
//			return true;
//		} catch (Exception e) {
//			logger.info("校验cookie有效性发生错误！");
//			e.printStackTrace();
//			return false;
//		}
//	}
//	public int checkMwCookieStatu(String cookieStr){
////		if(1<2)return true;
////		System.out.println("cookieStr:"+cookieStr);
//		HttpGet get = new HttpGet(checkMwCookieUseableUrl);
//		get.setHeader("Host", "weibo.cn");//主机名，可能是http://或者https://开头
//		pretendBrowserHeaderGet(get);
//		get.setHeader("Cookie", cookieStr);
//		try {
//			MyHttpConnectionManager.setHandleRedirect(httpClient, false);
//			HttpResponse response = httpClient.execute(get);
////			checkHttpResponse(response);
//			Header headerLocation = response.getFirstHeader("Location");
//			get.abort();
//			if(headerLocation != null){//跳转到登录界面
//				String location = headerLocation.getValue();
//				if(location.contains("login.weibo.cn/login")){
//					logger.warn("校验cookie时发生 登录跳转 ，cookie可能失效，尝试重新获取cookie");
//					return StaticValue.COOKIE_STATU_UNUSABLE;//返回不可用				
//				}
//				else if(location.contains("weibo.cn/pub/")){
//					logger.warn("校验cookie时发生 weibo.cn/pub/ 跳转，可能暂时不可用");
//					return StaticValue.COOKIE_STATU_SUSPENDED;
//				}
//				else{
//					logger.warn("校验cookie时发生跳转,可能暂时不可用！ location:"+location);
//					return StaticValue.COOKIE_STATU_SUSPENDED;
//				}
//			}
//			logger.info("校验cookie可用性成功！");
//			return StaticValue.COOKIE_STATU_USABLE;
//		} catch (Exception e) {
//			logger.info("校验cookie有效性发生错误！请检查错误！ 如:联网超时");
//			e.printStackTrace();
//			return StaticValue.COOKIE_STATU_SUSPENDED;//暂时确定为暂停
//		}
//	}
	public static void checkHttpResponse(HttpResponse response){
		System.out.println(response.getStatusLine());
		System.out.println(response.getParams());
		System.out.println(response.getProtocolVersion());
		
		Header[] allHeaders = response.getAllHeaders();
		for(Header header:allHeaders){
			System.out.println(header);
		}
	}
	public static String getUserIdFromHomePageLink(String HomePageLink){
		String userId = null;
		return userId;
	}

////////////////////////////////////////////dynamic func&var ////////////////////////////////	
	private getCookieUtil gcu = new getCookieUtil();
	private DefaultHttpClient httpClient = MyHttpConnectionManager.getHttpClient();	//后期升级动态client，每个工具对象的client可能不同
	private CookieStorePojo csp; //每个对象获得的cookie可能不同（动态分配）
	
	//直接构造出的对象，cookieStore为空（用于不使用cookie访问）
	public getPageUtil() {
		
	}
	//该方法获得的对象包含一个cookieStore（用于登录访问）
	public static getPageUtil getPageUtil(){
		getPageUtil gpu = new getPageUtil();
		gpu.csp = gpu.gcu.getCookieStore();
		return gpu;
	}

	public void setProxy(proxyPojo pp) {
		String proxyHost = pp.getIp();
		int proxyPort = Integer.valueOf(pp.getPort());
		HttpHost proxy = new HttpHost(proxyHost, proxyPort);
		httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(DefaultHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	/**更换该实体对象绑定的cookieStore，获得一个新的cookieStore
	 */
	public void changeStoreCookie(String reason){
		logger.info("开始更换gpu绑定的 账号序号为："+csp.getAccountNum()+"的cookieStore！更换原因："+reason);
		csp = gcu.getCookieStore();
		logger.info("更换的账号序号为:"+csp.getAccountNum());
	}
//手机版爬虫最新方法////////////////////////////////////////////////////////////
	/**获得一个响应，需要url和referer（跳转之前的链接）两个参数，返回response
	 */
	public HttpResponse getHttpResponseOfMW(String url, String referer) throws Exception {
		HttpGet get = new HttpGet(url);// 发送一个get请求
//		get.setHeader("Host", "weibo.cn");//主机名，可能是http://或者https://开头
		get = pretendBrowserHeaderGet(get);
		if (StringUtils.isNotBlank(referer))
			get.setHeader("Referer", referer);
		if(csp.getRemainConnectTime()<=0)	//判别是否超过连接次数
			changeStoreCookie("cookie超过连接次数需要更换");
		get.setHeader("Cookie", csp.getMwCookieStr() ); //使用静态方法获得csp的cookie
//		get.setHeader("Cookie", CookieStorePojo.getMwCookieStr(csp) ); //使用静态方法获得csp的cookie
		MyHttpConnectionManager.setHandleRedirect(httpClient, false);//需要关闭跳转
		HttpResponse response = httpClient.execute(get);
//		try {
//			response = httpClient.execute(get);
//		} catch (Exception e) {
//			logger.error("httpClient执行get时出现异常！");
//			e.printStackTrace();
//		}
//		checkHttpResponse(response);
		//可以设置如果长度某个阈值（如10000）则不需要检查location
		Header headerLocation = response.getFirstHeader("Location");//检查跳转情况
		if(headerLocation != null){//跳转到登录界面
			logger.warn("获取页面发生跳转，原始访问链接："+url);
			String location = headerLocation.getValue();
			if(location.contains("login.weibo.cn/login")){
				logger.warn("获取页面时发生 登录跳转 ，cookie可能失效，尝试重新获取cookie");
				csp = gcu.getCookieStore();			
			}
			else if(location.contains("m.weibo.cn/security")){
				logger.warn("获取页面时发生 账号异常跳转 ，cookie可能失效，尝试重新获取cookie");
				csp = gcu.getCookieStore();			
			}
			else if(location.contains("weibo.cn/pub/")){
				logger.warn("获取页面时发生 weibo.cn/pub/ 跳转 location:"+location);
			}
			else {
				logger.warn("获取页面时发生跳转 location："+location);
			}
			if(gcu == null){
				logger.error("getPageUtil中的getCookieUtil未初始化！对象为空！无法检测cookie有效性！");
				return null;
			}
			if(!(gcu.checkMwCookieStatu(csp.getMwCookieStr()) == StaticValue.COOKIE_STATU_USABLE))
				changeStoreCookie("获取页面发生跳转！检测cookie异常或不可用！"); //检测cookie正常，不正常则置换cookie，正常则可能是url或者参数错误导致的跳转,不用理会
			return null; //发生跳转，都返回空
		}
//		System.out.println(headerLocation.getValue());
		return response;
	}
	//模拟手机访问手机web 微博网站
	public String getPageSourceOfMW(String url,String referer,String charset) throws Exception{
		//1检查cookie存在
		if(csp == null){
			csp = gcu.getCookieStore();//cookie为空则用cookie工具获得一个cookie
			if(csp==null){//仍然为空，则无法获得有效的cookieStore
				logger.error("无法获得cookie，访问失败！");
				return null;
			}
		}
		//2获取response，检测其是否为空，为空则尝试重新登录
		HttpResponse response = getHttpResponseOfMW(url, referer);
		if(response == null){//发生跳转错误，尝试重新登录获得一次response
			logger.error("response获取失败，可能cookie失效,准备更换cookie，尝试重新获取页面");
			if(csp==null)
				csp = gcu.getCookieStore();
			response = getHttpResponseOfMW(url, referer);
			if(response == null){
				logger.error("重新获取页面失败！");
				return null;
			}//如果response不为空则继续进行			
		}
		//3获取实体，返回结果
		if(charset == null)
			charset = "utf-8";
		HttpEntity entity = response.getEntity();
//		System.out.println("entity text:"+EntityUtils.toString(entity));
		if(entity==null){
			logger.warn("entity为空,response可能没有携带实体");
			return null;
		}
		String page = EntityUtils.toString(entity);
//		entity.getContent().close();//消耗实体并关闭，避免异常
		return page;
	}
	public String getPageSourceOfMW(String url) throws Exception{
		return getPageSourceOfMW(url,null,"utf-8");
	}
	/**使用手机web获取一个用户的ID
	 */
	
	
 	public static void main(String[] args) throws Exception {
// 		getPageUtil gpu = new getPageUtil();
// 		String page = getAPage("http://weibo.cn/1649173367/info");
// 		String page = getAPage("http://weibo.cn/1649173367/info", "SUB=_2A257waXwDeThGeNG61cU8izNyjiIHXVW1r44rDV6PUJbrdAKLXSnkWpLHetS_RW_km6fXBFD7XaHvf0qLwWsWQ..; _T_WM=512579fa3c9d3d5d039a62b0d1099659; gsid_CTandWM=4uKd52341Zf9zzNNj77SBomhs1Q; ", true);
// 		checkMwCookieUseable("SUB=_2A257waXwDeThGeNG61cU8izNyjiIHXVW1r44rDV6PUJbrdAKLXSnkWpLHetS_RW_km6fXBFD7XaHvf0qLwWsWQ..; _T_WM=512579fa3c9d3d5d039a62b0d1099659; gsid_CTandWM=4uKd52341Zf9zzNNj77SBomhs1Q; ");
// 		System.out.println(page);
	}
}
