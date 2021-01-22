package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewFormSvc {
	public boolean getPms(String plid, String uid) {
		boolean isPms = false;	
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);

		isPms = reviewDao.getPms(plid, uid);	

		close(conn);
		return isPms;
	}
}
