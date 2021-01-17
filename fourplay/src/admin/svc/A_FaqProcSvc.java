package admin.svc;

import static db.JdbcUtil.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class A_FaqProcSvc {
// �Խñۿ� ���� ���� �۾��� ó���ϴ� Ŭ����
	public boolean afaqInsert(FaqInfo faqInfo) throws Exception {
	// �Խñ� ����� ���� �޼ҵ�
		boolean isSuccess = false;
		Connection conn = getConnection();
		A_FaqDao afaqDao = A_FaqDao.getInstance();
		afaqDao.setConnection(conn);

		int result = afaqDao.afaqInsert(faqInfo);
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
	
	public boolean afaqUpdate(FaqInfo faqInfo) throws Exception {
	// �Խñ� ������ ���� �޼ҵ�
		boolean isSuccess = false;
		Connection conn = getConnection();
		A_FaqDao afaqDao = A_FaqDao.getInstance();
		afaqDao.setConnection(conn);

		int result = afaqDao.afaqUpdate(faqInfo);
		if (result > 0) {
			commit(conn);
			isSuccess = true;
		} else {
			rollback(conn);
		}

		close(conn);
		return isSuccess;
	}
	public boolean afaqDelete(int idx, FaqInfo faqInfo) throws Exception {
	// �Խñ� ������ ���� �޼ҵ�
		boolean isSuccess = false;
		Connection conn = getConnection();
		A_FaqDao afaqDao = A_FaqDao.getInstance();
		afaqDao.setConnection(conn);

		int result = afaqDao.afaqDelete(idx, faqInfo);
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
