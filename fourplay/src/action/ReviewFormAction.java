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
		String inType = request.getParameter("inType");
		
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		
		if(loginMember != null) {
//			ReviewInfo review = new ReviewInfo(); 
//			ReviewFormSvc reviewFormSvc = new ReviewFormSvc();
//			String uid = loginMember.getMlid();
//			String plid = request.getParameter("plid");
			
			if(wtype.equals("up")) {	//�����̸�
				int idx = Integer.parseInt(request.getParameter("idx"));
//				review = reviewFormSvc.getReview(idx);
//				request.setAttribute("review", review);
				forward.setPath("/product/review_form.jsp?idx="+idx);
			} else {
//				if(inType.equals("pdt"))			forward.setPath("/member/order_list.mem");
				if(inType.equals("list")) {
					String plid = request.getParameter("plid");
					String plname = request.getParameter("plname");
					String olid = request.getParameter("olid");
					String odidx = request.getParameter("odidx");
					forward.setPath("/product/review_form.jsp?wtype=in&plid="+plid+"&plname="+plname+"&olid="+olid+"&odidx="+odidx);
				}
				
			}
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
