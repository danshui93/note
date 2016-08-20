package com.zf.po;

public class User {
	private int userId;
	private String uname;
	private String upwd;
	private String mood;
	private String img;
	private String nickName;
	public User(int userId, String uname, String upwd, String mood, String img, String nickName) {
		super();
		this.userId = userId;
		this.uname = uname;
		this.upwd = upwd;
		this.mood = mood;
		this.img = img;
		this.nickName = nickName;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUpwd() {
		return upwd;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", uname=" + uname + ", upwd=" + upwd + ", mood=" + mood + ", img=" + img
				+ ", nickName=" + nickName + "]";
	}
	
	
}
