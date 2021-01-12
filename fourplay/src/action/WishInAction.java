package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class WishInAction implements Action {
	// 장바구니에 선택한 상품정보를 저장하는 클래스
		public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			request.setCharacterEncoding("utf-8");
			String plid = request.getParameter("id");		// 상품아이디
			String price = request.getParameter("price");	// 실구매가
			String optValue = "";

			String buyer, isMember = "n";
			HttpSession session = request.getSession();
			MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
			if (loginMember == null) {	// 비회원이면
				buyer = session.getId();
			} else {	// 회원일 경우
				buyer = loginMember.getMlid();
				isMember = "y";
			}
			
			CartInfo cart = new CartInfo();
			cart.setMl_id(buyer);
			cart.setPl_id(plid);
			
			WishInSvc wishInSvc = new WishInSvc();
			int result = wishInSvc.wishInsert(cart);
			if (result == 0) {	// 위시리스트 등록에 실패했으면
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('상품이 위시리스트에 존재합니다.');");
				out.println("location.href='wish_list.crt'");
				out.println("</script>");
				out.close();
			}

			ActionForward forward = new ActionForward();
			forward.setPath("wish_list.crt" + request.getParameter("args"));	// 이동할 URL 지정
			forward.setRedirect(true);
			return forward;
		}
	}