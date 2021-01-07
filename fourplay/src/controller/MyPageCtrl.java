package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.mpg")
public class MyPageCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MyPageCtrl() {
        super();
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;

		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		
		switch (command) {
			case "/order_list.mpg" :		// ȸ�� �ֹ����� ����
				if(loginMember != null) {	// �α����� �����̸�
					action = new OrdListAction();
				}else {
					response.sendRedirect("login_form.jsp");
				}
				break;
			case "/non_order_list.mpg" :	// ��ȸ�� �ֹ���ȸ
				action = new NonOrdAction();
				break;
			case "/order_detail.mpg" :	// ȸ�� �ֹ� �󼼳��� ����
				action = new OrdDetailAction();
				break;
			case "/non_order_detail.mpg" :	// ��ȸ�� �ֹ� �󼼳��� ����
				action = new NonDetailAction();
				break;
		}

		try {
			forward = action.execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = 
					request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
