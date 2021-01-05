package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrdDetailSvc {
	public ArrayList<OrdDetailInfo> getOrdDetail(String olid) {
		//Ư�� �ֹ��� �ֹ��� �������� �������� �޼ҵ�
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
