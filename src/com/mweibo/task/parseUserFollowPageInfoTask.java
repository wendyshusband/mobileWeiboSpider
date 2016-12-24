package com.mweibo.task;

import com.mweibo.parsePage.parseUserFollowPage;
import com.sina.util.RadisUtil;
import com.sina.util.StaticValue;

public class parseUserFollowPageInfoTask implements Runnable {
	static final int sleepTime = StaticValue.SLEEP_TIME_PARSE_PAGE;
	public RadisUtil ru = new RadisUtil();


	@Override
	public void run() {
		parseUserFollowPage ufop = new parseUserFollowPage();
		while(true){
			if( !ru.isQueEmpty("mws.page.userFollowQue") ){
				ufop.parsePageAndStore();
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
