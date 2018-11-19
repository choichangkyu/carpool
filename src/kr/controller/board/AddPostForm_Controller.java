package kr.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Controller;

public class AddPostForm_Controller implements Controller{
	public String handRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "/page/board/add_post.jsp";
	}

}
