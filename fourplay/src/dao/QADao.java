package dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class QADao {
	private static QADao qaDao;
	private Connection conn;
	private QADao() {}
	
	public static QADao getInstance() {
		if (qaDao == null) {	// ���� ������ �ν��Ͻ��� ������
			qaDao = new QADao();	// ���Ӱ� �ν��Ͻ��� ����
		}
		return qaDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public int getArticleCount(String where) {
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		int result = 0;
		
		try {
			sql = "select count(*) from t_qna_list " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	result = rs.getInt(1);
		} catch(Exception e) {
			System.out.println("getArticleCount() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}

	public ArrayList<QAInfo> getArticleList(String where, int cpage, int limit) {
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		ArrayList<QAInfo> articleList = new ArrayList<QAInfo>();
		QAInfo qaInfo = null;
		int snum = (cpage - 1) * limit;

		try {
			sql ="select * from t_qna_list " + 
			where + " order by ql_idx desc limit " + snum + ", " + limit;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
			// rs�� ����� ��� �� ���·� �����ϱ� ���� if�� ������� �ʰ� �ٷ� while���
				qaInfo = new QAInfo();
				// �ϳ��� ���ڵ�(�Խñ�)�� ������ �ν��Ͻ� ����

				qaInfo.setQl_idx(rs.getInt("ql_idx"));
				qaInfo.setQl_writer(rs.getString("ql_writer"));
				qaInfo.setQl_title(rs.getString("ql_title"));
				qaInfo.setQl_content(rs.getString("ql_content"));
				qaInfo.setQl_qdate(rs.getString("ql_qdate"));
				qaInfo.setQl_adate(rs.getString("ql_adate"));
				qaInfo.setQl_status(rs.getString("ql_status"));
				qaInfo.setQl_ip(rs.getString("ql_ip"));

				articleList.add(qaInfo);
			}
		} catch(Exception e) {
			System.out.println("getArticleList() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return articleList;
	}
	
	
	public QAInfo getArticle(int idx) {
		QAInfo article = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			sql = "select * from t_qna_list where ql_idx = " + idx;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				article = new QAInfo();
				article.setQl_idx(rs.getInt("ql_idx"));
				article.setQl_writer(rs.getString("ql_writer"));
				article.setQl_title(rs.getString("ql_title"));
				article.setQl_content(rs.getString("ql_content"));
				article.setQl_qdate(rs.getString("ql_qdate"));
				article.setQl_adate(rs.getString("ql_adate"));
				article.setQl_status(rs.getString("ql_status"));
				article.setQl_ip(rs.getString("ql_ip"));
			}
		} catch(Exception e) {
			System.out.println("getArticle() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return article;
	}

	public int qaInsert(QAInfo qaInfo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;		// ����� �Խñ��� ��ȣ�� ��� ���� ResultSet
		int idx = 1, result = 0;	// ���ο� �۹�ȣ�� ���� ���� ��� ������ ������ ����
		String sql = null;

		
		try {
			sql = "select max(ql_idx) + 1 from t_qna_list";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())	idx = rs.getInt(1);
			// ����� �Խñ��� ���ο� �۹�ȣ ����

			sql = "insert into t_qna_list (ql_idx, ql_writer, ql_title, "
					+ "ql_content, ql_ip, ql_answer, al_idx)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setString(2, qaInfo.getQl_writer());
			pstmt.setString(3, qaInfo.getQl_title());
			pstmt.setString(4, qaInfo.getQl_content());
			pstmt.setString(5, qaInfo.getQl_ip());
			pstmt.setString(6, qaInfo.getQl_answer());
			pstmt.setInt(7, 1);

			result = pstmt.executeUpdate();
	

		} catch(Exception e) {
			System.out.println("qaInsert() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(pstmt);
		}

		return result;
	}

	public int qaUpdate(QAInfo qaInfo) {
		PreparedStatement pstmt = null;
		String sql = null;
		int result = 0;		// ���� ���� ��� ������ ������ ����

		try {
			sql = "update t_qna_list set ql_title = ?, ql_content = ? where ql_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qaInfo.getQl_title());
			pstmt.setString(2, qaInfo.getQl_content());
			pstmt.setInt(3, qaInfo.getQl_idx());
			result = pstmt.executeUpdate();	// �Խñ� ����
		} catch(Exception e) {
			System.out.println("qaUpdate() ����");
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int qaDelete(QAInfo qaInfo) {
		Statement stmt = null;
		String sql = null;
		int result = 0;		// ���� ���� ��� ������ ������ ����

		try {
			String where = " where ql_idx = " + qaInfo.getQl_idx();
				where += " and ql_writer = '" + qaInfo.getQl_writer() + "' ";
			sql = "update t_qna_list set ql_status = 'b' " + where;
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("qaDelete() ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}

	public QAInfo getArticleUp(int idx, String uid) {
	// ������ �ۿ� ���� ������ ���� ��� �ش� �����͸� ������ ������ �޼ҵ�
		QAInfo article = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
//			String where = " and fl_ismember = ";
//			if (ismember.equals("n")) {
//			// �Խù��� ��ȸ�����̸� ����ڰ� �Է��� ��ȣ�� ���ǿ� ����
//				where += "'n' and fl_pwd = '" + pwd + "'";
//			} else {
//			// �Խù��� ȸ�����̸� ���� �α����� ���̵�� �ۼ��� ���̵� ���ϴ� ������ ����
//				where += "'y' and fl_writer = '" + uid + "'";
//			}
			
			
			
			
			
			
			
			String where = " and ql_writer = '" + uid + "'";
			
			sql = "select * from t_qna_list where ql_idx = " + idx + where;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				article = new QAInfo();
				// �˻��� �Խù��� ������ ������ QAInfo�� �ν��Ͻ� article ����
				article.setQl_idx(rs.getInt("ql_idx"));
				article.setQl_writer(rs.getString("ql_writer"));
				article.setQl_title(rs.getString("ql_title"));
				article.setQl_content(rs.getString("ql_content"));
				article.setQl_qdate(rs.getString("ql_qdate"));
				article.setQl_status(rs.getString("ql_status"));
				article.setQl_ip(rs.getString("ql_ip"));
			}
		} catch(Exception e) {
			System.out.println("getArticleUp() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return article;
	}
}
