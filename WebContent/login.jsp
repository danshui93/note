<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>尚云</title>
<link href="statics/css/login.css" rel="stylesheet" type="text/css" />
<script src="statics/js/jquery-1.11.3.js" type=text/javascript></script>
<script src="statics/js/config.js" type=text/javascript></script>
 
</head>
<body>

<!--head-->
<div id="head">
	<div class="top">
        <div class="fl" style="margin-top:10px"><img src="statics/images/logo.png" /></div>
        <div class="fl"><img src="statics/images/li.png" /></div>
        <div class="fl yahei18">开启移动办公新时代！</div>       
    </div>
</div>

<!--login box-->
<div class="wrapper">
	<div id="box">
		<c:choose>
			<c:when test="${user!=null}">
				<a href="main"><img style="margin-left:300px;margin-top:180px;width:150px;height:150px" src="statics/images/a.png"/></a>
			</c:when>
			<c:otherwise>
		        <div class="loginbar">尚云用户登录</div>
		        <div id="tabcon">
		        <div class="box show">
		        	<form method='get' id="loginForm" action="user">
		        		<input type="text" class="user yahei16" id="uname" name="uname" value="${uname}" /><br /><br />
		            	<input type="password" class="pwd yahei16" id="upwd" name="upwd" value="${upwd}" /><br /><br />
		            	<input name="rem" type="checkbox" value="记住我"  class="inputcheckbox"/> <label>记住我</label>&nbsp; &nbsp; <span id="Msg">${msg }</span><br /><br />
		            	<input type="button" class="log jc yahei16" value="登 录" onclick="checkLogin()" />&nbsp; &nbsp; &nbsp; <input type="reset" value="取 消" class="reg jc yahei18" />
		            </form>
				</div>
		        </div> 
        </c:otherwise>
		</c:choose>       
    </div>
</div>

<div id="flash">
	<div class="pos">
		<a bgUrl="statics/images/banner-bg1.jpg" id="flash1" style="display:block;"><img src="statics/images/banner_pic1.png"></a>
	    <a bgUrl="statics/images/banner-bg2.jpg" id="flash2"                       ><img src="statics/images/banner-pic2.jpg"></a>	   
	</div>    
	<div class="flash_bar">
		<div class="dq" id="f1" onclick="changeflash(1)"></div>
		<div class="no" id="f2" onclick="changeflash(2)"></div>		
	</div>
</div>

<!--bottom-->
<div id="bottom">
	<div id="copyright">
        <div class="quick">
        	<ul>
                    <li><input type="button" class="quickbd iphone" onclick="location.href='http://www.shsxt.com'" /></li>
                    <li><input type="button" class="quickbd android" onclick="location.href='http://www.shsxt.com'" /></li>
                    <li><input type="button" class="quickbd pc" onclick="location.href='http://www.shsxt.com'" /></li>
                <div class="clr"></div>
            </ul>
            <div class="clr"></div>
        </div>
        <div class="text">
        <a href="http://www.shsxt.com">SXT尚云</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://www.bjsxt.com">北京尚学堂</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://www.shsxt.com">上海尚学堂</a>&nbsp;&nbsp;&nbsp;&nbsp; <br />
     Copyright © 2006-2026  上海尚学堂  All Rights Reserved  电话：021-67690939 QQ：3401997271 
        </div>
    </div>
</div>
</body>

</html>
