package admin.svc;

import static db.JdbcUtil.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class A_QAProcSvc {
// 게시글에 대한 실제 작업을 처리하는 클래스
	public boolean aqaInsert(QAInfo faqInfo) throws Exception {
	// 게시글 등록을 위한 메소드
		boolean isSuccess = false;
		Connection conn = getConnection();
		A_QADao aqaDao = A_QADao.getInstance();
		aqaDao.setConnection(conn);

		int result = aqaDao.aqaInsert(faqInfo);
		if (result > 0) {
		// 글등록하는 쿼리를 실행했을 때 등록된 레코드가 있으면
			commit(conn);	// 쿼리를 실제로 적용시킴
			isSuccess = true;
		} else {
		// 글등록하는 쿼리를 실행했을 때 등록된 레코드가 없으면
			rollback(conn);	// 실행했던 모든 쿼리를 무효화 시킴
		}

		close(conn);
		return isSuccess;
	}
	
	public boolean aqaUpdate(QAInfo faqInfo) throws Exception {
	// 게시글 수정을 위한 메소드
		boolean isSuccess = false;
		Connection conn = getConnection();
		A_QADao aqaDao = A_QADao.getInstance();
		aqaDao.setConnection(conn);

		int result = aqaDao.aqaUpdate(faqInfo);
		if (result > 0) {
			commit(conn);
			isSuccess = true;
		} else {
			rollback(conn);
		}

		close(conn);
		return isSuccess;
	}
	public boolean aqaDelete(int idx, QAInfo faqInfo) throws Exception {
	// 게시글 삭제를 위한 메소드
		boolean isSuccess = false;
		Connection conn = getConnection();
		A_QADao aqaDao = A_QADao.getInstance();
		aqaDao.setConnection(conn);

		int result = aqaDao.aqaDelete(idx, faqInfo);
		if (result > 0) {
			commit(conn);
			isSuccess = true;
		} else {
			rollback(conn);
		}

		close(conn);
		return isSuccess;
	}
}
