package com.mweibo.task;

import com.mweibo.parsePage.parseWeiboCommentPage;
import com.sina.util.RadisUtil;
import com.sina.util.StaticValue;

public class parseWeiboCommentPageTask implements Runnable {
	static final int sleepTime = StaticValue.SLEEP_TIME_PARSE_PAGE;
	RadisUtil ru = new RadisUtil();

	@Override
	public void run() {
		parseWeiboCommentPage wcp = new parseWeiboCommentPage();
		while(true){
			if( !ru.isQueEmpty("mws.page.weiboCommentQue") ){
				wcp.parsePageAndStore();
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
