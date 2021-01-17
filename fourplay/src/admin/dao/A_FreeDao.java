package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class A_FreeDao {
	private static A_FreeDao afreeDao;
	private Connection conn;
	private A_FreeDao() {}
	
	public static A_FreeDao getInstance() {
		if (afreeDao == null) {
			afreeDao = new A_FreeDao();	
		}
		return afreeDao;
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
			sql = "select count(*) from t_free_list " + " where fl_status = 'a' " + where;
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

	public ArrayList<FreeInfo> getArticleList(String where, int cpage, int limit) {
	// �˻��� �Խñ� ����� ArrayList���·� �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		ArrayList<FreeInfo> articleList = new ArrayList<FreeInfo>();
		FreeInfo freeInfo = null;

		int snum = (cpage - 1) * limit;

		try {
			sql ="select * from t_free_list where fl_status = 'a' " + 
			where + " order by fl_idx desc limit " + snum + ", " + limit;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				freeInfo = new FreeInfo();
				freeInfo.setFl_idx(rs.getInt("fl_idx"));
				freeInfo.setFl_reply(rs.getInt("fl_reply"));
				freeInfo.setFl_read(rs.getInt("fl_read"));
				freeInfo.setFl_writer(rs.getString("fl_writer"));
				freeInfo.setFl_title(rs.getString("fl_title"));
				freeInfo.setFl_content(rs.getString("fl_content"));
				freeInfo.setFl_date(rs.getString("fl_date"));
				freeInfo.setFl_status(rs.getString("fl_status"));
				freeInfo.setFl_ip(rs.getString("fl_ip"));

				articleList.add(freeInfo);
			}
		} catch(Exception e) {
			System.out.println("getArticleList() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return articleList;
	}
	
	public int setCountUp(int idx) {
	// �Խñ� ��ȸ�� ���� �޼ҵ�
		Statement stmt = null;
		String sql = null;
		int result = 0;
		
		try {
			sql = "update t_free_list set fl_read = fl_read + 1 where fl_idx = " + idx;
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("setCountUp() ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}

	public FreeInfo getArticle(int idx) {
	// ������ �Խù��� �����͸� FreeInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		FreeInfo article = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			sql = "select * from t_free_list where fl_status = 'a' and fl_idx = " + idx;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				article = new FreeInfo();
				article.setFl_idx(rs.getInt("fl_idx"));
				article.setFl_reply(rs.getInt("fl_reply"));
				article.setFl_read(rs.getInt("fl_read"));
				article.setFl_writer(rs.getString("fl_writer"));
				article.setFl_title(rs.getString("fl_title"));
				article.setFl_content(rs.getString("fl_content"));
				article.setFl_date(rs.getString("fl_date"));
				article.setFl_status(rs.getString("fl_status"));
				article.setFl_ip(rs.getString("fl_ip"));
			}
		} catch(Exception e) {
			System.out.println("getArticle() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return article;
	}

	public int freeInsert(FreeInfo freeInfo) {
	// �Խñ� ��� �޼ҵ�
		PreparedStatement pstmt = null;
		ResultSet rs = null;		// 
		int idx = 1, result = 0;	// ���ο� �۹�ȣ�� ���� ���� ��� ������ ������ ����
		String sql = null;

		try {
			sql = "select max(fl_idx) + 1 from t_free_list";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())	idx = rs.getInt(1);
			// ����� �Խñ��� ���ο� �۹�ȣ ����

			sql = "insert into t_free_list (fl_idx, fl_writer, " + 
				"fl_title, fl_content, fl_ip) values (?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setString(3, freeInfo.getFl_writer());
			pstmt.setString(5, freeInfo.getFl_title());
			pstmt.setString(6, freeInfo.getFl_content());
			pstmt.setString(7, freeInfo.getFl_ip());

			result = pstmt.executeUpdate();
			// ���ο� �Խñ� ���

		} catch(Exception e) {
			System.out.println("freeInsert() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(pstmt);
		}

		return result;
	}

}
