package com.lmxf.post.entity.supply;

/**
 * 补货商品记录信息
 * 
 * @author Administrator
 * 
 */
public class SupplyProduct {

	private String logid;
	private String sProductId;
	private String sOrderId;
	private String corpId;
	private String supplyId;
	private String productId;
	private int supplyNum;
	private int outNum;
	private String curState;
	private String stateTime;
	private int buyPrice;
	private String createTime;
	private int supplyRNum;
	private int supplyNNum;
	private int supplyDNum;
	private int supplyLNum;

	public int getSupplyRNum() {
		return supplyRNum;
	}

	public void setSupplyRNum(int supplyRNum) {
		this.supplyRNum = supplyRNum;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getsProductId() {
		return sProductId;
	}

	public void setsProductId(String sProductId) {
		this.sProductId = sProductId;
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

	public String getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
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

	public int getOutNum() {
		return outNum;
	}

	public void setOutNum(int outNum) {
		this.outNum = outNum;
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

	public int getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getSupplyNNum() {
		return supplyNNum;
	}

	public void setSupplyNNum(int supplyNNum) {
		this.supplyNNum = supplyNNum;
	}

	public int getSupplyDNum() {
		return supplyDNum;
	}

	public void setSupplyDNum(int supplyDNum) {
		this.supplyDNum = supplyDNum;
	}

	public int getSupplyLNum() {
		return supplyLNum;
	}

	public void setSupplyLNum(int supplyLNum) {
		this.supplyLNum = supplyLNum;
	}

}
