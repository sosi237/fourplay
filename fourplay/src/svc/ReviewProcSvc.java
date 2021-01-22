package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewProcSvc {
	public boolean reviewInsert(ReviewInfo review, String olid) {
		System.out.println("ReviewProcSvc reviewInsert");
		boolean isSuccess = false;
		int finalResult = 0;
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		finalResult = reviewDao.reviewInsert(review, olid);
		System.out.println(finalResult);
		if (finalResult == 1 || finalResult == 2) {
			commit(conn);
			isSuccess = true;
		} else {
			rollback(conn);
		}
		close(conn);
		return isSuccess;
	}
}
