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
		String bname = request.getParameter("bname"); 
		String olid = request.getParameter("olid");
		HttpSession session = request.getSession();	
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if(command.equals("/order_list.mpg")) {
			if(loginMember != null) { //로그인한 상태이면
				action = new OrdListAction();
			} else {	//로그인하지 않은 상태에서 주문조회 버튼을 눌렀으면
				if(bname == null || olid == null)		response.sendRedirect("login_form.jsp");
				if(bname != null && olid != null)		action = new OrdListAction();
			}
		} else if(command.equals("/order_detail.mpg")) {
			action = new OrdDetailAction();	
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
