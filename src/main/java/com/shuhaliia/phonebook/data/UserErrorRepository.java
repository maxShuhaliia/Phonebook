package com.shuhaliia.phonebook.data;

import org.springframework.validation.BindingResult;

public interface UserErrorRepository {

	public UserInfo getUser();
	
	public void setUserInfo(UserInfo user);
	
	public BindingResult getBindingResult();
	
	public void setBindingResult(BindingResult bindingResult);
}
