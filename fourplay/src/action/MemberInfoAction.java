package action;

import java.io.PrintWriter;
import javax.servlet.http.*;	
import java.util.*;
import svc.*;
import vo.*;

public class MemberInfoAction implements Action {
// ȸ������ ȭ������ �̵��ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();

		System.out.println("MemberInfoAction ");
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		
		if(loginMember != null) {	// �α��ε� ���¸�
			String uid = loginMember.getMlid();
			MembeViewSvc membeViewSvc = new MembeViewSvc();
			AddrInfo addr = membeViewSvc.getBasicAddr(uid);
			System.out.println("MemberInfoAction finished");
			request.setAttribute("loginMember", loginMember);
			request.setAttribute("addr", addr);
			forward.setPath("member/member_info.jsp");
		} 
		
		return forward;
	}
}
