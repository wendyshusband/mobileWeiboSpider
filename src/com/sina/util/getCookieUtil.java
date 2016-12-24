package com.sina.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AbstractDocument.Content;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;

import com.mweibo.task.UpdateCookieTask;
import com.sina.pojos.CookieStorePojo;
import com.sina.util.parseUtil;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.codec.binary.Base64;

/**
 * 获取用户cookie，并保存在cookie文件中
 * 
 * @author tzl
 *
 */

public class getCookieUtil {
	public static Logger logger = Logger.getLogger(getCookieUtil.class);
	public static int cookieIdx = 0;//颁发的cookie的序号，每次颁发后会+1
	private static CookieStorePojo[] cookieStores = new CookieStorePojo[StaticValue.count_of_login_user];//cookie统一管理

	DefaultHttpClient httpClient = new DefaultHttpClient();	//自己的client
	public ReadConfig rc = new ReadConfig("crawl.properties", true);
	public RadisUtil ru = new RadisUtil();

	public static getCookieUtil getCookieUtil(){
		return new getCookieUtil();
	}
	/**将response的set-cookie解析，解析出的cookie加入csp
	 */
	private static CookieStorePojo parseSet_Cookie(CookieStorePojo csp,HttpResponse response){
		Header[] headers = response.getHeaders("Set-Cookie");
		for(Header header:headers){
			String setCookieStr = header.getValue();
			String[] setCookieStrSplit = setCookieStr.split(";");
			String nameValueStr = setCookieStrSplit[0];
			String name = nameValueStr.split("=")[0];
			String value = nameValueStr.split("=")[1];
			BasicClientCookie cookie = new BasicClientCookie(name, value);
			for(String kvStr:setCookieStrSplit){
				if(!kvStr.contains("="))//不能被=号划分成两半
					continue;
				String k = kvStr.split("=")[0].trim();
				String v = kvStr.split("=")[1].trim();
				if(k.contains("domain")){
					cookie.setDomain(v);
				}
			}
			csp.addCookie(cookie);
		}
		System.out.println("cspSize"+csp.getCookies().size());
		return csp;
	}
	
	private static String checkMwCookieUseableUrl = "http://weibo.cn/2803301701/info";//人民日报的用户信息页面
	public  boolean checkMwCookieUseable(String cookieStr){
//		if(1<2)return true;
//		System.out.println("cookieStr:"+cookieStr);
		HttpGet get = new HttpGet(checkMwCookieUseableUrl);
		get.setHeader("Host", "weibo.cn");//主机名，可能是http://或者https://开头
		get = getPageUtil.pretendBrowserHeaderGet(get);
		get.setHeader("Cookie", cookieStr);
		try {
			MyHttpConnectionManager.setHandleRedirect(httpClient, false);
			HttpResponse response = httpClient.execute(get);
			get.abort();
//			checkHttpResponse(response);
			Header headerLocation = response.getFirstHeader("Location");
			if(headerLocation != null){//跳转到登录界面
				String location = headerLocation.getValue();
				if(location.contains("login.weibo.cn/login")){
					logger.warn("校验cookie时发生 登录跳转 ，cookie可能失效，尝试重新获取cookie");
					return false;//先返回空				
				}
				else if(location.contains("weibo.cn/pub/")){
					logger.warn("校验cookie时发生 weibo.cn/pub/ 跳转");
					return false;
				}
				else{
					logger.warn("校验cookie时发生跳转 location:"+location);
					return false;
				}
			}
			logger.info("校验cookie可用性成功！");
			return true;
		} catch (Exception e) {
			logger.info("校验cookie有效性发生错误！");
			e.printStackTrace();
			return false;
		}
	}
	public int checkMwCookieStatu(String cookieStr){
//		if(1<2)return true;
//		System.out.println("cookieStr:"+cookieStr);
		HttpGet get = new HttpGet(checkMwCookieUseableUrl);
		get.setHeader("Host", "weibo.cn");//主机名，可能是http://或者https://开头
		get = getPageUtil.pretendBrowserHeaderGet(get);
		get.setHeader("Cookie", cookieStr);
		try {
			MyHttpConnectionManager.setHandleRedirect(httpClient, false);
			HttpResponse response = httpClient.execute(get);
//			checkHttpResponse(response);
			Header headerLocation = response.getFirstHeader("Location");
			get.abort();
			if(headerLocation != null){//跳转到登录界面
				String location = headerLocation.getValue();
				if(location.contains("login.weibo.cn/login")){
					logger.warn("校验cookie时发生 登录跳转 ，cookie可能失效，尝试重新获取cookie");
					return StaticValue.COOKIE_STATU_UNUSABLE;//返回不可用				
				}
				else if(location.contains("weibo.cn/pub/")){
					logger.warn("校验cookie时发生 weibo.cn/pub/ 跳转，可能暂时不可用");
					return StaticValue.COOKIE_STATU_SUSPENDED;
				}
				else{
					logger.warn("校验cookie时发生跳转,可能暂时不可用！ location:"+location);
					return StaticValue.COOKIE_STATU_SUSPENDED;
				}
			}
			logger.info("校验cookie可用性成功！");
			return StaticValue.COOKIE_STATU_USABLE;
		} catch (Exception e) {
			logger.info("校验cookie有效性发生错误！请检查错误！ 如:联网超时");
			e.printStackTrace();
			return StaticValue.COOKIE_STATU_SUSPENDED;//暂时确定为暂停
		}
	}

	
//////////////////////////////////////////////////////////////
	//新部分！！新方法！！
///////////////////////////////////////////////////////////////	

//2个公用方法，获取cookie或者changeCookie，依赖于getPageUtil
	/**1.颁发一个cookieStorePojo，获取策略为：
	 * （1）不断从radis中获取cookie，然后验证cookie，成功返回，失败则 删除radis的cookie，继续从radis获取直到为空
	 * （2）若radis中cookie为空，尝试使用账号依次登录，获取一个cookie，成功则返回
	 * （3）若依次使用账号登录不成功，失败返回null
	 * @throws Exception 
	 */
	public CookieStorePojo getCookieStore() {
		CookieStorePojo csp = null;
		int accSize = StaticValue.count_of_login_user;

		//1.尝试从radis数据库中获取cookie，获得到正常可用的cookie则返回
		for(int ci = 0;ci<StaticValue.count_of_login_user;ci++){
			int accIdx = (ci+cookieIdx)%StaticValue.count_of_login_user;
			csp = ru.getCookieStore( accIdx );//从cookieIdx开始尝试
			if(csp == null) //radis数据库中不存在
				continue;
			int cookieStatu = checkMwCookieStatu(csp.getMwCookieStr());
			if(cookieStatu == StaticValue.COOKIE_STATU_USABLE){
				updateCookieStoreAndSetCookieIdx(csp);
				return cookieStores[accIdx];
			}
			else if(cookieStatu == StaticValue.COOKIE_STATU_UNUSABLE)
				ru.delCookieStore(csp.getAccountNum());//cookie不可用,从radis中删除
			else	//else cookie暂停使用状态，不删除，继续循环
				continue;
		}
		logger.warn("从radis数据库中没有获取到有效的cookie！尝试登录获取！");
////		 * （1）不断从radis中获取cookie，然后验证cookie，成功返回，失败则 删除radis的cookie，继续从radis获取直到为空
//		while(ru.getCookieStoreSize() > 0){
//			CookieStorePojo randCookieStore = ru.getRandCookieStore();
//			if(getPageUtil.checkMwCookieUseable(randCookieStore.getMwCookieStr()) )
//				return randCookieStore;//可用则返回
//			ru.delCookieStore(randCookieStore.getAccountNum());//否则删除cookieStore
//		}
		
//		* （2）若radis中cookie为空或者全部不可用，尝试检查cookie，会登录更新cookies
		UpdateCookieTask.updateCookieTask.interrupt();
//		 * （2）若radis中cookie为空或者全部不可用，尝试使用账号依次登录，获取一个cookie，成功则返回
//		for(int ui=0;ui<accSize;ui++){
//			int accIdx = (ui+cookieIdx)%StaticValue.count_of_login_user;
//			logger.info("尝试使用第"+accIdx+"个账号登录获取cookie");
//			String username = rc.getValue("username"+accIdx);
//			String password = rc.getValue("password"+accIdx);
//			try {
//				csp = getCookieStoreByWeiboCn(accIdx,username,password);
//				if(csp==null){
//					logger.error("尝试登录失败！没有获取到cookie！");
//					return null;
//				}
//				int cookieStatu = getPageUtil.checkMwCookieStatu(csp.getMwCookieStr());
//				if(cookieStatu == StaticValue.COOKIE_STATU_USABLE){
//					updateCookieStoreAndSetCookieIdx(csp);
//					ru.updateCookieStore(cookieStores[accIdx]);	//放到数据库
//					return cookieStores[accIdx];//返回cookie
//				}
//				else if( cookieStatu == StaticValue.COOKIE_STATU_UNUSABLE){//不可用
//					logger.error("登录获取的cookie不可用！请测试登录方法是否有效！尝试使用下一个账号登录！");
//				}
//				else{//cookie可用但是异常
//					cookieStores[accIdx].copy(csp);	//放到内存
//					ru.updateCookieStore(cookieStores[accIdx]);	//放到数据库
//					logger.warn("使用第"+accIdx+"个账号登录获取cookie时，发现账号异常，已保存cookie，尝试使用下一个账号登录。");
//				}
//			} catch (Exception e) {
//				logger.error("通过weibo.cn获取账号"+accIdx+"的cookie发生错误!");
//				e.printStackTrace();
//			}
//		}
//		 * （3）若依次使用账号登录不成功，失败返回null
		logger.error("从radis和模拟登录都无法获取有效的cookie！请检查配置文件账号密码 或者 网络通信！");
		return null;
	}
	/**2.使用账号登录，获取的cookieStore会自动保存在radis和内存对象数组中
	 * 账号正常和异常都会返回cookieStore，账号登录获取的cookie无效则返回空
	 */
	public CookieStorePojo LoginWeiboCnAndStoreCookie(int accIdx){
		logger.info("尝试使用第"+accIdx+"个账号登录获取cookie");
		CookieStorePojo csp = null;
		String username = rc.getValue("username"+accIdx);
		String password = rc.getValue("password"+accIdx);
		try {
			csp = getCookieStoreByWeiboCn(accIdx,username,password);
			if(csp==null){
				logger.error("尝试登录失败！没有获取到cookie！");
				return null;
			}
			int cookieStatu = checkMwCookieStatu(csp.getMwCookieStr());
			if( cookieStatu == StaticValue.COOKIE_STATU_UNUSABLE){//不可用
				logger.error("登录获取的cookie不可用！请测试登录方法是否有效！");
				return null;
			}
			else{//cookie可用但是异常
				updateCookieStoreAndSetCookieIdx(csp);//放到内存
				ru.updateCookieStore(cookieStores[accIdx]);	//放到数据库
				if(cookieStatu == StaticValue.COOKIE_STATU_SUSPENDED)
					logger.warn("使用第"+accIdx+"个账号登录获取cookie时，发现账号异常，已保存cookie，尝试使用下一个账号登录。");
				return cookieStores[accIdx];//返回cookie
			}
		} catch (Exception e) {
			logger.error("通过weibo.cn获取账号"+accIdx+"的cookie发生错误!");
			e.printStackTrace();
			return null;
		}
	}

	/**更新内存中的cookieStore,同时设置新的cookieIdx (不包括更新radis数据库的cookie)
	 */
	public static void updateCookieStoreAndSetCookieIdx(CookieStorePojo csp){
		int accIdx = csp.getAccountNum();
		if(accIdx>=0 && accIdx<StaticValue.count_of_login_user){
			if(cookieStores[accIdx]==null)
				cookieStores[accIdx] = csp;
			else
				cookieStores[accIdx].copy(csp);
			cookieIdx = (accIdx+1)%StaticValue.count_of_login_user;//设置新的cookieIdx
		}
		else
			logger.error("更新内存中cookieStore时，传递的cookieStore包含的accountNum异常！更新失败！");
	}
//过渡区域，包装好cookieStore返回的private方法
	/**登录login.weibo.cn/login获得cookieStore
	 * @throws Exception 
	 */
	private static CookieStorePojo getCookieStoreByWeiboCn(int accIdx, String username, String password) throws Exception{
		return getCookieStoreByWeiboCn2016( accIdx,  username,  password);
	}
	private static CookieStorePojo getCookieStoreByWeiboCom(int accIdx, String username, String password){
		return null;
	}
	private static CookieStorePojo getCookieStoreBySinaComCn(int accIdx, String username, String password){
		return null;
	}

//具体实现方法（该部分可能会经常更新，获取cookie的方法可能经常改变）
	private static CookieStorePojo getCookieStoreByWeiboCn2016(int accIdx, String username, String password) throws Exception {
		CookieStorePojo csp = new CookieStorePojo(accIdx);
		
		String content = "";
		String loginUrl = "https://login.weibo.cn/login/";
		String location = "";//跳转
		
		Header[] headers;
		Header header;
		HttpGet httpGet;
		HttpEntity entity;
		HttpResponse response;
		
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		//1.1获取第一个登陆页面（登陆主页）
		MyHttpConnectionManager.setHandleRedirect(httpClient, false);//打开httpclient的跳转
		httpGet = new HttpGet(loginUrl);
		System.out.println("request line:" + httpGet.getRequestLine());
		response = httpClient.execute(httpGet);
		csp = parseSet_Cookie(csp, response);	//得到_T_WM （weibo.cn）
		httpGet.abort();
		

		for(int tryLoginTime = 1;tryLoginTime<=10;tryLoginTime++){
			content = EntityUtils.toString(response.getEntity(), "utf-8");
			// step1.1:根据得到login页面，得到构造好参数后，准备提交post请求
			String vk = parseUtil.matchStringGroup1(content, "name=\"vk\"\\svalue=\"([^\"]+)\"");
			// <input type="password" name="password_8576" size="30" />
			String passwordName = parseUtil.matchStringGroup1(content, "type=\"password\"\\sname=\"([^\"]+)\"");
			String mobile = username;
			String remenber = "";
			String backURL = "http%3A%2F%2Fweibo.cn";//跳转后目标页面
			String backTitle = "手机新浪网";
			String tryCount = "";//尝试登录次数
			String submit = "登录";
			// <form
			// action="?rand=115447141&amp;backURL=http%3A%2F%2Fweibo.cn%2F&amp;backTitle=%E5%BE%AE%E5%8D%9A&amp;vt=4&amp;revalid=2&amp;ns=1"
			// method="post">
			String action = parseUtil.matchStringGroup1(content, "form\\saction=\"([^\"]+)\"");
			String loginNewUrl = loginUrl + action;
	
			//获取好普通参数后，分析是否有验证码
			String captchaImgUrl = parseUtil.matchStringGroup1(content,"请输入图片中的字符:<br/><img src=\"([^\"]+)\"");
			String captcha = "";//验证码
			String capId = null;//验证码ID
			if(captchaImgUrl!=null && captchaImgUrl!=""){
				//如果有验证码，解析get的网页内容
				capId = parseUtil.matchStringGroup1(content, "name=\"capId\" value=\"([^\"]+)\"");
				//get验证码图片，输入验证码
				httpGet = new HttpGet(captchaImgUrl);
				response = httpClient.execute(httpGet);
				entity = response.getEntity();
				InputStream inputStream = entity.getContent();  
				OutputStream out = new FileOutputStream(new File(".\\output\\captcha.gif"));  
				                       
				int read = 0;  
				byte[] bytes = new byte[1024];  
				                       
				while ((read = inputStream.read(bytes)) != -1) {  
				    out.write(bytes, 0, read);  
				}  			                      
				inputStream.close();  
				out.flush();  
				out.close();  
				System.out.println("请输入验证码，图片地址.\\output\\captcha.gif!");
				
				//创建输入对象
				captcha = new Scanner(System.in).nextLine();
				System.out.println("输入的验证码为："+captcha);
			}
			
			//2获取第二个跳转页面（post请求）
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			// 加入post表单参数
			qparams.add(new BasicNameValuePair("mobile", username));
			qparams.add(new BasicNameValuePair(passwordName, password));
			qparams.add(new BasicNameValuePair("remember", remenber));
			qparams.add(new BasicNameValuePair("backURL", backURL));
			qparams.add(new BasicNameValuePair("backTitle", backTitle));
			qparams.add(new BasicNameValuePair("tryCount", tryCount));
			qparams.add(new BasicNameValuePair("vk", vk));
			qparams.add(new BasicNameValuePair("submit", submit));
			//插入验证码的参数
			if(capId!=null){
				qparams.add(new BasicNameValuePair("capId", capId));
				qparams.add(new BasicNameValuePair("code", captcha));
			}
			UrlEncodedFormEntity params = new UrlEncodedFormEntity(qparams, "utf-8");// 将参数列表List<NameValuePair>封装好,才可以进行post提交
	
			HttpPost post = new HttpPost(loginNewUrl);// 表单提交地址
			System.out.println("post loginNewUrl:" + loginNewUrl);
			post.setEntity(params);// 加入参数
			MyHttpConnectionManager.setHandleRedirect(httpClient, false);//暂停httpclient的跳转
			response = httpClient.execute(post);
			entity = response.getEntity();// 获得响应内容
			csp = parseSet_Cookie(csp, response);	//得到weibo.cn 的cookie
			post.abort();
	
			headers = response.getAllHeaders();
			for (int i = 0; i < headers.length; i++) {
				System.out.println("post2(weibo.cn):"+headers[i]);
			}
			header = response.getFirstHeader("Location");
			if(header == null){
				logger.info("第"+tryLoginTime+"登录失败！验证码输入错误！");
				continue;
			}
			else{
				location = header.getValue();
				break;//跳出验证码循环
				
			}
		}
		
		
		// step3:开始get第3个中转页面（get得到sina.cn的域名cookie）
		System.out.println("get3 location:" + location);
		HttpGet get = new HttpGet(location);
//		get.setConfig(requestConfig);// 设置不跳转4.4版本
		httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,false);//设置不跳转4.1版本
		get.addHeader("Host", "newlogin.sina.cn");
		get = getPageUtil.pretendBrowserHeaderGet(get);
		response = httpClient.execute(get);
		entity = response.getEntity();// 获得响应内容
//		csp = parseSet_Cookie(csp, response);
		get.abort();

		// header = response.getHeaders("Set-Cookie");
		headers = response.getAllHeaders();
		for (int i = 0; i < headers.length; i++) {
			System.out.println("get3_sina.cn:"+headers[i]);
		}
		
		// step4 get第四个页面，weibo.com的cookie
		location = response.getFirstHeader("Location").getValue();
		System.out.println("get4（weibo.com） location:" + location);
		get = new HttpGet(location);
//		get.setConfig(requestConfig);// 设置不跳转4.4版本
		httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,false);//设置不跳转4.1版本
		get.addHeader("Host", "passport.weibo.com");
		get = getPageUtil.pretendBrowserHeaderGet(get);
		response = httpClient.execute(get);
		csp = parseSet_Cookie(csp, response);
		get.abort();

		headers = response.getAllHeaders();
		for (int i = 0; i < headers.length; i++) {

			System.out.println("get4(weibo.com):"+headers[i].toString());
		}
		httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,true);//设置跳转4.1版本		
		
		//5.cookie写入文件
		FileUtil fu = new FileUtil();
		String mwCookieStr = csp.buildCookieStrByDomain(".weibo.cn");
		String webCookieStr = csp.buildCookieStrByDomain(".weibo.com");
		System.out.println("mwCooStr:"+mwCookieStr);
		
		logger.info("获得网页版cookie为：" + webCookieStr);
		fu.writeFile(webCookieStr, "./webCookie");
		if (!webCookieStr.isEmpty())
			logger.info("获取网页版cookie成功！保存在./webCookie文件中");
		else
			logger.info("获取网页版cookie失败");
		
		logger.info("获得手机版cookie为：" + mwCookieStr);
		fu.writeFile(mwCookieStr, "./mwCookie");
		if (!mwCookieStr.isEmpty())
			logger.info("获取手机版cookie成功！保存在./mwCookie文件中");
		else
			logger.info("获取手机版cookie失败");
		return csp;
	}

	
	/**使用下一个账号从手机版微博获得cookie
	 * @throws Exception 
	 */
	public String getMWCookieUseNextUser() throws Exception{
		String cookie;
		StaticValue.login_user_id = (StaticValue.login_user_id + 1) % StaticValue.count_of_login_user;
		logger.info("尝试使用账号 "+StaticValue.login_user_id+"从手机版-微博进行登录");
		ReadConfig rc = new ReadConfig("crawl.properties", true);
		String user = rc.getValue("username" + StaticValue.login_user_id);
		String pwd = rc.getValue("password" + StaticValue.login_user_id);
		cookie = getCookieFromMW2016(user, pwd);
		return cookie;
	}
	//网页版微博cookie默认存入cookie文件中,包括.weibo.com 和 .weibo.cn手机和网页版的cookie
	public String getCookieFromMW2016(String username, String password) throws Exception {
		String cookie = "";
		String mwcookie = "";
		String content = "";
		Header[] header;
		String loginUrl = "http://login.weibo.cn/login/";
		HttpGet httpGet;
		HttpEntity entity;
//		CloseableHttpResponse response;
		HttpResponse httpResponse;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		//获取起始参数
//		getFristParameters();
		
		MyHttpConnectionManager.setHandleRedirect(httpClient, false);//打开httpclient的跳转
		httpGet = new HttpGet(loginUrl);
		System.out.println("request line:" + httpGet.getRequestLine());
		httpResponse = httpClient.execute(httpGet);
//		response = httpClient.execute(httpGet);
		entity = httpResponse.getEntity();
		content = EntityUtils.toString(entity, "utf-8");
		// System.out.println(content);
		String _T_WM = httpResponse.getHeaders("Set-Cookie")[0].getValue();
		System.out.println("_T_WM: "+_T_WM);
		

		
		
		

		// step2:根据得到login页面，得到构造好参数后，开始提交post请求
		// 构造post请求
		String vk = parseUtil.matchStringGroup1(content, "name=\"vk\"\\svalue=\"([^\"]+)\"");
		// <input type="password" name="password_8576" size="30" />
		String passwordName = parseUtil.matchStringGroup1(content, "type=\"password\"\\sname=\"([^\"]+)\"");
		String mobile = username;
		String remenber = "";
		String backURL = "http%3A%2F%2Fweibo.cn";//跳转后页面
		String backTitle = "手机新浪网";
		String tryCount = "";//尝试登录次数
		String submit = "登录";
		// <form
		// action="?rand=115447141&amp;backURL=http%3A%2F%2Fweibo.cn%2F&amp;backTitle=%E5%BE%AE%E5%8D%9A&amp;vt=4&amp;revalid=2&amp;ns=1"
		// method="post">
		String action = parseUtil.matchStringGroup1(content, "form\\saction=\"([^\"]+)\"");
		String loginNewUrl = loginUrl + action;

		// System.out.println(vk.toString()+vk);
		// System.out.println(passwordName.toString()+passwordName);
		// System.out.println(action.toString()+action);

		//获取好普通参数后，分析是否有验证码
		String captchaImgUrl = parseUtil.matchStringGroup1(content,"请输入图片中的字符:<br/><img src=\"([^\"]+)\"");
		String captcha = "";//验证码
		String capId = null;//验证码ID
		if(captchaImgUrl!=null && captchaImgUrl!=""){
			//如果有验证码，解析get的网页内容
			capId = parseUtil.matchStringGroup1(content, "name=\"capId\" value=\"([^\"]+)\"");
			//get验证码图片，输入验证码
			httpGet = new HttpGet(captchaImgUrl);
			httpResponse = httpClient.execute(httpGet);
			entity = httpResponse.getEntity();
//			System.out.println(EntityUtils.toString(entity).length());
			
//			InputStream inputStream = new GzipDecompressingEntity(entity).getContent();   
			InputStream inputStream = entity.getContent();
			// write the inputStream to a FileOutputStream  
			OutputStream out = new FileOutputStream(new File(".\\output\\captcha.gif"));  
			                       
			int read = 0;  
			byte[] bytes = new byte[1024];  
			                       
			while ((read = inputStream.read(bytes)) != -1) {  
			    out.write(bytes, 0, read);  
			}  
			                      
			inputStream.close();  
			out.flush();  
			out.close();  
			System.out.println("请输入验证码，图片地址.\\output\\captcha.gif!");
			//创建输入对象
			captcha = new Scanner(System.in).nextLine();
			System.out.println("输入的验证码为："+captcha);
		}
		
		
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		// 加入post表单参数
		qparams.add(new BasicNameValuePair("mobile", username));
		qparams.add(new BasicNameValuePair(passwordName, password));
		qparams.add(new BasicNameValuePair("remember", remenber));
		qparams.add(new BasicNameValuePair("backURL", backURL));
		qparams.add(new BasicNameValuePair("backTitle", backTitle));
		qparams.add(new BasicNameValuePair("tryCount", tryCount));
		qparams.add(new BasicNameValuePair("vk", vk));
		qparams.add(new BasicNameValuePair("submit", submit));
		//插入验证码的参数
		if(capId!=null){
			qparams.add(new BasicNameValuePair("capId", capId));
			qparams.add(new BasicNameValuePair("code", captcha));
		}
		UrlEncodedFormEntity params = new UrlEncodedFormEntity(qparams, "utf-8");// 将参数列表List<NameValuePair>封装好,才可以进行post提交

		HttpPost post = new HttpPost(loginNewUrl);// 表单提交地址
		// post.addHeader("Host", "login.weibo.cn");
		// post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64;
		// rv:43.0) Gecko/20100101 Firefox/43.0");
		// post.addHeader("Accept",
		// "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		// post.addHeader("Accept-Language",
		// "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		// post.addHeader("Accept-Encoding", "gzip, deflate");
		//// post.addHeader("Cookie", _T_WM);
		// post.addHeader("Connection", "keep-alive");

		System.out.println("post loginNewUrl:" + loginNewUrl);
		post.setEntity(params);// 加入参数
		MyHttpConnectionManager.setHandleRedirect(httpClient, false);//暂停httpclient的跳转
		httpResponse = httpClient.execute(post);
//		response = httpClient.execute(post);
		entity = httpResponse.getEntity();// 获得响应内容
		post.abort();

		// step3:开始get第一个中转页面
		header = httpResponse.getAllHeaders();
		for (int i = 0; i < header.length; i++) {
			System.out.println(header[i]);
		}

		String location = httpResponse.getFirstHeader("Location").getValue();
		System.out.println("get1 location:" + location);

		HttpGet get = new HttpGet(location);

		RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
//		get.setConfig(requestConfig);// 设置不跳转4.4版本
		httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,false);//设置不跳转4.1版本
		get.addHeader("Host", "newlogin.sina.cn");
		get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64;rv:43.0) Gecko/20100101 Firefox/43.0");
		get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		get.addHeader("Accept-Encoding", "gzip, deflate");
		get.addHeader("Connection", "keep-alive");

		httpResponse = httpClient.execute(get);
//		response = httpClient.execute(get);
		entity = httpResponse.getEntity();// 获得响应内容
		get.abort();

		// header = response.getHeaders("Set-Cookie");
		header = httpResponse.getAllHeaders();
		for (int i = 0; i < header.length; i++) {
			System.out.println(header[i]);
		}

		// step4 get2
		location = httpResponse.getFirstHeader("Location").getValue();
		System.out.println("get2 location:" + location);
		get = new HttpGet(location);
		requestConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
//		get.setConfig(requestConfig);// 设置不跳转4.4版本
		httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,false);//设置不跳转4.1版本
		get.addHeader("Host", "passport.weibo.com");
		get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0");
		get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		get.addHeader("Accept-Encoding", "gzip, deflate");
		get.addHeader("Connection", "keep-alive");
		httpResponse = httpClient.execute(get);
//		response = httpClient.execute(get);
		entity = httpResponse.getEntity();// 获得响应内容
		get.abort();

		header = httpResponse.getAllHeaders();
		for (int i = 0; i < header.length; i++) {

			System.out.println(header[i].toString());
		}
		String cookieResStr = setCookieStore(httpResponse);
		
//		cookieResStr += YF_Page_G0;
		System.out.println( "cookieResStr:"+cookieResStr);
//		List<Cookie> cookies = cookieStore.getCookies();
//		for (Cookie ck : cookies) {
//			System.out.println("ck:" + ck.toString());
//		}
		httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,true);//设置跳转4.1版本
		
		FileUtil fu = new FileUtil();
		logger.info("获得网页版cookie为：" + cookie);
		fu.writeFile(cookie, "./cookie");
		if (!cookie.isEmpty())
			logger.info("获取网页版cookie成功！保存在./cookie文件中");
		else
			logger.info("获取网页版cookie失败");
		logger.info("获得手机版cookie为：" + mwcookie);
		fu.writeFile(mwcookie, "./mwcookie");
		if (!mwcookie.isEmpty())
			logger.info("获取手机版cookie成功！保存在./cookie文件中");
		else
			logger.info("获取手机版cookie失败");
		return cookie;
	}
	//手机版微博cookie，默认存入mwcookie文件中
	public String getMWCookieFromMW2016(String username, String password) throws Exception {
		String cookie = "";
		String content = "";
		Header[] header;
		String loginUrl = "http://login.weibo.cn/login/";
		HttpGet httpGet;
		HttpEntity entity;
//		CloseableHttpResponse response;
		HttpResponse httpResponse;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		//获取起始参数
//		getFristParameters();
		
		MyHttpConnectionManager.setHandleRedirect(httpClient, false);//打开httpclient的跳转
		httpGet = new HttpGet(loginUrl);
		System.out.println("request line:" + httpGet.getRequestLine());
		httpResponse = httpClient.execute(httpGet);
//		response = httpClient.execute(httpGet);
		entity = httpResponse.getEntity();
		content = EntityUtils.toString(entity, "utf-8");
		// System.out.println(content);
		String _T_WM = httpResponse.getHeaders("Set-Cookie")[0].getValue();
		System.out.println("_T_WM: "+_T_WM);
		

		
		
		

		// step2:根据得到login页面，得到构造好参数后，开始提交post请求
		// 构造post请求
		String vk = parseUtil.matchStringGroup1(content, "name=\"vk\"\\svalue=\"([^\"]+)\"");
		// <input type="password" name="password_8576" size="30" />
		String passwordName = parseUtil.matchStringGroup1(content, "type=\"password\"\\sname=\"([^\"]+)\"");
		String mobile = username;
		String remenber = "";
		String backURL = "http%3A%2F%2Fweibo.cn";//跳转后页面
		String backTitle = "手机新浪网";
		String tryCount = "";//尝试登录次数
		String submit = "登录";
		// <form
		// action="?rand=115447141&amp;backURL=http%3A%2F%2Fweibo.cn%2F&amp;backTitle=%E5%BE%AE%E5%8D%9A&amp;vt=4&amp;revalid=2&amp;ns=1"
		// method="post">
		String action = parseUtil.matchStringGroup1(content, "form\\saction=\"([^\"]+)\"");
		String loginNewUrl = loginUrl + action;

		// System.out.println(vk.toString()+vk);
		// System.out.println(passwordName.toString()+passwordName);
		// System.out.println(action.toString()+action);

		//获取好普通参数后，分析是否有验证码
		String captchaImgUrl = parseUtil.matchStringGroup1(content,"请输入图片中的字符:<br/><img src=\"([^\"]+)\"");
		String captcha = "";//验证码
		String capId = null;//验证码ID
		if(captchaImgUrl!=null && captchaImgUrl!=""){
			//如果有验证码，解析get的网页内容
			capId = parseUtil.matchStringGroup1(content, "name=\"capId\" value=\"([^\"]+)\"");
			//get验证码图片，输入验证码
			httpGet = new HttpGet(captchaImgUrl);
			httpResponse = httpClient.execute(httpGet);
			entity = httpResponse.getEntity();
//			System.out.println(EntityUtils.toString(entity).length());
			
//			InputStream inputStream = new GzipDecompressingEntity(entity).getContent();   
			InputStream inputStream = entity.getContent();
			// write the inputStream to a FileOutputStream  
			OutputStream out = new FileOutputStream(new File(".\\output\\captcha.gif"));  
			                       
			int read = 0;  
			byte[] bytes = new byte[1024];  
			                       
			while ((read = inputStream.read(bytes)) != -1) {  
			    out.write(bytes, 0, read);  
			}  
			                      
			inputStream.close();  
			out.flush();  
			out.close();  
			System.out.println("请输入验证码，图片地址.\\output\\captcha.gif!");
			//创建输入对象
			captcha = new Scanner(System.in).nextLine();
			System.out.println("输入的验证码为："+captcha);
		}
		
		
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		// 加入post表单参数
		qparams.add(new BasicNameValuePair("mobile", username));
		qparams.add(new BasicNameValuePair(passwordName, password));
		qparams.add(new BasicNameValuePair("remember", remenber));
		qparams.add(new BasicNameValuePair("backURL", backURL));
		qparams.add(new BasicNameValuePair("backTitle", backTitle));
		qparams.add(new BasicNameValuePair("tryCount", tryCount));
		qparams.add(new BasicNameValuePair("vk", vk));
		qparams.add(new BasicNameValuePair("submit", submit));
		//插入验证码的参数
		if(capId!=null){
			qparams.add(new BasicNameValuePair("capId", capId));
			qparams.add(new BasicNameValuePair("code", captcha));
		}
		UrlEncodedFormEntity params = new UrlEncodedFormEntity(qparams, "utf-8");// 将参数列表List<NameValuePair>封装好,才可以进行post提交

		HttpPost post = new HttpPost(loginNewUrl);// 表单提交地址
		// post.addHeader("Host", "login.weibo.cn");
		// post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64;
		// rv:43.0) Gecko/20100101 Firefox/43.0");
		// post.addHeader("Accept",
		// "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		// post.addHeader("Accept-Language",
		// "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		// post.addHeader("Accept-Encoding", "gzip, deflate");
		//// post.addHeader("Cookie", _T_WM);
		// post.addHeader("Connection", "keep-alive");

		System.out.println("post loginNewUrl:" + loginNewUrl);
		post.setEntity(params);// 加入参数
		MyHttpConnectionManager.setHandleRedirect(httpClient, false);//暂停httpclient的跳转
		httpResponse = httpClient.execute(post);
//		response = httpClient.execute(post);
		entity = httpResponse.getEntity();// 获得响应内容
		post.abort();

		// step3:开始get第一个中转页面
		header = httpResponse.getAllHeaders();
		for (int i = 0; i < header.length; i++) {
			System.out.println(header[i]);
		}

		String location = httpResponse.getFirstHeader("Location").getValue();
		System.out.println("get1 location:" + location);

		HttpGet get = new HttpGet(location);

		RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
//		get.setConfig(requestConfig);// 设置不跳转4.4版本
		httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,false);//设置不跳转4.1版本
		get.addHeader("Host", "newlogin.sina.cn");
		get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64;rv:43.0) Gecko/20100101 Firefox/43.0");
		get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		get.addHeader("Accept-Encoding", "gzip, deflate");
		get.addHeader("Connection", "keep-alive");

		httpResponse = httpClient.execute(get);
//		response = httpClient.execute(get);
		entity = httpResponse.getEntity();// 获得响应内容
		get.abort();

		// header = response.getHeaders("Set-Cookie");
		header = httpResponse.getAllHeaders();
		for (int i = 0; i < header.length; i++) {
			System.out.println(header[i]);
		}

		// step4 get2
		location = httpResponse.getFirstHeader("Location").getValue();
		System.out.println("get2 location:" + location);
		get = new HttpGet(location);
		requestConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
//		get.setConfig(requestConfig);// 设置不跳转4.4版本
		httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,false);//设置不跳转4.1版本
		get.addHeader("Host", "passport.weibo.com");
		get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0");
		get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		get.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		get.addHeader("Accept-Encoding", "gzip, deflate");
		get.addHeader("Connection", "keep-alive");
		httpResponse = httpClient.execute(get);
//		response = httpClient.execute(get);
		entity = httpResponse.getEntity();// 获得响应内容
		get.abort();

		header = httpResponse.getAllHeaders();
		for (int i = 0; i < header.length; i++) {

			System.out.println(header[i].toString());
		}
		String cookieResStr = setCookieStore(httpResponse);
		
//		cookieResStr += YF_Page_G0;
		System.out.println( "cookieResStr:"+cookieResStr);
//		List<Cookie> cookies = cookieStore.getCookies();
//		for (Cookie ck : cookies) {
//			System.out.println("ck:" + ck.toString());
//		}
		httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,true);//设置跳转4.1版本
		
		return cookie;
	}
	
	public String getMWCookieFromFile() throws IOException {

		FileUtil fu = new FileUtil();
		if(FileUtil.length("./mwcookie")==0){
			logger.info("mwcookie文件为空，尝试使用下一账号登录获取cookie，并存入cookie文件");
			try {
				getMWCookieUseNextUser();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info("尝试使用下一账号获取cookie出错,将返回空cookie");
				return null;
			}
		}
		else{
			logger.info("存在cookie文件，直接读取cookie");
			String cookie = FileUtil.readFile("./mwcookie");
			return cookie;
		}
		return null;
	}
	
	
	public static String setCookieStore(HttpResponse httpResponse) throws ParseException {
		System.out.println("----setCookieStore");
//		cookieStore = new BasicCookieStore();
		String cookieResStr = "";
		Header[] headers = httpResponse.getHeaders("Set-Cookie");
		for (Header hd : headers) {
			BasicClientCookie cookie = null;
			String cookieStr = hd.getValue();// cookie字符串
			System.out.println("cookieStr:" + cookieStr);

			Matcher matcher = Pattern.compile("(\\w+)=([^;]*)").matcher(cookieStr);
			if (matcher.find()) {
				cookie = new BasicClientCookie(matcher.group(1), matcher.group(2));
				cookieResStr += matcher.group(1)+"="+matcher.group(2)+"; ";
			}

			while (matcher.find()) {
				String pname = matcher.group(1);
				String pvalue = matcher.group(2);

				switch (pname) {
				case "path":
					cookie.setPath(pvalue);
					break;
				case "domain":
					cookie.setDomain(pvalue);
					break;

				case "expires":
				     Date date=new   Date();//取时间 
				     Calendar   calendar   =   new   GregorianCalendar(); 
				     calendar.setTime(date); 
				     calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
				     date=calendar.getTime();  
					 cookie.setExpiryDate(new Date());
					break;
				}
			}
//			if (cookie != null)
//				cookieStore.addCookie(cookie);
		}
		return cookieResStr;
	}
	

	
	public static void main(String[] args) throws Exception {
		getCookieUtil gcu = new getCookieUtil();
		gcu.getCookieStore();

	}
}
