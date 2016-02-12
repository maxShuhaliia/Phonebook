package com.shuhaliia.phonebook.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.shuhaliia.phonebook.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:/jdbc.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth
			.inMemoryAuthentication()
			.withUser("admin").password("admin").roles("ADMIN");
		auth.userDetailsService(userDetailsService);	
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			
			.authorizeRequests()
				.antMatchers("/").authenticated()
				.and()
					.formLogin()
					.loginPage("/login")
					 .usernameParameter("login")
		             .passwordParameter("password")
					.and()
					
					
			.authorizeRequests()
			.antMatchers("/register").permitAll()
			.and()
			.logout()
			.permitAll();
		

		http.logout()
        .permitAll()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout")
        .invalidateHttpSession(true);	


	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource(){
			BasicDataSource dataSource = new BasicDataSource();
			dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
			dataSource.setUrl(env.getProperty("jdbc.url"));
			dataSource.setUsername(env.getProperty("jdbc.login"));
			dataSource.setPassword(env.getProperty("jdbc.password"));
			dataSource.setInitialSize(5);
		return dataSource;
	}
	
	@Bean
	 public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	        return new JdbcTemplate(dataSource);
	    }
	
	 @Bean
	 public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
	        return new NamedParameterJdbcTemplate(dataSource);
	    }
	 
		
		@Bean(name="authenticationManager")
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
		}
		
		@Bean
		public Md5PasswordEncoder passwordEncoder() throws Exception {
		return new Md5PasswordEncoder();
		}
	 
}
