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
		
		if(adminMember != null) {	// ������ �������� �α��εǾ�������
			AOrdListSvc aOrdListSvc = new AOrdListSvc();
			String status, schtype, keyword;
			status	= request.getParameter("status");	// �ֹ�����
			schtype = request.getParameter("schtype");	// �ֹ�����, �˻�����, Ű����(�ֹ��� ���̵�, �ֹ���ȣ)
			keyword = request.getParameter("keyword");	// �˻���
			String ord = request.getParameter("ord");	// ������������ �ֹ��� ���̵�(��a��d), �ֹ���ȣ(��a��d), �ֹ���date(��a��d), ������뿩��(��a��d)
			
			String where = "", orderby = " order by ol_date desc ";
			if (keyword != null && !keyword.equals(""))	{
				where += " and ol_" + schtype + " like '%" + keyword + "%' ";
			}
			if(status != null && !status.equals(""))	{
				where += " and ol_status ='" + status + "' ";
			}
			if (ord != null && !ord.equals("")) {
				orderby = " order by ol_" + ord.substring(0, ord.length() - 1) + 
				(ord.substring(ord.length() - 1).equals("d") ? " desc" : " asc");
			}	// ���İ� : buyera, buyerd, ida, idd, datea, dated, ol_statusa, ol_statusd
			
			if(!where.equals("") && where.substring(0,4).equals(" and")) {
				where = " where " + where.substring(4);
			}
			
			rcnt = aOrdListSvc.getOrdCount(where);	// ����¡�� ���� ��ü �ֹ����� ���� �޾ƿ�
			ordList = aOrdListSvc.getOrdList(where, orderby, cpage, psize);	// ��ü �ֹ������ �޾ƿ�
			
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
			pageInfo.setRcnt(rcnt);			// ��ü �ֹ�(���ڵ�) ����
			pageInfo.setBsize(bsize);		// ��ϳ� ������ ����
			pageInfo.setPsize(psize);		// �������� �ֹ� ����
			pageInfo.setSchtype(schtype);
			pageInfo.setKeyword(keyword);
			pageInfo.setStatus(status);
			pageInfo.setOrd(ord);			// ��������
			
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
