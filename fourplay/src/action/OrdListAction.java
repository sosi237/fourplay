package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class OrdListAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("OrdListAction");
		
		ArrayList<OrdListInfo> ordList = new ArrayList<OrdListInfo>();

		request.setCharacterEncoding("utf-8");
		int cpage = 1, pcnt, spage, epage, rcnt, bsize = 10, psize = 12;
		// ����¡�� �ʿ��� ������ ������ ���� ���� �� �ʱ�ȭ
		if (request.getParameter("cpage") != null)			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)			psize = Integer.parseInt(request.getParameter("psize"));
		
		String buyer ="";
		//buyer = (String)request.getAttribute("id");
		HttpSession session = request.getSession();	
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if(loginMember != null) {	//ȸ���� ���
			buyer = loginMember.getMl_id();
		} else	buyer = "sungda123";	//��ȸ���� ���(test������ ���� ����)
		System.out.println(buyer);
		
		OrdListSvc ordListSvc = new OrdListSvc();
		rcnt = ordListSvc.getOrdCount(buyer);	// �ֹ��� �� ����(������ ������ ���ϱ� ���� �ʿ�)
		ordList = ordListSvc.getOrdList(buyer, cpage, psize);
		// �� ���������� ������ �ֹ�����
		// �˻�����, ��������, limit���� ����� ���� ���ϱ� ���� ������������ ������ũ�⸦ �μ��� ������
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;				// ��ü �������� ����
		spage = (cpage - 1) / psize * psize + 1;	// ��� ���������� ��ȣ
		epage = spage + psize - 1;
		if (epage > pcnt)	epage = pcnt;			// ��� ���������� ��ȣ

		OrdPageInfo ordPageInfo = new OrdPageInfo();	// ����¡�� �ʿ��� ������ ���� �ν��Ͻ� ����
		ordPageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		ordPageInfo.setPcnt(pcnt);			// ��ü ������ ����
		ordPageInfo.setSpage(spage);		// ��� ���������� ��ȣ
		ordPageInfo.setEpage(epage);		// ��� ���������� ��ȣ
		ordPageInfo.setRcnt(rcnt);			// ��ü ��ǰ(���ڵ�) ����
		ordPageInfo.setBsize(bsize);		// ��ϳ� ������ ����
		ordPageInfo.setPsize(psize);		// �������� ��ǰ ����

		request.setAttribute("ordList", ordList);
		request.setAttribute("ordPageInfo", ordPageInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/member/order_list.jsp");
		return forward;
	}
}
