package com.mweibo.task;

import com.mweibo.parsePage.parseUserFansPage;
import com.sina.util.RadisUtil;
import com.sina.util.StaticValue;

public class parseUserFansInfoPageTask implements Runnable {
	static final int sleepTime = StaticValue.SLEEP_TIME_PARSE_PAGE;
	RadisUtil ru = new RadisUtil();
	

	@Override
	public void run() {
		parseUserFansPage ufp = new parseUserFansPage();
		while(true){
			if( !ru.isQueEmpty("mws.page.userFansQue") ){
				ufp.parsePageAndStore();
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
