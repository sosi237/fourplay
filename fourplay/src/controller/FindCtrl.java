package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import action.MemberForm;
import action.MemberProcAction;
import svc.LoginSvc;
import vo.ActionForward;
import vo.MemberInfo;

@WebServlet("*.find")
public class FindCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FindCtrl() {
        super();
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 사용자의 요청이 get이든 post이든 모두 처리하는 메소드
		request.setCharacterEncoding("utf-8");
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		
		ActionForward forward = null;
		Action action = null;
		
		switch (command) {
			 case "/find_id.find" :	// 아이디 찾는 폼
				action = new FindIdAction();
				break;
			 case "/find_pwd.find" :	// 비밀번호 찾는 폼
				action = new MemberProcAction();
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
