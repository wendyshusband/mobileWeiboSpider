package com.sina.pojos;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.log4j.Logger;

import com.sina.util.StaticValue;
import com.sina.util.getCookieUtil;
import com.sina.util.getPageUtil;

public class CookieStorePojo extends BasicCookieStore {
	public static Logger logger = Logger.getLogger(CookieStorePojo.class);
	private int accountNum;//账号序号
	private String mwCookieStr;
	private int remainConnectTime = StaticValue.COOKIE_CONNECT_LIMIT;//限制链接次数，为0则更换cookie
	
	public CookieStorePojo(int accountNum){
		this.accountNum = accountNum;
	}
	
	/**返回mwCookieStr字符串
	 */
	public String getMwCookieStr(){
		remainConnectTime -= 1;
		if(StringUtils.isBlank(mwCookieStr) )
			mwCookieStr = buildWeiboCnCookieStr();
		return mwCookieStr;			
	}
	/**该方法不能改变之前的csp指针指向的实体，该函数无效
	 */
//	public static String getMwCookieStr(CookieStorePojo csp){
//		if(csp.getRemainConnectTime()<=0){
//			logger.info("第"+csp.getAccountNum()+"个微博账号超过链接次数，暂时停止使用，更换一个微博账号cookie");
//			csp = getCookieUtil.getCookieStore() ;//这句话没有用！！！！！！！！！！！！
//			if(csp!=null)
//				logger.info("更换成第"+csp.getAccountNum()+"个微博账号cookie成功！");
//			else{
//				logger.error("更换微博账号cookie失败！无法获取cookie！");
//				return null;
//			}
//		}
//		return csp.getMwCookieStr();
//	}

	//拷贝一个cookieStore对象的信息到当前对象
	public void copy(CookieStorePojo csp){
		setAccountNum(csp.getAccountNum());
		setMwCookieStr(csp.getMwCookieStr());
		setRemainConnectTime(csp.getRemainConnectTime());
	}
	public int getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}
	public void setMwCookieStr(String mwCookieStr) {
		this.mwCookieStr = mwCookieStr;
	}
	public void setRemainConnectTime(int remainConnectTime){
		this.remainConnectTime = remainConnectTime;
	}
	public int getRemainConnectTime(){
		return remainConnectTime;
	}
	
	
	
	/**将cookieStore中weibo.cn域名下的cookies合成一个字符串
	 */
	public String buildWeiboCnCookieStr(){
		return buildCookieStrByDomain(".weibo.cn");
	}
	//如果getPageUtil检测到cookieStr出问题了，启动该方法更新一个cookie。如果仍然不行，获取页面失败。
	public String changeWeiboCnCookieStr(){
		return null;
	}
	public String buildCookieStrByDomain(String domain){
		List<Cookie> cookies = this.getCookies();
//		System.out.println("this.cookiesSize"+cookies.size());
//		System.out.println("domain:"+domain);
		String cookieString = "";
		for(Cookie cookie:cookies){
			String dm = cookie.getDomain();
//			System.out.println("name:"+cookie.getName());
//			System.out.println("value:"+cookie.getValue());
			if(dm.compareTo(domain)==0)
				cookieString += cookie.getName()+"="+cookie.getValue()+"; ";//要加分号，并加上一个空格
		}
//		System.out.println("cookieString:"+cookieString);
		return cookieString;
	}
}
