package com.zf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zf.po.Note;
import com.zf.po.Page;
import com.zf.po.User;
import com.zf.util.DBUtil;
import com.zf.util.WebUtils;

public class NoteDao {
	private static Logger logger =LoggerFactory.getLogger(UserDao.class);
	
	/**
	 * 插入note 
	 * @param type
	 * @return
	 */
	public int insertNote(Note note) {
		//1、加载驱动 获取连接
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn =DBUtil.getConn();
			//2、获取处理块
			String sql ="insert into t_note(noteid,title,typeid,content,pubDate) values(null,?,?,?,now())";
			ps =conn.prepareStatement(sql);
			//3、填充参数 并执行
			ps.setString(1, note.getTitle());
			ps.setInt(2, note.getTypeId());
			ps.setString(3, note.getContent());
			int rs =ps.executeUpdate();
			//4、分析结果
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("插入note错误");
		}finally{
			DBUtil.release(conn, ps, null);
		}
		return -1;
	}
	
	/**
	 * 根据noteid删除数据
	 * @param note
	 * @return
	 */
	public int deleteNote(Note note) {
		//1、加载驱动 获取连接
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn =DBUtil.getConn();
			//2、获取处理块
			String sql ="delete from t_note where noteId = ? ";
			ps =conn.prepareStatement(sql);
			//3、填充参数 并执行
			ps.setInt(1, note.getNoteId());
			int rs =ps.executeUpdate();
			//4、分析结果
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("删除note错误");
		}finally{
			DBUtil.release(conn, ps, null);
		}
		return -1;
	}
	
	/**
	 * 根据noteid修改note
	 * @param note
	 * @return
	 */
	public int updateNote(Note note) {
		//1、加载驱动 获取连接
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn =DBUtil.getConn();
			//2、获取处理块
			String sql ="update t_note set title = ?,typeid = ? ,content = ? where noteId = ? ";
			ps =conn.prepareStatement(sql);
			//3、填充参数 并执行
			ps.setString(1, note.getTitle());
			ps.setInt(2, note.getTypeId());
			ps.setString(3, note.getContent());
			ps.setInt(4, note.getNoteId());
			int rs =ps.executeUpdate();
			//4、分析结果
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("更新note错误");
		}finally{
			DBUtil.release(conn, ps, null);
		}
		return -1;
	}
	
	/**
	 * 查询note 
	 * @param type
	 * @return
	 */
	public List<Note> queryNote(Page page,Note note) {
		//1、加载驱动 获取连接
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Note> list = new ArrayList<Note>();
		try {
			conn =DBUtil.getConn();
			StringBuilder sb = new StringBuilder();
			//2、获取处理块
			String sql ="select noteId,title,content,tn.typeid,pubDate from t_note tn LEFT JOIN t_note_type tnt 	"
					+ " on tn.typeId = tnt.typeId WHERE tnt.userId = ? 	";	
			sb.append(sql);
			if(!WebUtils.isNull(note.getPubDateStr())){
				sb.append(" and DATE_FORMAT(pubdate,'%Y年%m月')=? ");
			}else if(note.getTypeId()>0){
				sb.append(" and tn.typeid=? ");
			}else if(!WebUtils.isNull(note.getTitle())){
				sb.append(" and (title like ? or content like ?) ");
			}
			sb.append(" ORDER BY tn.pubDate DESC limit ?,?");
			ps =conn.prepareStatement(sb.toString());
			//3、填充参数 并执行
			ps.setInt(1, note.getUserId());
			if(!WebUtils.isNull(note.getPubDateStr())){
				ps.setString(2, note.getPubDateStr());
				ps.setInt(3, page.getStart());
				ps.setInt(4, page.getPageSize());
			}else if(note.getTypeId()>0){
				ps.setInt(2, note.getTypeId());
				ps.setInt(3, page.getStart());
				ps.setInt(4, page.getPageSize());
			}else if(!WebUtils.isNull(note.getTitle())){
				ps.setString(2, "%"+note.getTitle()+"%");
				ps.setString(3, "%"+note.getTitle()+"%");
				ps.setInt(4, page.getStart());
				ps.setInt(5, page.getPageSize());
			}else{
				ps.setInt(2, page.getStart());
				ps.setInt(3, page.getPageSize());
			}
			
			rs =ps.executeQuery();
			//4、分析结果
			while(rs.next()){
				Note temp = new Note();
				temp.setNoteId(rs.getInt("noteId"));
				temp.setTitle(rs.getString("title"));
				temp.setContent(rs.getString("content"));
				temp.setTypeId(rs.getInt("typeid"));
				temp.setPubDate(rs.getTimestamp("pubDate"));
				temp.setPubDateStr(WebUtils.getDateStr(temp.getPubDate(), "yyyy-MM-dd"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询note错误");
		}finally{
			DBUtil.release(conn, ps, rs);
		}
		return list;
	}
	
	
	/**
	 * 查询note的记录数
	 * @param type
	 * @return
	 */
	public int countNote(Note note) {
		//1、加载驱动 获取连接
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn =DBUtil.getConn();
			//2、获取处理块
			StringBuilder sb = new StringBuilder();
			String sql ="select count(1) from t_note tn LEFT JOIN t_note_type tnt 	"
					+ " on tn.typeId = tnt.typeId WHERE tnt.userId = ? 	";
			sb.append(sql);
			if(!WebUtils.isNull(note.getPubDateStr())){
				sb.append(" and DATE_FORMAT(pubdate,'%Y年%m月')=? ");
			}else if(note.getTypeId()>0){
				sb.append(" and tn.typeid=? ");
			}else if(!WebUtils.isNull(note.getTitle())){
				sb.append(" and (title like ? or content like ?) ");
			}
			sb.append(" ORDER BY tn.pubDate DESC ");
			ps =conn.prepareStatement(sb.toString());
			//3、填充参数 并执行
			ps.setInt(1, note.getUserId());
			if(!WebUtils.isNull(note.getPubDateStr())){
				ps.setString(2, note.getPubDateStr());
			}else if(note.getTypeId()>0){
				ps.setInt(2, note.getTypeId());
			}else if(!WebUtils.isNull(note.getTitle())){
				ps.setString(2, "%"+note.getTitle()+"%");
				ps.setString(3, "%"+note.getTitle()+"%");
			}
			rs =ps.executeQuery();
			//4、分析结果
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询note错误");
		}finally{
			DBUtil.release(conn, ps, rs);
		}
		return -1;
	}
	
	/**
	 * 查询note并分页
	 * @param page
	 * @param note
	 * @return
	 */
	public Page<Note> queryPageNote(Page<Note> page,Note note) {
		int totalRecords = this.countNote(note);
		page.setTotalRecords(totalRecords);
		List<Note> list = this.queryNote(page,note);
		page.setData(list);
		return page;
	}
	
	/**
	 * 通过user 获取note按月份分组的情况
	 * @param user
	 */
	public List<Note> queryUserDateCount(User user){
		//1、加载驱动 获取连接
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Note> list = new ArrayList<Note>();
		try {
			conn =DBUtil.getConn();
			//2、获取处理块
			String sql ="select DATE_FORMAT(pubdate,'%Y年%m月') 	"
					+ " pubDateStr,count(1) c FROM t_note n LEFT JOIN t_note_type t "
					+ " ON n.typeId = t.typeId WHERE t.userId = ?	"
					+ " GROUP BY DATE_FORMAT(pubdate,'%Y年%m月') 	"
					+ " ORDER BY DATE_FORMAT(pubdate,'%Y年%m月') DESC";
			ps =conn.prepareStatement(sql);
			//3、填充参数 并执行
			ps.setInt(1, user.getUserId());
			rs =ps.executeQuery();
			//4、分析结果
			while(rs.next()){
				Note note = new Note();
				note.setPubDateStr(rs.getString("pubDateStr"));
				note.setCount(rs.getInt("c"));
				list.add(note);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询note日期数量错误");
		}finally{
			DBUtil.release(conn, ps, rs);
		}
		return list;
	}
	
	/**
	 * 通过noteid 获取整个note
	 * @param note
	 * @return
	 */
	public Note queryNoteByNoteId(Note note) {
		//1、加载驱动 获取连接
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn =DBUtil.getConn();
			//2、获取处理块
			String sql ="select noteId,title,content,n.typeid,pubDate,t.typename from t_note n 	"
					+ " left join t_note_type t on n.typeid = t.typeid "
					+ "WHERE noteId = ? 	";	
			ps =conn.prepareStatement(sql);
			//3、填充参数 并执行
			ps.setInt(1, note.getNoteId());
			rs =ps.executeQuery();
			//4、分析结果
			while(rs.next()){
				Note temp = new Note();
				temp.setNoteId(rs.getInt("noteId"));
				temp.setTitle(rs.getString("title"));
				temp.setContent(rs.getString("content"));
				temp.setTypeId(rs.getInt("typeid"));
				temp.setPubDate(rs.getTimestamp("pubDate"));
				temp.setTypeName(rs.getString("typename"));
				temp.setPubDateStr(WebUtils.getDateStr(temp.getPubDate(), "yyyy-MM-dd HH:mm:ss"));
				logger.info("查询note成功");
				return temp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询note错误");
		}finally{
			DBUtil.release(conn, ps, rs);
		}
		return null;
	}
	
	public int queryNoteByTypeId(Note note) {
		//1、加载驱动 获取连接
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0 ;
		try {
			conn =DBUtil.getConn();
			//2、获取处理块
			String sql ="select count(1) from t_note  WHERE typeId = ?  ";
			ps =conn.prepareStatement(sql);
			//3、填充参数 并执行
			ps.setInt(1, note.getTypeId());
			rs =ps.executeQuery();
			//4、分析结果
			if(rs.next()){
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询note错误");
		}finally{
			DBUtil.release(conn, ps, rs);
		}
		return result;
	}
}
