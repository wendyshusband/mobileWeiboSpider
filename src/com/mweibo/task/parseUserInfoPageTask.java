package com.mweibo.task;

import com.mweibo.parsePage.ParsePageAndStore;
import com.mweibo.parsePage.parseUserInfoPage;
import com.redis.MyJedisPool;
import com.sina.util.RadisUtil;
import com.sina.util.StaticValue;

import redis.clients.jedis.Jedis;

public class parseUserInfoPageTask implements Runnable {
	static final int sleepTime = StaticValue.SLEEP_TIME_PARSE_PAGE;
	RadisUtil ru = new RadisUtil();
	@Override
	public void run() {
		parseUserInfoPage puip = new parseUserInfoPage();
		while(true){
			if( !ru.isQueEmpty("mws.page.userInfoQue") ){
				puip.parsePageAndStore();
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
