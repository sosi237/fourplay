package admin.svc;

import static db.JdbcUtil.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class A_QAProcSvc {
// �Խñۿ� ���� ���� �۾��� ó���ϴ� Ŭ����
	public boolean aqaInsert(QAInfo faqInfo) throws Exception {
	// �Խñ� ����� ���� �޼ҵ�
		boolean isSuccess = false;
		Connection conn = getConnection();
		A_QADao aqaDao = A_QADao.getInstance();
		aqaDao.setConnection(conn);

		int result = aqaDao.aqaInsert(faqInfo);
		if (result > 0) {
		// �۵���ϴ� ������ �������� �� ��ϵ� ���ڵ尡 ������
			commit(conn);	// ������ ������ �����Ŵ
			isSuccess = true;
		} else {
		// �۵���ϴ� ������ �������� �� ��ϵ� ���ڵ尡 ������
			rollback(conn);	// �����ߴ� ��� ������ ��ȿȭ ��Ŵ
		}

		close(conn);
		return isSuccess;
	}
	
	public boolean aqaUpdate(QAInfo faqInfo) throws Exception {
	// �Խñ� ������ ���� �޼ҵ�
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
	// �Խñ� ������ ���� �޼ҵ�
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
