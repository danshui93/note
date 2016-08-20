package com.zf.web;

import java.io.IOException;
import java.util.Date;
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
import com.zf.service.NoteService;
import com.zf.service.TypeService;
import com.zf.util.WebUtils;

/**
 * Servlet implementation class NoteWeb
 */
@WebServlet("/note")
public class NoteWeb extends HttpServlet {
	private TypeService typeService = new TypeService();
	private NoteService noteService = new NoteService();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String act = req.getParameter("act");
		if(WebUtils.isNull(act)){
			view(req,resp);
		}else if(act.equals("save")){
			save(req,resp);
		}else if(act.equals("show")){
			show(req,resp);
		}else if(act.equals("find")){
			find(req,resp);
		}else if(act.equals("showForEdit")){
			showForEdit(req,resp);
		}else if(act.equals("del")){
			del(req,resp);
		}
	}
	
	private void del(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String noteId = req.getParameter("noteId");
		Note note = new Note();
		note.setNoteId(Integer.valueOf(noteId));
		ResultInfo<Note> noteInfo = noteService.removeNote(note);
		resp.getWriter().append(JSON.toJSONString(noteInfo)).flush();
		
	}

	private void showForEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String noteId = req.getParameter("noteId");
		Note note  = new Note();
		note.setNoteId(Integer.valueOf(noteId));
		ResultInfo<Note> noteEditInfo = noteService.findNoteByNoteId(note);
		User user = (User) req.getSession().getAttribute("user");
		ResultInfo<List<NoteType>>  noteTypeInfo = typeService.findUserNoteType(user);
		req.setAttribute("noteTypeInfo", noteTypeInfo);
		req.setAttribute("noteEditInfo", noteEditInfo);
		req.setAttribute("changePage", "note/noteForEdit.jsp");
		req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
	}
	
	
	private void find(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String typeId = req.getParameter("typeId");
		Note note  = new Note();
		note.setTypeId(Integer.valueOf(typeId));
		ResultInfo<Note> noteFindInfo = noteService.findNoteByTypeId(note);
		resp.getWriter().append(JSON.toJSONString(noteFindInfo)).flush();;
		
	}

	private void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String noteId = req.getParameter("noteId");
		Note note  = new Note();
		note.setNoteId(Integer.valueOf(noteId));
		ResultInfo<Note> noteShowInfo = noteService.findNoteByNoteId(note);
		req.setAttribute("noteShowInfo", noteShowInfo);
		req.setAttribute("changePage", "note/noteShow.jsp");
		req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
	}

	//点击note.jsp中保存按钮
	private void save(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String typeId = req.getParameter("typeId");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String noteId = req.getParameter("noteId");
		Note note = new Note();
		note.setTypeId(Integer.valueOf(typeId));
		note.setTitle(title);
		note.setContent(content);
		if(!WebUtils.isNull(noteId)){
			note.setNoteId(Integer.valueOf(noteId));
			ResultInfo<Note> noteInfo = noteService.editNote(note);
			if(noteInfo.getResultCode()>0){//添加成功
				resp.sendRedirect("main");
				return;
			}else{//回显数据到添加界面
				req.setAttribute("noteInfo", noteInfo);
				view(req, resp);
			}
		}else{
			ResultInfo<Note> noteInfo = noteService.addNote(note);
			if(noteInfo.getResultCode()>0){//添加成功
				resp.sendRedirect("main");
				return;
			}else{//回显数据到添加界面
				req.setAttribute("noteInfo", noteInfo);
				view(req, resp);
			}
		}
	}

	//点击发表日志,查看添加界面
	private void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("user");
		ResultInfo<List<NoteType>>  noteTypeInfo = typeService.findUserNoteType(user);
		req.setAttribute("noteTypeInfo", noteTypeInfo);
		req.setAttribute("changePage", "note/note.jsp");
		req.getRequestDispatcher("mainTemp.jsp").forward(req, resp);
		return;
		
	}

}
