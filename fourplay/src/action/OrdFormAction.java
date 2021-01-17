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
		ArrayList<CartInfo> pdtList = new ArrayList<CartInfo>();		// �����Ϸ��� ��ǰ(��)�� ���� ArrayList
		
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
			
		} else {	// �ٷ� ������ ���
			
		}

		OrdFormSvc ordFormSvc = new OrdFormSvc();
		if(loginMember != null) {
			MemberInfo addrInfo = new MemberInfo();
			addrInfo = ordFormSvc.getaddr(loginMember.getMlid());

			request.setAttribute("addrInfo", addrInfo);
			
		}
		
		pdtList = ordFormSvc.getOrdFrmPdtList(kind, where);
		request.setAttribute("pdtList", pdtList);
		ActionForward forward = new ActionForward();
		forward.setPath("/order/order_form.jsp");	
		return forward;
	}
}