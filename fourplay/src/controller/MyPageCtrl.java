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

      HttpSession session = request.getSession();
      MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
      
      switch (command) {
		case "/order_list.mpg" :      // ȸ�� �ֹ����� ����
		if(loginMember != null) {   // �α����� �����̸�
		   action = new OrdListAction();
		}else {
		   response.sendRedirect("login_form.jsp?ismember=n");
		    }
		    break;
		case "/non_order_list.mpg" :   // ��ȸ�� �ֹ���ȸ
		    action = new NonOrdAction();
		    break;
		case "/order_detail.mpg" :   // ȸ�� �ֹ� �󼼳��� ����
		    action = new OrdDetailAction();
		    break;
		case "/non_order_detail.mpg" :   // ��ȸ�� �ֹ� �󼼳��� ����
		    action = new NonDetailAction();
		    break;
		case "/mypage.mpg" :	// ���������� ȭ��
			action = new MyPageAction();
		 	break;
		case "/member_info.mpg" :	// ȸ�� ���� ���� ȭ��
			action = new MemberInfoAction();
			break;
		case "/member_proc.mpg" :	// ȸ�� ���� ���� ���(�̿ϼ�)
			action = new MemberProcAction();
			break;
		case "/mypage_pwd.mpg" :	// ȸ�� ���� ���� ȭ��
			action = new MyPagePwdAction();
			break;
		case "/member_del_proc.mpg" :	// ȸ�� ���� ���� ó�� ��� 
			action = new MemDelProcAction();
			break;
		case "/addr_view.mpg" :	// �ּҷ� ȭ��
			action = new AddrViewAction();
			break;
		case "/addr_del.mpg" :	// �ּҷ� ���� ���
			action = new AddrDelAction();
			break;
		case "/addr_form.mpg" :	// �ּҷ� �߰� ȭ��
			action = new AddrFormAction();
			break;
		case "/addr_in_proc.mpg" :	// �ּҷ� �߰� ���
			action = new AddrInProcAction();
			break;
		case "/point_list.mpg" :	// ����Ʈ ���� ȭ��
			action = new PointListAction();
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