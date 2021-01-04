package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class OrdListAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("OrdListAction");
		
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
			buyer = loginMember.getMl_id();
		} else	buyer = "sungda123";	//비회원인 경우(test용으로 성다 넣음)
		System.out.println(buyer);
		
		OrdListSvc ordListSvc = new OrdListSvc();
		rcnt = ordListSvc.getOrdCount(buyer);	// 주문의 총 개수(페이지 개수를 구하기 위해 필요)
		ordList = ordListSvc.getOrdList(buyer, cpage, psize);
		// 현 페이지에서 보여줄 주문내역
		// 검색조건, 정렬조건, limit에서 사용할 값을 구하기 위해 현재페이지와 페이지크기를 인수로 가져감
		
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
		
		ActionForward forward = new ActionForward();
		forward.setPath("/member/order_list.jsp");
		return forward;
	}
}
