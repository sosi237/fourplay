package action;

import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

public class MemDelProcAction implements Action {
// ȸ������ ���� ó��
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		
		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");

		MemDelProcSvc memDelProcSvc = new MemDelProcSvc();
		
		boolean isSuccess = memDelProcSvc.memDelete(uid, pwd);
		
		if (!isSuccess) {	// ȸ��Ż�� ����������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('ȸ�� Ż�� �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		forward.setPath("login_form.jsp");
		forward.setRedirect(true);
		return forward;
	}
}
