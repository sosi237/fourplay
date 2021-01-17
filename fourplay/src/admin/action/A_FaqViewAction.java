package admin.action;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class A_FaqViewAction implements A_Action {
// �ϳ��� �Խñ��� �� �� ��������ִ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));	
		int cpage = Integer.parseInt(request.getParameter("cpage"));

		String schtype = request.getParameter("schtype");	
		String keyword = request.getParameter("keyword");	
		
		A_FaqViewSvc afaqViewSvc = new A_FaqViewSvc();
		FaqInfo article = afaqViewSvc.getArticle(idx);
		// ������ �Խñ��� FreeInfo�� �ν��Ͻ��� �޾ƿ�
		
		ActionForward forward = new ActionForward();
		request.setAttribute("article", article);
		forward.setPath("/admin/bbs/a_faq_view.jsp");
		return forward;
	}
}
