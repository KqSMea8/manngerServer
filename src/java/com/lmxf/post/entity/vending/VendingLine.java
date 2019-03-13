package com.lmxf.post.entity.vending;

import java.util.List;

import com.lmxf.post.entity.product.ProductVUnder;

/**
 * 点位的线路信息
 * @author Administrator
 *
 */
public class VendingLine {

	private String logid;
	private String districtId;
	private String lineId;
	private String name;
	private int lineNum;
	private int onlineNum;
	private String description;
	private String createTime;
	private String corpId;
	
	//虚拟字段
	private String districtName;
	private List<ProductVUnder> productVUnder;
	
	
	
	public List<ProductVUnder> getProductVUnder() {
		return productVUnder;
	}
	public void setProductVUnder(List<ProductVUnder> productVUnder) {
		this.productVUnder = productVUnder;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLineNum() {
		return lineNum;
	}
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
	public int getOnlineNum() {
		return onlineNum;
	}
	public void setOnlineNum(int onlineNum) {
		this.onlineNum = onlineNum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lineId == null) ? 0 : lineId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendingLine other = (VendingLine) obj;
		if (lineId == null) {
			if (other.lineId != null)
				return false;
		} 
		if (!lineId.equals(other.lineId))
			return false;
		return true;
	}
}
