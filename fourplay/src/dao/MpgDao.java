package dao;

import static db.JdbcUtil.*;
import java.sql.*;
import java.util.ArrayList;

import vo.*;

public class MpgDao {
	private static MpgDao mpgDao;
	private Connection conn;

	private MpgDao() {}

	public static MpgDao getInstance() {
		if (mpgDao == null) {
			mpgDao = new MpgDao();
		}
		return mpgDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public AddrInfo getBasicAddr(String uid) {
	// ȸ�� ���� ������ �ּҸ� �̾ƿ��� �޼ҵ�
		AddrInfo addr = new AddrInfo();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from t_member_addr where ma_basic = 'y' and ml_id = '" + uid + "' " ;
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				addr.setMa_idx(rs.getInt("ma_idx"));
				addr.setMl_id(rs.getString("ml_id"));
				addr.setMa_zip(rs.getString("ma_zip"));
				addr.setMa_addr1(rs.getString("ma_addr1"));
				addr.setMa_addr2(rs.getString("ma_addr2"));
				addr.setMa_basic(rs.getString("ma_basic"));
			}
		} catch(Exception e) {
			System.out.println("getBasicAddr() ����");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return addr;
	}
	
	public ArrayList<AddrInfo> getAddrList(String uid) {
	// ȸ�� �ּҷ� �ּҸ� �̾ƿ��� �޼ҵ�
		ArrayList<AddrInfo> addrList = new ArrayList<AddrInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from t_member_addr where ml_id = '" + uid + "' " ;
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				AddrInfo addr = new AddrInfo();
				addr.setMa_idx(rs.getInt("ma_idx"));
				addr.setMl_id(rs.getString("ml_id"));
				addr.setMa_zip(rs.getString("ma_zip"));
				addr.setMa_addr1(rs.getString("ma_addr1"));
				addr.setMa_addr2(rs.getString("ma_addr2"));
				addr.setMa_basic(rs.getString("ma_basic"));

				addrList.add(addr);
			}
		} catch(Exception e) {
			System.out.println("getAddrList() ����");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return addrList;
	}
	
	public int addrDelete(String idx, String uid) {
	// ȸ���� ������ �ּ�(��)�� �ּҷϿ��� �����ϴ� �޼ҵ�
		int result = 0;
		Statement stmt = null;

		try {
			String[] arrIdx = idx.split(",");
			String where = "";
			for (int i = 0 ; i < arrIdx.length ; i++) {
				where += " or ma_idx = " + arrIdx[i];
			}
			where = " and (" + where.substring(4) + ")";
			String sql = "delete from t_member_addr where ml_id = '" + uid + "' " + where;
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("addrDelete() ����");		e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}
	
//	public int memberUpdate(MemberInfo loginMember, String uid) {
//	// ȸ�� ������ ���� �޼ҵ�
//		int result = 0;
//		Statement stmt = null ;
//		String sql = null;
//		
//		try {
//			sql = "update t_member_list set ml_pwd = '" + loginMember.getMlpwd() + "', ml_phone = '"+ loginMember.getMlphone()
//				+ "', ml_email = '"+ loginMember.getMlemail() + "' where ml_id = '" + uid + "' ";
//			stmt = conn.createStatement();
//			result = stmt.executeUpdate(sql);
//		} catch(Exception e) {
//			System.out.println("memberUpdate() ����");			e.printStackTrace();
//		} finally {
//			close(stmt);
//		}
//		return result;
//	}
	
	public int memDelete(String uid, String pwd) {
	// ȸ�� ���� ó���� ���� �޼ҵ�
		int result = 0;
		Statement stmt = null;
		String sql = null;

		try {
			sql = "update t_member_list set ml_status = 'c' where ml_id = '" + uid + "' and ml_pwd = '" + pwd + "'";
			System.out.println(sql);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("pdtDelete() ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	
	public ArrayList<MemberInfo> getPointList(String uid) {
	// ȸ�� ����Ʈ ������ �̾ƿ��� �޼ҵ�
		ArrayList<MemberInfo> pointList = new ArrayList<MemberInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select mp_date, mp_detail, mp_point, ml_id, if(mp_use = 'a', '+', '-') stat " + 
					" from t_member_point where ml_id = '" + uid + "'" ;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				MemberInfo point = new MemberInfo();
				point.setMpdate(rs.getString("mp_date"));
				point.setMpdetail(rs.getString("mp_detail"));
				point.setMpuse(rs.getString("stat"));
				point.setMppoint(rs.getInt("mp_point"));
				pointList.add(point);
			}
		} catch(Exception e) {
			System.out.println("getPointList() ����");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return pointList;
	}
	public OrdListInfo getPayTotal(String uid) {
	// �� ���ų����� �̾ƿ��� �޼ҵ�
		OrdListInfo total = new OrdListInfo();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select sum(ol_pay) total from t_order_list where ol_buyer = '" + uid + "' ";
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				total.setOl_pay(rs.getInt("total"));
				
			}
		} catch(Exception e) {
			System.out.println("getPayTotal() ����");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return total;
	}
	
	public int addrInsert(AddrInfo addr, String uid) {
		int result = 0;
		Statement stmt = null;
		
		try {
			String sql = "insert into t_member_addr (ml_id, ma_zip, ma_addr1, ma_addr2) values " +
					" ('" + uid + "', '" + addr.getMa_zip() + "', '" + addr.getMa_addr1() + "', '"+ addr.getMa_addr2() + "') "; 
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("addrInsert() ����");		e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}
	
	public int memberUpdate(MemberInfo loginMember, String uid) {
		// ȸ�� ������ ���� �޼ҵ�
			int result = 0;
			Statement stmt = null ;
			String sql = null;
			
			try {
				sql = "update t_member_list set ml_pwd = '" + loginMember.getMlpwd() + "', ml_phone = '"+ loginMember.getMlphone()
					+ "', ml_email = '"+ loginMember.getMlemail() + "' where ml_id = '" + uid + "' ";
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);
			} catch(Exception e) {
				System.out.println("memberUpdate() ����");			e.printStackTrace();
			} finally {
				close(stmt);
			}
			return result;
		}
}




	