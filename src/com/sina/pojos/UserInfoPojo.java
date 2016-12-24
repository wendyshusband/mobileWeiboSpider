package com.sina.pojos;

public class UserInfoPojo {


		private String uid;//用户id~
		private String other;//其他信息~
		private int followercount;//粉丝数
		private int friendcount;//关注数
		private int weibocount;//微博数
		private String nickname;//昵称~
		private String authentication;//认证~
		private String gender;//性别~
		private String imgSrc;//头像地址~
		private String location;//所在地~
		private String birthday;//生日~
		private String slogan;//简介~
		private String registTime;//注册时间
		private String contact;//联系信息
		private String education;//教育信息~
		private String career;//职业信息~
		private String tag;//标签信息~
		
		public UserInfoPojo()
		{
//			uid = "";
//			domain="";
			followercount = 0;
			friendcount = 0;
			weibocount = 0;
//			nickname="";
//			gender="";
//			imgSrc="";
//			location="";
//			birthday="";
//			slogan="";
//			registTime="";
//			contact="";
//			education="";
//			career="";
//			tag = "";
		}
		public String toString()
		{
			String str = "";
			str+="uid="+uid +"; ";
			str+="other="+other +";";
			str+="nickname="+nickname +";";
			str+="gender="+gender +";";
			str+="imgSrc="+imgSrc +";";
			str+="location="+location +";";
			str+="birthday="+birthday +";";
			str+="slogan="+slogan+";";
			str+="registTime="+registTime +";";
			str+="contact="+contact +";";
			str+="education="+education+";";
			str+="career="+career +";";
			str+="tag="+tag  +";";
			return str;
		}
		public String toJson()
		{
			String str = "{";
			str+="'uid':'"+uid +"',";
			str+="'other':'"+other +"',";
			str+="'nickname':'"+nickname +"',";
			str+="'gender':'"+gender +"',";
			str+="'imgSrc':'"+imgSrc +"',";
			str+="'location':'"+location +"',";
			str+="'birthday':'"+birthday +"',";
			str+="'slogan':'"+slogan+"',";
			str+="'registTime':'"+registTime +"',";
			str+="'contact':'"+contact +"',";
			str+="'education':'"+education+"',";
			str+="'career':'"+career +"',";
			str+="'tag':'"+tag  +"'}";
			return str;
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		
		public String getOther() {
			return other;
		}
		public void setOther(String other) {
			this.other =other;
		}
		
		public int getFollowercount() {
			return followercount;
		}
		public void setFollowercount(int followercount) {
			this.followercount =followercount;
		}
		
		public int getFriendcount() {
			return followercount;
		}
		public void setFriendcount(int friendcount) {
			this.friendcount =friendcount;
		}

		public int getWeibocount() {
			return weibocount;
		}
		public void setWeibocount(int weibocount) {
			this.weibocount =weibocount;
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
		
		public String getContact() {
			return contact;
		}
		public void setContact(String contact) {
			this.contact =contact;
		}
		
		public String getEducation() {
			return education;
		}
		public void setEducation(String education) {
			this.education =education;
		}
		
		public String getCareer() {
			return career;
		}
		public void setCareer(String career) {
			this.career =career;
		}
		
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
		

		public String getAuthentication() {
			return authentication;
		}
		public void setAuthentication(String authentication) {
			this.authentication = authentication;
		}
}
