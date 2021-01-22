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
		int maxSize = 5 * 1024 * 1024;		// 업로드 최대 용량으로 5MB로 지정
		String wtype = "", plid = "", uid = "", title = "", content = "", reviewImg = "", olid = "";
		int odidx;
		int rRate = 5; 
		MultipartRequest multi = new MultipartRequest(
			request, 	// request객체로 multi로 데이터들을 받기 위함
			uploadPath, // 서버에 실제로 파일이 저장될 위치 지정
			maxSize, 	// 한 번에 업로드할 수 있는 최대크기(byte단위)
			"utf-8", 	// 파일의 인코딩 방식(utf-8, euc-kr, ksc-5601 등)
			new DefaultFileRenamePolicy());	// 파일 이름의 중복 처리
		
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
		if (isSuccess) {	// 리뷰등록에 성공했으면
			forward.setPath("review_view.jsp?plid="+plid);
			forward.setRedirect(true);
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('리뷰 등록이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		return forward;
	}
}
