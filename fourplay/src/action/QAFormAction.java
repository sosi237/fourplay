package action;

import javax.servlet.http.*;	
import java.io.PrintWriter;
import svc.*;
import vo.*;

public class QAFormAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");	// 등록(in) / 수정(up) 여부
		ActionForward forward = new ActionForward();

		if (wtype.equals("up")) {
			HttpSession session = request.getSession();
			MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
			AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
			
			String uid = null, admId=null;
			if (loginMember != null)	uid = loginMember.getMlid();
			if (adminMember != null)	admId = adminMember.getAl_id();

			int idx = Integer.parseInt(request.getParameter("idx"));

			QAFormSvc qcFormSvc = new QAFormSvc();
			QAInfo article = qcFormSvc.getArticleUp(idx, uid);
			if (article != null) {
				request.setAttribute("article", article);
			} 
		}

		forward.setPath("/customer/qna_form.jsp");	// 이동할 URL 지정
		return forward;
	}
}
