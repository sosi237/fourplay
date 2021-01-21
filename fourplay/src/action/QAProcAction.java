package action;

import java.io.PrintWriter;	// ��� ���н� �ڹٽ�ũ��Ʈ ����� ���� import
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
import svc.*;
import vo.*;

public class QAProcAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("QAProcAction");
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");
		
		QAInfo qaInfo = new QAInfo();
		
		if (wtype.equals("in") || wtype.equals("up")) {
			qaInfo.setQl_writer(request.getParameter("writer"));
			qaInfo.setQl_title(request.getParameter("title"));
			qaInfo.setQl_content(request.getParameter("content"));
		}

		if (wtype.equals("del") || wtype.equals("up")) {
			int idx = Integer.parseInt(request.getParameter("idx"));	//������ 0
			qaInfo.setQl_idx(idx);
		}

		QAProcSvc qaProcSvc = new QAProcSvc();
		boolean isSuccess = false;	// ����(���, ����, ����) �������θ� ������ ����
		String link = null;			// �۾�(���, ����, ����) �� �̵��� URL�� ������ ����
		
		if (wtype.equals("in")) {
			if (loginMember != null) {	// ȸ�� �۵���� ���
				qaInfo.setQl_writer(loginMember.getMlid());	// �α����� ���̵� �ۼ��ڷ� ����
			} else {
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('ȸ�� ���� ����Դϴ�.');");
				out.println("location.href='../login_form.jsp';");
				out.println("</script>");
				out.close();
			}
			qaInfo.setQl_ip(request.getRemoteAddr());	// ����� IP�ּ� ����
			int idx = qaProcSvc.qaInsert(qaInfo);
			link = "brd_view.qna?idx=" + idx;

		} else if (wtype.equals("up")) {
			isSuccess = qaProcSvc.qaUpdate(qaInfo);
			int cpage = Integer.parseInt(request.getParameter("cpage"));
			String args = "?cpage=" + cpage;
			String schtype = request.getParameter("schtype");
			String keyword = request.getParameter("keyword");
			if (keyword != null && !keyword.equals("")) {
				args += "&schtype=" + schtype + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}
			link = "brd_view.qna" + args + "&idx=" + qaInfo.getQl_idx();

		} else {
			if (loginMember != null) {
				qaInfo.setQl_writer(loginMember.getMlid());
			} else if (adminMember != null) {
				qaInfo.setQl_writer(adminMember.getAl_id());
			}
			isSuccess = qaProcSvc.qaDelete(qaInfo);
			if (!isSuccess) {	// ���� ���н�
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('�߸��� ��η� �����̽��ϴ�.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
			link = "brd_list.qna";
		}

		if (isSuccess) {	// ������
			forward = new ActionForward();
			forward.setRedirect(true);	// �̵������ redirect�� �ϰڴٴ� �ǹ�
			forward.setPath(link);
		}

		return forward;
	}
}
