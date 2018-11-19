package kr.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Controller;

public class Logout_Controller implements Controller{
	
	@Override
	public String handRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("login_result");
		
		request.setAttribute("msg", "�α׾ƿ� �Ǿ����ϴ�");
		return "/jsp/login/logout.jsp";
	}
}