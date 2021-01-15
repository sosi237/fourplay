package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdmProcAction implements action.Action {	
// 관리자 계정 정보 수정, 삭제를 처리하는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AdmProcAction");
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		AdmProcSvc admProcSvc = new AdmProcSvc();

		String wtype = request.getParameter("wtype");
		String link = "";
		boolean isSuccess = false;
	
		if(wtype.equals("up")) { // 정보 수정의 경우
			AdminInfo adminMember = new AdminInfo();
			adminMember.setAl_id(request.getParameter("aid"));
			adminMember.setAl_name(request.getParameter("name"));
			adminMember.setAl_pwd(request.getParameter("pwd"));
			adminMember.setAl_email(request.getParameter("email"));
			adminMember.setAl_phone(request.getParameter("phone"));
			isSuccess = admProcSvc.admUpdate(adminMember);
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			if (isSuccess) {	// 성공시
				HttpSession session = request.getSession();
				session.setAttribute("adminMember", adminMember);
				out.println("<script>");
				out.println("alert('정보를 수정했습니다.');");
				out.println("location.replace('admin_form.adm');");
				out.println("</script>");
				out.close();
			} else {	
				out.println("<script>");
				out.println("alert('정보 수정에 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
		} else if(wtype.equals("del")) {	//관리자 계정 삭제의 경우
			
		} else {	// 상태 변경의 경우
			String idxs = request.getParameter("idxs");	// 관리자 인덱스들
			String st = request.getParameter("st");	// 관리자 게정 상태값들
//			String[] arrIdx = idxs.split(",");
//			String[] arrSt = st.split(",");
//			String where = "";
//			
//			for(int i = 0; i < arrIdx.length; i++) {
//				where
//			}

			isSuccess = admProcSvc.chStatus(idxs, st);
			
			if(isSuccess) {		// 상태변경에 성공했다면
				forward.setRedirect(true);
				forward.setPath("admin_list.adm");
			} else {	//상태변경에 실패했다면
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('정보 수정에 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
		}
		return forward;
	}
}
