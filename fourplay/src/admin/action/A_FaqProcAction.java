package admin.action;

import java.io.PrintWriter;	// ��� ���н� �ڹٽ�ũ��Ʈ ����� ���� import
import javax.servlet.*;
import javax.servlet.http.*;

import java.net.*;
import admin.svc.*;
import vo.*;

public class A_FaqProcAction implements A_Action {
// �Խñ� ��� ó�� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("A_FaqProcAction");
		ActionForward forward = null;
		HttpSession session = request.getSession();
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		// ��Ͻ� �ۼ���, ����/���� �� ���� üũ������ �α��� ������ ����
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");
		FaqInfo faqInfo = new FaqInfo();
		// ����ڰ� �Է��� ������(�Խñ�)���� ������ �ν��Ͻ�
		int idx = Integer.parseInt(request.getParameter("idx"));

		if (wtype.equals("in") || wtype.equals("up")) {
		// �۵���̳� ������ ��� �Խñ� �����͵��� �޾ƿ�
			faqInfo.setFq_writer(adminMember.getAl_id());
			faqInfo.setFq_title(request.getParameter("title"));
			faqInfo.setFq_content(request.getParameter("content"));
			// �޾� �� �����͵��� �ϳ��� �Խù��� �����ϴ� freeInfo �ν��Ͻ��� setter�� �̿��Ͽ� ����
		}
		
		if (wtype.equals("del") || wtype.equals("up")) {
		// �ۻ����� ������ ��� �Խñ� ��ȣ�� �޾ƿ�
			faqInfo.setFq_idx(idx);
		}

		A_FaqProcSvc afaqProcSvc = new A_FaqProcSvc();
		// ����Ͻ� ������ ó���� ���� Ŭ������ �ν��Ͻ� ����
		boolean isSuccess = false;	// ����(���, ����, ����) �������θ� ������ ����
		String link = null;			// �۾�(���, ����, ����) �� �̵��� URL�� ������ ����

		if (wtype.equals("in")) {
			faqInfo.setFq_status("a");
			
			//faqInfo.setFq_ip(request.getRemoteAddr());	// ����� IP�ּ� ����
			isSuccess = afaqProcSvc.afaqInsert(faqInfo);
			link = "bbs_list.afaq";

		} else if (wtype.equals("up")) {
			isSuccess = afaqProcSvc.afaqUpdate(faqInfo);
			int cpage = Integer.parseInt(request.getParameter("cpage"));
			String args = "?cpage=" + cpage;
			String schtype = request.getParameter("schtype");
			String keyword = request.getParameter("keyword");
			if (keyword != null && !keyword.equals("")) {
				args += "&schtype=" + schtype + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}
			link = "bbs_view.afaq" + args + "&idx=" + faqInfo.getFq_idx();

		} else {
			if (adminMember != null ) {
				faqInfo.setFq_writer(adminMember.getAl_id());
			}
			isSuccess = afaqProcSvc.afaqDelete(idx, faqInfo);
			if (!isSuccess) {	// ���� ���н�
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('�߸��� �����Դϴ�.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
			link = "bbs_list.afaq";
		}

		if (isSuccess) {	// ������
			forward = new ActionForward();
			forward.setRedirect(true);	// �̵������ redirect�� �ϰڴٴ� �ǹ�
			forward.setPath(link);
		}

		return forward;
	}
}
