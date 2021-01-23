package action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import svc.*;
import vo.*;

public class PdtViewAction implements Action {
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      System.out.println("PdtViewAction");
      PdtViewSvc pdtViewSvc = new PdtViewSvc();
      String id = request.getParameter("id");
      PdtInfo pdtInfo = pdtViewSvc.getPdtInfo(id);
      // ������ ���̵� �ش��ϴ� ��ǰ�� �������� PdtInfo�� �ν��Ͻ��� �޾� ��
      
      request.setCharacterEncoding("utf-8");
      int rCpage = 1, rPcnt, rSpage, rEpage, rCnt, rBsize = 10, rPsize = 5;
      if (request.getParameter("rCpage") != null)
         rCpage = Integer.parseInt(request.getParameter("rCpage"));
      
      ReviewListSvc reviewListSvc = new ReviewListSvc();
      ArrayList<ReviewInfo> articleList = new ArrayList<ReviewInfo>();
      rCnt = reviewListSvc.getArticleCount(id);
      articleList = reviewListSvc.getArticleList(id, rCpage, rPsize);

      rPcnt = rCnt / rPsize;
      if (rCnt % rPsize > 0)   rPcnt++;
      rSpage = (rCpage - 1) / rPsize * rPsize + 1;
      rEpage = rSpage + rPsize - 1;
      if (rEpage > rPcnt)   rSpage = rPcnt;

      ReviewPageInfo reviewPageInfo = new ReviewPageInfo();
      reviewPageInfo.setrCpage(rCpage);      // ���� ������ ��ȣ
      reviewPageInfo.setrCnt(rCnt);         // ��ü �Խñ� ��
      reviewPageInfo.setrPcnt(rPcnt);         // ��ü ������ ��
      reviewPageInfo.setrSpage(rSpage);      // ��� ���� ������ ��ȣ
      reviewPageInfo.setrEpage(rEpage);      // ��� ���� ������ ��ȣ
      reviewPageInfo.setrBsize(rBsize);
      
      request.setAttribute("pdtInfo", pdtInfo);
      request.setAttribute("articleList", articleList);
      request.setAttribute("reviewPageInfo", reviewPageInfo);

      ActionForward forward = new ActionForward();
      forward.setPath("/product/product_detail.jsp");

      return forward;
   }
}