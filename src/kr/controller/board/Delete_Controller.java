package kr.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Controller;
import kr.dao.BoardDAO;

public class Delete_Controller implements Controller {

	private static final int no = 0;

	@Override
	public String handRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardDAO dao = new BoardDAO();
		
		int cnt = dao.deleteBoard(no);
		
		request.setAttribute("cnt", cnt);
		
		return "/page/board/free_list.jsp";
	}

	
	
}
