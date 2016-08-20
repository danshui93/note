package com.zf.web.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zf.po.Note;
import com.zf.po.NoteType;
import com.zf.po.ResultInfo;
import com.zf.po.User;
import com.zf.service.NoteService;
import com.zf.service.TypeService;
import com.zf.service.UserService;
import com.zf.util.WebUtils;

/**
 * Servlet Filter implementation class AutoLogin
 */
@WebFilter("/*")
public class AutoLogin implements Filter {

    /**
     * Default constructor. 
     */
    public AutoLogin() {
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
		//强转req，resp
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		//如果session中存在user 就放行
		if(req.getSession().getAttribute("user")!=null){
			chain.doFilter(req, resp);
			return;
		}
		System.out.println("进入自动登录过滤器");
		//再判断cookies中是否存在正确的信息
		String userStr = null;
		Cookie[] cookies = req.getCookies();
		if(cookies!=null&&cookies.length>0){
			for (Cookie c : cookies) {
				if(c.getName().equals("userStr")){
					userStr = c.getValue();
					break;
				}
			}
		}
		if(!WebUtils.isNull(userStr)){
			String uname=userStr.split("-")[0];
			String upwd=userStr.split("-")[1];
			//login
			User inputUser = new User();
			inputUser.setUname(uname);
			inputUser.setUpwd(upwd);
			//进行登陆比较
			ResultInfo<User> info = new UserService().login(inputUser);
			if(info.getResultCode()>0){
				req.getSession().setAttribute("user", info.getResult());
				ResultInfo<List<Note>> noteDateInfo = new NoteService().findUserNoteCount(info.getResult());
				ResultInfo<List<NoteType>> typeCountInfo = new TypeService().findUserTypeCount(info.getResult());
				req.getSession().setAttribute("noteDateInfo", noteDateInfo);
				req.getSession().setAttribute("typeCountInfo", typeCountInfo);
			}
		}
		
		chain.doFilter(req, resp);
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
