package com.lmxf.post.entity.order;

/**
 * 订单状态信息
 * @author Administrator
 *
 */
public class OrderChange {
	
	private String logid;
	private String changeId;
	private String corpId;
	private String orderId;
	private String operId;
	private String operName;
	private String siteId;
	private String siteName;
	private String operAction;
	private String operTime;
	private String operCont;
	private String pocState;
	private int pocTimes;
	private String pocResult;
	private String createTime;
	private String proState;
	
	
	public String getProState() {
		return proState;
	}
	public void setProState(String proState) {
		this.proState = proState;
	}
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getChangeId() {
		return changeId;
	}
	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
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
	public String getOperAction() {
		return operAction;
	}
	public void setOperAction(String operAction) {
		this.operAction = operAction;
	}
	public String getOperTime() {
		return operTime;
	}
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	public String getOperCont() {
		return operCont;
	}
	public void setOperCont(String operCont) {
		this.operCont = operCont;
	}
	public String getPocState() {
		return pocState;
	}
	public void setPocState(String pocState) {
		this.pocState = pocState;
	}
	public int getPocTimes() {
		return pocTimes;
	}
	public void setPocTimes(int pocTimes) {
		this.pocTimes = pocTimes;
	}
	public String getPocResult() {
		return pocResult;
	}
	public void setPocResult(String pocResult) {
		this.pocResult = pocResult;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
