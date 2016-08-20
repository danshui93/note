package com.zf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zf.po.NoteType;
import com.zf.po.User;
import com.zf.util.DBUtil;

public class TypeDao {
	//获取记录器slf4
		private static Logger logger =LoggerFactory.getLogger(UserDao.class);
		/**
		 * 查找用户的notetype(根据用户名及密码查找用户  用户个人信息)
		 * @throws SQLException 
		 * @throws ClassNotFoundException 
		 */
		public List<NoteType>  queryNoteType(User inputUser) {
			List<NoteType> list = new ArrayList<NoteType>();
			//1、加载驱动 获取连接
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				conn =DBUtil.getConn();
				//2、获取处理块
				String sql ="select typeid,typename,userid "
						+ " from t_note_type where userid = ? order by typeid";
				ps =conn.prepareStatement(sql);
				//3、填充参数 并执行
				ps.setInt(1, inputUser.getUserId());
				rs =ps.executeQuery();
				NoteType type = null;
				//4、分析结果
				while(rs.next()){
					type = new NoteType();
					type.setTypeId(rs.getInt("typeid"));
					type.setTypeName(rs.getString("typename"));
					type.setUserId(rs.getInt("userid"));
					logger.info("查找类型成功");
					list.add(type);
				}
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("查询t_note_type存在错误");
			}finally {
				DBUtil.release(conn, ps, rs);
			}	
			return null;
		}
		
		/**
		 * 根据typename、userid 查找是否存在notetype
		 * @param type
		 * @return
		 */
		public NoteType queryNoteTypeByTypeName(NoteType type) {
			//1、加载驱动 获取连接
			try {
				Connection conn =DBUtil.getConn();
				//2、获取处理块
				String sql ="select typeid,typename,userid "
						+ " from t_note_type where userid = ? and typename = ? order by typeid";
				PreparedStatement ps =conn.prepareStatement(sql);
				//3、填充参数 并执行
				ps.setInt(1, type.getUserId());
				ps.setString(2, type.getTypeName());
				ResultSet rs =ps.executeQuery();
				//4、分析结果
				while(rs.next()){
					type.setTypeId(rs.getInt("typeid"));
					logger.info("查找"+type.getTypeName()+"成功");
					return type;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("查询t_note_type存在错误");
			}	
			return null;
		}
		
		/**
		 * 插入notetype  
		 * @param type
		 * @return
		 */
		public int insertNoteType(NoteType type) {
			//1、加载驱动 获取连接
			try {
				Connection conn =DBUtil.getConn();
				//2、获取处理块
				String sql ="insert into t_note_type(typeid,typename,userid) values(null,?,?)";
				PreparedStatement ps =conn.prepareStatement(sql);
				//3、填充参数 并执行
				ps.setString(1, type.getTypeName());
				ps.setInt(2, type.getUserId());
				int rs =ps.executeUpdate();
				//4、分析结果
				return rs;
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("插入notetype错误");
			}	
			return -1;
		}
		
		
		public int updateNoteType(NoteType type) {
			//1、加载驱动 获取连接
			try {
				Connection conn =DBUtil.getConn();
				//2、获取处理块
				String sql ="update t_note_type set typename = ? where typeid = ? ";
				PreparedStatement ps =conn.prepareStatement(sql);
				//3、填充参数 并执行
				ps.setString(1, type.getTypeName());
				ps.setInt(2, type.getTypeId());
				int rs =ps.executeUpdate();
				//4、分析结果
				return rs;
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("更新notetype错误");
			}	
			return -1;
		}
		
		//deleteNotetype
		public int deleteNoteType(NoteType type) {
			//1、加载驱动 获取连接
			try {
				Connection conn =DBUtil.getConn();
				//2、获取处理块
				String sql ="delete from t_note_type  where typeid = ? ";
				PreparedStatement ps =conn.prepareStatement(sql);
				//3、填充参数 并执行
				ps.setInt(1, type.getTypeId());
				int rs =ps.executeUpdate();
				//4、分析结果
				return rs;
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("删除notetype错误");
			}	
			return -1;
		}
		
		/**
		 * 根据user 查看notetype 按名字的分组的情况
		 * @param inputUser
		 * @return
		 */
		public List<NoteType>  queryUserTypeCount(User user) {
			List<NoteType> list = new ArrayList<NoteType>();
			//1、加载驱动 获取连接
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				conn =DBUtil.getConn();
				//2、获取处理块
				String sql ="select t.typename,t.typeid,COUNT(title) c 	"
						+ " from t_note_type t LEFT JOIN t_note n 	"
						+ " on t.typeId = n.typeId where t.userid = ?	"
						+ " GROUP BY t.typename,t.typeid ";
				ps =conn.prepareStatement(sql);
				//3、填充参数 并执行
				ps.setInt(1, user.getUserId());
				rs =ps.executeQuery();
				NoteType type = null;
				//4、分析结果
				while(rs.next()){
					type = new NoteType();
					type.setTypeId(rs.getInt("typeid"));
					type.setTypeName(rs.getString("typename"));
					type.setCount(rs.getInt("c"));
					logger.info("查找类型成功");
					list.add(type);
				}
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("查询t_note_type存在错误");
			}finally {
				DBUtil.release(conn, ps, rs);
			}	
			return null;
		}
}
