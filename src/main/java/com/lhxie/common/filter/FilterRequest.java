package com.lhxie.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class FilterRequest implements Filter{
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
			HttpServletRequest request = (HttpServletRequest)arg0;
			//重定向到错误提示页面
			request.getRequestDispatcher("/login/login.html").forward(arg0, arg1);
			return;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
