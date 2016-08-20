<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<div class="data_list">
	<div class="data_list_title">
		<span class="glyphicon glyphicon-cloud-upload"></span>&nbsp;新增云记
	 </div>
	<div class="container-fluid">
		<div class="container-fluid">
	  <div class="row" style="padding-top: 20px;">
	  	<div class="col-md-12">
	  	<c:choose>
	  		<c:when test="${noteTypeInfo.resultCode>0 }">
	  		<form class="form-horizontal" method="post" action="note?act=save">
	  		   <div class="form-group">
			    <label for="typeId" class="col-sm-2 control-label">类别:</label>
			    <div class="col-sm-6">
			    	<select id="typeId" class="form-control" name="typeId">
						<option value="-1">请选择云记类别...</option>
						<c:forEach items="${noteTypeInfo.result }" var="noteType">
							<c:choose>
							<c:when test="${noteInfo.resultCode<0 }">
								<option value="${noteType.typeId }"
								${noteInfo.result.typeId==noteType.typeId?'selected':'' }>${noteType.typeName }</option>
							</c:when>
							<c:otherwise>
								<option value="${noteType.typeId }">${noteType.typeName }</option>
							</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
			    </div>
			  </div>
			  <div class="form-group">
			  	<input type="hidden" name="noteId" value="28">
			  	<input type="hidden" name="act" value="save">
			    <label for="title" class="col-sm-2 control-label">标题:</label>
			    <div class="col-sm-10">
			      <input class="form-control" name="title" id="title" placeholder="云记标题" value="${noteInfo.result.title }">
			    </div>
			   </div>
			  
			  <div class="form-group">
			    <div class="col-sm-12">
			    	<textarea id='ueditor' name="content">${noteInfo.result.content }</textarea>
			    </div>
			  </div>			 
			  <div class="form-group">
			    <div class="col-sm-offset-6 col-sm-4">
			      <input type="submit" class="btn btn-primary" onclick="return saveNote();" value="保存">
					<font id="error" color="red"></font>  
			    </div>
			  </div>
			</form>
			</c:when>
			<c:otherwise>
				<h3><a href="type">请先添加类别</a></h3>
			</c:otherwise>
	  	</c:choose>
	  	</div>
	  </div>
	</div>	
</div>		
</div> 

<script type="text/javascript">
	$(function(){
		
		var ue = new UE.ui.Editor({
			initialFrameHeight:'270',initialFrameWidth:'100%'
		});
		ue.render('ueditor');
	})
</script> 