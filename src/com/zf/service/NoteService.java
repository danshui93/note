package com.zf.service;

import java.util.List;

import com.zf.dao.NoteDao;

import com.zf.po.Note;
import com.zf.po.Page;
import com.zf.po.ResultInfo;
import com.zf.po.User;

public class NoteService {
	private NoteDao noteDao = new NoteDao();
	public ResultInfo<Note> addNote(Note note){
		ResultInfo<Note>  info = new ResultInfo<Note>();
		int rs = noteDao.insertNote(note);
		if(rs>0){//插入成功 返回1
			info.setResultCode(1);
		}else{ //不成功
			info.setResultCode(-1);
			info.setResult(note);
		}
		return info;
		
	}
	
	public ResultInfo<Note> removeNote(Note note){
		ResultInfo<Note>  info = new ResultInfo<Note>();
		int rs = noteDao.deleteNote(note);
		if(rs>0){//删除成功 返回1
			info.setResultCode(1);
		}else{ //不成功
			info.setResultCode(-1);
		}
		return info;
		
	}
	
	public ResultInfo<Note> editNote(Note note){
		ResultInfo<Note>  info = new ResultInfo<Note>();
		int rs = noteDao.updateNote(note);
		if(rs>0){//插入成功 返回1
			info.setResultCode(1);
		}else{ //不成功
			info.setResultCode(-1);
			info.setResult(note);
		}
		return info;
		
	}
	
	public ResultInfo<Note> findNoteByNoteId(Note note){
		ResultInfo<Note>  info = new ResultInfo<Note>();
		note = noteDao.queryNoteByNoteId(note);
		if(note!=null){//查询成功 返回1
			info.setResultCode(1);
			info.setResult(note);
		}else{ //不成功
			info.setResultCode(-1);
		}
		return info;
		
	}
	
	public ResultInfo<Note> findNoteByTypeId(Note note){
		ResultInfo<Note>  info = new ResultInfo<Note>();
		int rs  = noteDao.queryNoteByTypeId(note);
		if(rs>0){//存在note  不能删
			info.setResultCode(-1);
		}else{ //不成功
			info.setResultCode(1);
		}
		return info;
		
	}
	
	
	public ResultInfo<Page<Note>> findNote(int current,Note note){
		ResultInfo<Page<Note>>  pageInfo = new ResultInfo<Page<Note>>();
		Page<Note> page = new Page<Note>();
		page.setCurrentPage(current);
		page = noteDao.queryPageNote(page,note);
		if(page.getTotalRecords()>0){//插入成功 返回1
			pageInfo.setResultCode(1);
			pageInfo.setResult(page);
		}else{ //不成功
			pageInfo.setResultCode(-1);
		}
		return pageInfo;
		
	}
	
	public ResultInfo<List<Note>> findUserNoteCount(User user){
		ResultInfo<List<Note>>  info = new ResultInfo<List<Note>>();
		List<Note> list = noteDao.queryUserDateCount(user);
		if(list!=null||list.size()>0){//插入成功 返回1
			info.setResultCode(1);
			info.setResult(list);
		}else{ //不成功
			info.setResultCode(-1);
		}
		return info;
		
	}
	
}
