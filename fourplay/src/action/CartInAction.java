package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartInAction implements Action {
// ��ٱ��Ͽ� ������ ��ǰ������ �����ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String plid = request.getParameter("id");		// ��ǰ���̵�
		String cnt = request.getParameter("cnt");		// ���� ����
		String price = request.getParameter("price");	// �Ǳ��Ű�
		String buyNow = request.getParameter("buyNow"); // �ٷα��ŷ� ���Դ��� Ȯ��
		String now = request.getParameter("now");
		String idxs = request.getParameter("idxs");	
		String args = "?now=" + now;
		if(request.getParameter("args") != null && request.getParameter("args").equals("")) {
			args = request.getParameter("args") + "&now=" + now;
		}
		int optCnt = 0;
		String optValue = "";
		if(buyNow !=null && buyNow.equals("y")) { // ��ǰ �����Ͽ��� �ٷα��ŷ� ��� ������
			optCnt = Integer.parseInt(request.getParameter("optCnt"));
			optValue = request.getParameter("optValue");
		} else {	// ��ٱ��� �߰��� ��������
			if (request.getParameter("optCnt") != null) {
				optCnt = Integer.parseInt(request.getParameter("optCnt"));	// �ɼ��� ����
				for (int i = 0 ; i < optCnt ; i++) {
					optValue += "," + request.getParameter("opt" + i);
				}
				optValue = optValue.substring(1);
			}
		}

		String buyer, isMember = "n";
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if (loginMember == null) {	// ��ȸ���̸�
			buyer = session.getId();
		} else {	// ȸ���� ���
			buyer = loginMember.getMlid();
			isMember = "y";
		}
		
		CartInfo cart = new CartInfo();
		cart.setCl_buyer(buyer);
		cart.setCl_ismember(isMember);
		cart.setPl_id(plid);
		cart.setCl_opt(optValue);
		cart.setCl_cnt(Integer.parseInt(cnt));
		
		CartInSvc cartInSvc = new CartInSvc();
		int result = cartInSvc.cartInsert(cart);
		if (result == 0) {	// īƮ��Ͽ� ����������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('����� �ʰ��Ͽ� ��ٱ��Ͽ� ��Ⱑ �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		ActionForward forward = new ActionForward();
		forward.setPath("cart_list.crt" + args);	// �̵��� URL ����			
		forward.setRedirect(true);
		return forward;
	}
}
