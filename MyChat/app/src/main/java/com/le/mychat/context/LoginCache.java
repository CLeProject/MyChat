package com.le.mychat.context;

import java.io.Serializable;

public class LoginCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 820484417723318244L;


	/**
	 * 登录成功后返回用户基本信息
	 */
	private UserInfo userInfo;

	/**
	 * 令牌
	 */
	private String token;










	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}






}
