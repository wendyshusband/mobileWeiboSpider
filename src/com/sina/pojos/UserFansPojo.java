package com.sina.pojos;

import java.util.ArrayList;
import java.util.List;

public class UserFansPojo {
	private String uid;
	private List<String> fansUidList = new ArrayList<String>();
	
	@Override
	public String toString() {
		String str ="{";
		str+="'id':'"+uid+"',";
		str+="'fansUidList':'"+fansUidList+"'";
		return str;
	}
		
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public List<String> getFansUidList() {
		return fansUidList;
	}
	
	public void setFansUidList(List<String> fansUidList) {
		this.fansUidList = fansUidList;
	}

	public void addFanUid(String fanUid){
		fansUidList.add(fanUid);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
