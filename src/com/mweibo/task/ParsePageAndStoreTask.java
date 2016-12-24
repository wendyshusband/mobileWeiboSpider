package com.mweibo.task;
//从radis中解析pageCache并且存储数据到Hbase
public class ParsePageAndStoreTask implements Runnable {
	@Override
	public void run() {
		//解析页面进程管理子进程，后期可以考虑使用线程池，根据radis里的数据量对每种线程进行线程池管理
		new Thread(new parseUserInfoPageTask(),"parseUserInfoPageTask").start();
		new Thread(new parseUserWeiboPageTask(),"parseUserInfoPageTask").start();
		new Thread(new parseUserFansInfoPageTask(),"parseUserFansInfoPageTask").start();
		new Thread(new parseUserFollowPageInfoTask(),"parseUserFollowPageInfoTask").start();
		new Thread(new parseWeiboCommentPageTask(),"parseWeiboCommentPageTask").start();
		new Thread(new parseWeiboRepostPageTask(),"parseWeiboRepostPageTask").start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread crawlTask = new Thread(new CrawlTask());
		crawlTask.start();
	}

}
