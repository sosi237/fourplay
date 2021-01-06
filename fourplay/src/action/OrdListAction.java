package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class OrdListAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//System.out.println("OrdListAction");
		ActionForward forward = new ActionForward();
		ArrayList<OrdListInfo> ordList = new ArrayList<OrdListInfo>();
		request.setCharacterEncoding("utf-8");
		int cpage = 1, pcnt, spage, epage, rcnt, bsize = 10, psize = 12;
		// 페이징에 필요한 값들을 저장할 변수 선언 및 초기화
		if (request.getParameter("cpage") != null)			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)			psize = Integer.parseInt(request.getParameter("psize"));
		
		String buyer ="";
		//buyer = (String)request.getAttribute("id");
		HttpSession session = request.getSession();	
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		
		if(loginMember != null) {	//회원인 경우
			buyer = loginMember.getMlid();
			OrdListSvc ordListSvc = new OrdListSvc();
			rcnt = ordListSvc.getOrdCount(buyer);	// 주문의 총 개수(페이지 개수를 구하기 위해 필요)
			ordList = ordListSvc.getOrdList(buyer, cpage, psize);
			//페이징
			pcnt = rcnt / psize;
			if (rcnt % psize > 0)	pcnt++;				// 전체 페이지수 구함
			spage = (cpage - 1) / psize * psize + 1;	// 블록 시작페이지 번호
			epage = spage + psize - 1;
			if (epage > pcnt)	epage = pcnt;			// 블록 종료페이지 번호
			OrdPageInfo ordPageInfo = new OrdPageInfo();	// 페이징에 필요한 정보를 담을 인스턴스 생성
			ordPageInfo.setCpage(cpage);		// 현재 페이지 번호
			ordPageInfo.setPcnt(pcnt);			// 전체 페이지 개수
			ordPageInfo.setSpage(spage);		// 블록 시작페이지 번호
			ordPageInfo.setEpage(epage);		// 블록 종료페이지 번호
			ordPageInfo.setRcnt(rcnt);			// 전체 상품(레코드) 개수
			ordPageInfo.setBsize(bsize);		// 블록내 페이지 개수
			ordPageInfo.setPsize(psize);		// 페이지내 상품 개수
			request.setAttribute("ordList", ordList);
			request.setAttribute("ordPageInfo", ordPageInfo);
			forward.setPath("/member/order_list.jsp");
		} else	{					//비회원인 경우
			buyer = session.getId();	
			String bname = request.getParameter("bname"); 
			String olid = request.getParameter("olid");
			String where = " and ol_bname = '" + bname + "' ";
			OrdListSvc ordListSvc = new OrdListSvc();
			OrdListInfo ordInfo = ordListSvc.getOrd(olid, where);
			request.setAttribute("ordInfo", ordInfo);
			forward.setPath("/member/non_order_list.jsp");
		}
		return forward;
	}
}
