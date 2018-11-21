<%@page import="kr.vo.MemberVO"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
MemberVO member = (MemberVO)session.getAttribute("member");
%>
<link rel="stylesheet" href="/carpool/assets/css/header.css">
 <script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
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
	<a id="main_link" href="/carpool"> 
		<img alt="google_logo" src="/carpool/assets/images/logo.png">
	</a>
	<nav id="gnb">
		<ul>
			<li><a href="<%=request.getContextPath()%>/member/list_user.do">회원관리</a></li>
			<li><a href="<%=request.getContextPath()%>/board/list_post.do">게시판</a></li>
			<li><a href="<%=request.getContextPath()%>/member/add_user.do">회원가입</a></li>
			<li><a href="<%=request.getContextPath()%>/carpool/add_carpool.do">카풀등록</a></li>
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
		<%-- <%
	    String clientId = "f7aj3kWnxJf7Qzs_qweZ";//애플리케이션 클라이언트 아이디값";
	    String redirectURI = URLEncoder.encode("http://127.0.0.1:8888/carpool/login/naver_login.do", "UTF-8");
	    SecureRandom random = new SecureRandom();
	    String state = new BigInteger(130, random).toString();
	    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
	    apiURL += "&client_id=" + clientId;
	    apiURL += "&redirect_uri=" + redirectURI;
	    apiURL += "&state=" + state;
	    session.setAttribute("state", state);
	 	%>
	 	<a href="<%=apiURL%>"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a> --%>
	 	<!-- 네이버아이디로로그인 버튼 노출 영역 -->
		  <div id="naver_id_login"></div>
		  <!-- //네이버아이디로로그인 버튼 노출 영역 -->
		  <script type="text/javascript">
		  	var naver_id_login = new naver_id_login("f7aj3kWnxJf7Qzs_qweZ", "http://127.0.0.1:8888/carpool/login/naver_login.do");
		  	var state = naver_id_login.getUniqState();
		  	naver_id_login.setButton("white", 2,40);
		  	naver_id_login.setDomain("http://127.0.0.1:8888/carpool");
		  	naver_id_login.setState(state);
		  	naver_id_login.setPopup();
		  	naver_id_login.init_naver_id_login();
		  </script>
		</c:when>
		
		<c:otherwise>
			<a>${ login_result.name }님</a>
			<input class="btn" type="button" name="logout" value="로그아웃">	
			<a href="<%=request.getContextPath()%>/member/detail_user.jsp?id=${login_result.id}">마이페이지</a>
		</c:otherwise>
	</c:choose>
	</div>
</div>