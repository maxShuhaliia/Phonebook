package com.shuhaliia.phonebook.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.shuhaliia.phonebook.data.UserInfo;
import com.shuhaliia.phonebook.data.UserRoleEnum;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		
		
		UserInfo user = userService.getUser(login);
		// указываем роли для этого пользователя
		if(user!=null){
		        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		        roles.add(new SimpleGrantedAuthority(user.getRole()));
		        UserDetails userDetails = new User(user.getLogin(), user.getPassword(), roles);
		    return userDetails;
		}else{
			
			Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
	        roles.add(new SimpleGrantedAuthority("NOT EXISTS"));
	        UserDetails userDetails = new User("njwandfviewqgvinvkermnvkmerre", "fgreagfsdafdggreghtrhjyukytu", roles);
			
			return userDetails;
		}
	}

}
