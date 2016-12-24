package com.sina.pojos;

import java.util.ArrayList;
import java.util.List;

public class UserFollowPojo {
	private String uid;
	private List<String> followUidList = new ArrayList<String>();

	public String toString() {
		String str ="{";
		str+="'id':'"+uid+"',";
		str+="'fansUidList':'"+followUidList+"'";
		return str;
	}
		
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public List<String> getFollowUidList() {
		return followUidList;
	}
	
	public void setFollowUidList(List<String> followUidList) {
		this.followUidList = followUidList;
	}

	public void addFollowUid(String followUid){
		followUidList.add(followUid);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
