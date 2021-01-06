package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class OrdDetailAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//System.out.println("OrdDetailAction");
		ActionForward forward = new ActionForward();
		OrdListInfo ordInfo = new OrdListInfo();
		request.setCharacterEncoding("utf-8");
		String olid = request.getParameter("olid");
		String where = "";
		HttpSession session = request.getSession();	
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if(loginMember != null) {
			where += " and ol_buyer ='"+ loginMember.getMlid() +"' ";
		}
		forward.setPath("/member/order_detail.jsp");
		OrdListSvc ordListSvc = new OrdListSvc();
		ordInfo = ordListSvc.getOrd(olid, where);
		request.setAttribute("ordInfo", ordInfo);
		return forward;
	}
}
