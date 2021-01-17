package admin.action;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class A_NoticeViewAction implements A_Action {
// �ϳ��� �Խñ��� �� �� ��������ִ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));	
		int cpage = Integer.parseInt(request.getParameter("cpage"));

		String schtype = request.getParameter("schtype");	
		String keyword = request.getParameter("keyword");	
		
		A_NoticeViewSvc anoticeViewSvc = new A_NoticeViewSvc();
		NoticeInfo article = anoticeViewSvc.getArticle(idx);
		// ������ �Խñ��� FreeInfo�� �ν��Ͻ��� �޾ƿ�
		
		ActionForward forward = new ActionForward();
		request.setAttribute("article", article);
		forward.setPath("/admin/bbs/a_notice_view.jsp");
		return forward;
	}
}
