package com.tim.w3gparser;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class User {
	@NotNull(message="用户名不能为空") 	
	private String userName;
	@Min(value=10,message="年龄最小值必须大于10岁")
	private int age;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	

}
