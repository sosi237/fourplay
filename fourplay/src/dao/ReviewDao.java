package dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class ReviewDao {
	private static ReviewDao reviewDao;
	private Connection conn;
	private ReviewDao() {}
	public static ReviewDao getInstance() {
		if (reviewDao == null) {
			reviewDao = new ReviewDao();	
		}
		return reviewDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getArticleCount(String plid) {
	// �ش� ��ǰ�� ���� ������ �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		int result = 0;
		
		try {
			sql = "select count(*) from t_review_list where rl_status = 'a' and pl_id = '" + plid + "' ";
			System.out.println(sql);
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
	
	public ArrayList<ReviewInfo> getArticleList(String plid, int rCpage, int rPsize) {
	// �ش� ��ǰ�� ���� ����� ArrayList���·� �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		ArrayList<ReviewInfo> articleList = new ArrayList<ReviewInfo>();
		ReviewInfo reviewInfo = null;

		int snum = (rCpage - 1) * rPsize;

		try {
			sql = "select * from t_review_list where rl_status = 'a' and pl_id = '" + plid + "' " +
			" order by rl_idx desc limit " + snum + ", " + rPsize;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				reviewInfo = new ReviewInfo();
				reviewInfo.setRl_idx(rs.getInt("rl_idx"));
				reviewInfo.setMl_id(rs.getString("ml_id"));
				reviewInfo.setPl_id(rs.getString("pl_id"));
				reviewInfo.setRl_title(rs.getString("rl_title"));
				reviewInfo.setRl_rate(rs.getInt("rl_rate"));
				reviewInfo.setRl_content(rs.getString("rl_content"));
				reviewInfo.setRl_img(rs.getString("rl_img"));
				reviewInfo.setRl_status(rs.getString("rl_status"));
				reviewInfo.setRl_date(rs.getString("rl_date"));

				articleList.add(reviewInfo);
			}
		} catch(Exception e) {
			System.out.println("getArticleList() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return articleList;
	}
	
	public int reviewInsert(ReviewInfo review, String olid) {
		System.out.println("dao reviewInsert");
		int cResult = 0, sResult = 0, last = 0, finalResult = 0;
		Statement stmt = null, stmt2 = null, stmt3 = null, stmt4 = null;
		CallableStatement cs = null;
		ResultSet rs = null, rs2 = null;
		String sql = null;
		
		try {
			sql ="{call sp_rl_insert(?, ?, ?, ?, ?, ?, ?)}";
			System.out.println(sql);
			cs = conn.prepareCall(sql);
			cs.setString(1, review.getMl_id());			cs.setString(2, review.getPl_id());
			cs.setInt(3, review.getOdidx());			cs.setString(4, review.getRl_title());
			cs.setInt(5, review.getRl_rate());			cs.setString(6, review.getRl_content());
			cs.setString(7, review.getRl_img());
			cResult = cs.executeUpdate();
			System.out.println(cResult);
			if(cResult > 0) {
				sql = "update t_order_detail set od_status = 'k' where od_idx = '" + review.getOdidx() + "' ";
				System.out.println(sql);
				stmt = conn.createStatement();
				sResult = stmt.executeUpdate(sql);
			}
			if(sResult > 0) {
				int a = -1, b = -2;
				sql = "select count(od_idx) from t_order_detail where ol_id = '" + olid + "' ";
				System.out.println(sql);
				stmt2 = conn.createStatement();
				rs = stmt2.executeQuery(sql);
				rs.next();
					a = rs.getInt(1);	// �� �ֹ��� ���� ������ ����
					System.out.println("a : " + a);
				
				sql = "select count(od_idx) from t_order_detail where ol_id = '" + olid + "' and od_status = 'k' ";
				System.out.println(sql);
				stmt3 = conn.createStatement();
				rs2 = stmt3.executeQuery(sql);
				rs2.next();
				b = rs.getInt(1);	// ������ �� �����ı� �ۼ��� ����
				
				System.out.println("b : " + b);
				
				if(a == b) {	// ��� ��ǰ�鿡 ���� �ı⸦ �ۼ�������
					sql = "update t_order_list set ol_status = 'k' where ol_id = '"+ olid + "' ";
					System.out.println(sql);
					stmt4 = conn.createStatement();
					last = stmt4.executeUpdate(sql);
					System.out.println("last: " +last);
					if(last > 0)	finalResult = 1;
				} else {	// ���� �ı⸦ �ۼ����� ���� ��ǰ�� ������  
					finalResult = 2;
				}
				System.out.println(finalResult);
			}
			
		} catch(Exception e) {
			System.out.println("reviewInsert() ����");
			e.printStackTrace();
		} finally {
			close(rs); close(cs); close(stmt);  close(stmt2); close(stmt3); close(stmt4);
		}

		return finalResult;
	}
	
}