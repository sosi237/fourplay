package dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class OrdDao {
	private static OrdDao ordDao;
	private Connection conn;
	private OrdDao() {}
	public static OrdDao getInstance() {
		if (ordDao == null) {
			ordDao = new OrdDao();
		}
		return ordDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	public int getOrdCount(String buyer) {	// 주문들의 총 개수를 리턴하는 메소드
		System.out.println("getOrdCount");
		
		int rcnt = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) from t_order_list where ol_buyer = '" + buyer + "' ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	rcnt = rs.getInt(1);
		} catch(Exception e) {
			System.out.println("getOrdCount() 오류");	e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return rcnt;
	}

	public ArrayList<OrdListInfo> getOrdList(String buyer, int cpage, int psize) {
		System.out.println("dao getOrdList");
		
		ArrayList<OrdListInfo> ordList = new ArrayList<OrdListInfo>();
		ArrayList<OrdDetailInfo> ordDetailList = new ArrayList<OrdDetailInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		OrdListInfo ordListInfo = null;		// 하나의 주문정보를 저장한 후 ordList에 저장될 인스턴스
		int snum = (cpage - 1) * psize;		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호

		try {
			//String sql = "select a.*, b.*, c.*  from t_order_list a, t_order_detail b, t_product_list c "
				//	+ " where a.ol_id = b.ol_id and b.pl_id = c.pl_id and ol_buyer = '"+ buyer +"' group by a.ol_id order by a.ol_date "
				//	+ " limit " + snum + ", " + psize;
			String sql = "select * from t_order_list where ol_buyer = '"+ buyer +"' group by ol_id order by ol_date limit " + snum + ", " + psize;
			System.out.println(sql);
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
			System.out.println("getOrdList() 오류");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return ordList;
	}
	
	public ArrayList<OrdDetailInfo> getOrdDetailList(String olid) {	//해당 주문번호의 주문 상세내역 정보를 arraylist로 담아 리턴함
		System.out.println("dao getOrdDetailList");
		
		ArrayList<OrdDetailInfo> ordDetailList = new ArrayList<OrdDetailInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		OrdDetailInfo ordDetailInfo = null;		// 하나의 상품정보를 저장한 후 pdtList에 저장될 인스턴스
		try {
			String sql = "select * from t_order_detail a, t_product_list b where a.pl_id = b.pl_id and ol_id = '" + olid + "' ";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				ordDetailInfo = new OrdDetailInfo();
				ordDetailInfo.setOl_id(rs.getString("ol_id"));
				ordDetailInfo.setPl_id(rs.getString("a.pl_id"));
				ordDetailInfo.setPl_name(rs.getString("pl_name"));
				ordDetailInfo.setPl_img1(rs.getString("pl_img1"));
				ordDetailInfo.setOd_opt(rs.getString("od_opt"));
				ordDetailInfo.setOd_idx(rs.getInt("od_idx"));
				ordDetailInfo.setOd_cnt(rs.getInt("od_cnt"));
				ordDetailInfo.setOd_price(rs.getInt("od_price"));
				ordDetailInfo.setPl_img1(rs.getString("pl_img1"));
				ordDetailInfo.setPl_name(rs.getString("pl_name"));
				ordDetailList.add(ordDetailInfo);
			}
		} catch(Exception e) {
			System.out.println("getOrdDetailList() 오류");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return ordDetailList;
	}
}
