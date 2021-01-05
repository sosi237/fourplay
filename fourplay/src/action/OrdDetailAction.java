package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class OrdDetailAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//System.out.println("OrdDetailAction");
		
		OrdListInfo ordInfo = new OrdListInfo();
		ArrayList<OrdDetailInfo> ordDetailList = new ArrayList<OrdDetailInfo>();
		request.setCharacterEncoding("utf-8");
		
		String olid = request.getParameter("olid");
		String where = "";
		HttpSession session = request.getSession();	
		
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		
		if(loginMember != null) {
			where += " and ol_buyer ='"+ loginMember.getMlid() +"' ";
		}
		
		OrdListSvc ordListSvc = new OrdListSvc();
		ordInfo = ordListSvc.getOrd(olid, where);
		
		OrdDetailSvc ordDetailSvc = new OrdDetailSvc();
		ordDetailList = ordDetailSvc.getOrdDetail(olid);
		
		request.setAttribute("ordInfo", ordInfo);
		request.setAttribute("ordDetailList", ordDetailList);
		
		System.out.println("OrdDetailAction");
		ActionForward forward = new ActionForward();
		forward.setPath("/member/order_detail.jsp");
		return forward;
	}
}
