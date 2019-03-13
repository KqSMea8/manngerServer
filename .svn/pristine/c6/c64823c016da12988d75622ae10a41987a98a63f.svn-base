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
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.repository.product.ProductClassifyDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.ProductClassifyReq;
import com.lmxf.post.tradepkg.partner.ProductClassifyResp;

public class TradeProductClassifyService extends TradeProcess{

	private final static Log log = LogFactory.getLog(TradeProductClassifyService.class);
	private ProductClassifyReq productClassifyReq;
	private ProductClassifyResp productClassifyResp;
	private VendingDao vendingDao;
	private ProductClassifyDao productClassifyDao;
	
	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Map ret = productClassifyReq.parseXml(apply_xml);
		if (ret.get("code") == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
			log.error("productClassifyReq.parseXml is error!");
			return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
		}
		
		Vending vendingP = (Vending) ret.get("vending");
		Vending vending=vendingDao.findBySiteId(vendingP.getSiteId());
		if(null == vending){
			return errorCodeDao.getErrorRespJson(GConstent.Site_No_Define);
		}
		
		String bcode = (String) ret.get("bcode");
		
		Page page = new Page("ProductClassify");
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
		
		ProductClassify productClassify=new ProductClassify();
		productClassify.setCorpId(vending.getCorpId());
		List<ProductClassify> productClassifyList = productClassifyDao.findAll(productClassify, page);
		if (productClassifyList == null || productClassifyList.size() < 0) {
			return errorCodeDao.getErrorRespJson(GConstent.Query_No_Data);
		}
		for(ProductClassify productClassifyi : productClassifyList){
			if(productClassifyi.getPicJson()!=null && !"".equals(productClassifyi.getPicJson())){
				String productPic = "{\"ProductPic\":"+ productClassifyi.getPicJson() + "}";
				JSONObject jsonObjectR = JSONObject.fromObject(productPic);
				JSONArray jsonArray = jsonObjectR.getJSONArray("ProductPic");
		        for (int i = 0; i < jsonArray.size(); i++) {
		        	JSONObject jSONObject = (JSONObject) jsonArray.get(i);
		        	String os = jSONObject.getString("os");
		        	String picUrl = jSONObject.getString("pic");
		        	if((bcode.equals(GParameter.platCode_Platform) && os.equals(GParameter.platCode_Platform))
		        		||(bcode.equals(GParameter.platCode_WeChatOfficialAccountPurchase) && os.equals(GParameter.platCode_WeChatOfficialAccountPurchase))
		        		||(bcode.equals(GParameter.platCode_Terminal) && os.equals(GParameter.platName_Terminal))
		        		||(bcode.equals(GParameter.platCode_WeChatAppletPurchase) && os.equals(GParameter.platCode_WeChatAppletPurchase))
		        		||(bcode.equals(GParameter.platCode_WeChatOfficialAccountReplenishment) && os.equals(GParameter.platCode_WeChatOfficialAccountReplenishment))
		        		||(bcode.equals(GParameter.platCode_AndroidPurchase) && os.equals(GParameter.platCode_AndroidPurchase))
		        		||(bcode.equals(GParameter.platCode_AndroidReplenishment) && os.equals(GParameter.platCode_AndroidReplenishment))
		        		||(bcode.equals(GParameter.platCode_MessageTransfer) && os.equals(GParameter.platCode_MessageTransfer))){
		        		
		        		productClassifyi.setPicJson(picUrl);
		        		continue;
		        	}
		        }
			}else{
				productClassifyi.setPicJson("[]");
			}
		}
		return productClassifyResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, page.getTotalCount(), productClassifyList.size(), productClassifyList);
	}

	public void setProductClassifyReq(ProductClassifyReq productClassifyReq) {
		this.productClassifyReq = productClassifyReq;
	}

	public void setProductClassifyResp(ProductClassifyResp productClassifyResp) {
		this.productClassifyResp = productClassifyResp;
	}

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
	}

	public void setProductClassifyDao(ProductClassifyDao productClassifyDao) {
		this.productClassifyDao = productClassifyDao;
	}
	
}
