package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrdDetailSvc {
	public ArrayList<OrdDetailInfo> getOrdDetail(String olid) {
		//특정 주문의 주문상세 정보만을 가져오는 메소드
		System.out.println("svc getOrdDetail");
		
		ArrayList<OrdDetailInfo> ordDetailList = new ArrayList<OrdDetailInfo>();
		Connection conn = getConnection();
		OrdDao ordDao = OrdDao.getInstance();
		ordDao.setConnection(conn);
		ordDetailList = ordDao.getOrdDetailList(olid);
		close(conn);

		return ordDetailList;
	}
}
