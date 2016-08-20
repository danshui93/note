<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="data_list">
	<div class="data_list_title">
		<span class="glyphicon glyphicon glyphicon-th-list"></span>&nbsp; <c:if test="${vs!=null && vs.length()>0}">『${vs }』</c:if>云记列表
	</div>
	<div class="note_datas">
		<ul>
			<c:choose>
				<c:when test="${PageInfo.resultCode>0 }">
					<c:forEach var="note" items="${PageInfo.result.data}">
					<li>『<fmt:formatDate value="${note.pubDate}" type="date" 
					pattern="yyyy-MM-dd"/>』&nbsp;&nbsp;<a href="note?act=show&noteId=${note.noteId }">${note.title }</a>
					</li>
					</c:forEach>
					<nav style="text-align: center">
					
						
						<ul class="pagination   center">
							<c:if test="${PageInfo.result.hasPre}">
								<li><a href="main?current=${PageInfo.result.currentPage-1}&act=${act}&val=${val}&vs=${vs}">&laquo;</a></li>
							</c:if>
							<c:forEach var="idx" begin="${PageInfo.result.startPage}" end="${PageInfo.result.endPage}">
								<li <c:if test="${idx==PageInfo.result.currentPage}">class="active"</c:if>><a
									href="main?current=${idx}&act=${act}&val=${val}&vs=${vs}">${idx}</a>
								</li>
							</c:forEach>
							<c:if test="${PageInfo.result.hasNext}">
								<li><a href="main?current=${PageInfo.result.currentPage+1}&act=${act}&val=${val}&vs=${vs}">&raquo;</a></li>
							</c:if>
						</ul>
					</nav>						
				</c:when>
				<c:otherwise>
					<li><a	href="note">请添加日志</a></li>
				</c:otherwise>
			</c:choose>			

		</ul>
	</div>
	
</div>
