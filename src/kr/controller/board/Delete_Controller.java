package kr.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Controller;
import kr.dao.BoardDAO;

public class Delete_Controller implements Controller {

	@Override
	public String handRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int no = request.getParameter("no") == null ? 0 : Integer.parseInt(request.getParameter("no"));

		BoardDAO dao = new BoardDAO();

		int cnt = dao.deleteBoard(no);

		request.setAttribute("cnt", cnt);

		return "/page/board/free_list.jsp";
	}

}
