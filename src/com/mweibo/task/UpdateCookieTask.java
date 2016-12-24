package com.mweibo.task;

import java.lang.Thread.State;

import org.apache.log4j.Logger;

import com.mweibo.crawl.crawlHotWeibos;
import com.sina.pojos.CookieStorePojo;
import com.sina.util.RadisUtil;
import com.sina.util.StaticValue;
import com.sina.util.getCookieUtil;
import com.sina.util.getPageUtil;

//该进程不会结束
public class UpdateCookieTask implements Runnable {
	private static final int sleepTime = 5*60*1000; //从radis中检测一次cookie可用性 
	private static RadisUtil ru = new RadisUtil();
	private static getCookieUtil gcu = new getCookieUtil(); 
//	private static getCookieUtil gcu = getCookieUtil.getCookieUtil(); 
	
	public static Logger logger = Logger.getLogger(UpdateCookieTask.class);
	public static Thread  updateCookieTask;
	@Override
	public void run() {
		logger.info("UpdateCookieTask-cookie维护线程启动成功！");
		while(true){
			checkCookie();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void checkCookie(){
		CookieStorePojo csp = null;
		for(int accIdx = 0;accIdx < StaticValue.count_of_login_user ; accIdx++){
			logger.info("开始检测第"+accIdx+"个账号的cookie有效性");
			csp = ru.getCookieStore(accIdx);
			if(csp == null){//没有获取到cookie，尝试登录
				gcu.LoginWeiboCnAndStoreCookie(accIdx);
				continue;
			}
			if( gcu.checkMwCookieStatu(csp.getMwCookieStr()) == StaticValue.COOKIE_STATU_UNUSABLE){
				logger.error("登录获取的mwCookieStr不可用！可能发生错误或过期，尝试登陆");
				gcu.LoginWeiboCnAndStoreCookie(accIdx);
				continue;
			}
			else{
				getCookieUtil.updateCookieStoreAndSetCookieIdx(csp);
			}
			
		}
	}
	public static void main(String[] args) {
		checkCookie();
	}
}
