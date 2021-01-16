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
	
	public ArrayList<CartInfo> getOrdFrmPdtList(String kind, String where) {
	// 장바구니나 바로구매를 통해 결제하려는 사용자에게 보여줄 상품목록을 리턴하는 메소드
		ArrayList<CartInfo> pdtList = new ArrayList<CartInfo>();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "";
			if (kind.equals("cart")) {	// 장바구니를 통한 구매일 경우
				sql = "select c.cl_idx, p.pl_id, p.pl_name, p.pl_img1, p.pl_opt, c.cl_opt, " + 
				" c.cl_cnt, p.pl_price, p.pl_discount from t_cart_list c, t_product_list p " + 
				" where c.pl_id = p.pl_id and p.pl_view = 'y' " + where + 
				" order by p.pl_id, c.cl_opt ";
			} else {	// 바로 구매를 통한 구매일 경우
				
			}
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CartInfo cart = new CartInfo();
				if (kind.equals("cart")) {	// 장바구니를 통한 구매일 경우
					cart.setCl_idx(rs.getInt("cl_idx"));
					cart.setCl_opt(rs.getString("cl_opt"));
					cart.setCl_cnt(rs.getInt("cl_cnt"));
				} else {	// 바로 구매를 통한 구매일 경우
					
				}
				cart.setPl_id(rs.getString("pl_id"));
				cart.setPl_name(rs.getString("pl_name"));
				cart.setPl_img1(rs.getString("pl_img1"));
				cart.setPl_opt(rs.getString("pl_opt"));
				int price = rs.getInt("pl_price");
				if (rs.getInt("pl_discount") > 0) {
					float rate = (float)rs.getInt("pl_discount") / 100;
					price = Math.round(price - (price * rate));
				}	// 상품가격은 할인율이 있을 경우 할인율을 적용한 가격으로 저장함
				cart.setPrice(price);
				pdtList.add(cart);
			}
		} catch(Exception e) {
			System.out.println("getOrdFrmPdtList() 오류");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return pdtList;
	}
	
	public MemberInfo getaddr(String uid) {	//로그인한 회원의 기본 배송지가 있으면 가져오는 메소드
		MemberInfo addrInfo = new MemberInfo();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from t_member_addr where ml_id = '" + uid + "' and ma_basic = 'y' ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				addrInfo.setMaaddr1(rs.getString("ma_addr1"));
				addrInfo.setMaaddr2(rs.getString("ma_addr2"));
				addrInfo.setMazip(rs.getString("ma_zip"));
				addrInfo.setMlid(rs.getString("ml_id"));
			}
		} catch(Exception e) {
			System.out.println("getaddr() 오류");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return addrInfo;
	}
	
	public int getOrdCount(String buyer) {	// 한 회원의 총 주문개수를 가져오는 메소드
		int rcnt = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) from t_order_list where ol_buyer = '" + buyer + "' ";
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
	
	public ArrayList<OrdListInfo> getOrdList(String buyer, int cpage, int psize){	// 한 회원의 총 주문내역을 가져오는 메소드
		ArrayList<OrdListInfo> ordList = new ArrayList<OrdListInfo>();
		ArrayList<OrdDetailInfo> ordDetailList = new ArrayList<OrdDetailInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		OrdListInfo ordListInfo = null;
		int snum = (cpage -1) * psize; // 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
		
		try {
			String sql = "select * from t_order_list where ol_ismember = 'y' and ol_buyer = '" + buyer 
					+ "' group by ol_id order by ol_date limit "+ snum + ", " + psize;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ordListInfo = new OrdListInfo();
				ordListInfo.setOl_id(rs.getString("ol_id"));
				ordListInfo.setOl_buyer(rs.getString("ol_buyer"));
				ordListInfo.setOl_bname(rs.getString("ol_bname"));
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
			String sql = "select * from t_order_detail a, t_product_list b where a.pl_id = b.pl_id and ol_id = '" + olid + "'";
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
				ordDetailList.add(ordDetailInfo);
			}
		} catch(Exception e) {
			System.out.println("getOrdDetailList() 오류");			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return ordDetailList;
	}
	
	public boolean chkNonOrd(String olid, String bname) {	// 입력한 정보에 해당하는 비회원 주문이 있는지를 검사하는 메소드
		boolean isSuccess = false;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from t_order_list where ol_ismember = 'n' and ol_bname = '" + bname 
					+ "' and ol_id = '" + olid + "' ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next())		isSuccess = true;
			System.out.println(isSuccess);
		} catch(Exception e) {
			System.out.println("chkNonOrd() 오류");			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return isSuccess;
	}
	
	public OrdListInfo getOrd(String olid, String where) {		//하나의 주문내역을 가져오는 메소드
		OrdListInfo ordInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<OrdDetailInfo> ordDetailList = new ArrayList<OrdDetailInfo>();
		
		try {
			String sql = "select * from t_order_list where ol_id = '" + olid + "' " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				ordInfo = new OrdListInfo();
				ordInfo.setOl_id(olid);
				ordInfo.setOl_buyer(rs.getString("ol_buyer"));
				ordInfo.setOl_bname(rs.getString("ol_bname"));
				ordInfo.setOl_bname(rs.getString("ol_bname"));
				ordInfo.setOl_bphone(rs.getString("ol_bphone"));
				ordInfo.setOl_bmail(rs.getString("ol_bmail"));
				ordInfo.setOl_rname(rs.getString("ol_rname"));
				ordInfo.setOl_rphone(rs.getString("ol_rphone"));
				ordInfo.setOl_rzip(rs.getString("ol_rzip"));
				ordInfo.setOl_raddr1(rs.getString("ol_raddr1"));
				ordInfo.setOl_raddr2(rs.getString("ol_raddr2"));
				ordInfo.setOl_comment(rs.getString("ol_comment"));
				ordInfo.setOl_payment(rs.getString("ol_payment"));
				ordInfo.setOl_status(rs.getString("ol_status"));
				ordInfo.setOl_date(rs.getString("ol_date"));
				ordInfo.setOl_usepnt(rs.getInt("ol_usepnt"));
				ordInfo.setOl_savepnt(rs.getInt("ol_savepnt"));
				ordInfo.setOl_pay(rs.getInt("ol_pay"));
				
				ordDetailList = getOrdDetailList(olid);
				
				ordInfo.setOrdDetailList(ordDetailList);
			}
		} catch(Exception e) {
			System.out.println("getOrd() 오류");			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return ordInfo;
	}
	
}
