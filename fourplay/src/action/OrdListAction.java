package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class OrdListAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//System.out.println("OrdListAction");
		ActionForward forward = new ActionForward();
		ArrayList<OrdListInfo> ordList = new ArrayList<OrdListInfo>();
		request.setCharacterEncoding("utf-8");
		int cpage = 1, pcnt, spage, epage, rcnt, bsize = 10, psize = 12;
		// ����¡�� �ʿ��� ������ ������ ���� ���� �� �ʱ�ȭ
		if (request.getParameter("cpage") != null)			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)			psize = Integer.parseInt(request.getParameter("psize"));
		
		HttpSession session = request.getSession();	
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		String buyer ="";
		String bname = request.getParameter("bname");
		String olid = request.getParameter("olid");		//��ȸ�� �ֹ���ȸ���� �Է��� �ֹ���ȣ
		OrdListSvc ordListSvc = new OrdListSvc();
		
		if(loginMember != null) {	//���� �α����� �����̸�
			if(bname != null && olid != null) {	//��ȸ�� �ֹ���ȸ â�� ���� �Է�������
				String where = " and ol_bname = '" + bname + "' ";
				OrdListInfo ordInfo = ordListSvc.getOrd(olid, where);
				request.setAttribute("ordInfo", ordInfo);
				forward.setPath("/member/non_order_list.jsp");
			} else {
				buyer = loginMember.getMlid();
				rcnt = ordListSvc.getOrdCount(buyer);	// �ֹ��� �� ����(������ ������ ���ϱ� ���� �ʿ�)
				ordList = ordListSvc.getOrdList(buyer, cpage, psize);
				//����¡
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
				forward.setPath("/member/order_list.jsp");
			}
		} else	{			// ���� �α������� ���� ���¸�			
			//��ȸ�� ����� �ֹ���ȣ ���� 1���� ������ �˻� �����ϹǷ� ����¡ �ʿ� ����
			String where = " and ol_bname = '" + bname + "' ";
			OrdListInfo ordInfo = ordListSvc.getOrd(olid, where);
			request.setAttribute("ordInfo", ordInfo);
			forward.setPath("/member/non_order_list.jsp");
		}
		return forward;
	}
}
