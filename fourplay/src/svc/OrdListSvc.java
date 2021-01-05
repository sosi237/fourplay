package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrdListSvc {
	public int getOrdCount(String buyer) {	// 검색된 주문의 전체 개수를 리턴하는 메소드
		System.out.println("svc getOrdCount");
		
		int rcnt = 0;	// 전체 레코드 개수를 저장할 변수
		Connection conn = getConnection();
		OrdDao ordDao = OrdDao.getInstance();
		ordDao.setConnection(conn);
		rcnt = ordDao.getOrdCount(buyer);
		close(conn);
		
		System.out.println(rcnt);
		return rcnt;
	}

	public ArrayList<OrdListInfo> getOrdList(String buyer, int cpage, int psize) {
		System.out.println("svc getOrdList");
		
		ArrayList<OrdListInfo> ordList = new ArrayList<OrdListInfo>();
		Connection conn = getConnection();
		OrdDao ordDao = OrdDao.getInstance();
		ordDao.setConnection(conn);
		ordList = ordDao.getOrdList(buyer, cpage, psize);
		close(conn);

		return ordList;
	}
	
	public OrdListInfo getOrd(String olid, String where) {
		System.out.println("svc getOrd");
		
		OrdListInfo ordInfo = new OrdListInfo();
		Connection conn = getConnection();
		OrdDao ordDao = OrdDao.getInstance();
		ordDao.setConnection(conn);
		
		ordInfo = ordDao.getOrd(olid, where);
		close(conn);
		
		return ordInfo;
	}
}
