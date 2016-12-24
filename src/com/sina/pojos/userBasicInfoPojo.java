package com.sina.pojos;

public class userBasicInfoPojo {
	
	private String uid;//用户id
	private String domain;//标注用户类别。如100605表示娱乐明星
	private String nickname;//昵称
	private String gender;//性别
	private String imgSrc;//头像地址
	private String location;//所在地
	private String birthday;//生日
	private String slogan;//简介
	private String registTime;//注册时间
	
	public userBasicInfoPojo(){
//		uid = "";
//		domain="";
//		nickname="";
//		gender="";
//		imgSrc="";
//		location="";
//		birthday="";
//		slogan="";
//		registTime="";
	}
	
	public String toString()
	{
		String str = "";
		str+="uid="+uid +"; ";
		str+="domain="+domain +";";
		str+="nickname="+nickname +";";
		str+="gender="+gender +";";
		str+="imgSrc="+imgSrc +";";
		str+="location="+location +";";
		str+="birthday="+birthday +";";
		str+="slogan="+slogan+";";
		str+="registTime="+registTime +";";
		return str;
	}
	

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain =domain;
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
	
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc =imgSrc;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location =location;
	}
	
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday =birthday;
	}
	
	public String getSlogan() {
		return slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan =slogan;
	}
	
	public String getRegistTime() {
		return registTime;
	}
	public void setRegistTime(String registTime) {
		this.registTime =registTime;
	}
	
}
