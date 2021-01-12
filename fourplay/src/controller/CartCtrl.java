package controller;

import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import action.*;
import vo.*;

@WebServlet("*.crt")
public class CartCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CartCtrl() {
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
			case "/cart_in.crt" :			// ��ٱ��� ��� ���
				action = new CartInAction();		break;
			case "/cart_list.crt" :			// ��ٱ��� ȭ��
				action = new CartListAction();		break;
			case "/wish_in.crt" :			// ���ø���Ʈ ��ϱ��
				action = new WishInAction();		break;
			case "/wish_del.crt" :			// ���ø���Ʈ �������
				action = new WishDelAction();		break;
			case "/wish_list.crt" :			// ���ø���Ʈ ȭ��
				action = new WishListAction();		break;
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
