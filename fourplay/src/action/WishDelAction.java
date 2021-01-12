package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class WishDelAction implements Action {
	// ��ٱ��Ͽ� ������ ��ǰ������ �����ϴ� Ŭ����
		public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			request.setCharacterEncoding("utf-8");
			String id = request.getParameter("id");		// ���ø���Ʈ ���̵�(��)
			
			String buyer;
			HttpSession session = request.getSession();
			MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
			buyer = loginMember.getMlid();


			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			
			WishDelSvc wishDelSvc = new WishDelSvc();
			int result = wishDelSvc.wishDelete(id, buyer);
			out.println(result);
			out.close();
			return null; 
		}
	}
