package action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import svc.*;
import vo.*;

public class NonOrdAction implements Action {	// �α��� ���ο� ������� ��ȸ�� �ֹ������� �޾ƿ� �����ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		ArrayList<OrdListInfo> ordList = new ArrayList<OrdListInfo>();
		OrdListInfo ordInfo = new OrdListInfo();
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String where = "";
		String bname = request.getParameter("bname");
		String olid = request.getParameter("olid");
		boolean isSuccess = false;
		
		if(bname != null && olid != null) {	// ��ȸ�� �ֹ���ȸ ������ ��� �ԷµǾ�����
			OrdListSvc ordListSvc = new OrdListSvc();
			isSuccess = ordListSvc.chkNonOrd(olid, bname);
			System.out.println(isSuccess);
			if(isSuccess) {		// �Է��� ���� �ش��ϴ� ��ȸ�� �ֹ��� ������
				where += " and ol_bname = '" + bname + "' and ol_ismember = 'n' ";
				ordInfo = ordListSvc.getOrd(olid, where);
				request.setAttribute("ordInfo", ordInfo);
				forward.setPath("/member/non_order_list.jsp");
			}else {
				out.println("<script>");
				out.println("alert('�Է��� ������ �ش��ϴ� �ֹ��� �����ϴ�.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
		} else {	// ��ȸ�� �ֹ���ȸ ������ �Է����� �ʾ��� ���
			out.println("<script>");
			out.println("alert('������ �Է��ϼ���.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		return forward;
	}
}
