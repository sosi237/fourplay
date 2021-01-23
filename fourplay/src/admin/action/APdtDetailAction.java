package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class APdtDetailAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		APdtDetailSvc pdtViewSvc = new APdtDetailSvc();
		String id = request.getParameter("id");
		PdtInfo pdtInfo = pdtViewSvc.getPdtInfo(id);
		ArrayList<PdtSizeInfo> pdtSizeList = new ArrayList<PdtSizeInfo>();
		pdtSizeList = pdtViewSvc.getPdtSizeList(id);
		// ������ ���̵� �ش��ϴ� ��ǰ�� �������� PdtInfo�� �ν��Ͻ��� �޾� ��
		request.setAttribute("pdtInfo", pdtInfo);
		request.setAttribute("pdtSizeList", pdtSizeList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/product_detail.jsp");

		return forward;
	}
}
