package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import dao.*;
import vo.*;

public class QAProcSvc {
	public boolean qaInsert(QAInfo qaInfo) throws Exception {
		boolean isSuccess = false;
		Connection conn = getConnection();
		QADao qaDao = QADao.getInstance();
		qaDao.setConnection(conn);

		int result = qaDao.qaInsert(qaInfo);
		if (result > 0) {
			commit(conn);	// 쿼리를 실제로 적용시킴
			isSuccess = true;
		} else {
			rollback(conn);	// 실행했던 모든 쿼리를 무효화 시킴
		}

		close(conn);
		return isSuccess;
	}
	public boolean qaUpdate(QAInfo qaInfo) throws Exception {
		boolean isSuccess = false;
		Connection conn = getConnection();
		QADao qaDao = QADao.getInstance();
		qaDao.setConnection(conn);

		int result = qaDao.qaUpdate(qaInfo);
		if (result > 0) {
			commit(conn);
			isSuccess = true;
		} else {
			rollback(conn);
		}

		close(conn);
		return isSuccess;
	}
	public boolean qaDelete(QAInfo qaInfo) throws Exception {
		boolean isSuccess = false;
		Connection conn = getConnection();
		QADao qaDao = QADao.getInstance();
		qaDao.setConnection(conn);

		int result = qaDao.qaDelete(qaInfo);
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
