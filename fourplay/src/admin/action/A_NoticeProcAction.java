package admin.action;

import java.io.PrintWriter;	// ��� ���н� �ڹٽ�ũ��Ʈ ����� ���� import
import javax.servlet.*;
import javax.servlet.http.*;

import java.net.*;
import admin.svc.*;
import vo.*;

public class A_NoticeProcAction implements A_Action {
// �Խñ� ��� ó�� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("A_NoticeProcAction");
		ActionForward forward = null;
		HttpSession session = request.getSession();
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		// ��Ͻ� �ۼ���, ����/���� �� ���� üũ������ �α��� ������ ����
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");
		NoticeInfo noticeInfo = new NoticeInfo();
		// ����ڰ� �Է��� ������(�Խñ�)���� ������ �ν��Ͻ�
		int idx = Integer.parseInt(request.getParameter("idx"));

		if (wtype.equals("in") || wtype.equals("up")) {
		// �۵���̳� ������ ��� �Խñ� �����͵��� �޾ƿ�
			noticeInfo.setNl_writer(adminMember.getAl_id());
			noticeInfo.setNl_title(request.getParameter("title"));
			noticeInfo.setNl_content(request.getParameter("content"));
			// �޾� �� �����͵��� �ϳ��� �Խù��� �����ϴ� freeInfo �ν��Ͻ��� setter�� �̿��Ͽ� ����
		}
		
		if (wtype.equals("del") || wtype.equals("up")) {
		// �ۻ����� ������ ��� �Խñ� ��ȣ�� �޾ƿ�
			noticeInfo.setNl_idx(idx);
		}

		A_NoticeProcSvc anoticeProcSvc = new A_NoticeProcSvc();
		// ����Ͻ� ������ ó���� ���� Ŭ������ �ν��Ͻ� ����
		boolean isSuccess = false;	// ����(���, ����, ����) �������θ� ������ ����
		String link = null;			// �۾�(���, ����, ����) �� �̵��� URL�� ������ ����

		if (wtype.equals("in")) {
			noticeInfo.setNl_status("a");	// �ϴ� �̰Խ� �۷� ����
	
			//noticeInfo.setNl_ip(request.getRemoteAddr());	// ����� IP�ּ� ����
			isSuccess = anoticeProcSvc.anoticeInsert(noticeInfo);
			link = "bbs_list.anotice";

		} else if (wtype.equals("up")) {
			isSuccess = anoticeProcSvc.anoticeUpdate(noticeInfo);
			int cpage = Integer.parseInt(request.getParameter("cpage"));
			String args = "?cpage=" + cpage;
			String schtype = request.getParameter("schtype");
			String keyword = request.getParameter("keyword");
			if (keyword != null && !keyword.equals("")) {
				args += "&schtype=" + schtype + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}
			link = "bbs_view.anotice" + args + "&idx=" + noticeInfo.getNl_idx();

		} else {
			if (adminMember != null ) {
				noticeInfo.setNl_writer(adminMember.getAl_id());
			}
			isSuccess = anoticeProcSvc.anoticeDelete(idx, noticeInfo);
			if (!isSuccess) {	// ���� ���н�
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('�߸��� �����Դϴ�.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
			link = "bbs_list.anotice";
		}

		if (isSuccess) {	// ������
			forward = new ActionForward();
			forward.setRedirect(true);	// �̵������ redirect�� �ϰڴٴ� �ǹ�
			forward.setPath(link);
		}

		return forward;
	}
}
