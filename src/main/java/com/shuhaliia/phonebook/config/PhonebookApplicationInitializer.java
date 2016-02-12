package com.shuhaliia.phonebook.config;

import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class PhonebookApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		
		return new Class<?>[]{RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[]{WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		
		return new String[]{"/"};
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setLoadOnStartup(1);
	}
}
