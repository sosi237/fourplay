package admin.svc;

import static db.JdbcUtil.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class A_NoticeProcSvc {
// �Խñۿ� ���� ���� �۾��� ó���ϴ� Ŭ����
	public boolean anoticeInsert(NoticeInfo noticeInfo) throws Exception {
	// �Խñ� ����� ���� �޼ҵ�
		boolean isSuccess = false;
		Connection conn = getConnection();
		A_NoticeDao anoticeDao = A_NoticeDao.getInstance();
		anoticeDao.setConnection(conn);

		int result = anoticeDao.anoticeInsert(noticeInfo);
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
	
	public boolean anoticeUpdate(NoticeInfo noticeInfo) throws Exception {
	// �Խñ� ������ ���� �޼ҵ�
		boolean isSuccess = false;
		Connection conn = getConnection();
		A_NoticeDao anoticeDao = A_NoticeDao.getInstance();
		anoticeDao.setConnection(conn);

		int result = anoticeDao.anoticeUpdate(noticeInfo);
		if (result > 0) {
			commit(conn);
			isSuccess = true;
		} else {
			rollback(conn);
		}

		close(conn);
		return isSuccess;
	}
	public boolean anoticeDelete(int idx, NoticeInfo noticeInfo) throws Exception {
	// �Խñ� ������ ���� �޼ҵ�
		boolean isSuccess = false;
		Connection conn = getConnection();
		A_NoticeDao anoticeDao = A_NoticeDao.getInstance();
		anoticeDao.setConnection(conn);

		int result = anoticeDao.anoticeDelete(idx, noticeInfo);
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
