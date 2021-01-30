package vo;

public class StatPdtInfo {
	private String ol_date, pl_id, pl_name, od_cnt, year, month;
	private int orders, refund, exchange, cancel, sales;
	// 주문건수, 반품건수, 교환건수, 취소건수, 매출액
	public String getOl_date() {
		return ol_date;
	}
	public void setOl_date(String ol_date) {
		this.ol_date = ol_date;
	}
	public String getPl_id() {
		return pl_id;
	}
	public void setPl_id(String pl_id) {
		this.pl_id = pl_id;
	}
	public String getPl_name() {
		return pl_name;
	}
	public void setPl_name(String pl_name) {
		this.pl_name = pl_name;
	}
	public String getOd_cnt() {
		return od_cnt;
	}
	public void setOd_cnt(String od_cnt) {
		this.od_cnt = od_cnt;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getOrders() {
		return orders;
	}
	public void setOrders(int orders) {
		this.orders = orders;
	}
	public int getRefund() {
		return refund;
	}
	public void setRefund(int refund) {
		this.refund = refund;
	}
	public int getExchange() {
		return exchange;
	}
	public void setExchange(int exchange) {
		this.exchange = exchange;
	}
	public int getCancel() {
		return cancel;
	}
	public void setCancel(int cancel) {
		this.cancel = cancel;
	}
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	
}
