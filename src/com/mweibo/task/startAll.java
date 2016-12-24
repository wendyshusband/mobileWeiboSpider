package com.mweibo.task;

import java.lang.Thread.State;

import com.sina.util.getCookieUtil;

public class startAll {

	public static void main(String[]args) throws InterruptedException{
/*		UpdateCookieTask.updateCookieTask = new Thread(new UpdateCookieTask(),"UpdateCookieTask");
		UpdateCookieTask.updateCookieTask.start();
		while(UpdateCookieTask.updateCookieTask.getState() == State.RUNNABLE){ //cookie更新线程 没有执行完则阻塞当前线程
			Thread.sleep(1000); 
		}
		new Thread(new CrawlTask(),"CrawlTask").start();*/
		new Thread(new ParsePageAndStoreTask(),"parsePageAndStoreTask").start();
	}
	
}
