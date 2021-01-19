package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class NoticeViewSvc {
// �Խñ� ������ ����Ͻ� ������ ó���ϴ� Ŭ����
	public NoticeInfo getArticle(int idx) {
	// �μ��� �޾ƿ� idx�� �ش��ϴ� �Խù� �ϳ��� ������ �����Ͽ� FreeInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		NoticeInfo article = null;	// ������ �Խù� ������ ���� �ν��Ͻ� ����
		Connection conn = getConnection();
		NoticeDao noticeDao = NoticeDao.getInstance();
		noticeDao.setConnection(conn);

		article = noticeDao.getArticle(idx);	// ������ �Խñ� �ޱ�

		close(conn);
		return article;
	}
}
