package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
	
	@Value("${demo.api-key:}")
	private String apiKey;
	
	@Bean
	public FilterRegistrationBean<ApiKeyFilter> apiKeyFilter() {
	    FilterRegistrationBean<ApiKeyFilter> registration = new FilterRegistrationBean<ApiKeyFilter>();
	    registration.setFilter(new ApiKeyFilter());
	    registration.addUrlPatterns("/api/*");
	    registration.addInitParameter("ApiKey", apiKey);
	    registration.setName("ApiKeyFilter");
	    registration.setOrder(1);
	    return registration;
	}
}
