package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewFormSvc {
	public ReviewInfo reviewInsert(String uid, String plid) {
		ReviewInfo review = null;	
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);

		review = reviewDao.reviewInsert(uid, plid);	

		close(conn);
		return review;
	}
}
