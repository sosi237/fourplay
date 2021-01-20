package admin.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import action.Action;
import admin.action.*;
import vo.*;

@WebServlet("*.orda")
public class AOrdCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AOrdCtrl() {
        super();
    }
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		System.out.println(command);
		
		ActionForward forward = null;
		Action action = null;
//		HttpSession session = request.getSession();
//		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		
		switch (command) {
			case "/admin/ord_list.orda" :		// 주문 목록 화면
				action = new AOrdListAction();
				break;
			case "/admin/ord_detail.orda" :		// 주문 상세내역 팝업창
				action = new AOrdDetailAction();
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
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
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
