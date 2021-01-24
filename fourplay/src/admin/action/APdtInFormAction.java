package admin.action;

import javax.servlet.http.*;	// �޾� �� request�� response�� ���� import
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class APdtInFormAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		APdtInFormSvc pdtInFormSvc = new APdtInFormSvc();
		// ��з�, �Һз�, �귣�� ����� �������� ���� SvcŬ����
		ArrayList<CataBigInfo> cataBigList = pdtInFormSvc.getCataBigList();			// ��з� ���
		ArrayList<CataSmallInfo> cataSmallList = pdtInFormSvc.getCataSmallList();	// �Һз� ���

		if (cataBigList != null && cataSmallList != null) {
			request.setAttribute("cataBigList", cataBigList);
			request.setAttribute("cataSmallList", cataSmallList);
			// ��� ������ ������ �з���� �귣�� ����� request��ü�� �Ӽ����� ����
		} else {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�߸��� ��η� �����̽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		forward.setPath("/admin/product/product_in_form.jsp");	// �̵��� URL ����
		return forward;
	}
}