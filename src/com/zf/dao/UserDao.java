package com.zf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zf.po.ResultInfo;
import com.zf.po.User;
import com.zf.util.DBUtil;

/**
 * 1、用户登录
 * @author 44703
 *
 */
public class UserDao {
	private static Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	public  User queryUser(User inputUser){
		
		//与数据库建立连接
		Connection conn = DBUtil.getConn();
		String sql = "select userid,username,upwd,mood,img,nickName from t_user where username = ? and upwd = ? ";
		//获得预编译块
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, inputUser.getUname());
			ps.setString(2, inputUser.getUpwd());
			//执行sql
			rs = ps.executeQuery();
			if(rs.next()){
				inputUser.setUserId(rs.getInt("userid"));
				inputUser.setUname(rs.getString("username"));
				inputUser.setMood(rs.getString("mood"));
				inputUser.setNickName(rs.getString("nickName"));
				inputUser.setImg(rs.getString("img"));
				logger.info("查找用户成功！");
				return inputUser;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public  User queryUserByNickName(String nickName){
		
		//与数据库建立连接
		Connection conn = DBUtil.getConn();
		String sql = "select userid,username,upwd,mood,img,nickName from t_user where nickName = ?";
		//获得预编译块
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, nickName);
			//执行sql
			rs = ps.executeQuery();
			if(rs.next()){
				user = new User();
				user.setUserId(rs.getInt("userid"));
				user.setUname(rs.getString("username"));
				user.setMood(rs.getString("mood"));
				user.setNickName(rs.getString("nickName"));
				user.setImg(rs.getString("img"));
				logger.info("查找"+nickName+"成功！");
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
		
	}
	
	public  User updateUser(User inputUser){
		
		//与数据库建立连接
		Connection conn = DBUtil.getConn();
		String sql = "update t_user set nickName =?,img = ?,mood = ? where userId = ? ";
		//获得预编译块
		PreparedStatement ps = null;
		User user = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, inputUser.getNickName());
			ps.setString(2, inputUser.getImg());
			ps.setString(3, inputUser.getMood());
			ps.setInt(4, inputUser.getUserId());
			//执行sql
			int rs = ps.executeUpdate();
			if(rs > 0){
				
				return inputUser;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
