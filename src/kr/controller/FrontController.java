package kr.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {
	private HandlerMapping mapping;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String proploLocation = config.getInitParameter("proplocation");
		mapping = new HandlerMapping(proploLocation);
	}
	test
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uri = request.getRequestURI();
		String context = request.getContextPath();
		uri = uri.substring(context.length());
		
		try {

			String callPage = mapping.get_Controller(uri).handRequest(request, response); // uri�� ���� �����ų jsp���� ��� ����
			if(callPage.startsWith("redirect:")) {
				response.sendRedirect(callPage.substring("redirect:".length()));
			} else {
				request.getRequestDispatcher(callPage).forward(request, response); // jsp���� ��ο� ���� �ش� ���� forward��Ŵ
			}
			/*RequestDispatcher dispatcher = request.getRequestDispatcher(callPage);
			dispatcher.forward(request, response);*/

		} catch (Exception e) {
			throw new ServletException(e);
		}

	}
}
