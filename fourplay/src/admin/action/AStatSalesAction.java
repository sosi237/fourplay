package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AStatSalesAction implements action.Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		String kind = request.getParameter("kind");
		String year = request.getParameter("year");
		year = "2020";
		AStatSalesSvc aStatSalesSvc = new AStatSalesSvc();
		ArrayList<StatPdtInfo> statSalesList = new ArrayList<StatPdtInfo>();
		
		if(kind.equals("monthly") || kind.equals("quarter")) {
			statSalesList = aStatSalesSvc.getMonthSales(year);
			request.setAttribute("year", year);
			request.setAttribute("statSalesList", statSalesList);
			forward.setPath("stat/sales_stat.jsp?kind=" + kind);
		} 

		return forward;
	}
}
