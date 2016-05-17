package com.lhxie.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		Cookie[] cookies = req.getCookies();
		boolean isLoginFlag = false;
		if(cookies != null && cookies.length > 0) {
			for(Cookie cookie : cookies) {
				if("sessionid".equals(cookie.getName())) {
					String sessionid = cookie.getValue();
					//表示用户已经登录
					if(req.getSession().getAttribute(sessionid) != null) {
						isLoginFlag = true;
						chain.doFilter(request, response);
						return;
					}
				}
			}
		}
		//没有登录 重定向到登录页面
		if(!isLoginFlag) {
			req.getRequestDispatcher(req.getContextPath() + "/pages/user/login.html").forward(req, resp);
			return;
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
