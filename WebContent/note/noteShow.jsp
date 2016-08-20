<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="data_list">
	<div class="data_list_title">
		<span class="glyphicon glyphicon-eye-open"></span>&nbsp;查看云记 
	</div>
	<div>
		
			
				<div class="note_title"><h2>${noteShowInfo.result.title }</h2></div>
				<div class="note_info">
					发布时间：『 ${noteShowInfo.result.pubDateStr }』&nbsp;&nbsp;云记类别：${noteShowInfo.result.typeName }
				</div>
				<div class="note_content">
					<p>${noteShowInfo.result.content }</p>
				</div>
				<div class="note_btn">
					<button class="btn btn-primary" type="button" onclick="update()">修改</button>
					<button class="btn btn-danger" type="button" ">删除</button>
				</div>			
			
			
		
		</div>	
	

</div>

<script type="text/javascript">
var id=${noteShowInfo.result.noteId};
var title = '${noteShowInfo.result.title}';
	function update(){
		window.location.href='note?act=showForEdit&noteId='+id;
	}
	$(function(){
		$('.note_btn .btn.btn-danger').on('click',function(){
			swal({
				  title: "是否删除["+title+"]?", 
				  text: '',
				  type: 'warning',
				  showCancelButton: true,
				  confirmButtonText: '确定删除',
				  cancelButtonText: '手贱了',
				}).then(function(isConfirm) {
						if (isConfirm === true) {
							 $.getJSON("note?act=del",{'noteId':id},function(data){
									if(data.resultCode==1){
										 window.location.href='main';
					
									}else{
										swal(
											      '删除失败',
											      '无法删除',
											      'error'
											    );
									}
								})
						}
				})
		})
		
		
	})
</script>