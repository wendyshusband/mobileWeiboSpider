package com.mweibo.task;

import com.mweibo.parsePage.parseUserWeiboPage;
import com.sina.util.RadisUtil;
import com.sina.util.StaticValue;

public class parseUserWeiboPageTask implements Runnable {
	static final int sleepTime = StaticValue.SLEEP_TIME_PARSE_PAGE;
	RadisUtil ru = new RadisUtil();

	@Override
	public void run() {
		parseUserWeiboPage uwp = new parseUserWeiboPage() ;
		while(true){
			if( !ru.isQueEmpty("mws.page.userWeiboQue") ){
				uwp.parsePageAndStore();
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
	}

}
