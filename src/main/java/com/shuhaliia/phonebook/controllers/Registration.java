package com.shuhaliia.phonebook.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shuhaliia.phonebook.data.Contact;
import com.shuhaliia.phonebook.data.UserErrorRepositoryImpl;
import com.shuhaliia.phonebook.data.UserInfo;

@Controller
@RequestMapping(value="/registration")
@PropertySource("classpath:/queries.properties")
public class Registration{
	
	@Autowired
	private UserErrorRepositoryImpl userErrorRepositoryImpl;
	
	@Autowired
	private Environment env;
	
	 @Autowired
	 private  JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("authenticationManager")
	protected AuthenticationManager authenticationManager;
	
	@Autowired
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	 
	
	@RequestMapping(value="/")
	public String register(@Valid UserInfo user, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes){
		
		
		
		
		if(result.hasErrors()){
			userErrorRepositoryImpl.setUserInfo(user);
			userErrorRepositoryImpl.setBindingResult(result);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
			redirectAttributes.addFlashAttribute("user", user);
			return "redirect:/register";	
		}
		
		
		addUser(user);
		autologin(user, request);
		return "redirect:/";
	}
	
	private void addUser(UserInfo user) {	
		String insertUser = env.getProperty("queries.insert.user");
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("login", user.getLogin());
		parameters.put("name", user.getFirstName());
		parameters.put("surname", user.getSurName());
		parameters.put("middlename", user.getMiddleName());
		parameters.put("password", user.getPassword());
		parameters.put("role", "USER");
		namedParameterJdbcTemplate.update(insertUser, parameters);
	}

	private void autologin(UserInfo userInfo, HttpServletRequest request) {
		String username = userInfo.getLogin();
		String password = userInfo.getPassword();
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		request.getSession();	
		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager.authenticate(token);// authenticates the token
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());// creates context for that session.
		//set necessary details in session
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("authorities", token.getAuthorities());	
	}

    @RequestMapping(value = "/isUserExist/{login}", method = RequestMethod.GET)
    @ResponseBody
    public boolean isuserExist(@PathVariable String login){
    	boolean isFindUser = true;
    	
    	String serchLogin = "SELECT * FROM user WHERE login=?";
    	
    	try{
    		jdbcTemplate.queryForObject(serchLogin, new UserRowMapper(), login);
    	}catch(EmptyResultDataAccessException e){
    		isFindUser = false;
    	}
    
    	return isFindUser;
    }
    
	private static final class UserRowMapper implements RowMapper<UserInfo>{

		@Override
		public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			return new UserInfo(
					rs.getString("login"),
					rs.getString("name"),
					rs.getString("surname"),
					rs.getString("middlename"),
					rs.getString("password"),
					rs.getString("role")						
					);
						
		}
		
		
	}
	
	
	
}
