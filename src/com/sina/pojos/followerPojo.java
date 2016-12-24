package com.sina.pojos;

public class followerPojo {
	private String uid;//用户id
	private String nickname;//昵称
	private String gender;//性别
	private String location;//所在地
	private String slogan;//简介
	
	public followerPojo(){
//		uid = "";
//		nickname="";
//		gender="";
//		location="";
//		slogan="";
	}
	
	public String toString()
	{
		String str = "";
		str+="uid="+uid +"; ";
		str+="nickname="+nickname +";";
		str+="gender="+gender +";";
		str+="location="+location +";";
		str+="slogan="+slogan+";";
		return str;
	}
	

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname =nickname;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender =gender;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location =location;
	}
	
	public String getSlogan() {
		return slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan =slogan;
	}
	
}
