package com.example.demo.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class ApiKeyFilter implements Filter {
	private String apiKey;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		apiKey = config.getInitParameter("ApiKey");
		if (!StringUtils.isEmpty(apiKey)) {
			System.out.println("ApiKeyFilter initialized with apiKey = " + apiKey);
		} else {
			System.out.println("ApiKeyFilter initialized without apiKey so no filtering");
		}
	}
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	System.out.println("doFilter apiKey=[" + apiKey + "]");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (!StringUtils.isEmpty(apiKey) && !apiKey.equals(req.getHeader("Authorization"))) {
			res.setStatus(HttpStatus.UNAUTHORIZED.value());
		} else {
			chain.doFilter(request, response);
		}
    }
}
