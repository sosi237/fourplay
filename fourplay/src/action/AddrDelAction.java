package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class AddrDelAction implements Action {
// �ּҷϿ��� ������ �ּҸ� �����ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String idx = request.getParameter("idx");
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		String uid = loginMember.getMlid();
		

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		System.out.println(idx);
		AddrtDelSvc addrtDelSvc = new AddrtDelSvc();
		int result = addrtDelSvc.addrDelete(idx, uid);
		out.println(result);
		out.close();

		return null;
	}
}
