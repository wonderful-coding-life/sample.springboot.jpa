package com.example.demo;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@WebFilter(urlPatterns = "/api/*")
@Order(1)
public class ApiKeyFilter implements Filter {

	@Value("${demo3.api-key}")
	private String apiKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (!StringUtils.isEmpty(apiKey) && !apiKey.equals(req.getHeader("Authorization"))) {
			res.setStatus(HttpStatus.UNAUTHORIZED.value());
		} else {
			chain.doFilter(request, response);
		}
    }
}
