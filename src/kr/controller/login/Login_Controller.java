package kr.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Controller;

public class Login_Controller implements Controller {
	@Override
	public String handRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if( request.getSession().getAttribute("login_result") != null ) {
			System.out.println("logincon line :12");
			return "redirect:" + request.getContextPath();
		}
		System.out.println("logincon line :14");
		return "/jsp/login/login.jsp";
	}
}
