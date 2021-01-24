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
		
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if(loginMember != null) {	// �α��ε� ���¸�
			request.setAttribute("loginMember", loginMember);
			String uid = loginMember.getMlid();
			MemberViewSvc MemberViewSvc = new MemberViewSvc();
			AddrInfo addr = MemberViewSvc.getBasicAddr(uid);
			request.setAttribute("addr", addr);
			forward.setPath("member/member_info.jsp");
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�α����� �ʿ��մϴ�.');");
			out.println("location.replace('login_form.jsp');");
			out.println("</script>");
			out.close();
		}	
		
		return forward;
	}
}
