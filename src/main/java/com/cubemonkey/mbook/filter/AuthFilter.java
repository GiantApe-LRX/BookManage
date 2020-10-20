package com.cubemonkey.mbook.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 限制不登陆不能进入管理页面
* @Description
* @author CubeMonkey Email:2192231938@qq.com
* @version 
* @date 2020年9月13日下午2:04:52
*
 */
public class AuthFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String requestURI = req.getRequestURI();
		//不需要过滤的url，包括LoginServlet，css，js，png等。
		String[] urls = {"/login", ".js",".css", ".ico",".jpg",".png"};
		boolean flag = false;
		for (String str : urls) {
		    if (requestURI.indexOf(str) != -1) {
		        flag = true;
		        break;
		    }
		}
		if(flag) {
			chain.doFilter(request, response);
		}else {
			HttpSession session = req.getSession();
			String username = (String) session.getAttribute("user");
			if (username != null) {
				chain.doFilter(request, response);
			}else {
				response.setContentType("text/html; charset=utf-8");
				req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
