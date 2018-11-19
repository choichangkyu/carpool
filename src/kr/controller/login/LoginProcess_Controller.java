package kr.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Controller;
import kr.dao.MemberDAO;
import kr.vo.MemberVO;

public class LoginProcess_Controller implements Controller{
	@Override
	public String handRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		MemberDAO dao = new MemberDAO();
		MemberVO member = new MemberVO();
		member.setId(id);
		member.setPassword(password);
		
		MemberVO login_result = dao.selectForLogin(member);
		
		String msg = "";
		if( login_result != null){
			switch(login_result.getType()) {
			case "A":
				msg = "�����ڴ� ȯ���մϴ�";
				break;
			case "U":
				msg = login_result.getId() + "�� ȯ���մϴ�";
				break;
			}
			HttpSession session = request.getSession();
			session.setAttribute("login_result", login_result);		
			
		} else {
			msg = "���̵� �Ǵ� �н����尡 �߸��Ǿ����ϴ�";
		}
		request.setAttribute("msg", msg);
		
		return "/jsp/login/loginProcess.jsp";
	}
/*	@Override
	public String handRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "redirect:" + request.getContextPath() + "/login/login.do";
	}
*/	
}
