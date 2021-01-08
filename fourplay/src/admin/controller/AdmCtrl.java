package admin.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import action.Action;
import admin.action.*;
import vo.*;

@WebServlet("*.adm")
public class AdmCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AdmCtrl() {
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
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		
		switch (command) {
			case "/admin/admin_list.adm" :	// 관리자 목록 화면
				// 여기서 if문으로 검사해서 super admin이면 admin_list.jsp로, 아니면 계정 정보 수정 창으로 이동. 액션 달라야
//				action = new AdmListAction();
				break;
			case "/admin/admin_join.adm" :	// 관리자 계정 생성 화면
				action = new AdmJoinAction();
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
				RequestDispatcher dispatcher = 	request.getRequestDispatcher(forward.getPath());
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

