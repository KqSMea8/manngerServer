package com.lmxf.post.entity.vending;

public class VendingLsdhandle {
	private String logid;
	private String lsdhandleId;
	private String lsdifferId;
	private String siteId;
	private int laneSId;
	private int laneEId;
	private String productId;
	private String proboxId;
	private String handleType;
	private String cont;
	private String operId;
	private String operTime;
	private String createTime;
	private String corpId;

	private VendingLsdiffer vendingLsdiffer;
	
	public VendingLsdiffer getVendingLsdiffer() {
		return vendingLsdiffer;
	}

	public void setVendingLsdiffer(VendingLsdiffer vendingLsdiffer) {
		this.vendingLsdiffer = vendingLsdiffer;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getLsdhandleId() {
		return lsdhandleId;
	}

	public void setLsdhandleId(String lsdhandleId) {
		this.lsdhandleId = lsdhandleId;
	}

	public String getLsdifferId() {
		return lsdifferId;
	}

	public void setLsdifferId(String lsdifferId) {
		this.lsdifferId = lsdifferId;
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

	public String getProboxId() {
		return proboxId;
	}

	public void setProboxId(String proboxId) {
		this.proboxId = proboxId;
	}

	public String getHandleType() {
		return handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

}
