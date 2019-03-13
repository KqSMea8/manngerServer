package com.lmxf.post.entity.supply;

import com.lmxf.post.entity.vending.VendingLanep;

/**
 * 补货站点商品
 * 
 * @author Administrator
 * 
 */
public class SupplyVproduct {

	private String logid;
	private String sVendingId;
	private String vOrderId;
	private String sOrderId;
	private String corpId;
	private String districtId;
	private String lineId;
	private String pointId;
	private String siteId;
	private int laneSId;
	private int laneEId;
	private String productId;
	private int supplyNum;
	private int rSupplyNum;
	private int saleNum;
	private String finshState;
	private String curState;
	private String stateTime;
	private String invalidTime;
	private String invalidState;
	private float buyPrice;
	private String createTime;

	// 虚拟字段
	private String productName;
	private String productPic;
	private int curNum;
	private VendingLanep vendingLanep;
	private Integer[] damageLaneSId;

	public Integer[] getDamageLaneSId() {
		return damageLaneSId;
	}

	public void setDamageLaneSId(Integer[] damageLaneSId) {
		this.damageLaneSId = damageLaneSId;
	}

	public VendingLanep getVendingLanep() {
		return vendingLanep;
	}

	public void setVendingLanep(VendingLanep vendingLanep) {
		this.vendingLanep = vendingLanep;
	}

	public String getvOrderId() {
		return vOrderId;
	}

	public void setvOrderId(String vOrderId) {
		this.vOrderId = vOrderId;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getsVendingId() {
		return sVendingId;
	}

	public void setsVendingId(String sVendingId) {
		this.sVendingId = sVendingId;
	}

	public String getsOrderId() {
		return sOrderId;
	}

	public void setsOrderId(String sOrderId) {
		this.sOrderId = sOrderId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getSupplyNum() {
		return supplyNum;
	}

	public void setSupplyNum(int supplyNum) {
		this.supplyNum = supplyNum;
	}

	public int getrSupplyNum() {
		return rSupplyNum;
	}

	public void setrSupplyNum(int rSupplyNum) {
		this.rSupplyNum = rSupplyNum;
	}

	public int getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}

	public String getFinshState() {
		return finshState;
	}

	public void setFinshState(String finshState) {
		this.finshState = finshState;
	}

	public String getCurState() {
		return curState;
	}

	public void setCurState(String curState) {
		this.curState = curState;
	}

	public String getStateTime() {
		return stateTime;
	}

	public void setStateTime(String stateTime) {
		this.stateTime = stateTime;
	}

	public String getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getInvalidState() {
		return invalidState;
	}

	public void setInvalidState(String invalidState) {
		this.invalidState = invalidState;
	}

	public float getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(float buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPic() {
		return productPic;
	}

	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}

	public int getCurNum() {
		return curNum;
	}

	public void setCurNum(int curNum) {
		this.curNum = curNum;
	}
}
