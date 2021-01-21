package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class ReviewFormAction implements Action {
// ���� ���/���� ������ �����ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		String wtype = request.getParameter("wtype");
		
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		
		if(loginMember != null) {
			String uid = loginMember.getMlid();
			String plid = request.getParameter("plid");
			
			ReviewInfo review = new ReviewInfo(); 
			ReviewFormSvc reviewFormSvc = new ReviewFormSvc();
			if(wtype.equals("up")) {	//�����̸�
				int idx = Integer.parseInt(request.getParameter("idx"));
//				review = reviewFormSvc.getReview(idx);
//				request.setAttribute("review", review);
				forward.setPath("/product/review_form.jsp?idx="+idx);
			}
			forward.setPath("/product/review_form.jsp");
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('ȸ�� ���� ����Դϴ�.\n�α��� ���ּ���.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		return forward;
	}
}
