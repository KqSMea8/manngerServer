package com.lmxf.post.service.partner;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.lmxf.post.entity.product.ProductInfo;
import com.lmxf.post.entity.product.ProductLunder;
import com.lmxf.post.entity.product.ProductUnder;
import com.lmxf.post.entity.product.ProductVUnder;
import com.lmxf.post.entity.supply.SupplyConfig;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingDistrict;
import com.lmxf.post.entity.vending.VendingLanep;
import com.lmxf.post.entity.vending.VendingLine;
import com.lmxf.post.repository.product.ProductInfoDao;
import com.lmxf.post.repository.product.ProductUnderDao;
import com.lmxf.post.repository.product.ProductVUnderDao;
import com.lmxf.post.repository.supply.SupplyConfigDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.repository.vending.VendingDistrictDao;
import com.lmxf.post.repository.vending.VendingLanepDao;
import com.lmxf.post.repository.vending.VendingLineDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.UnderInfoReq;
import com.lmxf.post.tradepkg.partner.UnderInfoResp;
import com.lmxf.post.repository.product.ProductLunderDao;

public class TradeUnderInfoService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeUnderInfoService.class);
	private UnderInfoReq underInfoReq;
	private UnderInfoResp underInfoResp;
	private ProductVUnderDao productVUnderDao;
	private ProductLunderDao productLunderDao;
	private VendingDao vendingDao;
	private VendingLineDao vendingLineDao;
	private VendingLanepDao vendingLanepDao;
	private VendingDistrictDao vendingDistrictDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Map ret = underInfoReq.parseXml(apply_xml);
		if (ret.get("code") == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
			log.error("underInfoReq.parseXml is error!");
			return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
		}
		ProductVUnder productVUnderP = (ProductVUnder) ret.get("productVUnder");
		//分页查询
		String bcode = (String) ret.get("bcode");
		Page page = new Page("UnderInfo");
		Dict dict=(Dict) CacheUtils.get(CacheUtils.errorCodeCache,"issuedPage_size");
	    String pageNum = dict!=null?dict.getDictValue():null;
		page.setCurrentPage(Integer.parseInt((String) ret.get("istart")));
		page.setPageNum(10);
		if (pageNum != null && !"".equals(pageNum)) {
			try {
				page.setPageNum(Integer.parseInt(pageNum));
			} catch (Exception e) {
				return errorCodeDao.getErrorRespJson(GConstent.Program_App_Execp);
			}
		}
		//查询线路分页
		List<ProductVUnder> lineVunderList=productVUnderDao.findPageGroupLine(productVUnderP);
		List<VendingLine> vendingLineList=new ArrayList();
		for(ProductVUnder productVUnder:lineVunderList){
			VendingLine vendingLine=this.vendingLineDao.findByLineId(productVUnder.getLineId());
			if(vendingLine!=null){
				VendingDistrict vendingDistrict = vendingDistrictDao.findByDistrictId(vendingLine.getDistrictId());
				if(vendingDistrict != null){
					vendingLine.setDistrictName(vendingDistrict.getName());
				}
				vendingLineList.add(vendingLine);
				
			}
		}
		List<ProductVUnder> productVUnderListR=null;
		//查询线路下下架站点
		for(VendingLine vendingLine:vendingLineList){
			//需要查询下架的售货机
			productVUnderListR=new ArrayList();
			//查询下架的售货机货道信息
			ProductVUnder productVUnderPP=new ProductVUnder();
			productVUnderPP.setCurState(productVUnderP.getCurState());
			productVUnderPP.setLoginId(productVUnderP.getLoginId());
			productVUnderPP.setLineId(vendingLine.getLineId());
			List<ProductVUnder> productVUnderList=productVUnderDao.findAll(productVUnderPP);
			for(ProductVUnder productVUnder:productVUnderList){
				//查询站点信息
				Vending vending=this.vendingDao.findBySiteId(productVUnder.getSiteId());
				//查询站点下架货道商品信息
				ProductLunder productLunderP=new ProductLunder();
				productLunderP.setvUnderId(productVUnder.getvUnderId());
				List<ProductLunder> productLunderList=this.productLunderDao.findAll(productLunderP);
				productVUnder.setProductLunderList(productLunderList);
				//查询货道当前信息
				for(ProductLunder productLunder:productLunderList){
					VendingLanep vendingLanepP=new VendingLanep();
					vendingLanepP.setSiteId(productLunder.getSiteId());
					vendingLanepP.setLaneSId(productLunder.getLaneSId());
					vendingLanepP.setLaneEId(productLunder.getLaneEId());
					VendingLanep vendingLanep=this.vendingLanepDao.findOneCabinet(vendingLanepP);
					productLunder.setVendingLanep(vendingLanep);
					
				}
				if(productLunderList!=null && vending!=null){
					productVUnder.setVending(vending);
					productVUnderListR.add(productVUnder);
				}else{
					log.error("下架查询接口 检测到站点信息或站点下架货道信息为空:"+productVUnder.getSiteId());
				}
				vendingLine.setProductVUnder(productVUnderListR);
			}
		}
		return underInfoResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, page.getTotalCount(), vendingLineList.size(), vendingLineList,bcode);
	}

	public void setUnderInfoReq(UnderInfoReq underInfoReq) {
		this.underInfoReq = underInfoReq;
	}

	public void setUnderInfoResp(UnderInfoResp underInfoResp) {
		this.underInfoResp = underInfoResp;
	}

	public void setProductVUnderDao(ProductVUnderDao productVUnderDao) {
		this.productVUnderDao = productVUnderDao;
	}

	public void setProductLunderDao(ProductLunderDao productLunderDao) {
		this.productLunderDao = productLunderDao;
	}

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
	}

	public void setVendingLineDao(VendingLineDao vendingLineDao) {
		this.vendingLineDao = vendingLineDao;
	}

	public void setVendingLanepDao(VendingLanepDao vendingLanepDao) {
		this.vendingLanepDao = vendingLanepDao;
	}

	public void setVendingDistrictDao(VendingDistrictDao vendingDistrictDao) {
		this.vendingDistrictDao = vendingDistrictDao;
	}


}
