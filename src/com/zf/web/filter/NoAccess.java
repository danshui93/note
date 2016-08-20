package com.zf.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class NoAccess
 */
@WebFilter("/*")
public class NoAccess implements Filter {

    /**
     * Default constructor. 
     */
    public NoAccess() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//强转req、resp
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		System.out.println("进入权限过滤器");
		//如果session中存在user 就放行
		if(req.getSession().getAttribute("user")!=null){
			chain.doFilter(req, resp);
			return;
		}
		//获取请求资源
		String uri = req.getRequestURI();
		
		//如果访问静态资源或者login.jsp就放行
		if(uri.contains("statics/")||uri.contains("login.jsp")||uri.contains("UEditor/")){
			chain.doFilter(req, resp);
			return;
		}
		
		//与登录、退出有关也放行
		if(uri.contains("/user")){
			String act = req.getParameter("act");
			if(null==act||act.equals("")||act.equals("logout")){
				chain.doFilter(req, resp);
				return;
			}
		}
		
		//拒绝操作
		//req.getContextPath()获取当前系统路径(上下文路径)
		resp.sendRedirect(req.getContextPath()+"/login.jsp");
		return;
		
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
