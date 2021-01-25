package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class A_QAViewAction implements A_Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));	// �۹�ȣ
		String mlid = request.getParameter("mlid");	// �ۼ���(ȸ��id)
		int cpage = Integer.parseInt(request.getParameter("cpage"));// ���� ��������ȣ

		String schtype = request.getParameter("schtype");	// �˻�����
		String keyword = request.getParameter("keyword");	// �˻���
		
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		
		if(loginMember != null) {	// �α������� �ʾҰų� �ش� ���� �ۼ��� ȸ���� �ƴϸ�
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('������ �����ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		} else {
			A_QAViewSvc aqaViewSvc = new A_QAViewSvc();
			QAInfo article = aqaViewSvc.getArticle(idx);
			request.setAttribute("article", article);
			forward.setPath("/admin/bbs/a_aqa_view.jsp");
		}
		
		return forward;
	}
}
