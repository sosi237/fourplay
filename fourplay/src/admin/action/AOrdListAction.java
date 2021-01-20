package admin.action;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import admin.svc.*;
import svc.OrdListSvc;
import vo.*;

public class AOrdListAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AOrdListAction");
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		ArrayList<OrdListInfo> ordList = new ArrayList<OrdListInfo>();
		
		int cpage = 1, pcnt, spage, epage, rcnt, bsize = 10, psize = 12;		// ����¡�� �ʿ��� ������ ������ ���� ���� �� �ʱ�ȭ
		if (request.getParameter("cpage") != null)			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)			psize = Integer.parseInt(request.getParameter("psize"));

		HttpSession session = request.getSession();
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		String buyer = "";
		
		if(adminMember != null) {	// �α����� ȸ���̸�
			AOrdListSvc aOrdListSvc = new AOrdListSvc();
			
			rcnt = aOrdListSvc.getOrdCount();	// ����¡�� ���� ��ü �ֹ����� ���� �޾ƿ�
			ordList = aOrdListSvc.getOrdList(cpage, psize);	// ��ü �ֹ������ �޾ƿ�
			
			pcnt = rcnt / psize;
			if (rcnt % psize > 0)	pcnt++;				// ��ü ��������
			spage = (cpage - 1) / psize * psize + 1;	// ��� ���������� ��ȣ
			epage = spage + psize - 1;
			if (epage > pcnt)	epage = pcnt;			// ��� ���������� ��ȣ
			
			OrdPageInfo pageInfo = new OrdPageInfo();	// ����¡�� �ʿ��� ������ ���� �ν��Ͻ� ����
			pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
			pageInfo.setPcnt(pcnt);			// ��ü ������ ����
			pageInfo.setSpage(spage);		// ��� ���������� ��ȣ
			pageInfo.setEpage(epage);		// ��� ���������� ��ȣ
			pageInfo.setRcnt(rcnt);			// ��ü ��ǰ(���ڵ�) ����
			pageInfo.setBsize(bsize);		// ��ϳ� ������ ����
			pageInfo.setPsize(psize);		// �������� ��ǰ ����
			
			request.setAttribute("ordList", ordList);
			request.setAttribute("pageInfo", pageInfo);
			forward.setPath("order/a_order_list.jsp");
		} else {	// �α����ؾ߸� ���� �� �����Ƿ�
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�߸��� ��η� �����̽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		return forward;
	}
}
