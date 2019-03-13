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
import com.lmxf.post.entity.vending.VendingStock;
import com.lmxf.post.repository.vending.VendingStockDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.VendingProductInfoReq;
import com.lmxf.post.tradepkg.partner.VendingProductInfoResp;

/**
 * 售货机商品信息查询
 * @author Administrator
 *
 */
public class TradeVendingProductInfoService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeVendingProductInfoService.class);
	private VendingProductInfoReq vendingProductInfoReq;
	private VendingProductInfoResp vendingProductInfoResp;
	private VendingStockDao vendingStockDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Map ret = vendingProductInfoReq.parseXml(apply_xml);
		if (ret.get("code") == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
			log.error("vendingProductInfoReq.parseXml is error!");
			return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
		}
		VendingStock vendingStockP = (VendingStock) ret.get("vendingStock");
		String bcode = (String) ret.get("bcode");
		
		Page page = new Page("VendingProductInfo");
		Dict dict=(Dict) CacheUtils.get(CacheUtils.errorCodeCache,"issuedPage_size");
	    String pageNum = dict!=null?dict.getDictValue():null;
		page.setCurrentPage(Integer.parseInt((String) ret.get("istart")));
		page.setPageNum(8);
		if (pageNum != null && !"".equals(pageNum)) {
			try {
				page.setPageNum(Integer.parseInt(pageNum));
			} catch (Exception e) {
				return errorCodeDao.getErrorRespJson(GConstent.Program_App_Execp);
			}
		}
		List<VendingStock> vendingStockList = vendingStockDao.findAll(vendingStockP, page);
		if (vendingStockList == null || vendingStockList.size() < 0) {
			return errorCodeDao.getErrorRespJson(GConstent.Query_No_Data);
		}
		for(VendingStock vendingStock : vendingStockList){
			String productPic = "{\"ProductPic\":"+ vendingStock.getPicUrl() + "}";
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
	        		
	        		vendingStock.setPicUrl(picUrl);
	        		continue;
	        	}
	        }
		}
		return vendingProductInfoResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, page.getTotalCount(), vendingStockList.size(), vendingStockList);
	}

	public void setVendingProductInfoReq(VendingProductInfoReq vendingProductInfoReq) {
		this.vendingProductInfoReq = vendingProductInfoReq;
	}

	public void setVendingProductInfoResp(
			VendingProductInfoResp vendingProductInfoResp) {
		this.vendingProductInfoResp = vendingProductInfoResp;
	}

	public void setVendingStockDao(VendingStockDao vendingStockDao) {
		this.vendingStockDao = vendingStockDao;
	}

}
