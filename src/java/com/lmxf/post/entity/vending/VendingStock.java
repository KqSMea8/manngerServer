package com.lmxf.post.entity.vending;

/**
 * 售货机商品库存信息
 * @author Administrator
 *
 */
public class VendingStock {
	
	private String logid;
	private String wproductId;
	private String corpId;
	private String siteId;
	private String siteName;
	private String productId;
	private String productName;
	private int num;
	private String recoveryNum;
	private String overdueNum;
	private String typeId;
	private float salePrice;
	private String picUrl;
	private String createTime;
	
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getWproductId() {
		return wproductId;
	}
	public void setWproductId(String wproductId) {
		this.wproductId = wproductId;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getRecoveryNum() {
		return recoveryNum;
	}
	public void setRecoveryNum(String recoveryNum) {
		this.recoveryNum = recoveryNum;
	}
	public String getOverdueNum() {
		return overdueNum;
	}
	public void setOverdueNum(String overdueNum) {
		this.overdueNum = overdueNum;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public float getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
