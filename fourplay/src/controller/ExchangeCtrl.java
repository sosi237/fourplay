package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.PdtListAction;
import action.PdtViewAction;
import vo.ActionForward;

@WebServlet("*.exc")
public class ExchangeCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ExchangeCtrl() {
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

    		// ������� ��û ������ ���� ���� �ٸ� action�� ����
    		switch (command) {
    			case "/exc_inform.exc" :		// ��ǰ ��� ��
    				action = new PdtListAction();		break;
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
