package action;

import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;	
import svc.*;
import vo.*;

public class MyPageAction implements Action {
// ���������� ȭ������ �̵��ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
				
		
		if (loginMember != null) {
			request.setAttribute("loginMember", loginMember);
			forward.setPath("member/mypage.jsp");
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
