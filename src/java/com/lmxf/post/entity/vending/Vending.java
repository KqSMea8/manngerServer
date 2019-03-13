package com.lmxf.post.entity.vending;

import java.util.List;

import com.lmxf.post.entity.supply.SupplyVproduct;

/**
 * 售货机的基本信息，主柜信息
 * 
 * @author Administrator
 * 
 */
public class Vending {

	private String logid;
	private String siteId;
	private String siteCode;
	private String siteName;
	private String factoryId;
	private String cabinetType;
	private String deviceId;
	private String districtId;
	private String lineId;
	private String pointId;
	private String longitude;
	private String latitude;
	private String payType;
	private String mediaType;
	private String onlineTime;
	private String initTime;
	private String curState;
	private String stateTime;
	private String netWork;
	private String netSate;
	private String netTime;
	private String sellState;
	private String sellTime;
	private int laneNum;
	private int pMaxNum;
	private int pCurNum;
	private int stockLevel;
	private String corpId;
	private String mConfigId;
	private String configFile;
	private String createTime;
	private String address;
	private String description;
	private String platType;

	// 虚拟字段
	private String fayWay;
	private float discount;
	private List<SupplyVproduct> supplyVproductList;
	private String supplyState;
	private String supplySTime;
	private String underState;
	private String underSTime;

	private VendingState vendingState;

	public String getPlatType() {
		return platType;
	}

	public void setPlatType(String platType) {
		this.platType = platType;
	}

	public VendingState getVendingState() {
		return vendingState;
	}

	public void setVendingState(VendingState vendingState) {
		this.vendingState = vendingState;
	}

	public String getSupplySTime() {
		return supplySTime;
	}

	public void setSupplySTime(String supplySTime) {
		this.supplySTime = supplySTime;
	}

	public String getUnderSTime() {
		return underSTime;
	}

	public void setUnderSTime(String underSTime) {
		this.underSTime = underSTime;
	}

	public String getSupplyState() {
		return supplyState;
	}

	public void setSupplyState(String supplyState) {
		this.supplyState = supplyState;
	}

	public String getUnderState() {
		return underState;
	}

	public void setUnderState(String underState) {
		this.underState = underState;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

	public String getCabinetType() {
		return cabinetType;
	}

	public void setCabinetType(String cabinetType) {
		this.cabinetType = cabinetType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getInitTime() {
		return initTime;
	}

	public void setInitTime(String initTime) {
		this.initTime = initTime;
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

	public String getNetWork() {
		return netWork;
	}

	public void setNetWork(String netWork) {
		this.netWork = netWork;
	}

	public String getNetSate() {
		return netSate;
	}

	public void setNetSate(String netSate) {
		this.netSate = netSate;
	}

	public String getNetTime() {
		return netTime;
	}

	public void setNetTime(String netTime) {
		this.netTime = netTime;
	}

	public String getSellState() {
		return sellState;
	}

	public void setSellState(String sellState) {
		this.sellState = sellState;
	}

	public String getSellTime() {
		return sellTime;
	}

	public void setSellTime(String sellTime) {
		this.sellTime = sellTime;
	}

	public int getLaneNum() {
		return laneNum;
	}

	public void setLaneNum(int laneNum) {
		this.laneNum = laneNum;
	}

	public int getpMaxNum() {
		return pMaxNum;
	}

	public void setpMaxNum(int pMaxNum) {
		this.pMaxNum = pMaxNum;
	}

	public int getpCurNum() {
		return pCurNum;
	}

	public void setpCurNum(int pCurNum) {
		this.pCurNum = pCurNum;
	}

	public int getStockLevel() {
		return stockLevel;
	}

	public void setStockLevel(int stockLevel) {
		this.stockLevel = stockLevel;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getmConfigId() {
		return mConfigId;
	}

	public void setmConfigId(String mConfigId) {
		this.mConfigId = mConfigId;
	}

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFayWay() {
		return fayWay;
	}

	public void setFayWay(String fayWay) {
		this.fayWay = fayWay;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public List<SupplyVproduct> getSupplyVproductList() {
		return supplyVproductList;
	}

	public void setSupplyVproductList(List<SupplyVproduct> supplyVproductList) {
		this.supplyVproductList = supplyVproductList;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((siteId == null) ? 0 : siteId.hashCode());
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
		Vending other = (Vending) obj;
		if (siteId == null) {
			if (other.siteId != null)
				return false;
		}
		if (!siteId.equals(other.siteId))
			return false;
		return true;
	}
}