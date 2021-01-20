package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.ArrayList;

import admin.svc.*;
import vo.*;

public class A_QAListAction implements A_Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<QAInfo> articleList = new ArrayList<QAInfo>();
System.out.println("A_QAListAction");
		request.setCharacterEncoding("utf-8");
		int cpage = 1, limit = 10;
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		String schtype = request.getParameter("schtype");
		String keyword = request.getParameter("keyword");	

		String where = "";	
		if (keyword != null && !keyword.equals("")) {
			if (schtype.equals("tc")) {	
				where = " and (nl_title like '%" + keyword + "%' " + 
					" or nl_content like '%" + keyword + "%') ";
			} else {
				where = " and nl_" + schtype + " like '%" + keyword + "%' ";
			}
		}

		A_QAListSvc aqaListSvc = new A_QAListSvc();

		int rcnt = aqaListSvc.getArticleCount(where);

		articleList = aqaListSvc.getArticleList(where, cpage, limit);

		int pcnt = rcnt / limit;
		if (rcnt % limit > 0)	pcnt++;
		int spage = (cpage - 1) / limit * limit + 1;
		int epage = spage + limit - 1;
		if (epage > pcnt)	epage = pcnt;

		PageInfo pageInfo = new PageInfo();
		pageInfo.setCpage(cpage);		// 현재 페이지 번호
		pageInfo.setRcnt(rcnt);			// 전체 게시글 수
		pageInfo.setPcnt(pcnt);			// 전체 페이지 수
		pageInfo.setSpage(spage);		// 블록 시작 페이지 번호
		pageInfo.setEpage(epage);		// 블록 종료 페이지 번호
		pageInfo.setSchtype(schtype);	// 검색조건
		pageInfo.setKeyword(keyword);	// 검색어
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("articleList", articleList);

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/bbs/a_aqa_list.jsp");
		return forward;
	}
}
