package com.sina.pojos;

public class userMoreInfoPojo {

	private String contact;//联系信息
	private String education;//教育信息
	private String career;//职业信息
	private String tag;//标签信息
	
	public userMoreInfoPojo(){
//		contact="";
//		education="";
//		career="";
//		tag = "";
	}
	
	public String toString()
	{
		String str = "";
		str+="contact="+contact +";";
		str+="education="+education+";";
		str+="career="+career +";";
		str+="tag="+tag  +";";
		return str;
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
	
}
