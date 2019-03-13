package com.lmxf.post.alipay.entity;

import java.io.Serializable;

/**
 * 支付宝的实体
 * 
 * @author zheng.zhiwu payplat 2016-5-13
 */
public class Alipay implements Serializable {
	private static final long serialVersionUID = 8384230850117278977L;
	private String action = null;
	private String method = null;
	private String target = null;
	private String sign = null;
	private String body = null;
	private String _input_charset = null;
	private String total_fee = null;
	private String subject = null;
	private String sign_type = null;
	private String service = null;
	private String notify_url = null;
	private String partner = null;
	private String payment_type = null;
	private String return_url = null;
	private String logistics_type = null;
	private String logistics_fee = null;
	private String receive_address = null;
	private String receive_phone = null;
	private String receive_name = null;
	private String out_trade_no = null;
	private String price = null;
	private String receive_mobile = null;
	private String quantity = null;
	private String seller_email = null;
	private String receive_zip = null;
	private String logistics_payment = null;
	private String show_url = null;
	private String payType = null;
	private String payurl = null;; // 完整的支付http
	private String debuginfo = null;;// debug信息
	private String qrcode = null;
	private String time_expire = null;

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getDebuginfo() {
		return debuginfo;
	}

	public void setDebuginfo(String debuginfo) {
		this.debuginfo = debuginfo;
	}

	public String getPayurl() {
		return payurl;
	}

	public void setPayurl(String payurl) {
		this.payurl = payurl;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String get_input_charset() {
		return _input_charset;
	}

	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getLogistics_type() {
		return logistics_type;
	}

	public void setLogistics_type(String logistics_type) {
		this.logistics_type = logistics_type;
	}

	public String getLogistics_fee() {
		return logistics_fee;
	}

	public void setLogistics_fee(String logistics_fee) {
		this.logistics_fee = logistics_fee;
	}

	public String getReceive_address() {
		return receive_address;
	}

	public void setReceive_address(String receive_address) {
		this.receive_address = receive_address;
	}

	public String getReceive_phone() {
		return receive_phone;
	}

	public void setReceive_phone(String receive_phone) {
		this.receive_phone = receive_phone;
	}

	public String getReceive_name() {
		return receive_name;
	}

	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getReceive_mobile() {
		return receive_mobile;
	}

	public void setReceive_mobile(String receive_mobile) {
		this.receive_mobile = receive_mobile;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSeller_email() {
		return seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public String getReceive_zip() {
		return receive_zip;
	}

	public void setReceive_zip(String receive_zip) {
		this.receive_zip = receive_zip;
	}

	public String getLogistics_payment() {
		return logistics_payment;
	}

	public void setLogistics_payment(String logistics_payment) {
		this.logistics_payment = logistics_payment;
	}

	public String getShow_url() {
		return show_url;
	}

	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}

}
