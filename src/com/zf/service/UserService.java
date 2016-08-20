package com.zf.service;

import com.zf.dao.UserDao;
import com.zf.po.ResultInfo;
import com.zf.po.User;
import com.zf.util.WebUtils;

public class UserService {
	private UserDao dao = new UserDao();
	/**
	 * 1、对密码进行加密
	 * 2、进行比较，返回Resultinfo
	 */
	public ResultInfo<User> login(User inputUser){
		//加密
		String upwd = inputUser.getUpwd();
		upwd = WebUtils.encode(upwd);
		
		inputUser.setUpwd(upwd);
		//比较
		ResultInfo<User>  info = new ResultInfo<User>();
		User user = dao.queryUser(inputUser);
		if(null==user){
			info.setResultCode(-1);
		}else{
			info.setResult(user);
		}
		return info;
		
	}
	
	public ResultInfo<User> findUserByNickName(String nickName){
		ResultInfo<User>  info = new ResultInfo<User>();
		User user = dao.queryUserByNickName(nickName);
		if(null==user){//不重复 返回1
			info.setResultCode(1);
		}else{ //重复
			info.setResultCode(-1);;
		}
		return info;
		
	}
	
	public ResultInfo<User> changeUser(User inputUser){
		ResultInfo<User>  info = new ResultInfo<User>();
		User user = dao.updateUser(inputUser);
		if(null==user){//不成功 设为-1
			info.setResultCode(-1);
		}else{ //重复
			info.setResult(user);
			info.setResultCode(1);
		}
		return info;
		
	}
	
}
