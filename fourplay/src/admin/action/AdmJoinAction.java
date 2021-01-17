package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdmJoinAction implements action.Action {	
// �α����� ������ super admin���� �˻��ϰ� ������ ���� ���� ȭ������ �̵���Ű�� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		AdmJoinSvc admJoinSvc = new AdmJoinSvc();
		boolean isSA = admJoinSvc.isSA(adminMember);
		System.out.println(isSA);
		ActionForward forward = new ActionForward();
		
		if(isSA) {
			forward.setPath("admin/admin_join.jsp");	// �̵��� URL ����
		}
		return forward;
	}
}
