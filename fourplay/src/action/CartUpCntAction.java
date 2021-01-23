package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartUpCntAction implements Action {
	// ��ٱ��Ͽ� Ư�� ��ǰ�� �ɼ��� �����ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String idx = request.getParameter("idx");	// ��ٱ��� ���̵�
		String cnt = request.getParameter("cnt");	// ������ ��ǰ�� ����
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		String buyer, isMember = "n";	// ȸ�����ο� ������ ���̵�� where������ �������� ����
		if (loginMember == null) {	// ��ȸ���̸�
			buyer = session.getId();
		} else {	// ȸ���� ���
			buyer = loginMember.getMlid();
			isMember = "y";
		}

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		CartUpCntSvc cartUpCntSvc = new CartUpCntSvc();
		int result = cartUpCntSvc.cartCntUpdate(idx, cnt, buyer, isMember);
		out.println(result);
		out.close();

		return null;
	}
}
