package com.shuhaliia.phonebook.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.shuhaliia.phonebook.data.UserInfo;

@Service
@PropertySource("classpath:/queries.properties")
public class UserServiceImpl implements UserService {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Environment env;
	
	 @Autowired
	 public void setDataSource(DataSource dataSource) {
	        this.jdbcTemplate = new JdbcTemplate(dataSource);
	    }

	@Override
	public UserInfo getUser(String login) {
		
		String sql = env.getProperty("queries.select.user");
	
	try{	
	
		UserInfo user = jdbcTemplate.queryForObject(sql, new Object[]{login},
				new RowMapper<UserInfo>() {
		            public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		                UserInfo user = new UserInfo(); 
		                user.setLogin(rs.getString("login"));
		                user.setPassword(rs.getString("password"));
		                user.setRole(rs.getString("role"));
		                return user;

            }
    });
		
		return user;
		
	}catch(EmptyResultDataAccessException e){
		return null;
		}

		
		
	}

}
