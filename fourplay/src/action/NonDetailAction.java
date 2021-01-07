package action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import svc.*;
import vo.*;

public class NonDetailAction implements Action{	// �α��� ���ο� ������� ��ȸ���� �ֹ� �󼼳����� ������ �����ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		OrdListInfo detailInfo = new OrdListInfo();
		request.setCharacterEncoding("utf-8");
		String olid = request.getParameter("olid");
		String bname = request.getParameter("bname");
		String where = "";
		HttpSession session = request.getSession();
		System.out.println("nondetailaction");
		where += " and ol_ismember = 'n' and ol_bname ='" +bname+ "' ";
		OrdListSvc ordListSvc = new OrdListSvc();
		detailInfo = ordListSvc.getOrd(olid, where);
		request.setAttribute("detailInfo", detailInfo);
		forward.setPath("/member/order_detail.jsp");
			
		return forward;
	}
}
