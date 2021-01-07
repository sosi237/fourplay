package action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import svc.*;
import vo.*;

public class OrdDetailAction implements Action {	// ȸ���� Ư�� �ֹ� �󼼳����� ������ �����ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		OrdListInfo detailInfo = new OrdListInfo();
		request.setCharacterEncoding("utf-8");
		String olid = request.getParameter("olid");
		String where = "";
		HttpSession session = request.getSession();
		
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if(loginMember != null) {	// �α����� ���¸�
			where += " and ol_buyer = '" + loginMember.getMlid() + "' and ol_ismember = 'y' ";
			OrdListSvc ordListSvc = new OrdListSvc();
			detailInfo = ordListSvc.getOrd(olid, where);
			request.setAttribute("detailInfo", detailInfo);
			forward.setPath("/member/order_detail.jsp");
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�߸��� ��η� �����̽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		return forward;
	}
}
