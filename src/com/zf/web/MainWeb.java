package com.zf.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.zf.po.Note;
import com.zf.po.NoteType;
import com.zf.po.Page;
import com.zf.po.ResultInfo;
import com.zf.po.User;
import com.zf.service.NoteService;
import com.zf.service.TypeService;
import com.zf.util.WebUtils;

@WebServlet(name="MainWeb",urlPatterns="/main")
public class MainWeb extends HttpServlet{
	private NoteService noteService  = new NoteService();
	private TypeService typeService = new TypeService();
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String act = req.getParameter("act");
		if(WebUtils.isNull(act)){
			listNote(req,resp);
		}else if(act.equals("date")){//按时间查询	
			Note note = new Note();
			note.setPubDateStr(req.getParameter("val"));
			req.setAttribute("vs", req.getParameter("vs"));
			listNote(req,resp,note);
		}else if(act.equals("key")){
			Note note = new Note();
			note.setTitle(req.getParameter("val"));
			req.setAttribute("vs", req.getParameter("val"));
			listNote(req,resp,note);
		}else if(act.equals("type")){
			Note note = new Note();
			note.setTypeId(Integer.valueOf(req.getParameter("val")));
			req.setAttribute("vs", req.getParameter("vs"));
			listNote(req,resp,note);
		}else if(act.equals("refresh")){
			refresh(req,resp);
		}
		
	}

	protected void listNote(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("user");
		
		//左边的两个显示框，将数据存入session中
		ResultInfo<List<Note>> noteDateInfo = noteService.findUserNoteCount(user);
		ResultInfo<List<NoteType>> typeCountInfo = typeService.findUserTypeCount(user);
		req.getSession().setAttribute("noteDateInfo", noteDateInfo);
		req.getSession().setAttribute("typeCountInfo", typeCountInfo);
		
		//封装note
		Note note = new Note();
		note.setUserId(user.getUserId());
		
		String currentPage = req.getParameter("current");
		int current = 1;
		if(!WebUtils.isNull(currentPage)){
			current=Integer.valueOf(currentPage);
		}
		ResultInfo<Page<Note>> PageInfo = noteService.findNote(current,note);
		req.setAttribute("PageInfo",PageInfo);
 		req.setAttribute("changePage","note/noteList.jsp");
		req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
		return;
	}
	
	
	protected void listNote(HttpServletRequest req, HttpServletResponse resp,Note note) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("user");
		//封装note
		note.setUserId(user.getUserId());
		
		String currentPage = req.getParameter("current");
		int current = 1;
		if(!WebUtils.isNull(currentPage)){
			current=Integer.valueOf(currentPage);
		}
		ResultInfo<Page<Note>> PageInfo = noteService.findNote(current,note);
		req.setAttribute("act", req.getParameter("act"));
		req.setAttribute("val", req.getParameter("val"));
		req.setAttribute("PageInfo",PageInfo);
 		req.setAttribute("changePage","note/noteList.jsp");
		req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
		return;
	}
	
	
	protected void refresh(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("user");
		//左边的类别显示框，查到数据存入writer中
		ResultInfo<List<NoteType>> typeCountInfo = typeService.findUserTypeCount(user);
		req.getSession().setAttribute("typeCountInfo", typeCountInfo);
		//resp.getWriter().append(JSON.toJSONString(typeCountInfo));
	
	}
	
	
}
