package com.zf.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSON;
import com.zf.po.ResultInfo;
import com.zf.po.User;
import com.zf.service.UserService;
import com.zf.util.WebUtils;

/**
 * Servlet implementation class UserWeb
 */
@WebServlet(name="UserWeb",urlPatterns="/user")
public class UserWeb extends HttpServlet {
	private UserService ser;
	public UserWeb() {
		ser=new UserService();
	}
	
	private static final long serialVersionUID = 1L;
  
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String act = req.getParameter("act");
		if(act==null||act.equals("")){
			login(req,resp);
		}else if(act.equals("logout")){
			logout(req,resp);
		}else if(act.equals("view")){
			view(req,resp);
		}else if(act.equals("repeat")){
			isUnique(req,resp);
		}else if(act.equals("save")){
			save(req,resp);
		}
	}

	private void save(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//获取上下文路径
		String contextPath = req.getServletContext().getRealPath("/");
		//文件工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//缓存 5k
		factory.setSizeThreshold(5*1024);
		//临时文件地址
		factory.setRepository(new File(contextPath+"upload"));
		//文件上传处理器
		ServletFileUpload upload = new ServletFileUpload(factory);
		//设置文件字符集
		upload.setHeaderEncoding("utf-8");
		//设置文件上传的最大大小 10m
		upload.setFileSizeMax(10*1024*1024);
		
		//获取Session的User
		User user = (User)req.getSession().getAttribute("user");
		//运用upload分析req
		try {
			List<FileItem> items = upload.parseRequest(req);
			Iterator<FileItem> itemIt = items.iterator();
			while (itemIt.hasNext()) {
				FileItem item = itemIt.next();
				if(item.isFormField()){//表单普通域
					if(item.getFieldName().equals("nickName")){
						user.setNickName(item.getString("utf-8"));
					}else if(item.getFieldName().equals("mood")){
						user.setMood(item.getString("utf-8"));
					}
				}else{//文件
					String fileName =  item.getName();
					if(!WebUtils.isNull(fileName)){
						fileName = WebUtils.getNow()+fileName.substring(fileName.lastIndexOf("."));//制作文件的名称
						try {
							item.write(new File(contextPath+"upload/"+fileName));
							user.setImg(fileName);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//用户信息准备完全
		ResultInfo<User> info = ser.changeUser(user);
		if(info.getResultCode()>0){
			//更新成功
			//将最新user替换session中的user
			req.getSession().setAttribute("user", info.getResult());
			req.getRequestDispatcher("user?act=view").forward(req, resp);
		}else{
			req.getRequestDispatcher("user?act=view").forward(req, resp);
			return;
		}
		
		
 		
	}

	private void isUnique(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ResultInfo<User> info = new ResultInfo<User>();
		info.setResultCode(-1);
		String nickName = req.getParameter("nickName");
		if(!WebUtils.isNull(nickName)){
			info = ser.findUserByNickName(nickName);
		}
		PrintWriter pw = resp.getWriter();
		pw.write(JSON.toJSONString(info));
		pw.flush();
		return;
	}

	private void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("changePage", "user/user.jsp");
		req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
	}

	private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.getSession().removeAttribute("user");
		Cookie cookie = new Cookie("userStr", "");
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
		resp.sendRedirect("login.jsp");
	}

	private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();
		//获取uname和upwd,生成相应的User
		String uname = req.getParameter("uname");
		System.out.println(uname);
		String upwd = req.getParameter("upwd");
		if(uname==null||upwd==null){
			resp.sendRedirect("login.jsp");
			return;
		}
		User inputUser = new User();
		inputUser.setUname(uname);
		inputUser.setUpwd(upwd);
		//进行登陆比较
		ResultInfo<User> info = ser.login(inputUser);
		if(info.getResultCode()>0){//成功
			//添加session
			session.setAttribute("user", info.getResult());
			//根据rem  添加cookie
			String rem = req.getParameter("rem");
			if(!WebUtils.isNull(rem)){
				Cookie cookie = new Cookie("userStr",uname+"-"+upwd);
				cookie.setMaxAge(1*60*60*24*7);//有效期7天
				//设置路径
				cookie.setPath(req.getContextPath());
				//添加cookie
				resp.addCookie(cookie);
			}
			resp.sendRedirect("main");
			return;
		}else{
			req.setAttribute("uname", uname);
			req.setAttribute("upwd", upwd);
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}
		
	}
	


}
