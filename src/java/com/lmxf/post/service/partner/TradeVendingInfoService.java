package com.lmxf.post.service.partner;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.add.FavourableObject;
import com.lmxf.post.entity.parameter.Dict;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingState;
import com.lmxf.post.repository.add.FavourableObjectDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.repository.vending.VendingStateDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.VendingInfoReq;
import com.lmxf.post.tradepkg.partner.VendingInfoResp;


/**
 * 售货机信息查询
 * @author Administrator
 *
 */
public class TradeVendingInfoService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeVendingInfoService.class);
	private VendingInfoReq vendingInfoReq;
	private VendingInfoResp vendingInfoResp;
	private VendingDao vendingDao;
	private FavourableObjectDao favourableObjectDao;
	private VendingStateDao vendingStateDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Map ret = vendingInfoReq.parseXml(apply_xml);
		if (ret.get("code") == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
			log.error("vendingInfoReq.parseXml is error!");
			return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
		}
		Vending vendingP = (Vending) ret.get("vending");
		
		Page page = new Page("Vending");
		Dict dict=(Dict) CacheUtils.get(CacheUtils.errorCodeCache,"issuedPage_size");
	    String pageNum = dict!=null?dict.getDictValue():null;
		page.setCurrentPage(Integer.parseInt((String) (ret.get("istart")==null?"1":ret.get("istart"))));
		page.setPageNum(10);
		if (pageNum != null && !"".equals(pageNum)) {
			try {
				page.setPageNum(Integer.parseInt(pageNum));
			} catch (Exception e) {
				return errorCodeDao.getErrorRespJson(GConstent.Program_App_Execp);
			}
		}
		// 查询符合条件的地点信息
		List<Vending> vendingList = vendingDao.findAllLimit(vendingP);
		if(null == vendingList || vendingList.size() <= 0){
			return errorCodeDao.getErrorRespJson(GConstent.Site_No_Define);
		}
		//查找优惠信息
		for(Vending vending : vendingList){
			FavourableObject favourableObjectP = new FavourableObject();
			favourableObjectP.setCorpId(vending.getCorpId());
			favourableObjectP.setFavObjId(vending.getSiteId());
			favourableObjectP.setValidSTime(DateUtil.getDate());
			favourableObjectP.setFavType(GParameter.favType_vending);
			FavourableObject favourableObject = favourableObjectDao.findOne(favourableObjectP);
			if(favourableObject != null){
				vending.setPayType(favourableObject.getPayType());
				vending.setFayWay(favourableObject.getFavWay());
				vending.setDiscount(favourableObject.getDiscount());
			}

			VendingState vendingState=this.vendingStateDao.findBySiteId(vendingP.getSiteId());
			vending.setVendingState(vendingState);
		}
		return vendingInfoResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, page.getTotalCount(), vendingList.size(), vendingList);
	}

	public void setVendingInfoReq(VendingInfoReq vendingInfoReq) {
		this.vendingInfoReq = vendingInfoReq;
	}

	public void setVendingInfoResp(VendingInfoResp vendingInfoResp) {
		this.vendingInfoResp = vendingInfoResp;
	}

	public VendingDao getVendingDao() {
		return vendingDao;
	}

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
	}

	public void setFavourableObjectDao(FavourableObjectDao favourableObjectDao) {
		this.favourableObjectDao = favourableObjectDao;
	}

	public void setVendingStateDao(VendingStateDao vendingStateDao) {
		this.vendingStateDao = vendingStateDao;
	}

}
