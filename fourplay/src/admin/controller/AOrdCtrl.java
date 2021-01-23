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
		
		switch (command) {
			case "/admin/ord_list.orda" :		// �ֹ� ��� ȭ��
				action = new AOrdListAction();
				break;
			case "/admin/ord_detail.orda" :		// �ֹ� �󼼳��� �˾�â
				action = new AOrdDetailAction();
				break;
			case "/admin/pdt_view.orda" :		// ��ǰ���� �˾�â
				action = new AOrdPdtAction();
				break;
			case "/admin/ord_proc.orda" :		// �ֹ� �󼼳��� �˾�â
				action = new AOrdProcAction();
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
