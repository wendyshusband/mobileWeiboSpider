package com.mweibo.task;

import com.mweibo.parsePage.parseWeiboRepostPage;
import com.sina.util.RadisUtil;
import com.sina.util.StaticValue;

public class parseWeiboRepostPageTask implements Runnable {
	static final int sleepTime = StaticValue.SLEEP_TIME_PARSE_PAGE;
	public RadisUtil ru = new RadisUtil();

	@Override
	public void run() {
		parseWeiboRepostPage wrp = new parseWeiboRepostPage();
		while(true){
			if( !ru.isQueEmpty("mws.page.weiboRepostQue") ){
				wrp.parseAndStorePage();;
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
