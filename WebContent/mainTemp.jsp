<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>主页</title>
<link href="statics/css/note.css" rel="stylesheet">
<link href="statics/bootstrap3/css/bootstrap.css" rel="stylesheet">
<link href="statics/css/sweetalert2.min.css" rel="stylesheet">
<script src="statics/bootstrap3/js/jquery-1.11.3.js"></script>
<script src="statics/bootstrap3/js/bootstrap.js"></script>
<script src="statics/bootstrap3/js/sweetalert2.min.js"></script>
 <!-- 配置文件 -->
<script type="text/javascript" src="UEditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="UEditor/ueditor.all.js"></script>

<style type="text/css">
  body {
       padding-top: 60px;
       padding-bottom: 40px;
       background: url(statics/images/bg.gif) repeat;
     }
</style>
</head>
<body>

<!-- 导航  -->
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" style="font-size:25px" href="http://localhost:8080/cloudnote/main">尚云笔记</a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="main"><i class="glyphicon glyphicon-cloud"></i>&nbsp;主&nbsp;&nbsp;页</a></li>
        <li><a href="note"><i class="glyphicon glyphicon-pencil"></i>&nbsp;发表云记</a></li>
        <li><a href="type"><i class="glyphicon glyphicon-list"></i>&nbsp;类别管理</a></li>
        <li><a href="user?act=view"><i class="glyphicon glyphicon-user"></i>&nbsp;个人中心</a>
       
      </li></ul>
      <form class="navbar-form navbar-right" role="search" action="main">
        <div class="form-group">
          <input type="hidden" name="act" value="key">
          <input type="text" name="val" class="form-control" placeholder="搜索云记">
        </div>
        <button type="submit" class="btn btn-default">查询</button>
      </form>      
    </div>
  </div>
</nav>

<!-- 中间 -->
<div class="container">
	<div class="row-fluid">
		<div class="col-md-3">
			<div class="data_list">
				<div class="data_list_title"><span class="glyphicon glyphicon-user"></span>&nbsp;个人中心&nbsp;&nbsp;&nbsp;&nbsp;<a href="user?act=logout">退出</a></div>
				<div class="userimg">
					<img src="upload/${user.img }">
				</div>
				<div class="nick">${user.nickName }</div>
				<div class="mood">${user.mood }</div>
			</div>	
			<div class="data_list">
				<div class="data_list_title">
					<span class="glyphicon glyphicon-calendar">
					</span>&nbsp;云记日期 
				</div>
				
				<div>
				<c:if test="${noteDateInfo.resultCode>0 }">
					<ul class="nav nav-pills nav-stacked">
					  <c:forEach items="${noteDateInfo.result }" var="note">
						<li><a href="main?act=date&val=${note.pubDateStr }&vs=${note.pubDateStr }">${note.pubDateStr }<span class="badge">${note.count }</span></a></li>
					 </c:forEach>
					 
					</ul>
				</c:if> 
				</div>
				
			</div>		
			<div class="data_list">
				<div class="data_list_title">
					<span class="glyphicon glyphicon-list-alt">
					</span>&nbsp;云记类别 
				</div>
				
				<div>
				<c:if test="${typeCountInfo.resultCode>0 }">
					<ul class="nav nav-pills nav-stacked" id="findType">
					  <c:forEach items="${typeCountInfo.result }" var="noteType">
						<li><a href="main?act=type&val=${noteType.typeId }&vs=${noteType.typeName }">${noteType.typeName }<span class="badge">${noteType.count }</span></a></li>
					 </c:forEach>
					 
					</ul>
				</c:if>						
				</div>
				
			</div>			
		</div>
		<div class="col-md-9">
			<jsp:include page="${changePage }"></jsp:include>
		</div>		
	</div>
</div>