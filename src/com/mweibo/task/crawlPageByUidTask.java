package com.mweibo.task;

import org.apache.log4j.Logger;

import com.mweibo.crawl.crawlHotWeibos;
import com.mweibo.crawl.crawlPageByUid;
import com.redis.MyJedisPool;
import com.sina.util.RadisUtil;

import redis.clients.jedis.Jedis;

public class crawlPageByUidTask implements Runnable {
	static final int sleepTime = 1000*60*10;//1000*10;
//	Jedis jedis=MyJedisPool.getJedis();
	RadisUtil ru = new RadisUtil();
	public static Logger logger = Logger.getLogger(crawlPageByUidTask.class);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		crawlPageByUid cpUid=new crawlPageByUid();
		while(true){
			if(!ru.isQueEmpty("mws.uid.crawlQue")){
				//利用uid爬取页面
				try {
					cpUid.crawlPage();
					//Thread.sleep(1000*60*10);
				} catch (Exception e) {
					
					e.printStackTrace();
				}//操作cpUid
			}
			try {
				logger.warn("带爬取的uid队列为空，线程暂停爬取！");
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
		Thread cpuTask = new Thread(new crawlPageByUidTask());
		cpuTask.start();
	}

}
