package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AOrdProcAction implements action.Action {	
// �ֹ����� ������ ó���ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ActionForward");
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		AOrdProcSvc aOrdProcSvc = new AOrdProcSvc();
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		boolean isSuccess = false;
		
		String idxs = request.getParameter("idxs");	// �ֹ����̵��
		String st = request.getParameter("st");	// �ֹ� ���°���
		isSuccess = aOrdProcSvc.chStatus(idxs, st);
		
		if(isSuccess) {		// ���º��濡 �����ߴٸ�
			forward.setRedirect(true);
			forward.setPath("ord_list.orda");
		} else {	//���º��濡 �����ߴٸ�
			out.println("<script>");
			out.println("alert('�ֹ����� ���濡 �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		return forward;
	}
		
}
