package admin.action;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class A_FaqListAction implements A_Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<FaqInfo> articleList = new ArrayList<FaqInfo>();

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

		A_FaqListSvc afaqListSvc = new A_FaqListSvc();

		int rcnt = afaqListSvc.getArticleCount(where);

		articleList = afaqListSvc.getArticleList(where, cpage, limit);

		int pcnt = rcnt / limit;
		if (rcnt % limit > 0)	pcnt++;
		int spage = (cpage - 1) / limit * limit + 1;
		int epage = spage + limit - 1;
		if (epage > pcnt)	epage = pcnt;

		PageInfo pageInfo = new PageInfo();
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setRcnt(rcnt);			// ��ü �Խñ� ��
		pageInfo.setPcnt(pcnt);			// ��ü ������ ��
		pageInfo.setSpage(spage);		// ��� ���� ������ ��ȣ
		pageInfo.setEpage(epage);		// ��� ���� ������ ��ȣ
		pageInfo.setSchtype(schtype);	// �˻�����
		pageInfo.setKeyword(keyword);	// �˻���
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("articleList", articleList);

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/bbs/a_faq_list.jsp");
		return forward;
	}
}
