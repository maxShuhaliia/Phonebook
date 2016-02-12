package com.shuhaliia.phonebook.data;

import org.springframework.validation.BindingResult;

public class UserErrorRepositoryImpl implements UserErrorRepository {

	public UserInfo user;
	public BindingResult bindingResult;
	
	@Override
	public UserInfo getUser() {
		return user;
	}
	@Override
	public void setUserInfo(UserInfo user) {
				this.user = user;
	}
	@Override
	public BindingResult getBindingResult() {
		return bindingResult;
	}
	@Override
	public void setBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
		
	}

}
