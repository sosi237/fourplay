package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AStatPdtAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		String kind = request.getParameter("kind");
		String date = request.getParameter("date");
		date = "2101";
		AStatPdtSvc aStatPdtSvc = new AStatPdtSvc();
		ArrayList<StatPdtInfo> statPdtList = new ArrayList<StatPdtInfo>();
		statPdtList = aStatPdtSvc.getBestTen();
		request.setAttribute("statPdtList", statPdtList);
		forward.setPath("stat/pdt_stat.jsp");

		return forward;
	}
}
