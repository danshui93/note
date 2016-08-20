<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="data_list">
	<div class="data_list_title">
		<span class="glyphicon glyphicon-list"></span>&nbsp;类型列表
		<span class="noteType_add">
			<button class="btn btn-sm btn-success" type="button" id="addBtn">添加类别</button>
		</span>
		
	 </div>
	<div>
<c:choose>
	<c:when test="${info.resultCode>0 }">
		 	<table class="table table-hover table-striped ">
		 		<tbody><tr>
		 			<th>编号</th>
		 			<th>类型</th>
		 			<th>操作</th>
		 		</tr>
						
		<c:forEach var="noteType"  items="${info.result }">
			 		<tr>
			 			<td>${noteType.typeId }</td>
			 			<td>${noteType.typeName }</td>
			 			<td>
			 			<button class="btn btn-primary" type="button">修改</button>&nbsp;
			 			<button class="btn btn-danger del" type="button">删除</button>
			 			</td>
			 		</tr>
		</c:forEach>
		</tbody></table>
		
	</c:when>
	<c:otherwise>
		<h3>${user.uname }不存在日志类型，请添加</h3>
	</c:otherwise>
</c:choose>
	</div>	
</div>


<!-- 模态框 -->
<div class="modal fade" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">添加类别</h4>
      </div>
      <div class="modal-body">
      	<form class="form-horizontal">
         <div class="form-group">
         	<input type="hidden" id="typeId" name="typeId">
		    <label for="typeName" class="col-sm-2 control-label">名称:</label>
		    <div class="col-sm-10">
		    	<input type="text" class="form-control" name="typeName" 
		    	id="typeName" placeholder="类型名称">
		    </div>
		  </div>
		 </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success">
   		<span class="glyphicon glyphicon-floppy-saved"></span>&nbsp;&nbsp;保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/javascript">
	$(function(){
		bindChange();
		bindDel();
		$("#addBtn").click(
			function(){
				//弹出模态框
				
				$('#myModal').modal('show');
				//清除内容
				$("#typeName").val("");
				//按钮默认为不能用
				$(".modal-footer>.btn.btn-success").attr("disabled",true);
			}		
		)
		
		$("#typeName").blur(
			function(){
				//获取值
				var val = $(this).val();
				//判断是否为空
				if(val.length==0){
					$(".modal-footer>.btn.btn-success").attr("disabled",true);
					return;
				}
				//ajax typeName判断是否重复
				$.getJSON("type?act=repeat",{"typeName":val},function(data){
					if(data.resultCode==-1){
						$(".modal-footer>.btn.btn-success").html("类型名重复").addClass("btn-danger").attr("disabled",true);
						return;
					}
					
				})
			}
			
		)
		
		$("#typeName").focus(
			function(){
				//还原为保存 清除danger颜色  变为可用
				$(".modal-footer>.btn.btn-success").html("<span class='glyphicon glyphicon-floppy-saved'></span>&nbsp;&nbsp;保存").
				removeClass("btn-danger").attr("disabled",false);
			}		
		)
		
		//绑定保存按钮的click事件
		$(".modal-footer>.btn.btn-success").click(
			function(){
				//ajax提交web保存到数据库
				$.getJSON("type?act=save",{"typeName":$("#typeName").val(),'typeId':$("#typeId").val()},
					function(data){
					if(data.resultCode>0){
						if($('#typeId').val().length==0){//没有id ，执行添加
								var typeId = data.result.typeId;
								var typeName = data.result.typeName;
								addTr(typeId,typeName);
							}else{//有id ，执行修改   
							var targetTr;
							$('table tr:gt(0)').each(function(i){
								var targetTd = $(this).children('td:eq(0)');
								if(targetTd.text()==$('#typeId').val()){
									targetTr = targetTd.parent();
									return;
								}
							})
							targetTr.children('td').eq(1).html($('#typeName').val());
						}
						$('#myModal').modal('hide');
					}
				})
				
				
			}		
		)
		
		
		
	})
	
	function addTr(typeId,typeName){
		var tr = "<tr><td>"+typeId+"</td><td>"+typeName+"</td>";
		tr+="<td><button class='btn btn-primary' type='button'>修改</button>&nbsp;";
 		tr+="<button class='btn btn-danger del' type='button'>删除</button></td></tr>";
 		var length = $(".table.table-hover.table-striped ").length;
		if($(".table.table-hover.table-striped ").length==0){
			//获取h3的父节点
			var div = $('h3').parent();
			//移除h3
			$('h3').remove();
			//拼接table
			var table = "";
			table+="<table class=\"table table-hover table-striped \">";
			table+="<tbody><tr><th>编号</th><th>类型</th><th>操作</th></tr>";
			table+=tr;
			table+="</tbody></table>";
			div.append(table);
		}else{
			$(".table.table-hover.table-striped ").append(tr);
		}
		bindChange();
		bindDel();
	}
	
	
	function bindChange(){
		$('table .btn.btn-primary').on('click',function(){
			//修改标题
			$('.modal-title').html('修改类别');
			//弹出模态框
			$('#myModal').modal('show');
			//将typeid  和 typename 赋予 两个input中
			//首先获修改按钮的tr 再找到子元素 td[0] td[1] 就获得了id 和 name
			var parObj = $(this).parent().parent();
			var typeId = parObj.children('td').eq('0').text();
			var typeName = parObj.children('td').eq('1').text();
			$('#typeName').val(typeName);
			$('#typeId').val(typeId);
		})
	}
	
	function bindDel(){
		$('table .btn.btn-danger.del').on('click',function(){
			//将typeid  和 typename 赋予 两个input中
			//首先获修改按钮的tr 再找到子元素 td[0] td[1] 就获得了id 和 name
			var parObj = $(this).parent().parent();
			var typeId = parObj.children('td').eq('0').text();
			var typeName = parObj.children('td').eq('1').text();
			
			swal({
				  title: "是否删除["+typeName+"]?", 
				  text: '',
				  type: 'warning',
				  showCancelButton: true,
				  confirmButtonText: '确定删除',
				  cancelButtonText: '手贱了',
				}).then(function(isConfirm) {
				  if (isConfirm === true) {
					  $.getJSON("note?act=find",{'typeId':typeId},function(data){
						  if(data.resultCode>0){
							  $.getJSON("type?act=del",{'typeId':typeId},function(data){
									if(data.resultCode==1){
										 swal(
											      '操作成功',
											      '已成功删除数据',
											      'success'
											    );
										
										parObj.remove();
										
										if($(".table.table-hover.table-striped  tr").length==1){
											//获取h3的父节点
											var div = $(".table.table-hover.table-striped ").parent();
											//移除h3
											$(".table.table-hover.table-striped ").remove();
											//拼接table
											var h3 = "<h3>${user.uname }不存在日志类型，请添加</h3>";
											div.append(h3);
										}
									}
									
									
								});
						   
						  }else{
							  swal(
								      '删除失败',
								      '该类别中存在笔记无法删除',
								      'error'
								    );
						  }
						  
						  
						  
					 }); 
				}
						  
		    })
					 
			
		    
			
			
			
			
	   })
		
		
	}
	
	
</script>
    