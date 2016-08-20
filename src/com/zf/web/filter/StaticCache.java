package com.zf.web.filter;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import com.zf.util.WebUtils;


/**
 * 处理中文乱码
 * @author Administrator
 *
 */
public class StaticCache implements Filter {
	FilterConfig fConfig;
    /**
     * Default constructor. 
     */
    public StaticCache() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("进入静态缓存....");
		HttpServletRequest req =(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse) response;
		//获取文件后缀
		String url =req.getRequestURI();
		//获取后缀
		String suffix =url.substring(url.lastIndexOf("."));	
		//获取参数的值
		String value =fConfig.getInitParameter(suffix);
		//设置时间
		if(!WebUtils.isNull(value)){
			resp.setDateHeader("expires", Integer.valueOf(value)*60*60*1000);
		}
		chain.doFilter(req, resp);
		return ;		
	}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.fConfig =fConfig;
	}

}
