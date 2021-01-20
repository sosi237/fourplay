package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class QAViewAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));	// �۹�ȣ
		int cpage = Integer.parseInt(request.getParameter("cpage"));// ���� ��������ȣ

		String schtype = request.getParameter("schtype");	// �˻�����
		String keyword = request.getParameter("keyword");	// �˻���
		
		QAViewSvc qaViewSvc = new QAViewSvc();
		QAInfo article = qaViewSvc.getArticle(idx);
		
		ActionForward forward = new ActionForward();
		request.setAttribute("article", article);
		forward.setPath("/customer/qna_view.jsp");
		return forward;
	}
}
