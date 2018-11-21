package kr.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Controller;
import kr.dao.MemberDAO;
import kr.vo.MemberVO;

public class AddUser_Controller implements Controller{
	
	@Override
	public String handRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberDAO dao = new MemberDAO();
		
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String id = request.getParameter("id");
		String loginId = request.getParameter("loginId");

		MemberVO member = new MemberVO();
		member.setEmail(email);
		member.setId(id);
		member.setName(name);
		member.setPassword("pwd");
		member.setBirth("10월7일");
		member.setAge(20);
		member.setTel("01071415275");

		int cnt = dao.insertMember(member);
		request.setAttribute("cnt", cnt);
		
		return "/jsp/member/add_user.jsp";
	}

}
