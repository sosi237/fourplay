package admin.action;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import admin.svc.*;
import svc.OrdListSvc;
import vo.*;

public class AOrdListAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AOrdListAction");
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		ArrayList<OrdListInfo> ordList = new ArrayList<OrdListInfo>();
		
		int cpage = 1, pcnt, spage, epage, rcnt, bsize = 10, psize = 12;		// 페이징에 필요한 값들을 저장할 변수 선언 및 초기화
		if (request.getParameter("cpage") != null)			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)			psize = Integer.parseInt(request.getParameter("psize"));

		HttpSession session = request.getSession();
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		
		if(adminMember != null) {	// 관리자 계정으로 로그인되어있으면
			AOrdListSvc aOrdListSvc = new AOrdListSvc();
			String status, schtype, keyword;
			status	= request.getParameter("status");	// 주문상태
			schtype = request.getParameter("schtype");	// 주문상태, 검색조건, 키워드(주문자 아이디, 주문번호)
			keyword = request.getParameter("keyword");	// 검색어
			String ord = request.getParameter("ord");	// 정렬조건으로 주문자 아이디(오a내d), 주문번호(오a내d), 주문일date(오a내d), 계정사용여부(오a내d)
			
			String where = "", orderby = " order by ol_date desc ";
			if (keyword != null && !keyword.equals(""))	{
				where += " and ol_" + schtype + " like '%" + keyword + "%' ";
			}
			if(status != null && !status.equals(""))	{
				where += " and ol_status ='" + status + "' ";
			}
			if (ord != null && !ord.equals("")) {
				orderby = " order by ol_" + ord.substring(0, ord.length() - 1) + 
				(ord.substring(ord.length() - 1).equals("d") ? " desc" : " asc");
			}	// 정렬값 : buyera, buyerd, ida, idd, datea, dated, ol_statusa, ol_statusd
			
			if(!where.equals("") && where.substring(0,4).equals(" and")) {
				where = " where " + where.substring(4);
			}
			
			rcnt = aOrdListSvc.getOrdCount(where);	// 페이징을 위해 전체 주문내역 수를 받아옴
			ordList = aOrdListSvc.getOrdList(where, orderby, cpage, psize);	// 전체 주문목록을 받아옴
			
			pcnt = rcnt / psize;
			if (rcnt % psize > 0)	pcnt++;				// 전체 페이지수
			spage = (cpage - 1) / psize * psize + 1;	// 블록 시작페이지 번호
			epage = spage + psize - 1;
			if (epage > pcnt)	epage = pcnt;			// 블록 종료페이지 번호
			
			OrdPageInfo pageInfo = new OrdPageInfo();	// 페이징에 필요한 정보를 담을 인스턴스 생성
			pageInfo.setCpage(cpage);		// 현재 페이지 번호
			pageInfo.setPcnt(pcnt);			// 전체 페이지 개수
			pageInfo.setSpage(spage);		// 블록 시작페이지 번호
			pageInfo.setEpage(epage);		// 블록 종료페이지 번호
			pageInfo.setRcnt(rcnt);			// 전체 주문(레코드) 개수
			pageInfo.setBsize(bsize);		// 블록내 페이지 개수
			pageInfo.setPsize(psize);		// 페이지내 주문 개수
			pageInfo.setSchtype(schtype);
			pageInfo.setKeyword(keyword);
			pageInfo.setStatus(status);
			pageInfo.setOrd(ord);			// 정렬조건
			
			request.setAttribute("ordList", ordList);
			request.setAttribute("pageInfo", pageInfo);
			forward.setPath("order/a_order_list.jsp");
		} else {	// 로그인해야만 들어올 수 있으므로
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('잘못된 경로로 들어오셨습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		return forward;
	}
}
