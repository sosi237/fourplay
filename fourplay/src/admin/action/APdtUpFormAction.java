package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class APdtUpFormAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		APdtInFormSvc aPdtInFormSvc = new APdtInFormSvc();
		// ��з�, �Һз�, �귣�� ����� �������� ���� SvcŬ����
		ArrayList<CataBigInfo> cataBigList = aPdtInFormSvc.getCataBigList();			// ��з� ���
		ArrayList<CataSmallInfo> cataSmallList = aPdtInFormSvc.getCataSmallList();	// �Һз� ���
		// �з��� �귣�� ��ϵ��� ��ǰ����� PdtInFormSvc�� �̿��Ͽ� ������
		APdtDetailSvc aPdtDetailSvc = new APdtDetailSvc();
		String id = request.getParameter("id");
		PdtInfo pdtInfo = aPdtDetailSvc.getPdtInfo(id);
		// ��ǰ������ ��ǰ������ PdtViewSvc�� �̿��Ͽ� �޾� ��

		request.setAttribute("cataBigList", cataBigList);
		request.setAttribute("cataSmallList", cataSmallList);
		request.setAttribute("pdtInfo", pdtInfo);
		// ���� ������ ������ �з���� �귣�� ���, ��ǰ������ request��ü�� �Ӽ����� ����

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/product_up_form.jsp");	// �̵��� URL ����
		return forward;
	}
}
