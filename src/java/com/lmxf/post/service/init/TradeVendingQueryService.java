package com.lmxf.post.service.init;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.add.FavourableObject;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingState;
import com.lmxf.post.repository.add.FavourableObjectDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.repository.vending.VendingStateDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.init.VendingQueryReq;
import com.lmxf.post.tradepkg.init.VendingQueryResp;

public class TradeVendingQueryService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeVendingQueryService.class);
	private VendingQueryReq vendingQueryReq;
	private VendingQueryResp vendingQueryResp;
	private VendingDao vendingDao;
	private FavourableObjectDao favourableObjectDao;
	private VendingStateDao vendingStateDao;
	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Map ret = vendingQueryReq.parseXml(apply_xml);
		if (ret.get("code") == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
			log.error("vendingQueryReq.parseXml is error!");
			return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
		}
		Vending vendingP = (Vending) ret.get("vending");
		Vending vending = vendingDao.findBySiteId(vendingP.getSiteId());
		if(vending == null){
			return errorCodeDao.getErrorRespJson(GConstent.Site_No_Define);
		}
		VendingState vendingState=this.vendingStateDao.findBySiteId(vendingP.getSiteId());
		FavourableObject favourableObjectP = new FavourableObject();
		favourableObjectP.setCorpId(vending.getCorpId());
		favourableObjectP.setFavObjId(vending.getSiteId());
		favourableObjectP.setValidSTime(DateUtil.getDate());
		favourableObjectP.setFavType(GParameter.favType_vending);
		FavourableObject favourableObject = favourableObjectDao.findOne(favourableObjectP);
		if(favourableObject != null){
			vending.setFayWay(favourableObject.getFavWay());
			vending.setDiscount(favourableObject.getDiscount());
		}
		return vendingQueryResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1, vending,vendingState);
	}

	public void setVendingQueryReq(VendingQueryReq vendingQueryReq) {
		this.vendingQueryReq = vendingQueryReq;
	}

	public void setVendingStateDao(VendingStateDao vendingStateDao) {
		this.vendingStateDao = vendingStateDao;
	}

	public void setVendingQueryResp(VendingQueryResp vendingQueryResp) {
		this.vendingQueryResp = vendingQueryResp;
	}

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
	}

	public void setFavourableObjectDao(FavourableObjectDao favourableObjectDao) {
		this.favourableObjectDao = favourableObjectDao;
	}

}
