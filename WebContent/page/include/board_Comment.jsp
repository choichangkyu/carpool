<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/css/comment.css">
<script>
	$(document).ready(function(){
		
		$('input[name=rollin]').click(function(){
			
			$.ajax({
				url : "<%=request.getContextPath()%>/board/addComment.do",
				type : "post",
				data : {
					'writer' : '${login_result.name}',
					'post_no' : '${board.board_no}',
					'content' : $('#content').val()
				},
				success : callback,
				error : function(xhr, ajaxOptions, thrownError) {
					alert(xhr.status);
					alert(thrownError);
				}	
			});
			
		});
	});
	
	function callback(data){
		if(data == 1){
			alert('댓글등록 완료');
			location.reload();
		}else{
			alert('댓글등록 실패');
		}
	}
</script>

	<div id="comment_box">
		<div id="table">
			<form>
	
				<div class="row">
					<span class="cell col1">댓글달기</span>
					<span class="cell col2"><textarea rows="2" cols="60" id="content"></textarea></span>
				</div>
				
				<div align="right">
					<span class="cell col2"><input type="button" value="등록" name="rollin"></span>
				</div>	
			</form>
		</div>
	</div>	
	
