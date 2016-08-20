package com.zf.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.zf.po.Note;
import com.zf.po.NoteType;
import com.zf.po.ResultInfo;
import com.zf.po.User;
import com.zf.service.TypeService;
import com.zf.util.WebUtils;

@WebServlet(name="TypeWeb",urlPatterns={"/type"})
public class TypeWeb extends HttpServlet {
	private TypeService typeService = new TypeService();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String act = req.getParameter("act");
		if(WebUtils.isNull(act)){
			viewList(req,resp);
		}else if(act.equals("repeat")){
			isUnique(req,resp);
		}else if(act.equals("save")){
			save(req,resp);
		}else if(act.equals("del")){
			del(req,resp);
		}
	}

	private void del(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String typeId = req.getParameter("typeId");
		ResultInfo<NoteType> info = null;
		NoteType type = new NoteType();
		type.setTypeId(Integer.valueOf(typeId));
		info = typeService.removeNoteType(type);
		resp.getWriter().append(JSON.toJSONString(info)).flush();
		return;
		
	}




	private void save(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = (User) req.getSession().getAttribute("user");
		int userId = user.getUserId();
		//获取typename 插入数据库
		String typeName = req.getParameter("typeName");
		String typeId = req.getParameter("typeId");
		ResultInfo<NoteType> info = null;
		//封装NoteType
		NoteType type = new NoteType();
		type.setUserId(userId);
		type.setTypeName(typeName);
		if(WebUtils.isNull(typeId)){
			 info = typeService.addNoteType(type);
		}else{
			type.setTypeId(Integer.valueOf(typeId));
			info = typeService.changeNoteType(type);
		}
		resp.getWriter().append(JSON.toJSONString(info)).flush();
		return;
	}




	private void isUnique(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = (User)req.getSession().getAttribute("user");
		ResultInfo<NoteType> info = new ResultInfo<NoteType>();
		info.setResultCode(-1);
		NoteType type = new NoteType();
		String typeName = req.getParameter("typeName");
		type.setUserId(user.getUserId());
		type.setTypeName(typeName);
		if(!WebUtils.isNull(typeName)){
			info = typeService.findNoteTypeByTypeName(type);
		}
		PrintWriter pw = resp.getWriter();
		pw.write(JSON.toJSONString(info));
		pw.flush();
		return;
		
	}




	private void viewList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取session 中的user
		User user = (User) req.getSession().getAttribute("user");
		ResultInfo<List<NoteType>> info = typeService.findUserNoteType(user);
		//跳转页面
		req.setAttribute("info",info);
		req.setAttribute("changePage", "noteType/type.jsp");
		req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
		return;
		
	}
}
