package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdmFormAction implements action.Action {	
// ������ �α��� ���¸� �˻��ϰ� ������ ���� ����ȭ������ �̵���Ű�� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		
		System.out.println("AdmFormAction");
		
		HttpSession session = request.getSession();
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		
		if(adminMember != null) {	// ������ �������� �α��ε� ���¸�
			request.setAttribute("adminMember", adminMember);
			forward.setPath("admin/admin_form.jsp");
		} else {	// �α����� Ǯ������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���� ������ �����ϴ�.\n�ٽ� �α����ϼ���.');");
			out.println("location.replace('login_form.jsp');");
			out.println("</script>");
			out.close();
		}
		return forward;
	}
}
