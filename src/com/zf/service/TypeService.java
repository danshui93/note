package com.zf.service;

import java.util.List;

import com.zf.dao.TypeDao;
import com.zf.po.NoteType;
import com.zf.po.ResultInfo;
import com.zf.po.User;

public class TypeService {
	private TypeDao typeDao = new TypeDao();
	
	public ResultInfo<List<NoteType>> findUserNoteType(User inputUser){
		List<NoteType> list = typeDao.queryNoteType(inputUser);
		ResultInfo<List<NoteType>> info = new ResultInfo<List<NoteType>>();
		if(null!=list&&list.size()>0){ //成功
			info.setResultCode(1);
			info.setResult(list);
		}else{   //失败
			info.setResultCode(-1);
		}
		return info;
	}
	
	public ResultInfo<NoteType> findNoteTypeByTypeName(NoteType type){
		ResultInfo<NoteType>  info = new ResultInfo<NoteType>();
		type = typeDao.queryNoteTypeByTypeName(type);
		if(null==type){//不重复 返回1
			info.setResultCode(1);
		}else{ //重复
			info.setResultCode(-1);
			info.setResult(type);
		}
		return info;
		
	}
	
	public ResultInfo<NoteType> addNoteType(NoteType type){
		ResultInfo<NoteType>  info = new ResultInfo<NoteType>();
		int rs = typeDao.insertNoteType(type);
		if(rs>0){//插入成功 返回1
			info=this.findNoteTypeByTypeName(type);
			info.setResultCode(1);
		}else{ //不成功
			info.setResultCode(-1);
		}
		return info;
		
	}
	
	public ResultInfo<NoteType> changeNoteType(NoteType type){
		ResultInfo<NoteType>  info = new ResultInfo<NoteType>();
		int rs = typeDao.updateNoteType(type);
		if(rs>0){//插入成功 返回1
			info.setResultCode(1);
			info.setResult(type);
		}else{ //不成功
			info.setResultCode(-1);
		}
		return info;
		
	}
	
	//移除notetype
	public ResultInfo<NoteType> removeNoteType(NoteType type){
		ResultInfo<NoteType>  info = new ResultInfo<NoteType>();
		int rs = typeDao.deleteNoteType(type);
		if(rs>0){//删除成功 返回1
			info.setResultCode(1);
		}else{ //不成功
			info.setResultCode(-1);
		}
		return info;
		
	}
	
	public ResultInfo<List<NoteType>> findUserTypeCount(User inputUser){
		List<NoteType> list = typeDao.queryUserTypeCount(inputUser);
		ResultInfo<List<NoteType>> info = new ResultInfo<List<NoteType>>();
		if(null!=list&&list.size()>0){ //成功
			info.setResultCode(1);
			info.setResult(list);
		}else{   //失败
			info.setResultCode(-1);
		}
		return info;
	}
}
