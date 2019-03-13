package com.lmxf.post.entity.product;

import com.lmxf.post.entity.vending.VendingLanep;

/**
 * 站点货道商品下架记录
 * @author Administrator
 *
 */
public class ProductLunder {

	private String logid;
	private String lunderId;
	private String vUnderId;
	private String underId;
	private String districtId;
	private String lineId;
	private String pointId;
	private String siteId;
	private String siteName;
	private String productId;
	private int underNum;
	private int laneSId;
	private int laneEId;
	private String corpId;
	private String createTime;
	private String curState;
	
	private VendingLanep vendingLanep;
	
	
	public VendingLanep getVendingLanep() {
		return vendingLanep;
	}
	public void setVendingLanep(VendingLanep vendingLanep) {
		this.vendingLanep = vendingLanep;
	}
	public String getvUnderId() {
		return vUnderId;
	}
	public void setvUnderId(String vUnderId) {
		this.vUnderId = vUnderId;
	}
	public String getCurState() {
		return curState;
	}
	public void setCurState(String curState) {
		this.curState = curState;
	}
	//虚拟字段
	private String[] lunderIds;
	
	
	public String[] getLunderIds() {
		return lunderIds;
	}
	public void setLunderIds(String[] lunderIds) {
		this.lunderIds = lunderIds;
	}
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getLunderId() {
		return lunderId;
	}
	public void setLunderId(String lunderId) {
		this.lunderId = lunderId;
	}
	public String getUnderId() {
		return underId;
	}
	public void setUnderId(String underId) {
		this.underId = underId;
	}
	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
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
	public int getUnderNum() {
		return underNum;
	}
	public void setUnderNum(int underNum) {
		this.underNum = underNum;
	}
	public int getLaneSId() {
		return laneSId;
	}
	public void setLaneSId(int laneSId) {
		this.laneSId = laneSId;
	}
	public int getLaneEId() {
		return laneEId;
	}
	public void setLaneEId(int laneEId) {
		this.laneEId = laneEId;
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
