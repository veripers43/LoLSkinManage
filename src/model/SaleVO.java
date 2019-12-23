package model;

public class SaleVO {
	
	private String saleNo;
	private String userId;
	private String skinName;
	private String saleDate;
	private int no;
	
	public SaleVO(String saleNo, String userId, String skinName, String saleDate) {
		super();
		this.saleNo = saleNo;
		this.userId = userId;
		this.skinName = skinName;
		this.saleDate = saleDate;
	}
	
	
	public String getSaleNo() {
		return saleNo;
	}
	
	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	
	
	public SaleVO(String userId, String skinName, String saleDate) {
		super();
		this.userId = userId;
		this.skinName = skinName;
		this.saleDate = saleDate;
	}
	public SaleVO(String saleDate, String skinName, int no) {
		super();
		this.saleDate = saleDate;
		this.skinName = skinName;
		this.no = no;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	
	public String getSkinName() {
		return skinName;
	}
	public void setSkinName(String skinName) {
		this.skinName = skinName;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setNo(int no) {
		this.no = no;
	}
	public int getNo() {
		return no;
	}
	

	
	
}
