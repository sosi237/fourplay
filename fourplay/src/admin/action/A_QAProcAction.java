package admin.action;

import java.io.PrintWriter;	// ��� ���н� �ڹٽ�ũ��Ʈ ����� ���� import
import javax.servlet.*;
import javax.servlet.http.*;

import java.net.*;
import admin.svc.*;
import vo.*;

public class A_QAProcAction implements A_Action {
// �Խñ� ��� ó�� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("A_QAProcAction");
		ActionForward forward = null;
		HttpSession session = request.getSession();
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		// ��Ͻ� �ۼ���, ����/���� �� ���� üũ������ �α��� ������ ����
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");
		QAInfo qaInfo = new QAInfo();
		// ����ڰ� �Է��� ������(�Խñ�)���� ������ �ν��Ͻ�
		int idx = Integer.parseInt(request.getParameter("idx"));

		if (wtype.equals("in") || wtype.equals("up")) {
		// �۵���̳� ������ ��� �Խñ� �����͵��� �޾ƿ�
			qaInfo.setQl_writer(adminMember.getAl_id());
			qaInfo.setQl_title(request.getParameter("title"));
			qaInfo.setQl_content(request.getParameter("content"));
			// �޾� �� �����͵��� �ϳ��� �Խù��� �����ϴ� freeInfo �ν��Ͻ��� setter�� �̿��Ͽ� ����
		}
		
		if (wtype.equals("del") || wtype.equals("up")) {
		// �ۻ����� ������ ��� �Խñ� ��ȣ�� �޾ƿ�
			qaInfo.setQl_idx(idx);
		}

		A_QAProcSvc aqaProcSvc = new A_QAProcSvc();
		// ����Ͻ� ������ ó���� ���� Ŭ������ �ν��Ͻ� ����
		boolean isSuccess = false;	// ����(���, ����, ����) �������θ� ������ ����
		String link = null;			// �۾�(���, ����, ����) �� �̵��� URL�� ������ ����

		if (wtype.equals("in")) {	
			//qaInfo.setQl_ip(request.getRemoteAddr());	// ����� IP�ּ� ����
			isSuccess = aqaProcSvc.aqaInsert(qaInfo);
			link = "brd_list.aqa";

		} else if (wtype.equals("up")) {
			isSuccess = aqaProcSvc.aqaUpdate(qaInfo);
			int cpage = Integer.parseInt(request.getParameter("cpage"));
			String args = "?cpage=" + cpage;
			String schtype = request.getParameter("schtype");
			String keyword = request.getParameter("keyword");
			if (keyword != null && !keyword.equals("")) {
				args += "&schtype=" + schtype + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}
			link = "brd_view.aqa" + args + "&idx=" + qaInfo.getQl_idx();

		} else {
			if (adminMember != null ) {
				qaInfo.setQl_writer(adminMember.getAl_id());
			}
			isSuccess = aqaProcSvc.aqaDelete(idx, qaInfo);
			if (!isSuccess) {	// ���� ���н�
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('�߸��� �����Դϴ�.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
			link = "brd_list.aqa";
		}

		if (isSuccess) {	// ������
			forward = new ActionForward();
			forward.setRedirect(true);	// �̵������ redirect�� �ϰڴٴ� �ǹ�
			forward.setPath(link);
		}

		return forward;
	}
}
