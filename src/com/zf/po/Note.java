package com.zf.po;

import java.util.Date;

public class Note {
	/*
	 *  `noteId`,
  		`title`,
  		`content`,
  		`typeId`,
  		`pubDate`
	 */
	private int noteId;
	private String title;
	private String content;
	private int typeId;
	private Date pubDate;
	private int userId;
	private String pubDateStr;
	private int count;
	private String typeName;
	
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPubDateStr() {
		return pubDateStr;
	}
	public void setPubDateStr(String pubDateStr) {
		this.pubDateStr = pubDateStr;
	}
	public Note() {
		// TODO Auto-generated constructor stub
	}
	public Note(int noteId, String title, String content, int typeId, Date pubDate) {
		super();
		this.noteId = noteId;
		this.title = title;
		this.content = content;
		this.typeId = typeId;
		this.pubDate = pubDate;
	}
	public int getNoteId() {
		return noteId;
	}
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	
	
}
