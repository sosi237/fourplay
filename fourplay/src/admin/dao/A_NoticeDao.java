package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class A_NoticeDao {
	private static A_NoticeDao anoticeDao;
	private Connection conn;
	private A_NoticeDao() {}
	
	public static A_NoticeDao getInstance() {
		if (anoticeDao == null) {
			anoticeDao = new A_NoticeDao();	
		}
		return anoticeDao;
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
			sql = "select count(*) from t_notice_list " +
					" where nl_status = 'a' " + where;
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

	public ArrayList<NoticeInfo> getArticleList(String where, int cpage, int limit) {
	// �˻��� �Խñ� ����� ArrayList���·� �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		ArrayList<NoticeInfo> articleList = new ArrayList<NoticeInfo>();
		NoticeInfo noticeInfo = null;

		int snum = (cpage - 1) * limit;

		try {
			sql ="select * from t_notice_list where nl_status = 'a' " + 
			where + " order by nl_idx desc limit " + snum + ", " + limit;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				noticeInfo = new NoticeInfo();
				noticeInfo.setNl_idx(rs.getInt("nl_idx"));
				noticeInfo.setNl_read(rs.getInt("nl_read"));
				noticeInfo.setNl_writer(rs.getString("nl_writer"));
				noticeInfo.setNl_title(rs.getString("nl_title"));
				noticeInfo.setNl_content(rs.getString("nl_content"));
				noticeInfo.setNl_date(rs.getString("nl_date"));

				articleList.add(noticeInfo);
			}
		} catch(Exception e) {
			System.out.println("getArticleList() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return articleList;
	}
	
	
	public NoticeInfo getArticle(int idx) {
	// ������ �Խù��� �����͸� FreeInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		NoticeInfo article = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			sql = "select * from t_notice_list where nl_idx = " + idx;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				article = new NoticeInfo();
				article.setNl_idx(rs.getInt("nl_idx"));
				article.setNl_read(rs.getInt("nl_read"));
				article.setNl_writer(rs.getString("nl_writer"));
				article.setNl_title(rs.getString("nl_title"));
				article.setNl_content(rs.getString("nl_content"));
				article.setNl_date(rs.getString("nl_date"));
				article.setNl_status(rs.getString("nl_status"));
			}
		} catch(Exception e) {
			System.out.println("getArticle() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return article;
	}

	public int anoticeInsert(NoticeInfo noticeInfo) {
	// �Խñ� ��� �޼ҵ�
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		int idx = 1, result = 0;	
		String sql = null;

		try {
			System.out.println("dao");
			sql = "select max(nl_idx) + 1 from t_notice_list";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())	idx = rs.getInt(1);

			sql = "insert into t_notice_list (nl_idx, nl_writer, nl_title, nl_content, al_idx) values (?, ?, ?, ?, ?)";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setString(2, noticeInfo.getNl_writer());
			pstmt.setString(3, noticeInfo.getNl_title());
			pstmt.setString(4, noticeInfo.getNl_content());
			pstmt.setInt(5, 1);

			result = pstmt.executeUpdate();

		} catch(Exception e) {
			System.out.println("anoticeInsert() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(pstmt);
		}

		return result;
	}

	public int anoticeUpdate(NoticeInfo noticeInfo) {
	 // �Խñ� ������ ���� �޼ҵ�
	    PreparedStatement pstmt = null;
	    String sql = null;
	    int result = 0;      // ���� ���� ��� ������ ������ ����

     try {
	       sql = "update t_notice_list set nl_title = ?, nl_content = ? where nl_idx = ?";
	       pstmt = conn.prepareStatement(sql);
	       pstmt.setString(1, noticeInfo.getNl_title());
		   pstmt.setString(2, noticeInfo.getNl_content());
		   pstmt.setInt(3, noticeInfo.getNl_idx());
		   result = pstmt.executeUpdate();   // �Խñ� ����
		  } catch(Exception e) {
		      System.out.println("anoticeUpdate() ����");
		      e.printStackTrace();
		   } finally {
		      close(pstmt);
		   }

		   return result;
	 }

	public int anoticeDelete(int idx, NoticeInfo noticeInfo) {
	// �Խñ� ������ ���� �޼ҵ�
		Statement stmt = null;
		String sql = null;
		int result = 0;		

		try {
			sql = "delete from t_notice_list where nl_idx = " + idx;
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);;

		} catch(Exception e) {
			System.out.println("anoticeDelete() ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}

	public NoticeInfo getArticleUp(int idx, String uid) {
	// ������ �ۿ� ���� ������ ���� ��� �ش� �����͸� ������ ������ �޼ҵ�
		NoticeInfo article = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			String where = " and nl_writer = '" + uid + "'";
			sql = "select * from t_notice_list where nl_idx = " + idx ;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				article = new NoticeInfo();
				// �˻��� �Խù��� ������ ������ FreeInfo�� �ν��Ͻ� article ����
				article.setNl_idx(rs.getInt("nl_idx"));
				article.setNl_read(rs.getInt("nl_read"));
				article.setNl_writer(rs.getString("nl_writer"));
				article.setNl_title(rs.getString("nl_title"));
				article.setNl_content(rs.getString("nl_content"));
				article.setNl_date(rs.getString("nl_date"));
				// �޾ƿ� ���ڵ��� article �ν��Ͻ��� ��� ������ ���� ����
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
