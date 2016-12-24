package com.sina.pojos;

import java.util.ArrayList;
import java.util.List;

public class userWeiboInfoPojo {

	private String uid;
	private userBasicInfoPojo basicInfo;//用户基本信息
	private userMoreInfoPojo moreInfo;//用户联系、教育等信息
	private int followerCount;//粉丝数
	private int friendCount;//关注数
	private int weiboCount;//微博数
	private List<String> followerList;//粉丝uid列表
	private List<String> friendList;//关注uid列表
	private List<String> weiboList;//微博wid列表
	
	public  userWeiboInfoPojo(){
//		uid="";
		basicInfo=new userBasicInfoPojo();
		moreInfo=new userMoreInfoPojo();
		followerCount = 0;
		friendCount = 0;
		weiboCount = 0;
		followerList=new ArrayList<String>();
		friendList=new ArrayList<String>();
		weiboList=new ArrayList<String>();
	}
	
	public String toString(){
		
		String str = "";
		str+="uid="+uid +";";
		str+="basicInfo=["+ basicInfo.toString()+"];";
		str+="moreInfo=["+moreInfo.toString()+"];";
		str+="followerCount="+followerCount +";";
		str+="friendCount="+friendCount +";";
		str+="weiboCount="+weiboCount +";";
		str+="followerList="+followerList.toString()+";";
		str+="friendList="+friendList.toString()+";";
		str+="weiboList="+weiboList.toString()+";";
		return str;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public userBasicInfoPojo getbasicInfo(){
		return basicInfo;
	}
	public void setBasicInfo(userBasicInfoPojo basicInfo){
		this.basicInfo=basicInfo;
	}
	
	public userMoreInfoPojo getmoreInfo(){
		return moreInfo;
	}
	public void setMoreInfo(userMoreInfoPojo moreInfo){
		this.moreInfo=moreInfo;
	}
	

	public int getFollowerCount() {
		return followerCount;
	}
	public void setFollowerCount(int followerCount) {
		this.followerCount =followerCount;
	}
	
	public int getFriendCount() {
		return followerCount;
	}
	public void setFriendCount(int friendCount) {
		this.friendCount =friendCount;
	}

	public int getWeiboCount() {
		return weiboCount;
	}
	public void setWeiboCount(int weiboCount) {
		this.weiboCount =weiboCount;
	}
	
	public List<String> getFollowerList(){
		return followerList;
	}
	public void setFollowerList(List<String> followerList){
		this.followerList=followerList;
	}
	
	public List<String> getFriendList(){
		return friendList;
	}
	public void setFriendList(List<String> friendList){
		this.friendList=friendList;
	}
	
	public List<String> getWeiboList(){
		return weiboList;
	}
	public void setWeiboList(List<String> weiboList){
		this.weiboList=weiboList;
	}
}
