package action;

import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;	
import svc.*;
import vo.*;

public class MembeViewAction implements Action {
// ȸ������ ȭ������ �̵��ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		
		if(loginMember != null) {	// �α��ε� ���¸�
			String uid = loginMember.getMlid();
			MembeViewSvc membeViewSvc = new MembeViewSvc();
			AddrInfo addr = membeViewSvc.getBasicAddr(uid);
			System.out.println("MembeViewAction finished");
			
			request.setAttribute("loginMember", loginMember);
			request.setAttribute("addr", addr);
			forward.setPath("member/member_info.jsp");
		} else {	// �α����� Ǯ������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���� ������ �����ϴ�.\n�ٽ� �α����ϼ���.');");
			out.println("location.replace('login_form.jsp');");
			out.println("</script>");
			out.close();
		}
		return forward;
	}
}
