package action;

import javax.servlet.http.*;	
import java.io.PrintWriter;
import svc.*;
import vo.*;

public class QAFormAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");	// ���(in) / ����(up) ����
		ActionForward forward = new ActionForward();

		if (wtype.equals("up")) {//�����̸�
			HttpSession session = request.getSession();
			MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
			String uid = null;
			if (loginMember != null)	uid = loginMember.getMlid();
			int idx = Integer.parseInt(request.getParameter("idx"));

			QAViewSvc qaViewSvc = new QAViewSvc();
			QAInfo article = qaViewSvc.getArticle(idx);
			request.setAttribute("article", article);
			forward.setPath("/customer/qna_form.jsp?idx="+idx);
			
		} else {	//����̸�
			forward.setPath("/customer/qna_form.jsp");	
		}
		
		return forward;
	}
}
