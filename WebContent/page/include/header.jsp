<%@page import="kr.vo.MemberVO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
MemberVO member = (MemberVO)session.getAttribute("member");
%>
<link rel="stylesheet" href="/Mission-Web-MVC01/assets/css/header.css">
<script type="text/javascript">
	$(document).ready(function(){
		$("form[name=login_form]").submit(function(e){
			var id = $("input[name=login_id]").val();
			var password = $("input[name=login_password]").val();
			
			if( id == "" ){
				$("input[name=login_id]").focus();
				alert("아이디를 입력하세요");
				return false;
			} else if( password == "" ){
				$("input[name=login_password]").focus();
				alert("비밀번호를 입력하세요");
				return false;
			}
			
			e.preventDefault();

			$.ajax({
				url : '<%=request.getContextPath()%>/login/loginProcess.do',
				type : 'post',
				data : {
					'id' : id,
					'password' : password
				},
				success : callback,
				error : function(xhr, ajaxOptions, thrownError) {
					alert(xhr.status);
					alert(thrownError);
				}
			});
			return true;
		});
		
		$("input[name=logout]").click(function(){
			$.ajax({
				url : '<%=request.getContextPath()%>/login/logout.do',
				type : 'post',
				success : callback,
				error : function(xhr, ajaxOptions, thrownError) {
					alert(xhr.status + " " + thrownError);
				}
			});
		});
		function callback(data) {
			alert(data.trim());
			location.reload();
		}
	});
</script>
<div id="header_content">
	<a id="main_link" href="/Mission-Web-MVC01"> 
		<img alt="google_logo" src="/Mission-Web-MVC01/assets/images/logo.png">
	</a>
	<nav id="gnb">
		<ul>
			<li><a href="<%=request.getContextPath()%>/member/list_user.do">회원관리</a></li>
			<li><a href="<%=request.getContextPath()%>/board/list_post.do">게시판</a></li>
			<li><a href="<%=request.getContextPath()%>/member/add_user.do">회원가입</a></li>
		</ul>
	</nav>
	
	<div id="user_info">
	<c:choose>
		<c:when test="${ empty login_result }">
		<form name="login_form" action="<%=request.getContextPath()%>/login/login.do" method="POST">
			<input class="login" type="text" name="login_id" placeholder="Id">
			<input class="login" type="password" name="login_password" placeholder="Password">
	
			<input type="submit" class="btn" value="로그인">
		</form>
		<input type="button" class="btn" value="네이버로로그인">
		</c:when>
		
		<c:otherwise>
			<a>${ login_result.name }님</a>
			<input class="btn" type="button" name="logout" value="로그아웃">	
			<a href="<%=request.getContextPath()%>/member/detail_user.jsp?id=${login_result.id}">마이페이지</a>
		</c:otherwise>
	</c:choose>
	</div>
</div>