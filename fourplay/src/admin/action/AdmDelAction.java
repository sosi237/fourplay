package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdmDelAction implements action.Action {	
// ������ �α��� ���¸� �˻��ϰ� ������ ���� ����ȭ������ �̵���Ű�� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		
		ArrayList<AdminInfo> admList = new ArrayList<AdminInfo>();
		
		int cpage = 1, pcnt, spage, epage, rcnt, bsize = 10, psize = 10;		// ����¡�� �ʿ��� ������ ������ ���� ���� �� �ʱ�ȭ
		if (request.getParameter("cpage") != null)			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)			psize = Integer.parseInt(request.getParameter("psize"));
		String status, schtype, keyword;
		status	= request.getParameter("status");	// ��뿩��(a: �̻�� b: �����)
		schtype = request.getParameter("schtype");	// �˻��������� ���̵�(id)�� �̸�(name)
		keyword = request.getParameter("keyword");	// �˻���
		String ord = request.getParameter("ord");	// ������������ ������ ���̵�(��a��d), �������̸�name(��a), �����date(��a��d), ������뿩��status(��a��d)
		String where = " where al_id != 'sa' ", orderby = "";
		if (keyword != null && !keyword.equals(""))	{
			where += " and al_" + schtype + " like '%" + keyword + "%' ";
		}
		if(status != null && !status.equals(""))	{
			if(where.equals(""))	where = " where al_status ='" + status + "' ";
			else 					where += " and al_status ='" + status + "' ";
		}
		if (ord != null && !ord.equals("")) {
			orderby = " order by al_" + ord.substring(0, ord.length() - 1) + 
			(ord.substring(ord.length() - 1).equals("d") ? " desc" : " asc");
		}	// ���İ� : aida, aidd, namea, named, datea, dated, statusa, statusd
		AdmListSvc admListSvc = new AdmListSvc();
		admList = admListSvc.getAdmList(where, orderby, cpage, psize);
		rcnt = admListSvc.getAdmCount(where);	// �˻��� ������ ������ �� ����(������ ������ ���ϱ� ���� �ʿ�)
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;				// ��ü �������� ����
		spage = (cpage - 1) / psize * psize + 1;	// ��� ���������� ��ȣ
		epage = spage + psize - 1;
		if (epage > pcnt)	epage = pcnt;			// ��� ���������� ��ȣ

		AdmPageInfo pageInfo = new AdmPageInfo();	// ����¡�� �ʿ��� ������ ���� �ν��Ͻ� ����
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setPcnt(pcnt);			// ��ü ������ ����
		pageInfo.setSpage(spage);		// ��� ���������� ��ȣ
		pageInfo.setEpage(epage);		// ��� ���������� ��ȣ
		pageInfo.setRcnt(rcnt);			// ��ü ������ ���� ����
		pageInfo.setBsize(bsize);		// ��ϳ� ������ ����
		pageInfo.setPsize(psize);		// �������� ������ ���� ����
		pageInfo.setSchtype(schtype);
		pageInfo.setKeyword(keyword);
		pageInfo.setStatus(status);
		pageInfo.setOrd(ord);			// ��������
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("admList", admList);

		AdmJoinSvc admJoinSvc = new AdmJoinSvc();
		boolean isSA = admJoinSvc.isSA(adminMember);
		if(isSA) {	// sa�����̸�
			forward.setPath("admin/admin_del.jsp");	
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���� ������ �����ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		return forward;
	}
}
