package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AMemStatAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("AMemStatAction");
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		String kind = request.getParameter("kind");
		AMemStatSvc aMemStatSvc= new AMemStatSvc();
		
		if(kind.equals("gender")) {
			int male = 0, female = 0;
			male = aMemStatSvc.getMemCount("M");
			female = aMemStatSvc.getMemCount("F");
			request.setAttribute("male", male);
			request.setAttribute("female", female);
			forward.setPath("stat/mem_stat.jsp?kind=gender");
		} else if (kind.equals("age")) {
			ArrayList ageList = new ArrayList();
			ageList = aMemStatSvc.getAgeList();
			request.setAttribute("ageList", ageList);
			forward.setPath("stat/mem_stat.jsp?kind=age");
		}
		
		return forward;
	}
}
