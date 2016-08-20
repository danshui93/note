<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<div class="data_list">
	<div class="data_list_title"><span class="glyphicon glyphicon-edit"></span>&nbsp;个人中心 </div>
	<div class="container-fluid">
	  <div class="row" style="padding-top: 20px;">
	  	<div class="col-md-8">
	  		<form class="form-horizontal" method="post" action="user?act=save" enctype="multipart/form-data" onsubmit="return checkUser();">
			  <div class="form-group">
			  	<input type="hidden" name="act" value="save">
			    <label for="nickName" class="col-sm-2 control-label">昵称:</label>
			    <div class="col-sm-3">
			      <input class="form-control" name="nickName" id="nickName" placeholder="昵称" value="${user.nickName }">
			    </div>
			    <label for="img" class="col-sm-2 control-label">头像:</label>
			    <div class="col-sm-5">
			    	<input type="file" id="img" name="img">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="mood" class="col-sm-2 control-label">心情:</label>
			    <div class="col-sm-10">
			      <textarea class="form-control" name="mood" id="mood" rows="3">${user.mood }</textarea>
			    </div>
			  </div>			 
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <button type="submit" id="btn" class="btn btn-success">修改</button>&nbsp;&nbsp;<span style="color:red" id="msg"></span>
			    </div>
			  </div>
			</form>
	  	</div>
  		<div class="col-md-4"><img style="width:260px;height:200px" src="upload/${user.img }"></div>
	  </div>
	</div>	
</div>

<script type="text/javascript">
	$(function(){
		//获取目标对象
		var target = $('#nickName');
		target.blur(
			function(){
				//获取value
				var nickName = target.val();
				//1.空值
				if(nickName.length==0){
					$('#msg').html('昵称不能为空！');
					$('#btn').attr('disabled',true);
					return;
				}
				//2.没动
				if(nickName == '${user.nickName}'){
					return;
				}
				//3.修改后进行ajax
				$.post('user?act=repeat',{'nickName':nickName},function(data){
					var s=eval('('+data+')');
					if(s.resultCode<0){//负数，重复
						$('#msg').html('昵称已存在,请修改！');
						$('#btn').attr('disabled',true);

					}
				});
				
				$('#nickName').focus(
					function(){
						$('#msg').html('');
						$('#btn').attr('disabled',false);
					}		
				)
			}		
		)
	})
</script>
