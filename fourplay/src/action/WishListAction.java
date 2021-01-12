package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class WishListAction implements Action {
	// ��ٱ��Ͽ��� ������ �ش� ������� ��ٱ��� ����� �������� Ŭ����
			public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
				request.setCharacterEncoding("utf-8");
				ArrayList<CartInfo> wishList = new ArrayList<CartInfo>();

				String where = "";
				HttpSession session = request.getSession();
				MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
				where = " and w.ml_id = '" + loginMember.getMlid();

				WishListSvc wishListSvc = new WishListSvc();
				wishList = wishListSvc.getWishList(where);
				request.setAttribute("wishList", wishList);
				
				ActionForward forward = new ActionForward();
				forward.setPath("/member/wish_list.jsp");	// �̵��� URL ����
				return forward;
			}
	}