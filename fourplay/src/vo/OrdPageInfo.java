package vo;

public class OrdPageInfo {
	private int cpage, pcnt, spage, epage, rcnt, bsize, psize;
	// 현재 페이지 번호, 전체 페이지 수, 시작 페이지, 종료 페이지, 레코드(주문) 개수, 블록크기(한 화면에 몇 페이지까지 보여줄지), 페이지 크기(한 페이지에서 주문 몇개 보여줄지)
	private String sdate, edate;
	
	private String ord;
	// 정렬조건 : 주문자 아이디(오a내d), 주문번호(오a내d), 주문일date(오a내d)
	private String status, schtype, keyword;
	// 검색조건 : 주문상태, 검색조건, 키워드(주문자 아이디, 주문번호)
	
	public int getCpage() {
		return cpage;
	}
	public void setCpage(int cpage) {
		this.cpage = cpage;
	}
	public int getPcnt() {
		return pcnt;
	}
	public void setPcnt(int pcnt) {
		this.pcnt = pcnt;
	}
	public int getSpage() {
		return spage;
	}
	public void setSpage(int spage) {
		this.spage = spage;
	}
	public int getEpage() {
		return epage;
	}
	public void setEpage(int epage) {
		this.epage = epage;
	}
	public int getRcnt() {
		return rcnt;
	}
	public void setRcnt(int rcnt) {
		this.rcnt = rcnt;
	}
	public int getBsize() {
		return bsize;
	}
	public void setBsize(int bsize) {
		this.bsize = bsize;
	}
	public int getPsize() {
		return psize;
	}
	public void setPsize(int psize) {
		this.psize = psize;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSchtype() {
		return schtype;
	}
	public void setSchtype(String schtype) {
		this.schtype = schtype;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
