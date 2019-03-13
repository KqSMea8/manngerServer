package com.lmxf.post.entity.supply;

import java.util.List;

import com.lmxf.post.entity.vending.Vending;

/**
 * 补货站点商品
 * 
 * @author Administrator
 * 
 */
public class SupplyVOrder {

	private String logid;
	private String vOrderId;
	private String sOrderId;
	private String supplierId;
	private String corpId;
	private String supplyId;
	private String curState;
	private String stateTime;
	private String siteId;
	private String lineId;
	private int supplyNum;
	private String createTime;

	private Vending vending;
	private List<SupplyVproduct> supplyVProductList;
	
	

	public List<SupplyVproduct> getSupplyVProductList() {
		return supplyVProductList;
	}

	public void setSupplyVProductList(List<SupplyVproduct> supplyVProductList) {
		this.supplyVProductList = supplyVProductList;
	}

	public Vending getVending() {
		return vending;
	}

	public void setVending(Vending vending) {
		this.vending = vending;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getvOrderId() {
		return vOrderId;
	}

	public void setvOrderId(String vOrderId) {
		this.vOrderId = vOrderId;
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

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public int getSupplyNum() {
		return supplyNum;
	}

	public void setSupplyNum(int supplyNum) {
		this.supplyNum = supplyNum;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
