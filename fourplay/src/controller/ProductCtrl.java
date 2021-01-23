package controller;

import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import action.*;
import vo.*;

@WebServlet("*.pdt")
public class ProductCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProductCtrl() {
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
    			case "/pdt_list.pdt" :		// ��ǰ ��� ��
    				action = new PdtListAction();		break;
    			case "/pdt_view.pdt" :			// ��ǰ �� ȭ��
    				action = new PdtViewAction();		break;
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
