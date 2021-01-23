package action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import svc.*;
import vo.*;

public class ReviewListAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ReviewListAction");
		
		request.setCharacterEncoding("utf-8");		
		String plid = request.getParameter("plid");
		int rCpage = 1, rPcnt, rSpage, rEpage, rCnt, rBsize = 10, rPsize = 5;
		if (request.getParameter("rCpage") != null)
			rCpage = Integer.parseInt(request.getParameter("rCpage"));
		
		ReviewListSvc reviewListSvc = new ReviewListSvc();
		ArrayList<ReviewInfo> articleList = new ArrayList<ReviewInfo>();
		rCnt = reviewListSvc.getArticleCount(plid);
		articleList = reviewListSvc.getArticleList(plid, rCpage, rPsize);

		rPcnt = rCnt / rPsize;	//��ü ��������
		if (rCnt % rPsize > 0)	rPcnt++;
		rSpage = (rCpage - 1) / rPsize * rPsize + 1;
		rEpage = rSpage + rPsize - 1;
		if (rEpage > rPcnt)	rEpage = rPcnt;

		ReviewPageInfo reviewPageInfo = new ReviewPageInfo();
		reviewPageInfo.setrCpage(rCpage);		// ���� ������ ��ȣ
		reviewPageInfo.setrCnt(rCnt);			// ��ü �Խñ� ��
		reviewPageInfo.setrPcnt(rPcnt);			// ��ü ������ ��
		reviewPageInfo.setrSpage(rSpage);		// ��� ���� ������ ��ȣ
		reviewPageInfo.setrEpage(rEpage);		// ��� ���� ������ ��ȣ
		reviewPageInfo.setrBsize(rBsize);
		ActionForward forward = new ActionForward();
		request.setAttribute("articleList", articleList);
		request.setAttribute("reviewPageInfo", reviewPageInfo);
		forward.setPath("/product/review_list.jsp");

		return forward;
	}
}
