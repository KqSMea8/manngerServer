package com.lmxf.post.service.partner;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.parameter.Dict;
import com.lmxf.post.entity.product.ProductClassify;
import com.lmxf.post.entity.product.ProductInfo;
import com.lmxf.post.repository.product.ProductClassifyDao;
import com.lmxf.post.repository.product.ProductInfoDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.ProductInfoReq;
import com.lmxf.post.tradepkg.partner.ProductInfoResp;

public class TradeProductInfoService extends TradeProcess{

	private final static Log log = LogFactory.getLog(TradeProductInfoService.class);
	private ProductInfoReq productInfoReq;
	private ProductInfoResp productInfoResp;
	private ProductInfoDao productInfoDao;
	private ProductClassifyDao productClassifyDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		
		Map ret = productInfoReq.parseXml(apply_xml);
		
		if (ret.get("code") == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
			log.error("productInfoReq.parseXml is error!");
			return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
		}
		
		String bcode = (String) ret.get("bcode");
		
		Page page = new Page("ProductInfo");
		Dict dict=(Dict) CacheUtils.get(CacheUtils.errorCodeCache,"issuedPage_size");
	    String pageNum = dict!=null?dict.getDictValue():null;
		page.setCurrentPage(Integer.parseInt((String) ret.get("istart")));
		page.setPageNum(20);
		if (pageNum != null && !"".equals(pageNum)) {
			try {
				page.setPageNum(Integer.parseInt(pageNum));
			} catch (Exception e) {
				return errorCodeDao.getErrorRespJson(GConstent.Program_App_Execp);
			}
		}
		ProductInfo productInfoP = (ProductInfo) ret.get("productInfo");
		List<ProductInfo> productInfoList = productInfoDao.findAll(productInfoP, page);
		
		if(null == productInfoList || productInfoList.size() < 0){
			return errorCodeDao.getErrorRespJson(GConstent.Product_Info_No_Found);
		}
		
		for(ProductInfo productInfo : productInfoList){
			ProductClassify productClassifyP = new ProductClassify();
			productClassifyP.setClassifyId(productInfo.getTypeId());
			productClassifyP.setCorpId(productInfo.getCorpId());
			ProductClassify productClassify = productClassifyDao.findOne(productClassifyP);
			if(productClassify != null){
				productInfo.setTypeName(productClassify.getClassifyName());
			}
			String productPic = "{\"ProductPic\":"+ productInfo.getPicJson() + "}";
			JSONObject jsonObjectR = JSONObject.fromObject(productPic);
			JSONArray jsonArray = jsonObjectR.getJSONArray("ProductPic");
	        for (int i = 0; i < jsonArray.size(); i++) {
	        	JSONObject jSONObject = (JSONObject) jsonArray.get(i);
	        	String os = jSONObject.getString("os");
	        	String picUrl = jSONObject.getString("pic");
	        	if((bcode.equals(GParameter.platCode_Platform) && os.equals(GParameter.platName_Platform))
	        		||(bcode.equals(GParameter.platCode_WeChatOfficialAccountPurchase) && os.equals(GParameter.platName_WeChatOfficialAccountPurchase))
	        		||(bcode.equals(GParameter.platCode_Terminal) && os.equals(GParameter.platName_Terminal))
	        		||(bcode.equals(GParameter.platCode_WeChatAppletPurchase) && os.equals(GParameter.platName_WeChatAppletPurchase))
	        		||(bcode.equals(GParameter.platCode_WeChatOfficialAccountReplenishment) && os.equals(GParameter.platName_WeChatOfficialAccountReplenishment))
	        		||(bcode.equals(GParameter.platCode_AndroidPurchase) && os.equals(GParameter.platName_AndroidPurchase))
	        		||(bcode.equals(GParameter.platCode_AndroidReplenishment) && os.equals(GParameter.platName_AndroidReplenishment))
	        		||(bcode.equals(GParameter.platCode_MessageTransfer) && os.equals(GParameter.platName_MessageTransfer))){
	        		productInfo.setPicJson(picUrl);
	        		continue;
	        	}
	        }
		}
		return productInfoResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, productInfoList.size(), productInfoList.size() ,productInfoList);
	}
	public void setProductInfoReq(ProductInfoReq productInfoReq) {
		this.productInfoReq = productInfoReq;
	}
	public void setProductInfoResp(ProductInfoResp productInfoResp) {
		this.productInfoResp = productInfoResp;
	}
	public void setProductInfoDao(ProductInfoDao productInfoDao) {
		this.productInfoDao = productInfoDao;
	}
	public void setProductClassifyDao(ProductClassifyDao productClassifyDao) {
		this.productClassifyDao = productClassifyDao;
	}
	
}
