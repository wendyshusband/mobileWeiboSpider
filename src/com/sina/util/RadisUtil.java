package com.sina.util;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.redis.MyJedisPool;
import com.sina.pojos.CookieStorePojo;
import com.sina.pojos.userWeiboInfoPojo;
import com.sina.pojos.UserWeiboPojo;

import redis.clients.jedis.Jedis;

public class RadisUtil {
	// MyJedisPool pool = new MyJedisPool();
	// Jedis jedis = pool.getJedis();
	private Jedis jedis = MyJedisPool.getJedis();
	public static Logger logger = Logger.getLogger(RadisUtil.class);

	/**
	 * 数据结构包括(默认全都是手机版微博weibo.cn的 微博名、微博page)： 
	 * mws. 
	 * 	uid. 
	 * 		allSet: c
	 * 
	 * 		rawlQue:待爬取队列
	 * 	wid. 
	 * 		allSet: 
	 * 		crawlQue: 
	 * 	page. 
	 * 		userInfoQue 
	 * 		userWeiboQue 
	 * 		userFansQue
	 * 		userFollowQue 
	 * 		weiboCommentQue 
	 * 		weiboRepostQue 
	 * 	mwCookie.
	 * 		cookieIdxSet:cookie的账号序号存储表 
	 * 		.NUMBER
	 * 		第NUMBER个账号所存储的cookie，结构是一个hash表(一个cookie对多个域名有不同的cookieStr）
	 * 		使用"mwCookieStr"键值获取手机微博的cookie字符串 如jedis.hget(mws.mwCookie.2,"mwCookieStr" )
	 * 
	 */

	public void addUid(String uid) {
		if (!jedis.sismember("mws.uid.allSet", uid)) {
			jedis.sadd("mws.uid.allSet", uid);
			jedis.rpush("mws.uid.crawlQue", uid);
		}
	}

	public void addWid(String wid) {
		if (!jedis.sismember("mws.wid.allSet", wid)) {
			jedis.sadd("mws.wid.allSet", wid);
			jedis.rpush("mws.wid.crawlQue", wid);
		}
	}

	public void addUidToCrawlQue(String uid) {
		jedis.rpush("mws.uid.crawlQue", uid);
	}

	public void addWidToCrawlQue(String wid) {
		jedis.rpush("mws.wid.crawlQue", wid);
	}

	// 队列pop操作
	public String popUidFromCrawlQue() {
		if (jedis.llen("mws.uid.crawlQue") > 0) {
			return jedis.lpop("mws.uid.crawlQue");
		} else
			return null;
	}

	public String popWidFromCrawlQue() {
		if (jedis.llen("mws.wid.crawlQue") > 0) {
			return jedis.lpop("mws.wid.crawlQue");
		} else
			return null;
	}

	public String popUserInfoPageFromPageQue() {
		if (jedis.llen("mws.page.userInfoQue") > 0) {// 不先测试的话，队列数量为0删除会报错！
			return jedis.lpop("mws.page.userInfoQue");
		} else
			return null;
	}

	public String popUserWeiboPageFromPageQue() {
		if (jedis.llen("mws.page.userWeiboQue") > 0) {// 不先测试的话，队列数量为0删除会报错！
			return jedis.lpop("mws.page.userWeiboQue");
		} else
			return null;
	}

	public String popUserFansPageFromPageQue() {
		if (jedis.llen("mws.page.userFansQue") > 0) {// 不先测试的话，队列数量为0删除会报错！
			return jedis.lpop("mws.page.userFansQue");
		} else
			return null;
	}

	public String popUserFollowPageFromPageQue() {
		if (jedis.llen("mws.page.userFollowQue") > 0) {// 不先测试的话，队列数量为0删除会报错！
			return jedis.lpop("mws.page.userFollowQue");
		} else
			return null;
	}

	public String popWeiboCommentPageFromPageQue() {
		if (jedis.llen("mws.page.weiboCommentQue") > 0) {// 不先测试的话，队列数量为0删除会报错！
			return jedis.lpop("mws.page.weiboCommentQue");
		} else
			return null;
	}

	public String popWeiboRepostPageFromPageQue() {
		if (jedis.llen("mws.page.weiboRepostQue") > 0) {// 不先测试的话，队列数量为0删除会报错！
			return jedis.lpop("mws.page.weiboRepostQue");
		} else
			return null;
	}

	// 添加page操作
	public void addUserInfoPage(String page) {
		jedis.rpush("mws.page.userInfoQue", page);
	}

	public void addUserWeiboPage(String page) {
		jedis.rpush("mws.page.userWeiboQue", page);
	}

	public void addUserFansPage(String page) {
//		System.out.println("fansPage"+page);
		jedis.rpush("mws.page.userFansQue", page);
	}

	public void addUserFollowPage(String page) {
		jedis.rpush("mws.page.userFollowQue", page);
	}

	public void addWeiboCommentPage(String page) {
		jedis.rpush("mws.page.weiboCommentQue", page);
	}

	public void addWeiboRepostPage(String page) {
		jedis.rpush("mws.page.weiboRepostQue", page);

	}

	public boolean isQueEmpty(String queName) {
		return jedis.llen(queName) == 0;
	}

	// cookie存储相关操作
	/**
	 * 删除一个cookie
	 */
	public void delCookieStore(int accountNum) {
		jedis.srem("mws.mwCookie.cookieIdxSet", String.valueOf(accountNum));// 删除索引表
		jedis.del("mws.mwCookie." + accountNum);// 删除内容
	}

	/**
	 * 使用构造好的cookieStore对radis数据库更新/插入操作
	 */
	public void updateCookieStore(CookieStorePojo csp) {
		if (csp != null) {
			jedis.sadd("mws.mwCookie.cookieIdxSet", String.valueOf(csp.getAccountNum()));
			jedis.hset("mws.mwCookie." + String.valueOf(csp.getAccountNum()), "mwCookieStr", csp.getMwCookieStr());
		}
	}

	/**
	 * 按账号下标从radis数据库中获取一个cookie
	 */
	public CookieStorePojo getCookieStore(int cookieIdx) {
		if (!jedis.sismember("mws.mwCookie.cookieIdxSet", String.valueOf(cookieIdx))) {
			return null; // 索引中不存在该cookie序号
		}
		CookieStorePojo csp = new CookieStorePojo(cookieIdx);
		String mwCookieStr = jedis.hget("mws.mwCookie." + cookieIdx, "mwCookieStr");
		csp.setMwCookieStr(mwCookieStr);
		csp.setAccountNum(cookieIdx);
		return csp;
	}

	/**
	 * 获取radis中cookie的个数
	 */
	public int getCookieStoreSize() {
		return jedis.scard("mws.mwCookie.cookieIdxSet").intValue();
	}

	/**
	 * 随机取出一个cookie
	 */
	public CookieStorePojo getRandCookieStore() {
		String actIdx = jedis.srandmember("mws.mwCookie.cookieIdxSet");
		if (StringUtils.isBlank(actIdx)) // 不存在cookie
			return null;
		CookieStorePojo csp = new CookieStorePojo(Integer.valueOf(actIdx));
		String mwCookieStr = jedis.hget("mws.mwCookie." + actIdx, "mwCookieStr");
		csp.setMwCookieStr(mwCookieStr);
		csp.setAccountNum(Integer.valueOf(actIdx));
		return csp;
	}

	/**
	 * 从radis中获得指定的cookie
	 */

	public static void main(String args[]) {
		
		System.out.println();
	}

}
