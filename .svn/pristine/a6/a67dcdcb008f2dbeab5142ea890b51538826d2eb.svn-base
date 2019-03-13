package com.lmxf.post.service.partner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.product.ProductInfo;
import com.lmxf.post.entity.product.ProductOnline;
import com.lmxf.post.entity.vending.VendingLanep;
import com.lmxf.post.repository.product.ProductInfoDao;
import com.lmxf.post.repository.product.ProductOnlineDao;
import com.lmxf.post.repository.vending.VendingLanepDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.LaneProductInfoReq;
import com.lmxf.post.tradepkg.partner.LaneProductInfoResp;

public class TradeLaneProductInfoService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeLaneProductInfoService.class);
	private LaneProductInfoReq laneProductInfoReq;
	private LaneProductInfoResp laneProductInfoResp;
	private VendingLanepDao vendingLanepDao;
	private ProductInfoDao productInfoDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Map ret = laneProductInfoReq.parseXml(apply_xml);
		if (ret.get("code") == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
			log.error("laneProductInfoReq.parseXml is error!");
			return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
		}
		VendingLanep vendingLanepP = (VendingLanep) ret.get("vendingLanep");
		String bcode = (String) ret.get("bcode");
		
		List<VendingLanep> vendingLanepList = vendingLanepDao.findAll(vendingLanepP);
		if (vendingLanepList == null || vendingLanepList.size() < 0) {
			return errorCodeDao.getErrorRespJson(GConstent.Query_No_Data);
		}
		String lanepInfo = "";
		Map<String,ProductInfo> productMap=new HashMap<String, ProductInfo>();
		ProductInfo productOnline=null;
		for(VendingLanep vendingLanep : vendingLanepList){
			if(productMap.get(vendingLanep.getProductId())!=null){
				productOnline=productMap.get(vendingLanep.getProductId());
			}else{
				productOnline = productInfoDao.findByProductId(vendingLanep.getProductId());
				productMap.put(vendingLanep.getProductId(), productOnline);
			}
			if(null != productOnline){
				vendingLanep.setProductName(productOnline.getName());
			}
			if(vendingLanep.getProductPic()!=null && !"".equals(vendingLanep.getProductPic() )){
				String productPic = "{\"ProductPic\":"+ vendingLanep.getProductPic() + "}";
				JSONObject jsonObjectR = JSONObject.fromObject(productPic);
				JSONArray jsonArray = jsonObjectR.getJSONArray("ProductPic");
		        for (int i = 0; i < jsonArray.size(); i++) {
		        	JSONObject jSONObject = (JSONObject) jsonArray.get(i);
		        	String os = jSONObject.getString("os");
		        	String picUrl = jSONObject.getString("pic");
		        	if((bcode.equals(GParameter.platCode_Platform) && os.equals(GParameter.platCode_Platform))
		        		||(bcode.equals(GParameter.platCode_WeChatOfficialAccountPurchase) && os.equals(GParameter.platCode_WeChatOfficialAccountPurchase))
		        		||(bcode.equals(GParameter.platCode_Terminal) && os.equals(GParameter.platCode_Terminal))
		        		||(bcode.equals(GParameter.platCode_WeChatAppletPurchase) && os.equals(GParameter.platCode_WeChatAppletPurchase))
		        		||(bcode.equals(GParameter.platCode_WeChatOfficialAccountReplenishment) && os.equals(GParameter.platCode_WeChatOfficialAccountReplenishment))
		        		||(bcode.equals(GParameter.platCode_AndroidPurchase) && os.equals(GParameter.platCode_AndroidPurchase))
		        		||(bcode.equals(GParameter.platCode_AndroidReplenishment) && os.equals(GParameter.platCode_AndroidReplenishment))
		        		||(bcode.equals(GParameter.platCode_MessageTransfer) && os.equals(GParameter.platCode_MessageTransfer))){
		        		
		        		vendingLanep.setProductPic(picUrl);
		        		continue;
		        	}
		        }
			}else{
				vendingLanep.setProductPic("");
			}
	       lanepInfo = lanepInfo + "$" + (vendingLanep.getLaneSId()+"|"+vendingLanep.getLaneEId()+"|"+vendingLanep.getProductId()+"|"+vendingLanep.getProductName()+"|"+vendingLanep.getProductPic()+"|"+vendingLanep.getCurCap()+"|"+vendingLanep.getCapacity()+"|"+vendingLanep.getWarnCap()+"|"+vendingLanep.getProductSate()+"|"+(productOnline!=null?productOnline.getTypeId():"No")+"|"+(vendingLanep!=null?vendingLanep.getSalePrice():"0"));//+"|"+(productOnline!=null?productOnline.getSpec():"No");
		}
		return laneProductInfoResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1,lanepInfo.substring(1));
	}

	public void setLaneProductInfoReq(LaneProductInfoReq laneProductInfoReq) {
		this.laneProductInfoReq = laneProductInfoReq;
	}

	public void setLaneProductInfoResp(LaneProductInfoResp laneProductInfoResp) {
		this.laneProductInfoResp = laneProductInfoResp;
	}

	public void setVendingLanepDao(VendingLanepDao vendingLanepDao) {
		this.vendingLanepDao = vendingLanepDao;
	}

	public void setProductInfoDao(ProductInfoDao productInfoDao) {
		this.productInfoDao = productInfoDao;
	}



}
