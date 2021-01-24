package admin.action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import admin.svc.*;
import vo.*;

public class APdtUpProcAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		APdtUpProcSvc aPdtUpProcSvc = new APdtUpProcSvc();
		request.setCharacterEncoding("utf-8");
		String uploadPath = "D:/khs/jsp/work/fourplay/WebContent/product/pdt_img";
		// ������ ������ ���� ��ġ�� ����
		int maxSize = 5 * 1024 * 1024;		// ���ε� �ִ� �뷮���� 5MB�� ����
		String id = "", sCata = "", name = "", opt = "", view = "", sizeOpt = "";
		String img1 = "", img2 = "", img3 = "", desc = "";
		String price = "0", cost = "0", discount = "0", stock = "0";

		MultipartRequest multi = new MultipartRequest(
			request, 	// request��ü�� multi�� �����͵��� �ޱ� ����
			uploadPath, // ������ ������ ������ ����� ��ġ ����
			maxSize, 	// �� ���� ���ε��� �� �ִ� �ִ�ũ��(byte����)
			"utf-8", 	// ������ ���ڵ� ���(utf-8, euc-kr, ksc-5601 ��)
			new DefaultFileRenamePolicy());	// ���� �̸��� �ߺ� ó��

		id = multi.getParameter("id");	
		sCata = multi.getParameter("sCata");
		name = multi.getParameter("name");	
		sizeOpt = multi.getParameter("sizeOpt");
		opt = multi.getParameter("allopt");
		view = multi.getParameter("view");	
		price = multi.getParameter("price");
		if (price == null || price.equals(""))	price = "0";
		cost = multi.getParameter("cost");
		if (cost == null || cost.equals(""))	cost = "0";
		stock = multi.getParameter("stock");
		if (stock == null || stock.equals(""))	stock = "0";
		discount = multi.getParameter("discount");
		if (discount == null || discount.equals(""))	discount = "0";
		// ����� ��ǰ�� ���� �޾� ��

		Enumeration files = multi.getFileNames();	// ���ε��� ���� �̸����� Enumeration������ �޾� ��
		while (files.hasMoreElements()) {
			String f = (String)files.nextElement();
			switch (f) {
				case "img1" : img1 = multi.getFilesystemName(f);	break;
				case "img2" : img2 = multi.getFilesystemName(f);	break;
				case "img3" : img3 = multi.getFilesystemName(f);	break;
				case "desc" : desc = multi.getFilesystemName(f);	break;
			}
		}

		if (img1 == null || img1.equals(""))	img1 = multi.getParameter("oldImg1");
		if (img2 == null || img2.equals(""))	img2 = multi.getParameter("oldImg2");
		if (img3 == null || img3.equals(""))	img3 = multi.getParameter("oldImg3");
		if (desc == null || desc.equals(""))	desc = multi.getParameter("oldDesc");
		// �̹����� �������� ���� ��� ���� �̹��� �̸��� �޾� ��

		PdtInfo pdt = new PdtInfo();
		pdt.setPl_id(id);
		pdt.setCs_idx(Integer.parseInt(sCata));		pdt.setPl_price(Integer.parseInt(price));
		pdt.setPl_cost(Integer.parseInt(cost));		pdt.setPl_discount(Integer.parseInt(discount));
		pdt.setPs_stock(Integer.parseInt(stock));	pdt.setPl_name(name);
		pdt.setPl_opt(opt);		pdt.setPl_view(view);	pdt.setPl_img1(img1);
		pdt.setPl_img2(img2);	pdt.setPl_img3(img3);	pdt.setPl_desc(desc);
		// ������ ��ǰ������ PdtInfo�� �ν��Ͻ� pdt�� ����
		
		boolean isSuccess = aPdtUpProcSvc.pdtUpdate(pdt, sizeOpt);
		if (!isSuccess) {	// ��ǰ������ ����������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��ǰ ������ �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		String args = multi.getParameter("args");
		forward.setPath("pdt_detail.pdta" + args + "&id=" + id);
		forward.setRedirect(true);	// �����Ĺ���� �ƴ� �����̷�Ʈ ������� ȭ���� �̵���Ŵ
		return forward;
	}
}
