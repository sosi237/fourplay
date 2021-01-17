package admin.action;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class A_FreeListAction implements A_Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<FreeInfo> articleList = new ArrayList<FreeInfo>();

		request.setCharacterEncoding("utf-8");
		int cpage = 1, limit = 10;
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		String schtype = request.getParameter("schtype");
		String keyword = request.getParameter("keyword");	

		String where = "";	
		if (keyword != null && !keyword.equals("")) {
			if (schtype.equals("tc")) {	
				where = " and (fl_title like '%" + keyword + "%' " + 
					" or fl_content like '%" + keyword + "%') ";
			} else {
				where = " and fl_" + schtype + " like '%" + keyword + "%' ";
			}
		}

		A_FreeListSvc afreeListSvc = new A_FreeListSvc();

		int rcnt = afreeListSvc.getArticleCount(where);

		articleList = afreeListSvc.getArticleList(where, cpage, limit);

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
		forward.setPath("/admin/bbs/a_free_list.jsp");
		return forward;
	}
}
