package admin.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import admin.action.*;
import action.*;
import vo.*;

@WebServlet("*.stat")
public class AStatisticsCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AStatisticsCtrl() {
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
			case "/admin/mem_stat.stat" :		// 회원 통계 화면
				action = new AMemStatAction();	break;
			case "/admin/pdt_stat.stat" :		// 상품 통계 화면
				action = new AStatPdtAction();	break;
			case "/admin/sales_stat.stat" :		// 매출 통계 화면
				action = new AStatSalesAction();	break;
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
