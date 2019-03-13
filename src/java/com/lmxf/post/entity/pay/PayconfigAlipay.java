package com.lmxf.post.entity.pay;

public class PayconfigAlipay {

	private String logid;
	private String payConfigId;
	private String corpId;
	private String payAccept;
	private String autoReturn;
	private String payType;
	private String alipayVersion;
	private String enType;
	private String pid;
	private String key;
	private String isCheckIn;
	private String rate;
	private String createTime;
	private String alipayApi;
	
	private String signType;//加密类型  MD5
	private String tradeType;//交易类型,微信支付取值如下：JSAPI，NATIVE，APP  支付宝支付取值如下：10
	private String body;//商品描述,商品简单描述，该字段须严格按照规范传递String(128)
	private String authBack;
	private String returnNotice;
	private String path;//项目路径
	private String contextPath;//
	 /**支付宝公钥**/
	private String publKey;
	private String title;
	/**支付宝私钥**/
	private String privKey;
	
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getPayConfigId() {
		return payConfigId;
	}
	public void setPayConfigId(String payConfigId) {
		this.payConfigId = payConfigId;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getPayAccept() {
		return payAccept;
	}
	public void setPayAccept(String payAccept) {
		this.payAccept = payAccept;
	}
	public String getAutoReturn() {
		return autoReturn;
	}
	public void setAutoReturn(String autoReturn) {
		this.autoReturn = autoReturn;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getAlipayVersion() {
		return alipayVersion;
	}
	public void setAlipayVersion(String alipayVersion) {
		this.alipayVersion = alipayVersion;
	}
	public String getEnType() {
		return enType;
	}
	public void setEnType(String enType) {
		this.enType = enType;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getIsCheckIn() {
		return isCheckIn;
	}
	public void setIsCheckIn(String isCheckIn) {
		this.isCheckIn = isCheckIn;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAuthBack() {
		return authBack;
	}
	public void setAuthBack(String authBack) {
		this.authBack = authBack;
	}
	public String getReturnNotice() {
		return returnNotice;
	}
	public void setReturnNotice(String returnNotice) {
		this.returnNotice = returnNotice;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public String getPublKey() {
		return publKey;
	}
	public void setPublKey(String publKey) {
		this.publKey = publKey;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrivKey() {
		return privKey;
	}
	public void setPrivKey(String privKey) {
		this.privKey = privKey;
	}
	public String getAlipayApi() {
		return alipayApi;
	}
	public void setAlipayApi(String alipayApi) {
		this.alipayApi = alipayApi;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
}
