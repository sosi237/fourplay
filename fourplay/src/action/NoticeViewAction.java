package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class NoticeViewAction implements Action {
// �ϳ��� �Խñ��� �� �� ��������ִ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));	
		int cpage = Integer.parseInt(request.getParameter("cpage"));

		String schtype = request.getParameter("schtype");	
		String keyword = request.getParameter("keyword");	
		
		NoticeViewSvc noticeViewSvc = new NoticeViewSvc();
		NoticeInfo article = noticeViewSvc.getArticle(idx);
		// ������ �Խñ��� FreeInfo�� �ν��Ͻ��� �޾ƿ�
		
		ActionForward forward = new ActionForward();
		request.setAttribute("article", article);
		forward.setPath("/bbs/notice_view.jsp");
		return forward;
	}
}
