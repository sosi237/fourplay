package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import admin.svc.*;
import vo.*;

public class A_QAFormAction implements A_Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");
		ActionForward forward = new ActionForward();

		if (wtype.equals("up")) {
			HttpSession session = request.getSession();
			AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
			String uid = null;
			if (adminMember != null)	uid = adminMember.getAl_id();

			int idx = Integer.parseInt(request.getParameter("idx"));
			
			A_QAFormSvc aqaFormSvc = new A_QAFormSvc();
			QAInfo article = aqaFormSvc.getArticleUp(idx, uid);
			if (article != null) 	request.setAttribute("article", article);		
		}	

		forward.setPath("/admin/bbs/a_aqa_form.jsp");
		return forward;
	}
}
