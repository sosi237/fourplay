package admin.action;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class APdtListAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		// ��ǰ ����� ������ ArrayList��ü�� PdtInfo�� �ν��Ͻ��� ������

		request.setCharacterEncoding("utf-8");
		int cpage = 1, pcnt, spage, epage, rcnt, bsize = 10, psize = 10;
		// ����¡�� �ʿ��� ������ ������ ���� ���� �� �ʱ�ȭ
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)
			psize = Integer.parseInt(request.getParameter("psize"));

		// �˻����� ������Ʈ���� ����
		String isview, schtype, keyword, sdate, edate, bcata, scata, sprice, eprice, stock;
		isview	= request.getParameter("isview");	// �Խÿ���(y, n)
		schtype = request.getParameter("schtype");	// �˻��������� ��ǰ���̵�(id)�� ��ǰ��(name)
		keyword = request.getParameter("keyword");	// �˻���
		sdate	= request.getParameter("sdate");	// ��ϱⰣ �� �˻� ������
		edate	= request.getParameter("edate");	// ��ϱⰣ �� �˻� ������
		bcata	= request.getParameter("bcata");	// ��з�
		scata	= request.getParameter("scata");	// �Һз�
		sprice	= request.getParameter("sprice");	// ���ݴ� �� ���� ���ݴ�
		eprice	= request.getParameter("eprice");	// ���ݴ� �� ���� ���ݴ�
		stock	= request.getParameter("stock");	// ���(�̻�)

		// �������� : ����price(��a��d), ��ǰ��name(��a), �����date(��a��d), �α�salecnt(��d), ����review(��d)
		String ord = request.getParameter("ord");

		String where = "", orderby = "";
		if (isview != null && !isview.equals(""))	where += " and a.pl_view = '" + isview + "' ";
		if (sdate != null && !sdate.equals(""))		where += " and a.pl_date >= '" + sdate + "' ";
		if (edate != null && !edate.equals(""))		where += " and a.pl_date <= '" + edate + " 23:59:59' ";
		if (bcata != null && !bcata.equals(""))		where += " and b.cb_idx  = '" + bcata + "' ";
		if (scata != null && !scata.equals(""))		where += " and a.cs_idx = '" + scata + "' ";
		if (sprice != null && !sprice.equals(""))	where += " and a.pl_price >= '" + sprice + "' ";
		if (eprice != null && !eprice.equals(""))	where += " and a.pl_price <= '" + eprice + "' ";
		if (keyword != null && !keyword.equals(""))	where += " and a.pl_" + schtype + " like '%" + keyword + "%' ";
		if (stock != null && !stock.equals("")) {
			if (stock.equals("-1") || stock.equals("0"))	where += " and a.pl_stock = '" + stock + "' ";
			// ����� ���Ѵ��̰ų� 0(ǰ��)�̸� �ش� ��ǰ���� �˻��ϰ�, 
			else									where += " and a.pl_stock >= '" + stock + "' ";
			// ����� �Ϲ� ������ ��� �ش� ���� �̻��� ��ǰ���� �˻���(���Ѵ� ����)
		}
		// �˻����ǿ� where�� ����

		if (ord != null && !ord.equals(""))
			orderby = " order by a.pl_" + ord.substring(0, ord.length() - 1) + 
			(ord.substring(ord.length() - 1).equals("d") ? " desc" : " asc");
		// ���İ� : pricea, priced, namea, datea, dated, salecntd, reviewd

		APdtListSvc pdtListSvc = new APdtListSvc();
		
		rcnt = pdtListSvc.getPdtCount(where);	// �˻��� ��ǰ�� �� ����(������ ������ ���ϱ� ���� �ʿ�)

		pdtList = pdtListSvc.getPdtList(where, orderby, cpage, psize);
		// �� ���������� ������ �˻��� ��ǰ���
		// �˻�����, ��������, limit���� ����� ���� ���ϱ� ���� ������������ ������ũ�⸦ �μ��� ������

		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;				// ��ü �������� ����
		spage = (cpage - 1) / psize * psize + 1;	// ��� ���������� ��ȣ
		epage = spage + psize - 1;
		if (epage > pcnt)	epage = pcnt;			// ��� ���������� ��ȣ

		PdtPageInfo pageInfo = new PdtPageInfo();	// ����¡�� �ʿ��� ������ ���� �ν��Ͻ� ����
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setPcnt(pcnt);			// ��ü ������ ����
		pageInfo.setSpage(spage);		// ��� ���������� ��ȣ
		pageInfo.setEpage(epage);		// ��� ���������� ��ȣ
		pageInfo.setRcnt(rcnt);			// ��ü ��ǰ(���ڵ�) ����
		pageInfo.setBsize(bsize);		// ��ϳ� ������ ����
		pageInfo.setPsize(psize);		// �������� ��ǰ ����

		pageInfo.setIsview(isview);		// �Խÿ���(��ü�Խ�, �Խû�ǰ, �̰Խû�ǰ)
		pageInfo.setSchtype(schtype);	// �˻�����(��ǰ�ڵ�, ��ǰ��)
		pageInfo.setKeyword(keyword);	// �˻���
		pageInfo.setSdate(sdate);		// ����� �˻� ������
		pageInfo.setEdate(edate);		// ����� �˻� ������
		pageInfo.setBcata(bcata);		// ��з�
		pageInfo.setScata(scata);		// �Һз�
		pageInfo.setSprice(sprice);		// ���ݴ� ���� ����
		pageInfo.setEprice(eprice);		// ���ݴ� ���� ����
		pageInfo.setStock(stock);		// ���(�̻�)
		pageInfo.setOrd(ord);			// ��������

		APdtInFormSvc pdtInFormSvc = new APdtInFormSvc();
		// ��з�, �Һз�, �귣�� ����� �������� ���� SvcŬ����
		ArrayList<CataBigInfo> cataBigList = pdtInFormSvc.getCataBigList();			// ��з� ���
		ArrayList<CataSmallInfo> cataSmallList = pdtInFormSvc.getCataSmallList();	// �Һз� ���

		request.setAttribute("pdtList", pdtList);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("cataBigList", cataBigList);
		request.setAttribute("cataSmallList", cataSmallList);
		// ��ǰ��� ȭ��(pdt_list.jsp)���� ���(pdtList)�� ����¡ ����(pageInfo), �з����� request�� ��� ������

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/product_list.jsp");
		return forward;
	}
}
