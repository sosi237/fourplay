package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdmListAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AdmListAction");
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		ArrayList<AdminInfo> admList = new ArrayList<AdminInfo>();
		HttpSession session = request.getSession();
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		
		String kind = "";
		if(request.getParameter("kind") != null) 	kind = request.getParameter("kind");
		
		if(adminMember != null && adminMember.getAl_id().equals("sa")) {
			int cpage = 1, pcnt, spage, epage, rcnt, bsize = 10, psize = 3;
			// ����¡�� �ʿ��� ������ ������ ���� ���� �� �ʱ�ȭ
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

			System.out.println("admListAction finished");
			
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
			forward.setPath("admin/admin_list.jsp");
			if(kind.equals("join"))	{
				forward.setPath("/admin/admin_list.jsp");
			}
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���� ������ �����ϴ�.');");
			out.println("location.replace('../index.jsp');");
			out.println("</script>");
			out.close();
		}
		
		return forward;
	}
}
