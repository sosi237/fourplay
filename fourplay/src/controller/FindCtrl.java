package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.find")
public class FindCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FindCtrl() {
        super();
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// ������� ��û�� get�̵� post�̵� ��� ó���ϴ� �޼ҵ�
		request.setCharacterEncoding("utf-8");
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		
		ActionForward forward = null;
		Action action = null;
		
		switch (command) {
			 case "/find_id.find" :	// ���̵� ã�� ȭ��
				action = new FindIdAction();
				break;
			 case "/find_id_proc.find" :	// ���̵� ã�� ���
				action = new FindIdProcAction();
					break;
			 case "/find_pwd.find" :	// ��й�ȣ ã�� ��
				action = new FindPwdAction();
				break;
			 case "/find_pwd_proc.find" :	// ��й�ȣ ã�� ���
				action = new FindPwdProcAction();

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
