package kr.controller;

import java.io.IOException;
import java.util.Iterator;

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
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI();
		System.out.println(uri);	//ok
		String context = request.getContextPath();
		System.out.println(context);	//ok
		uri = uri.substring(context.length());	
		
		try {
/*			Controller controller = mapping.get_Controller(uri);
			String callPage = controller.handRequest(request, response);*/
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
