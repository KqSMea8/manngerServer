package com.lmxf.post.entity.order;

import java.util.List;

/**
 * 订单商品信息
 * @author Administrator
 *
 */
public class OrderProduct {
	
	private String logid;
	private String prodetailId;
	private String corpId;
	private String orderId;
	private String torderId;
	private String productId;
	private String productName;
	private String productTypeId;
	private String factoryId;
	private float normalPrice;
	private float salePrice;
	private int saleNum;
	private int outNum;
	private int reNum;
	private String createTime;
	
	private List<OrderBox> orderBoxList;
	private String productCode;
//	private String product_img; // 商品图片 
//	private String metering_num;
//	private String metering_name;
	
	public List<OrderBox> getOrderBoxList() {
		return orderBoxList;
	}
	public void setOrderBoxList(List<OrderBox> orderBoxList) {
		this.orderBoxList = orderBoxList;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getProdetailId() {
		return prodetailId;
	}
	public void setProdetailId(String prodetailId) {
		this.prodetailId = prodetailId;
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
	public String getTorderId() {
		return torderId;
	}
	public void setTorderId(String torderId) {
		this.torderId = torderId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	public float getNormalPrice() {
		return normalPrice;
	}
	public void setNormalPrice(float normalPrice) {
		this.normalPrice = normalPrice;
	}
	public float getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
	}
	public int getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}
	public int getOutNum() {
		return outNum;
	}
	public void setOutNum(int outNum) {
		this.outNum = outNum;
	}
	public int getReNum() {
		return reNum;
	}
	public void setReNum(int reNum) {
		this.reNum = reNum;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
