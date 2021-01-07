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
				System.out.println(cart);
				System.out.println(sql);
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
					" from t_cart_list c , t_product_list p t_product_size s" + 
					" where c.pl_id = p.pl_id and p.pl_view = 'y' and p.ps_stock != 0 " + where + 
					" order by p.pl_id, c.cl_opt";
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
}