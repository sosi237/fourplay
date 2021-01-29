package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import dao.OrdDao;
import vo.*;

public class AOrdListSvc {
	public int getOrdCount(String where) {	//한 회원의 전체 주문 개수를 리턴
		int rcnt = 0;	
		Connection conn = getConnection();
		AOrdDao aOrdDao = AOrdDao.getInstance();
		aOrdDao.setConnection(conn);
		rcnt = aOrdDao.getOrdCount(where);
		close(conn);

		return rcnt;
	}
	public ArrayList<OrdListInfo> getOrdList(String where, String orderby, int cpage, int psize){
		ArrayList<OrdListInfo> ordList = new ArrayList<OrdListInfo>();
		Connection conn = getConnection();
		AOrdDao aOrdDao = AOrdDao.getInstance();
		aOrdDao.setConnection(conn);
		ordList = aOrdDao.getOrdList(where, orderby, cpage, psize);
		close(conn);
		return ordList;
	}
	public OrdListInfo getOrd(String olid) {		//하나의 주문내역 정보만 가져오는 메소드
		System.out.println("svc getOrd");
		OrdListInfo detailInfo = new OrdListInfo();
		Connection conn = getConnection();
		AOrdDao aOrdDao = AOrdDao.getInstance();
		aOrdDao.setConnection(conn);
		detailInfo = aOrdDao.getOrd(olid);
		close(conn);			
		return detailInfo;
	}
}
