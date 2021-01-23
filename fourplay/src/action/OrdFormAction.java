package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class OrdFormAction implements Action {
// ȸ�������� ��ǰ������ �޾ƿ� �ֹ��� �������� �����ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String kind = request.getParameter("kind");		// ��ٱ��� �Ǵ� �ٷ� ���Ÿ� �����ϴ� ������(cart : ��ٱ��ϸ� ���� ����, direct : �ٷ� ����)
		String idxs = request.getParameter("idxs");		// ��ٱ��Ϸ� ���Ž� ������ ��ǰ(��)�� īƮ�ε���(cl_idx) ��ȣ(��)�� ��ǥ�� ���е�
		String ismember = request.getParameter("ismember");		
		if(request.getParameter("ismember") == null)	ismember = "n";
		ArrayList<CartInfo> pdtList = new ArrayList<CartInfo>();		// �����Ϸ��� ��ǰ(��)�� ���� ArrayList
		System.out.println("OrdFormAction");
		String where = "";
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		
		if (kind.equals("cart")) {	// ��ٱ��ϸ� ���� ������ ���
			String[] arrIdxs = idxs.split(",");
			for (int i = 0 ; i < arrIdxs.length ; i++) {
				where += " or c.cl_idx = " + arrIdxs[i];
			}
			where = " and (" + where.substring(4) + ") ";
			
			if (loginMember == null) {	// ��ȸ���̸�
				where += " and c.cl_buyer = '" + session.getId() + "' and c.cl_ismember = 'n' ";
			} else {	// ȸ���̸�
				where += " and c.cl_buyer = '" + loginMember.getMlid() + "' and c.cl_ismember = 'y' ";
			}
			
		}
		OrdFormSvc ordFormSvc = new OrdFormSvc();
		
		if(loginMember != null) {
			MembeViewSvc membeViewSvc = new MembeViewSvc();
			AddrInfo addrInfo = membeViewSvc.getBasicAddr(loginMember.getMlid());

			request.setAttribute("addrInfo", addrInfo);
			
		}
		pdtList = ordFormSvc.getOrdFrmPdtList(kind, where);
		if(pdtList != null) {
			System.out.println("OrdFormAction a");
		}
		request.setAttribute("pdtList", pdtList);
		ActionForward forward = new ActionForward();
		forward.setPath("/order/order_form.jsp?ismember="+ismember);	
		return forward;
	}
}
