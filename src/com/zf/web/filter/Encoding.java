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
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
/**
 * 处理中文乱码
 * @author Administrator
 *
 */
@WebFilter(filterName="Encoding",urlPatterns="/*")
public class Encoding implements Filter {
	FilterConfig fConfig;
    /**
     * Default constructor. 
     */
    public Encoding() {
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
		System.out.println("进入字符集拦截器....");
		HttpServletRequest req =(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse) response;
		
		//1、获取的tomcat版本URIEncoding默认值不再是ISO8859-1，而变成了UTF-8 
		//Apache Tomcat/7.0.64
		//Apache Tomcat/8.0.36
		String serverInfo =req.getServletContext().getServerInfo();
		Pattern pattern =Pattern.compile("cat/(\\d+)");
		Matcher matcher =pattern.matcher(serverInfo);
		int version =7;
		if(matcher.find()){
			System.out.println(matcher.group(0)+"-->"+matcher.group(1));
			version =Integer.valueOf(matcher.group(1));
		}
		if(version>=8){ //tomcat8不用处理
			chain.doFilter(req, resp);			
			return ;	
		}
		
		//tomcat8以下的版本
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8"); 
		
		//处理get
		String method =req.getMethod();
		
		if(method.equalsIgnoreCase("get")){ //处理get请求
			chain.doFilter(new MyHttpServletRequestWrapper(req), resp);		
			return ;
		}
		
		chain.doFilter(req, resp);			
		return ;
		
	}
	//针对get请求
	class MyHttpServletRequestWrapper extends HttpServletRequestWrapper{
		private HttpServletRequest request;
		public MyHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
			this.request =request;
			
		}
		
		@Override
		public String getParameter(String name) {
			String value =this.request.getParameter(name);
			//字符串处理  编码	
			if(value==null){
				return value;
			}
			try {
				return new String(value.getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return value;
			
		}
		
		
	}
	
	
	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.fConfig =fConfig;
	}

}
