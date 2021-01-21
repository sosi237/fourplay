package action;

import java.io.PrintWriter;
import javax.servlet.http.*;	
import java.util.*;
import svc.*;
import vo.*;

public class MemberInfoAction implements Action {
// 회원정보 화면으로 이동하는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();

		System.out.println("MemberInfoAction ");
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		
		if(loginMember != null) {	// 로그인된 상태면
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
