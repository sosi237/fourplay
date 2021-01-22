package action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import svc.*;
import vo.*;

public class ReviewProcAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		ReviewProcSvc reviewProcSvc = new ReviewProcSvc();
		request.setCharacterEncoding("utf-8");
System.out.println("ReviewProcAction");
		String uploadPath = "C:/Users/S/git/fourplay/fourplay/WebContent/product/r_img";
		int maxSize = 5 * 1024 * 1024;		// ���ε� �ִ� �뷮���� 5MB�� ����
		String wtype = "", plid = "", uid = "", title = "", content = "", reviewImg = "", olid = "";
		int odidx;
		int rRate = 5; 
		MultipartRequest multi = new MultipartRequest(
			request, 	// request��ü�� multi�� �����͵��� �ޱ� ����
			uploadPath, // ������ ������ ������ ����� ��ġ ����
			maxSize, 	// �� ���� ���ε��� �� �ִ� �ִ�ũ��(byte����)
			"utf-8", 	// ������ ���ڵ� ���(utf-8, euc-kr, ksc-5601 ��)
			new DefaultFileRenamePolicy());	// ���� �̸��� �ߺ� ó��
		
		wtype = multi.getParameter("wtype");
		plid = multi.getParameter("plid");
		uid = multi.getParameter("uid");
		olid = multi.getParameter("olid");
		odidx = Integer.parseInt(multi.getParameter("odidx"));
		title = multi.getParameter("title");
		rRate = Integer.parseInt(multi.getParameter("rRate"));
		content = multi.getParameter("content");
		reviewImg = multi.getFilesystemName("reviewImg");
		if(reviewImg == null)				reviewImg = "";

		ReviewInfo review = new ReviewInfo();
		review.setMl_id(uid);				review.setPl_id(plid);
		review.setRl_content(content);		review.setRl_img(reviewImg);
		review.setRl_rate(rRate);			review.setRl_title(title);
		review.setOdidx(odidx);
		boolean isSuccess = reviewProcSvc.reviewInsert(review, olid);
		if (isSuccess) {	// �����Ͽ� ����������
			forward.setPath("review_view.jsp?plid="+plid);
			forward.setRedirect(true);
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���� ����� �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		return forward;
	}
}
