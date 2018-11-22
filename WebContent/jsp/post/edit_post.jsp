<%@page import="kr.dao.BoardDAO"%>
<%@page import="kr.vo.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.sql.*"%>

<%
	request.setCharacterEncoding("utf-8");

	String title = request.getParameter("title");
	String id = request.getParameter("id");
	String content = request.getParameter("content");
	int no = Integer.parseInt(request.getParameter("no"));

	BoardVO board = new BoardVO();
	board.setTitle(title);
	board.setId(id);
	board.setContent(content);
	board.setBoard_no(no);

	BoardDAO dao = new BoardDAO();
	int cnt = dao.updateBoard(board);
%>
<script>
	
<%if (cnt != 0) {%>
	alert("글 수정완료");
<%} else {%>
	alert("글 수정실패");
<%}%>
	location.href = "/Mission-Web/page/board/detail_post.jsp?no=<%= no %>";
</script>