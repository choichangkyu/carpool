<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	$("input").click(function(event){
		var name = $(event.target).attr("name");
		switch(name){
		case "edit_board":
			if( confirm("글을 수정하시겠습니까?") )
				location.href = "<%= request.getContextPath() %>/board/edit_post.no?no=${no}";
			break;
		case "delete_board":
			if( confirm("글을 삭제하시겠습니까?") )
				location.href = "<%= request.getContextPath() %>/board/delete_board.no?no=${no}";
			break;
		case "post_list":
			location.href = "<%= request.getContextPath() %>/board/free_board_list.do";
			break;
		}
	});
	
	$("img.download").click(function(){
		$("form[name=downForm]").submit();
	});
	
});

</script>
<div align="center">
	<hr>
	<h3>상세</h3>
	<hr>
	<table style="width: 100%;">
 		<c:if test="${ board != null }">
			<tr>
				<th width="23%">번호</th>
				<td>${ board.board_no }</td>
			</tr>
			<tr>
				<th width="23%">작성날짜</th>
				<td>${ board.regDate }</td>
			</tr>
			<tr>
				<th width="23%">제목</th>
				<td><c:out value="${ board.title }" /></td>
			</tr>
 			<tr>
				<th width="23%">글쓴이</th>
				<td>${ board.id }</td>
			</tr>
			<tr>
				<th width="23%">내용</th>
				<td><c:out value="${ board.content }"/></td>
			</tr>
			<tr>
				<th width="23%">조회수</th>
				<td>${ board.cnt }</td>
			</tr>
		</c:if>
	</table>
	<br>
	<c:if test="${ login_result.id == board.id }">
		<input class="btn" type="button" name="post_edit" value="수정"> 
		<input class="btn" type="button" name="delete" value="삭제">
	</c:if>
	<br>
	<!-- 댓글 목록 들어갈 자리 detail_board.do 에서 불러오면 됨-->
	<c:forEach var="cl" items="${commentList }">
			<table>
				<tr>			
					<th>작성자</th>
					<td>${cl.writer }</td>
				</tr>
				<tr>			
					<th>내용</th>
					<td>${cl.content }</td>
				</tr>
				<tr>			
					<th>등록일</th>
					<td>${cl.regDate }</td>
				</tr>	
			</table>
	</c:forEach>
	<br>
	<c:if test="${ ! empty login_result }">
		<jsp:include page="/page/include/board_Comment.jsp"/>
	</c:if>
	
	<br>
		<input class="btn" type="button" name="post_list" value="목록">
</div>