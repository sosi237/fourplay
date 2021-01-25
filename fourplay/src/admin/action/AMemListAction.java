package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AMemListAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AMemListAction");
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		ArrayList<MemberInfo> memberList = new ArrayList<MemberInfo>();
		HttpSession session = request.getSession();
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		
		if(adminMember != null ) {
			int cpage = 1, pcnt, spage, epage, rcnt, bsize = 10, psize = 10;
			// ����¡�� �ʿ��� ������ ������ ���� ���� �� �ʱ�ȭ
			if (request.getParameter("cpage") != null)			cpage = Integer.parseInt(request.getParameter("cpage"));
			if (request.getParameter("psize") != null)			psize = Integer.parseInt(request.getParameter("psize"));
			
			String schtype, keyword;
			schtype = request.getParameter("schtype");	// �˻��������� ���̵�(id), �̸�(name), ����(status)
			keyword = request.getParameter("keyword");	// �˻���
			String ord = request.getParameter("ord");	// ������������ ������ ���̵�(��a��d), �������̸�name(��a), �����date(��a��d), ������뿩��status(��a��d)
			
			String where = "", orderby = "";
			if (keyword != null && !keyword.equals(""))	{
				
				where = " where ml_" + schtype + " like '%" + keyword + "%' ";
				if (schtype.equals("status")) {
					where = " where ml_" + schtype + " like '%" + (keyword.equals("�Ϲ�") ? "a" : (keyword.equals("�޸�") ? "b" : "c") )+ "%' ";
				}
			}
		
			if (ord != null && !ord.equals("")) {
				orderby = " order by ml_" + ord.substring(0, ord.length() - 1) + 
				(ord.substring(ord.length() - 1).equals("d") ? " desc" : " asc");
			}	// ���İ� : aida, aidd, namea, named, datea, dated, statusa, statusd
	
			AMemListSvc aMemListSvc = new AMemListSvc();
			memberList = aMemListSvc.getMemberList(where, orderby, cpage, psize);
			rcnt = aMemListSvc.getMemCount(where);	// �˻��� ������ ������ �� ����(������ ������ ���ϱ� ���� �ʿ�)
	
			pcnt = rcnt / psize;
			if (rcnt % psize > 0)	pcnt++;				// ��ü �������� ����
			spage = (cpage - 1) / psize * psize + 1;	// ��� ���������� ��ȣ
			epage = spage + psize - 1;
			if (epage > pcnt)	epage = pcnt;			// ��� ���������� ��ȣ
	
			AdmPageInfo pageInfo = new AdmPageInfo();	// ����¡�� �ʿ��� ������ ���� �ν��Ͻ� ����
			pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
			pageInfo.setRcnt(rcnt);			// ��ü ������ ���� ����
			pageInfo.setPcnt(pcnt);			// ��ü ������ ����
			pageInfo.setSpage(spage);		// ��� ���������� ��ȣ
			pageInfo.setEpage(epage);		// ��� ���������� ��ȣ
			pageInfo.setBsize(bsize);		// ��ϳ� ������ ����
			pageInfo.setPsize(psize);		// �������� ������ ���� ���� (limit)
			pageInfo.setSchtype(schtype);	// �˻�����
			pageInfo.setKeyword(keyword);	// �˻���
			pageInfo.setOrd(ord);			// ��������
			
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("memberList", memberList);
			forward.setPath("member/a_member_list.jsp");
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���� ������ �����ϴ�.');");
			out.println("location.replace('/fourplay/login_form.jsp');");
			out.println("</script>");
			out.close();
		}
		
		return forward;
	}
}

