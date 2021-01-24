package dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class CartDao {
   private static CartDao cartDao;
   private Connection conn;
   private CartDao() {}
   public static CartDao getInstance() {
      if (cartDao == null) {
    	  cartDao = new CartDao();
      }
      return cartDao;
   }
   public void setConnection(Connection conn) {
      this.conn = conn;
   }
   public int cartInsert(CartInfo cart) {
		// ����ڰ� ������ ��ǰ�� ��ٱ��Ͽ� ��� �޼ҵ�
			int result = 0;
			Statement stmt = null;
			ResultSet rs = null;

			try {
				stmt = conn.createStatement();
				String sql = "select cl_idx from t_cart_list " + 
					" where cl_buyer = '" + cart.getCl_buyer() + "' " + 
					" and cl_ismember = '" + cart.getCl_ismember() + "' " + 
					" and pl_id = '" + cart.getPl_id() + "' " + 
					" and cl_opt = '" + cart.getCl_opt() + "' ";
				rs = stmt.executeQuery(sql);
				if (rs.next()) {	// ��ٱ����� ���� ������ �� ������ ��ǰ�� ���� ���
					sql = "update t_cart_list set cl_cnt = cl_cnt + " + cart.getCl_cnt() + 
						" where cl_idx = " + rs.getInt("cl_idx");
				} else {	// ó�� ��ٱ��Ͽ� ��� ��ǰ�� ���
					sql = "insert into t_cart_list (cl_buyer, cl_ismember, pl_id, cl_opt, cl_cnt) " +
					" values ('" + cart.getCl_buyer() + "', '" + cart.getCl_ismember() + "', '" + 
					cart.getPl_id() + "', '" + cart.getCl_opt() + "', '" + cart.getCl_cnt() + "')";
				}

				result = stmt.executeUpdate(sql);

			} catch(Exception e) {
				System.out.println("cartInsert() ����");		e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}

			return result;
		}   
	public ArrayList<CartInfo> getCartList(String where) {
		// ��ٱ��Ͽ��� ������ Ư�� �����(ȸ��, ��ȸ��)�� ��ٱ��� ����� �����ϴ� �޼ҵ�
		ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select c.cl_idx, p.pl_id, p.pl_name, p.pl_img1, " +
					" p.pl_opt, c.cl_opt, c.cl_cnt, p.pl_price,p.pl_discount, s.ps_stock " + 
					" from t_cart_list c , t_product_list p, t_product_size s" + 
					" where c.pl_id = p.pl_id and p.pl_view = 'y' and s.ps_stock != 0 " + where + 
					" group by c.cl_idx order by p.pl_id, c.cl_opt";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				CartInfo cart = new CartInfo();
				cart.setCl_idx(rs.getInt("cl_idx"));
				cart.setPl_id(rs.getString("pl_id"));
				cart.setPl_name(rs.getString("pl_name"));
				cart.setPl_img1(rs.getString("pl_img1"));
				cart.setPl_opt(rs.getString("pl_opt"));
				cart.setCl_opt(rs.getString("cl_opt"));
				cart.setCl_cnt(rs.getInt("cl_cnt"));
				cart.setPs_stock(rs.getInt("ps_stock"));
				cart.setPl_price(rs.getInt("pl_price"));
				cart.setPl_discount(rs.getInt("pl_discount"));
				int price = rs.getInt("pl_price");	// �� ���Ű�
				if (rs.getInt("pl_discount") > 0) {
					float rate = (float)rs.getInt("pl_discount") / 100;
					price = Math.round(price - (price * rate));
				}	// ��ǰ ������ �������� ���� ��� �������� ������ �������� ������
				cart.setPrice(price);
				cartList.add(cart);
			}
		} catch(Exception e) {
			System.out.println("getCartList() ����");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return cartList;		
	}
	public int cartCntUpdate( String idx, String cnt, String buyer, String isMember) {
		// ����ڰ� ������ ��ǰ�� ������ �����ϴ� �޼ҵ�
			int result = 0;
			Statement stmt = null;

			try {
				stmt = conn.createStatement();
				String sql = "update t_cart_list set cl_cnt = '" + cnt + "' where cl_buyer = '" + 
					buyer + "' and cl_ismember = '" + isMember + "' and cl_idx = " + idx;

				result = stmt.executeUpdate(sql);
			} catch(Exception e) {
				System.out.println("cartCntUpdate() ����");		e.printStackTrace();
			} finally {
				close(stmt);
			}

			return result;
		}
	public int cartDelete(String idx, String buyer, String isMember) {
	// ����ڰ� ������ ��ǰ(��)�� ��ٱ��Ͽ��� �����ϴ� �޼ҵ�
		int result = 0;
		Statement stmt = null;

		try {
			String[] arrIdx = idx.split(",");
			String where = "";
			for (int i = 0 ; i < arrIdx.length ; i++) {
				where += " or cl_idx = " + arrIdx[i];
			}
			where = " and (" + where.substring(4) + ")";

			String sql = "delete from t_cart_list where cl_buyer = '" + buyer + 
				"' and cl_ismember = '" + isMember + "' " + where;

			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("cartDelete() ����");		e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}

   public int wishInsert(CartInfo cart) {
		// ����ڰ� ������ ��ǰ�� ��ٱ��Ͽ� ��� �޼ҵ�
			int result = 0;
			Statement stmt = null;
			ResultSet rs = null;

			try {
				stmt = conn.createStatement();
				String sql = "select wl_id from t_wish_list " + 
					" where pl_id = '" + cart.getPl_id() + "' " +
					" and ml_id = '" + cart.getMl_id() + "'";
				rs = stmt.executeQuery(sql);
				if (!rs.next()) {	// ó�� ��ٱ��Ͽ� ��� ��ǰ�� ���
					sql = "insert into t_wish_list (ml_id, pl_id) " +
					" values ('" + cart.getMl_id() + "', '" + cart.getPl_id() +  "')";
					result = stmt.executeUpdate(sql);
				}

			} catch(Exception e) {
				System.out.println("wishInsert() ����");		e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}

			return result;
		} 
   
   public int wishDelete(String id, String buyer) {
		// ����ڰ� ������ ��ǰ(��)�� ��ٱ��Ͽ��� �����ϴ� �޼ҵ�
			int result = 0;
			Statement stmt = null;

			try {
				String sql = "delete from t_wish_list where ml_id = '" + buyer + 
					"' and wl_id = '" + id + "'";

				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);
			} catch(Exception e) {
				System.out.println("wishDelete() ����");		e.printStackTrace();
			} finally {
				close(stmt);
			}

			return result;
		}
   
	public ArrayList<CartInfo> getWishList(String where) {
		// ��ٱ��Ͽ��� ������ Ư�� �����(ȸ��, ��ȸ��)�� ��ٱ��� ����� �����ϴ� �޼ҵ�
		ArrayList<CartInfo> wishList = new ArrayList<CartInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select w.wl_id, p.pl_id, p.pl_name, p.pl_img1, p.pl_price, p.pl_discount" + 
					" from t_wish_list w ,t_member_list m, t_product_list p " + 
					" where w.ml_id = m.ml_id and w.pl_id = p.pl_id and p.pl_view = 'y' " + where + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				CartInfo wish = new CartInfo();
				wish.setWl_id(rs.getInt("wl_id"));
				wish.setPl_id(rs.getString("pl_id"));
				wish.setPl_name(rs.getString("pl_name"));
				wish.setPl_img1(rs.getString("pl_img1"));
				int price = rs.getInt("pl_price");	// �� ���Ű�
				if (rs.getInt("pl_discount") > 0) {
					float rate = (float)rs.getInt("pl_discount") / 100;
					price = Math.round(price - (price * rate));
				}	// ��ǰ ������ �������� ���� ��� �������� ������ �������� ������
				wish.setPrice(price);
				wishList.add(wish);
			}
		} catch(Exception e) {
			System.out.println("getWishList() ����");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return wishList;		
	}
}