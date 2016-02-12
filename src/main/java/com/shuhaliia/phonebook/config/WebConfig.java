package com.shuhaliia.phonebook.config;


import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.shuhaliia.phonebook.data.UserErrorRepositoryImpl;
import com.shuhaliia.phonebook.service.UserDetailsServiceImpl;




@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.shuhaliia.phonebook")
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public ViewResolver viewResolver(SpringTemplateEngine templateEngine){
		ThymeleafViewResolver viewResolver = new  ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		return viewResolver;
	}
	
	
	@Bean
	public TemplateEngine templateEngine(TemplateResolver templateResolver){
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
		}
	
	@Bean
	public TemplateResolver templateResolver(){
		TemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		return templateResolver;
		}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Bean
	public UserDetailsService userDetailsService(){
		return new UserDetailsServiceImpl();
	}

	@Bean
	public UserErrorRepositoryImpl userErrorRepositoryImpl(){
		return new UserErrorRepositoryImpl();
	}
	
	
}
