package com.zf.po;



import java.util.List;

/**
 * 记录数 每页条数  当前页
 * @author Administrator
 *
 */
public class Page<T>{
	//当前页
	private int currentPage;
	//总记录数
	private int totalRecords;
	//每页条数
	private int pageSize =3;
	//总页数
	private int totalPage;
	//起始页
	private int startPage;
	//结束页
	private int endPage;	
	//是否存在上一页
	private boolean hasPre;
	//是否存在下一页
	private boolean hasNext;
	//mysql 起始索引  limit 起始索引,pagesize
	private int start;	
	//显示页数
	private int showPages =5;	
	//记录
	private List<T> data ;
	
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		
		this.totalRecords = totalRecords;
		//总页数
		totalPage = (this.totalRecords+pageSize-1)/pageSize;
		//当前页
		if(currentPage ==0){
			currentPage =1;
		}		
		if(currentPage>totalPage){
			currentPage=totalPage;
		}		
		//结束页-起始页=4  保证5个页
		//起始页
		startPage =currentPage-2;	
		if(startPage<1){
			startPage =1;
		}
		//结束页
		endPage  =currentPage+2;	
		endPage =endPage<5?5:endPage;
		endPage = endPage>totalPage?totalPage:endPage;
		if(endPage==totalPage){
			if(endPage-4>1){
				startPage =endPage-4;				
			}
		}
		
		//是否存在上一页
		hasPre =(currentPage==1)?false:true;
		//是否存在下一页
		hasNext =(currentPage==totalPage)?false:true;
		
		//起始索引
		start =(currentPage-1)*pageSize;
		
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isHasPre() {
		return hasPre;
	}
	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public int getShowPages() {
		return showPages;
	}
	public void setShowPages(int showPages) {
		this.showPages = showPages;
	}	
	
}