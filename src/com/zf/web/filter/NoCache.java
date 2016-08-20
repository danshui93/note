package com.zf.web.filter;



import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 禁止浏览器缓存所有动态页面
 * @author Administrator
 *
 */
public class NoCache implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		//禁止浏览器缓存所有动态页面
		resp.setDateHeader("Expires",-1);
		resp.setHeader("Cache-Control","no-cache");
		resp.setHeader("Pragma","no-cache");

		System.out.println("不缓存页面....");
		chain.doFilter(req, resp);
		
	}

	@Override
	public void destroy() {
		
	}

	
}
