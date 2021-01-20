package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;

import dao.OrdDao;

import java.util.*;
import vo.*;

public class AOrdDao {
	private static AOrdDao aOrdDao;
	private Connection conn;
	private AOrdDao() {}
	public static AOrdDao getInstance() {
		if (aOrdDao == null) {
			aOrdDao = new AOrdDao();
		}
		return aOrdDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	public int getOrdCount() {	// 총 주문개수를 가져오는 메소드
		int rcnt = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) from t_order_list ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	rcnt = rs.getInt(1);
		} catch(Exception e) {
			System.out.println("getOrdCount() 오류");			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<OrdListInfo> getOrdList(int cpage, int psize){	// 모든 회원/비회원의 총 주문내역을 가져오는 메소드
		ArrayList<OrdListInfo> ordList = new ArrayList<OrdListInfo>();
		ArrayList<OrdDetailInfo> ordDetailList = new ArrayList<OrdDetailInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		OrdListInfo ordListInfo = null;
		int snum = (cpage -1) * psize; // 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
		
		try {
			String sql = "select * from t_order_list group by ol_id order by ol_date limit "+ snum + ", " + psize;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ordListInfo = new OrdListInfo();
				ordListInfo.setOl_id(rs.getString("ol_id"));
				ordListInfo.setOl_buyer(rs.getString("ol_buyer"));
				ordListInfo.setOl_bname(rs.getString("ol_bname"));
				ordListInfo.setOl_bphone(rs.getString("ol_bphone"));
				ordListInfo.setOl_bmail(rs.getString("ol_bmail"));
				ordListInfo.setOl_rname(rs.getString("ol_rname"));
				ordListInfo.setOl_rphone(rs.getString("ol_rphone"));
				ordListInfo.setOl_rzip(rs.getString("ol_rzip"));
				ordListInfo.setOl_raddr1(rs.getString("ol_raddr1"));
				ordListInfo.setOl_raddr2(rs.getString("ol_raddr2"));
				ordListInfo.setOl_comment(rs.getString("ol_comment"));
				ordListInfo.setOl_payment(rs.getString("ol_payment"));
				ordListInfo.setOl_status(rs.getString("ol_status"));
				ordListInfo.setOl_date(rs.getString("ol_date"));
				ordListInfo.setOl_usepnt(rs.getInt("ol_usepnt"));
				ordListInfo.setOl_savepnt(rs.getInt("ol_savepnt"));
				ordListInfo.setOl_pay(rs.getInt("ol_pay"));
				
				ordDetailList = getOrdDetailList(rs.getString("ol_id"));
				
				ordListInfo.setOrdDetailList(ordDetailList);
				ordList.add(ordListInfo);
			}
		} catch(Exception e) {
			System.out.println("getOrdList() 오류");			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return ordList;
	}
	
	public ArrayList<OrdDetailInfo> getOrdDetailList(String olid){	//하나의 주문에 속한 여러 상세내역을 가져오는 메소드
		ArrayList<OrdDetailInfo> ordDetailList = new ArrayList<OrdDetailInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		OrdDetailInfo ordDetailInfo = null;
		
		try {
			String sql = "select * from t_order_detail a, t_product_list b where a.pl_id = b.pl_id and ol_id = '" + olid + "' ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ordDetailInfo = new OrdDetailInfo();
				ordDetailInfo.setOl_id(rs.getString("ol_id"));
				ordDetailInfo.setPl_id(rs.getString("pl_id"));
				ordDetailInfo.setPl_name(rs.getString("pl_name"));
				ordDetailInfo.setPl_img1(rs.getString("pl_img1"));
				ordDetailInfo.setOd_opt(rs.getString("od_opt"));
				ordDetailInfo.setOd_idx(rs.getInt("od_idx"));
				ordDetailInfo.setOd_cnt(rs.getInt("od_cnt"));
				ordDetailInfo.setOd_price(rs.getInt("od_price"));
				ordDetailInfo.setOd_status(rs.getString("od_status"));
				ordDetailList.add(ordDetailInfo);
			}
		} catch(Exception e) {
			System.out.println("getOrdDetailList() 오류");			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return ordDetailList;
	}
	public OrdListInfo getOrd(String olid) {		//하나의 주문내역을 가져오는 메소드
		OrdListInfo detailInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<OrdDetailInfo> ordDetailList = new ArrayList<OrdDetailInfo>();
		
		try {
			String sql = "select * from t_order_list where ol_id = '" + olid + "' ";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				detailInfo = new OrdListInfo();
				detailInfo.setOl_id(olid);
				detailInfo.setOl_buyer(rs.getString("ol_buyer"));
				detailInfo.setOl_bname(rs.getString("ol_bname"));
				detailInfo.setOl_bname(rs.getString("ol_bname"));
				detailInfo.setOl_bphone(rs.getString("ol_bphone"));
				detailInfo.setOl_bmail(rs.getString("ol_bmail"));
				detailInfo.setOl_rname(rs.getString("ol_rname"));
				detailInfo.setOl_rphone(rs.getString("ol_rphone"));
				detailInfo.setOl_rzip(rs.getString("ol_rzip"));
				detailInfo.setOl_raddr1(rs.getString("ol_raddr1"));
				detailInfo.setOl_raddr2(rs.getString("ol_raddr2"));
				detailInfo.setOl_comment(rs.getString("ol_comment"));
				detailInfo.setOl_payment(rs.getString("ol_payment"));
				detailInfo.setOl_status(rs.getString("ol_status"));
				detailInfo.setOl_date(rs.getString("ol_date"));
				detailInfo.setOl_usepnt(rs.getInt("ol_usepnt"));
				detailInfo.setOl_savepnt(rs.getInt("ol_savepnt"));
				detailInfo.setOl_pay(rs.getInt("ol_pay"));
				
				ordDetailList = getOrdDetailList(olid);
				
				detailInfo.setOrdDetailList(ordDetailList);
			}
		} catch(Exception e) {
			System.out.println("getOrd() 오류");			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return detailInfo;
	}
}
