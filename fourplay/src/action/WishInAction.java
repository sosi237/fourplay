package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class WishInAction implements Action {
	// ��ٱ��Ͽ� ������ ��ǰ������ �����ϴ� Ŭ����
		public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			request.setCharacterEncoding("utf-8");
			String plid = request.getParameter("id");		// ��ǰ���̵�
			String buyer = "", args = "";
			if(request.getParameter("args") != null && !request.getParameter("args").equals("")) {
				args = request.getParameter("args");
			}
			HttpSession session = request.getSession();
			MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
			buyer = loginMember.getMlid();
			CartInfo cart = new CartInfo();
			cart.setMl_id(buyer);
			cart.setPl_id(plid);
			WishInSvc wishInSvc = new WishInSvc();
			int result = wishInSvc.wishInsert(cart);
			if (result == 0) {	// ���ø���Ʈ ��Ͽ� ����������
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('��ǰ�� ���ø���Ʈ�� �����մϴ�.');");
				out.println("location.href='wish_list.crt'");
				out.println("</script>");
				out.close();
			}

			ActionForward forward = new ActionForward();
			forward.setPath("wish_list.crt");	// �̵��� URL ����
			forward.setRedirect(true);
			return forward;
		}
	}