package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class QAViewAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));	// 글번호
		int cpage = Integer.parseInt(request.getParameter("cpage"));// 현재 페이지번호

		String schtype = request.getParameter("schtype");	// 검색조건
		String keyword = request.getParameter("keyword");	// 검색어
		
		QAViewSvc qaViewSvc = new QAViewSvc();
		QAInfo article = qaViewSvc.getArticle(idx);
		
		ActionForward forward = new ActionForward();
		request.setAttribute("article", article);
		forward.setPath("/customer/qna_view.jsp");
		return forward;
	}
}
