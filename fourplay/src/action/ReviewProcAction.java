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
		String uploadPath = "E:/psy/jsp/work/fourplay/WebContent/product/r_img";
		int maxSize = 5 * 1024 * 1024;		// ���ε� �ִ� �뷮���� 5MB�� ����
		String wtype = "", plid = "", uid = "", title = "", content = "", reviewImg = "", olid = "";
		int idx = 0; 	//����/������ �ʿ��� ���� �۹�ȣ
		
		int odidx= 0, rRate = 5; 
		MultipartRequest multi = new MultipartRequest(
			request, 	
			uploadPath, 
			maxSize, 	
			"utf-8", 	
			new DefaultFileRenamePolicy());	
		
		wtype = multi.getParameter("wtype");
		plid = multi.getParameter("plid");
		uid = multi.getParameter("uid");
		olid = multi.getParameter("olid");
		title = multi.getParameter("title");
		rRate = Integer.parseInt(multi.getParameter("rRate"));
		content = multi.getParameter("content").replace("\r\n", "<br />");
		reviewImg = multi.getFilesystemName("reviewImg");
		
		if(wtype.equals("in")) {	//���� ����̸�
			odidx = Integer.parseInt(multi.getParameter("odidx"));
			if(reviewImg == null)				reviewImg = "";
	
			ReviewInfo review = new ReviewInfo();
			review.setMl_id(uid);				review.setPl_id(plid);
			review.setRl_content(content);		review.setRl_img(reviewImg);
			review.setRl_rate(rRate);			review.setRl_title(title);
			review.setOdidx(odidx);				
			boolean isSuccess = reviewProcSvc.reviewInsert(review, olid);
			if (isSuccess) {	// �����Ͽ� ����������
				forward.setPath("pdt_view.pdt?id="+plid);
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
		} else if(wtype.equals("up")) {	//�����̸�
			idx = Integer.parseInt(request.getParameter("idx"));
			if(reviewImg == null|| reviewImg.equals(""))		reviewImg = multi.getParameter("oldImg");
			ReviewInfo review = new ReviewInfo();
			review.setRl_idx(idx);
			review.setMl_id(uid);				review.setPl_id(plid);
			review.setRl_content(content);		review.setRl_img(reviewImg);
			review.setRl_rate(rRate);			review.setRl_title(title);
			boolean isSuccess = reviewProcSvc.reviewUpdate(review, idx);
			if (isSuccess) {	// ��������� ����������
				forward.setPath("review_view.jsp?idx="+idx);
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
		} else {	//������
			idx = Integer.parseInt(request.getParameter("idx"));
			plid = request.getParameter("plid");
			boolean isSuccess = reviewProcSvc.reviewDel(idx);
			if(isSuccess) {
				forward.setPath("review_list.review?plid="+plid);
				forward.setRedirect(true);
			} else {
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('���� ������ �����߽��ϴ�.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
		}
		
		return forward;
	}
}
