package com.mweibo.task;

import com.mweibo.crawl.crawlHotWeibos;
import com.sina.util.StaticValue;

public class crawlHotWeibosTask implements Runnable {
    private final int sleepTime=1000*60*20;//20分钟更新一次
  
	public void run(){
		crawlHotWeibos chw=new crawlHotWeibos();
		while(true){
			try {
				chw.getHotWeibosFromWebMobile();
				Thread.sleep(sleepTime);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public static void main(String[]args) throws InterruptedException{
		
		Thread crawlHotWeibos =new Thread(new crawlHotWeibosTask());
		crawlHotWeibos.start();

     }
	
}
