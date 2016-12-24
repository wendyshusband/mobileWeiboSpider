package com.mweibo.task;

import com.mweibo.crawl.crawlPageByWid;
import com.redis.MyJedisPool;
import com.sina.util.RadisUtil;

import redis.clients.jedis.Jedis;

public class crawlPageByWidTask implements Runnable {
	static final int sleepTime = 1000*60*10;// 1000*10;
//	Jedis jedis=MyJedisPool.getJedis();
	RadisUtil ru = new RadisUtil();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		crawlPageByWid cpWid=new crawlPageByWid();
		while(true){
			if(!ru.isQueEmpty("mws.wid.crawlQue")){
				//操作cpWid
				try {
					cpWid.crawlPage();
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
			else{
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
		
	}

}
