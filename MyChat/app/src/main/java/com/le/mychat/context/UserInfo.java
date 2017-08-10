package com.le.mychat.context;

import org.json.JSONObject;

import java.io.Serializable;


public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4917770757825864116L;
	private String id;
	private String image;
	/**
	 * 岗位
	 */
	private String name;
	private String sex;
	private String mobile;
	private String role;
	private String loginName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}



	public static UserInfo parse(JSONObject json) {
		if (json != null) {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(json.optString("ID"));
			userInfo.setImage(json.optString("Image"));
			userInfo.setName(json.optString("CName"));
			userInfo.setLoginName(json.optString("EName"));
			userInfo.setSex(json.optString("Sex"));
			userInfo.setRole(json.optString("Role_Name"));
			return userInfo;
		}
		return null;
	}
}
