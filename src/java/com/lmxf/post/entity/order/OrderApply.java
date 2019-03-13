package com.lmxf.post.entity.order;

import java.util.List;



/**
 * 订单主单信息
 * @author Administrator
 *
 */
public class OrderApply {
	
	private String logid;
	private String orderId;
	private String torderId;
	private String corpId;
	private String districtId;
	private String lineId;
	private String pointId;
	private String siteId;
	private String siteName;
	private String loginId;
	private String loginName;
	private String saleChannel;
	private String outState;
	private int pNum;
	private String favWay;
	private float buyPrice;
	private float salePrice;
	private float payPrice;
	private float favPrice;
	private float returnMoney;
	private float profitMoney;
	private String payType;
	private String payState;
	private String returnType;
	private String fetchOverTime;
	private String curState;
	private String stateTime;
	private String abnomarlState;
	private String outId;
	private String aStateTime;
	private String orderType;
	private String passWord;
	private String encryptionType;
	private String slat;
	private String createTime;
	private String pTradeNo;
	private String rTradeNo;
	private String pushState;
	private String pStateTime;
	
	//虚拟字段
	private List<OrderProduct> orderProductList;
	private List<OrderBox> orderBoxList;
	private List<OrderBox> orderLaneList;
	private String beginTime;
	private String endTime;
	private String openId;
	private String payMethod;
	private String productId;
	private String laneSId;
	private String laneEId;
	private String operId;
	private String operName;
	
	private String appId;
	private String timeStamp;
	private String nonceStr;
	private String pckage;
	private String signType;
	private String paySign;
	private String title;
	private String imgUrl;
	private String codeUrl;
	private String notice;
	
	
	
	public List<OrderBox> getOrderLaneList() {
		return orderLaneList;
	}
	public void setOrderLaneList(List<OrderBox> orderLaneList) {
		this.orderLaneList = orderLaneList;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getOperId() {
		return operId;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getLaneSId() {
		return laneSId;
	}
	public void setLaneSId(String laneSId) {
		this.laneSId = laneSId;
	}
	public String getLaneEId() {
		return laneEId;
	}
	public void setLaneEId(String laneEId) {
		this.laneEId = laneEId;
	}
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTorderId() {
		return torderId;
	}
	public void setTorderId(String torderId) {
		this.torderId = torderId;
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
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getSaleChannel() {
		return saleChannel;
	}
	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}
	public String getOutState() {
		return outState;
	}
	public void setOutState(String outState) {
		this.outState = outState;
	}
	public int getpNum() {
		return pNum;
	}
	public void setpNum(int pNum) {
		this.pNum = pNum;
	}
	public String getFavWay() {
		return favWay;
	}
	public void setFavWay(String favWay) {
		this.favWay = favWay;
	}
	public float getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(float buyPrice) {
		this.buyPrice = buyPrice;
	}
	public float getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
	}
	public float getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(float payPrice) {
		this.payPrice = payPrice;
	}
	public float getFavPrice() {
		return favPrice;
	}
	public void setFavPrice(float favPrice) {
		this.favPrice = favPrice;
	}
	public float getReturnMoney() {
		return returnMoney;
	}
	public void setReturnMoney(float returnMoney) {
		this.returnMoney = returnMoney;
	}
	public float getProfitMoney() {
		return profitMoney;
	}
	public void setProfitMoney(float profitMoney) {
		this.profitMoney = profitMoney;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getFetchOverTime() {
		return fetchOverTime;
	}
	public void setFetchOverTime(String fetchOverTime) {
		this.fetchOverTime = fetchOverTime;
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
	public String getAbnomarlState() {
		return abnomarlState;
	}
	public void setAbnomarlState(String abnomarlState) {
		this.abnomarlState = abnomarlState;
	}
	public String getOutId() {
		return outId;
	}
	public void setOutId(String outId) {
		this.outId = outId;
	}
	public String getaStateTime() {
		return aStateTime;
	}
	public void setaStateTime(String aStateTime) {
		this.aStateTime = aStateTime;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getEncryptionType() {
		return encryptionType;
	}
	public void setEncryptionType(String encryptionType) {
		this.encryptionType = encryptionType;
	}
	public String getSlat() {
		return slat;
	}
	public void setSlat(String slat) {
		this.slat = slat;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getpTradeNo() {
		return pTradeNo;
	}
	public void setpTradeNo(String pTradeNo) {
		this.pTradeNo = pTradeNo;
	}
	public String getrTradeNo() {
		return rTradeNo;
	}
	public void setrTradeNo(String rTradeNo) {
		this.rTradeNo = rTradeNo;
	}
	public List<OrderProduct> getOrderProductList() {
		return orderProductList;
	}
	public void setOrderProductList(List<OrderProduct> orderProductList) {
		this.orderProductList = orderProductList;
	}
	public List<OrderBox> getOrderBoxList() {
		return orderBoxList;
	}
	public void setOrderBoxList(List<OrderBox> orderBoxList) {
		this.orderBoxList = orderBoxList;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPushState() {
		return pushState;
	}
	public void setPushState(String pushState) {
		this.pushState = pushState;
	}
	public String getpStateTime() {
		return pStateTime;
	}
	public void setpStateTime(String pStateTime) {
		this.pStateTime = pStateTime;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getPckage() {
		return pckage;
	}
	public void setPckage(String pckage) {
		this.pckage = pckage;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
}
