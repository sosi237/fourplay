package action;

import java.io.PrintWriter;	// 등록 실패시 자바스크립트 사용을 위한 import
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
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		
		if (wtype.equals("in") || wtype.equals("up")) {
			qaInfo.setQl_writer(request.getParameter("writer"));
			qaInfo.setQl_title(request.getParameter("title"));
			qaInfo.setQl_content(request.getParameter("content"));
		}

		if (wtype.equals("del") || wtype.equals("up")) {
			qaInfo.setQl_idx(idx);
		}

		QAProcSvc qaProcSvc = new QAProcSvc();
		boolean isSuccess = false;	// 쿼리(등록, 수정, 삭제) 성공여부를 저장할 변수
		String link = null;			// 작업(등록, 수정, 삭제) 후 이동할 URL을 저장할 변수
		
		if (wtype.equals("in")) {
			if (loginMember != null) {	// 회원 글등록일 경우
				qaInfo.setQl_writer(loginMember.getMlid());	// 로그인한 아이디를 작성자로 지정
			} else if (adminMember != null) {	// 관리자 글등록일 경우
				qaInfo.setQl_writer(adminMember.getAl_id());	// 로그인한 아이디를 작성자로 지정
			}
			qaInfo.setQl_ip(request.getRemoteAddr());	// 등록자 IP주소 지정
			isSuccess = qaProcSvc.qaInsert(qaInfo);
			link = "brd_list.qna";

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
			if (!isSuccess) {	// 삭제 실패시
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('잘못된 경로로 들어오셨습니다.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
			link = "brd_list.qna";
		}

		if (isSuccess) {	// 성공시
			forward = new ActionForward();
			forward.setRedirect(true);	// 이동방식을 redirect로 하겠다는 의미
			forward.setPath(link);
		}

		return forward;
	}
}
