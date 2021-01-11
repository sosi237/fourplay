package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdmProcAction implements action.Action {	
// ������ ���� ���� ����, ������ ó���ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		AdmProcSvc admProcSvc = new AdmProcSvc();

		String wtype = request.getParameter("wtype");
		String link = "";
		boolean isSuccess = false;
	
		AdminInfo adminMember = new AdminInfo();
		
		if(wtype.equals("up")) { // ���� ������ ���
			adminMember.setAl_id(request.getParameter("aid"));
			adminMember.setAl_name(request.getParameter("name"));
			adminMember.setAl_pwd(request.getParameter("pwd"));
			adminMember.setAl_email(request.getParameter("email"));
			adminMember.setAl_phone(request.getParameter("phone"));
			
			isSuccess = admProcSvc.admUpdate(adminMember);
			
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			if (isSuccess) {	// ������
				HttpSession session = request.getSession();
				session.setAttribute("adminMember", adminMember);
				
				out.println("<script>");
				out.println("alert('������ �����߽��ϴ�.');");
				out.println("location.replace('admin_form.adm');");
				out.println("</script>");
				out.close();
				
			} else {	
				out.println("<script>");
				out.println("alert('���� ������ �����߽��ϴ�.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
		}
		return forward;
	}
}
