package com.mweibo.task;

import com.redis.MyJedisPool;

import redis.clients.jedis.Jedis;

public class CrawlTask implements Runnable{
	@Override
	public void run() {
		new Thread(new crawlHotWeibosTask(),"crawlHotWeibosTask").start();
		new Thread(new crawlPageByUidTask(),"crawlPageByUidTask").start();
		new Thread(new crawlPageByWidTask(),"crawlPageByWidTask").start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread crawlTask = new Thread(new CrawlTask());
		crawlTask.start();
	}
}
