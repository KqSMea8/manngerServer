package com.lmxf.post.entity.stock;

public class StockProduct {

	private String logid;
	private String pstockId;
	private String productId;
	private String productName;
	private int totalNum;
	private int curNum;
	private int overNum;
	private String corpId;
	private String createTime;
	
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getPstockId() {
		return pstockId;
	}
	public void setPstockId(String pstockId) {
		this.pstockId = pstockId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public int getCurNum() {
		return curNum;
	}
	public void setCurNum(int curNum) {
		this.curNum = curNum;
	}
	public int getOverNum() {
		return overNum;
	}
	public void setOverNum(int overNum) {
		this.overNum = overNum;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
