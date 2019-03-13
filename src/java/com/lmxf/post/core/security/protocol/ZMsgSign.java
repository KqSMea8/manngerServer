package com.lmxf.post.core.security.protocol;

import java.util.Date;

/**
 * 
 * 此类描述的是：报文安全域
 * 
 * @author: wusijie
 * @version: 2014-6-6 上午10:55:59
 */
public class ZMsgSign {
	private String salt;
	private String algo;
	private String time;
	private String sign;
	private String msgid;
	private String site_id;
	private String site_name;
	private String auth_id;
	private String auth_name;
	private String trade_code;
	private String trade_name;

	private String oauth_id;
	private String oauth_name;
	private Date begin_time;

	public String getTrade_name() {
		return trade_name;
	}

	public void setTrade_name(String tradeName) {
		trade_name = tradeName;
	}

	public String getTrade_code() {
		return trade_code;
	}

	public void setTrade_code(String tradeCode) {
		trade_code = tradeCode;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getAlgo() {
		return algo;
	}

	public void setAlgo(String algo) {
		this.algo = algo;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String siteId) {
		site_id = siteId;
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String siteName) {
		site_name = siteName;
	}

	public String getAuth_id() {
		return auth_id;
	}

	public void setAuth_id(String authId) {
		auth_id = authId;
	}

	public String getAuth_name() {
		return auth_name;
	}

	public void setAuth_name(String authName) {
		auth_name = authName;
	}

	public String getOauth_id() {
		return oauth_id;
	}

	public void setOauth_id(String oauthId) {
		oauth_id = oauthId;
	}

	public String getOauth_name() {
		return oauth_name;
	}

	public void setOauth_name(String oauthName) {
		oauth_name = oauthName;
	}

	public Date getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(Date beginTime) {
		begin_time = beginTime;
	}

	
}
