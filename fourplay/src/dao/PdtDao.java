package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import vo.CataBigInfo;
import vo.CataSmallInfo;
import vo.PdtInfo;

public class PdtDao {
   private static PdtDao pdtDao;
   private Connection conn;

   private PdtDao() {}
   
   public static PdtDao getInstance() {
      if (pdtDao == null) {
         pdtDao = new PdtDao();
      }
      return pdtDao;
   }   
   public void setConnection(Connection conn) {
      this.conn = conn;
   }
   
   public ArrayList<CataBigInfo> getCataBigList() {
   // DB���� ��з� ����� �޾� �����ϴ� �޼ҵ�
      ArrayList<CataBigInfo> cataBigList = new ArrayList<CataBigInfo>();
      CataBigInfo bigInfo = null;
      Statement stmt = null;
      ResultSet rs = null;
      String sql = null;
      
      try {
         sql = "select * from t_cata_big";
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         while (rs.next()) {
            bigInfo = new CataBigInfo();
            // cataBigList�� ���� CataBigInfo�� �ν��Ͻ� ����

            bigInfo.setCb_idx(rs.getInt("cb_idx"));
            bigInfo.setCb_name(rs.getString("cb_name"));
            bigInfo.setCb_date(rs.getString("cb_date"));

            cataBigList.add(bigInfo);
            // ������ ArrayList�� ������ CataBigInfo�� �ν��Ͻ� ����
         }
      } catch(Exception e) {
         System.out.println("getCataBigList() ����");
         e.printStackTrace();
      } finally {
         close(rs);   close(stmt);
      }

      return cataBigList;
   }

   public ArrayList<CataSmallInfo> getCataSmallList() {
   // DB���� �Һз� ����� �޾� �����ϴ� �޼ҵ�
      ArrayList<CataSmallInfo> cataSmallList = new ArrayList<CataSmallInfo>();
      CataSmallInfo smallInfo = null;
      Statement stmt = null;
      ResultSet rs = null;
      String sql = null;
      
      try {
         sql = "select * from t_cata_small order by cb_idx, cs_idx";
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         while (rs.next()) {
            smallInfo = new CataSmallInfo();
            // cataBigList�� ���� CataBigInfo�� �ν��Ͻ� ����

            smallInfo.setCs_idx(rs.getInt("cs_idx"));
            smallInfo.setCb_idx(rs.getInt("cb_idx"));
            smallInfo.setCs_name(rs.getString("cs_name"));
            smallInfo.setCs_date(rs.getString("cs_date"));

            cataSmallList.add(smallInfo);
            // ������ ArrayList�� ������ CataSmallInfo�� �ν��Ͻ� ����
         }
      } catch(Exception e) {
         System.out.println("getCataSmallList() ����");
         e.printStackTrace();
      } finally {
         close(rs);   close(stmt);
      }

      return cataSmallList;
   }

   public int getPdtCount(String where) {
   // ������ �޾ƿ� ���ǿ� �´� ��ǰ���� �� ������ �����ϴ� �޼ҵ�
      int rcnt = 0;
      Statement stmt = null;
      ResultSet rs = null;
      String sql = null;

      try {
         sql = "select count(*) from t_product_list a, t_cata_big b, t_cata_small c " + 
            " where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx " + where;
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         if (rs.next())   rcnt = rs.getInt(1);
      } catch(Exception e) {
         System.out.println("getPdtCount() ����");
         e.printStackTrace();
      } finally {
         close(rs);   close(stmt);
      }

      return rcnt;
   }


   public ArrayList<PdtInfo> getPdtList(String where, String orderby, int cpage, int psize) {
   // �˻����ǰ� ���������� �޾ƿ� ���ǿ� �´� ��ǰ���� �����Ͽ� �� ����� ArrayList<PdtInfo>������ �����ϴ� �޼ҵ�
      ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
      // ��ǰ ����� ������ ArrayList��ü�� PdtInfo�� �ν��Ͻ��� ������
      Statement stmt = null;
      ResultSet rs = null;
      String sql = null;
      PdtInfo pdtInfo = null;      // �ϳ��� ��ǰ������ ������ �� pdtList�� ����� �ν��Ͻ�
      int snum = (cpage - 1) * psize;      // ������ limit ��ɿ��� �����͸� ������ ���� �ε��� ��ȣ

      try {
         sql = "select  a.*, b.cb_name, c.cs_name, d.ps_salecnt, d.ps_stock " + 
               " from t_product_list a,  t_cata_big b, t_cata_small c, t_product_size d " + 
               " where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx and d.pl_id = a.pl_id " +
               where + " group by a.pl_id " + orderby +" limit " + snum + ", " + psize;

         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         while (rs.next()) {
            pdtInfo = new PdtInfo();
            pdtInfo.setPl_id(rs.getString("pl_id"));
            pdtInfo.setCs_idx(rs.getInt("cs_idx"));
            pdtInfo.setPl_name(rs.getString("pl_name"));
            pdtInfo.setPl_price(rs.getInt("pl_price"));
            pdtInfo.setPl_cost(rs.getInt("pl_cost"));
            pdtInfo.setPl_discount(rs.getInt("pl_discount"));
            pdtInfo.setPl_opt(rs.getString("pl_opt"));
            pdtInfo.setPl_img1(rs.getString("pl_img1"));
            pdtInfo.setPl_img2(rs.getString("pl_img2"));
            pdtInfo.setPl_img3(rs.getString("pl_img3"));
            pdtInfo.setPl_desc(rs.getString("pl_desc"));
            pdtInfo.setPl_review(rs.getInt("pl_review"));
            pdtInfo.setPl_view(rs.getString("pl_view"));
            pdtInfo.setPl_date(rs.getString("pl_date"));
            pdtInfo.setPs_stock(rs.getInt("ps_stock"));
            pdtInfo.setAl_idx(rs.getInt("al_idx"));
            pdtList.add(pdtInfo);
         }
      } catch(Exception e) {
         System.out.println("getPdtList() ����");
         e.printStackTrace();
      } finally {
         close(rs);   close(stmt);
      }

      return pdtList;
   }
   public ArrayList<PdtInfo> getBestPdtList(String scata) {
   // �˻����ǰ� ���������� �޾ƿ� ���ǿ� �´� ��ǰ���� �����Ͽ� �� ����� ArrayList<PdtInfo>������ �����ϴ� �޼ҵ�
      ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
      // ��ǰ ����� ������ ArrayList��ü�� PdtInfo�� �ν��Ͻ��� ������
      Statement stmt = null;
      ResultSet rs = null;
      String sql = null;
      PdtInfo pdtInfo = null;      // �ϳ��� ��ǰ������ ������ �� pdtList�� ����� �ν��Ͻ�

      try {
         sql = "select a.*, b.cb_name, c.cs_name , d.ps_salecnt, d.ps_stock " + 
               " from t_product_list a, t_cata_big b, t_cata_small c, t_product_size d " + 
               " where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx and a.pl_view = 'y' and a.cs_idx = '" +
               scata +"' order by d.ps_salecnt desc limit 0,3";
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         while (rs.next()) {
            pdtInfo = new PdtInfo();
            pdtInfo.setPl_id(rs.getString("pl_id"));
            pdtInfo.setCs_idx(rs.getInt("cs_idx"));
            pdtInfo.setPl_name(rs.getString("pl_name"));
            pdtInfo.setPl_price(rs.getInt("pl_price"));
            pdtInfo.setPl_cost(rs.getInt("pl_cost"));
            pdtInfo.setPl_discount(rs.getInt("pl_discount"));
            pdtInfo.setPl_opt(rs.getString("pl_opt"));
            pdtInfo.setPl_img1(rs.getString("pl_img1"));
            pdtInfo.setPl_img2(rs.getString("pl_img2"));
            pdtInfo.setPl_img3(rs.getString("pl_img3"));
            pdtInfo.setPl_desc(rs.getString("pl_desc"));
            pdtInfo.setPs_stock(rs.getInt("ps_stock"));
            pdtInfo.setPs_salecnt(rs.getInt("ps_salecnt"));
            pdtInfo.setPl_review(rs.getInt("pl_review"));
            pdtInfo.setPl_view(rs.getString("pl_view"));
            pdtInfo.setPl_date(rs.getString("pl_date"));
            pdtInfo.setAl_idx(rs.getInt("al_idx"));
            pdtList.add(pdtInfo);
         }
      } catch(Exception e) {
         System.out.println("getPdtList() ����");
         e.printStackTrace();
      } finally {
         close(rs);   close(stmt);
      }

      return pdtList;
   }
   
   public ArrayList<PdtInfo> getSearchPdtList(String keyword) {
      // �˻����ǰ� ���������� �޾ƿ� ���ǿ� �´� ��ǰ���� �����Ͽ� �� ����� ArrayList<PdtInfo>������ �����ϴ� �޼ҵ�
         ArrayList<PdtInfo> searchList = new ArrayList<PdtInfo>();
         // ��ǰ ����� ������ ArrayList��ü�� PdtInfo�� �ν��Ͻ��� ������
         PreparedStatement pstmt = null;
         ResultSet rs = null;
         String sql = null;
         PdtInfo pdtInfo = null;      // �ϳ��� ��ǰ������ ������ �� pdtList�� ����� �ν��Ͻ�

         try {
            if (keyword != null) {
            sql = "select a.*, b.cb_name, c.cs_name , d.ps_salecnt, d.ps_stock " + 
                  " from t_product_list a, t_cata_big b, t_cata_small c, t_product_size d " + 
                  " where a.pl_name like ? or b.cb_name like ? or c.cs_name ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
                pstmt.setString(2, "%" + keyword + "%");
                pstmt.setString(3, "%" + keyword + "%");
            }
            rs = pstmt.executeQuery(sql);
            while (rs.next()) {
               pdtInfo = new PdtInfo();
               pdtInfo.setPl_id(rs.getString("pl_id"));
               pdtInfo.setCs_idx(rs.getInt("cs_idx"));
               pdtInfo.setPl_name(rs.getString("pl_name"));
               pdtInfo.setPl_price(rs.getInt("pl_price"));
               pdtInfo.setPl_cost(rs.getInt("pl_cost"));
               pdtInfo.setPl_discount(rs.getInt("pl_discount"));
               pdtInfo.setPl_opt(rs.getString("pl_opt"));
               pdtInfo.setPl_img1(rs.getString("pl_img1"));
               pdtInfo.setPl_img2(rs.getString("pl_img2"));
               pdtInfo.setPl_img3(rs.getString("pl_img3"));
               pdtInfo.setPl_desc(rs.getString("pl_desc"));
               pdtInfo.setPs_stock(rs.getInt("ps_stock"));
               pdtInfo.setPs_salecnt(rs.getInt("ps_salecnt"));
               pdtInfo.setPl_review(rs.getInt("pl_review"));
               pdtInfo.setPl_view(rs.getString("pl_view"));
               pdtInfo.setPl_date(rs.getString("pl_date"));
               pdtInfo.setAl_idx(rs.getInt("al_idx"));
               
               searchList.add(pdtInfo);
            }
         } catch(Exception e) {
            System.out.println("getPdtList() ����");
            e.printStackTrace();
         } finally {
            close(rs);   close(pstmt);
         }

         return searchList;   
   }
   
   public PdtInfo getPdtInfo(String id) {
   // ������ id�� �ش��ϴ� �ϳ��� ��ǰ������ PdtInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
      PdtInfo pdtInfo = null;
      Statement stmt = null;
      ResultSet rs = null;
      String sql = null;

      try {
         int saleCnt = 0;
         stmt = conn.createStatement();
         sql = "select count(*) from t_order_detail where pl_id = '" + id + "'";
         // ������ ��ǰ�� �Ǹŷ��� ���ϱ� ���� ����
         rs = stmt.executeQuery(sql);
         if (rs.next())   saleCnt = rs.getInt(1);

         sql = "select a.*, b.cb_name, c.cs_name, d.ps_stock, d.ps_salecnt" +
               " from t_product_list a, t_cata_big b, t_cata_small c, t_product_size d " +
               " where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx and a.pl_id = d.pl_id and a.pl_id = '" + id +
               "' group by a.pl_id ";
         rs = stmt.executeQuery(sql);
         if (rs.next()) {
            pdtInfo = new PdtInfo();
            pdtInfo.setPl_id(rs.getString("pl_id"));
            pdtInfo.setCs_idx(rs.getInt("cs_idx"));
            pdtInfo.setPl_name(rs.getString("pl_name"));
            pdtInfo.setPl_price(rs.getInt("pl_price"));
            pdtInfo.setPl_cost(rs.getInt("pl_cost"));
            pdtInfo.setPl_discount(rs.getInt("pl_discount"));
            pdtInfo.setPl_opt(rs.getString("pl_opt"));
            pdtInfo.setPl_img1(rs.getString("pl_img1"));
            pdtInfo.setPl_img2(rs.getString("pl_img2"));
            pdtInfo.setPl_img3(rs.getString("pl_img3"));
            pdtInfo.setPl_desc(rs.getString("pl_desc"));
            pdtInfo.setPs_stock(rs.getInt("ps_stock"));
            pdtInfo.setPs_salecnt(rs.getInt("ps_salecnt"));
            pdtInfo.setPl_review(rs.getInt("pl_review"));
            pdtInfo.setPl_view(rs.getString("pl_view"));
            pdtInfo.setPl_date(rs.getString("pl_date"));
            pdtInfo.setAl_idx(rs.getInt("al_idx"));
         }
      } catch(Exception e) {
         System.out.println("getPdtInfo() ����");
         e.printStackTrace();
      } finally {
         close(rs);   close(stmt);
      }

      return pdtInfo;
   }
}