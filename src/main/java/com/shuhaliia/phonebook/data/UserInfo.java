package com.shuhaliia.phonebook.data;


import javax.validation.constraints.Size;

public class UserInfo{
	
	@Size(min=3, max = 15, message="login is incorrect")
	private String login;
	@Size(min=3, max = 15, message="first name is incorrect")
	private String firstName;
	@Size(min=3, max = 15, message="surname is incorrect")
	private String surName;
	@Size(min=3, max = 15, message="middle name is incorrect")
	private String middleName;
	@Size(min=3, max = 15, message="password is incorrect")
	private String password;
	
	private String role;
		
	public UserInfo(){}
	
	public UserInfo(String login, String firstName, String surName, String middleName, String password, String role){
		this.login = login;
		this.firstName = firstName;
		this.surName = surName;
		this.middleName = middleName;
		this.password = password;
		this.role = role;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "login:"+login+" firstName:"+firstName+" surName:"+surName+" middleName:"+middleName;
	}	
}
