package com.lmxf.post.service.partner;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.statement.StatementProduct;
import com.lmxf.post.entity.statement.StatementSupply;
import com.lmxf.post.entity.supply.SupplyConfig;
import com.lmxf.post.entity.supply.SupplyOrder;
import com.lmxf.post.entity.supply.SupplyProduct;
import com.lmxf.post.entity.supply.SupplyVending;
import com.lmxf.post.entity.supply.SupplyVproduct;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingLanep;
import com.lmxf.post.entity.vending.VendingStock;
import com.lmxf.post.repository.parameter.SequenceIdDao;
import com.lmxf.post.repository.statement.StatementProductDao;
import com.lmxf.post.repository.statement.StatementSupplyDao;
import com.lmxf.post.repository.supply.SupplyConfigDao;
import com.lmxf.post.repository.supply.SupplyOrderDao;
import com.lmxf.post.repository.supply.SupplyProductDao;
import com.lmxf.post.repository.supply.SupplyVendingDao;
import com.lmxf.post.repository.supply.SupplyVproductDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.repository.vending.VendingLanepDao;
import com.lmxf.post.repository.vending.VendingStockDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.SupplyEventReq;
import com.lmxf.post.tradepkg.partner.SupplyEventResp;

public class TradeSupplyEventService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeSupplyEventService.class);
	private SupplyEventReq supplyEventReq;
	private SupplyEventResp supplyEventResp;
	private SupplyOrderDao supplyOrderDao;
	private SupplyConfigDao supplyConfigDao;
	private SupplyVendingDao supplyVendingDao;
	private SupplyVproductDao supplyVproductDao;
	private VendingDao vendingDao;
	private SupplyProductDao supplyProductDao;
	private VendingLanepDao vendingLanepDao;
	private VendingStockDao vendingStockDao;
	private SequenceIdDao sequenceIdDao;
	private StatementProductDao statementProductDao;
	private StatementSupplyDao statementSupplyDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Map ret = supplyEventReq.parseXml(apply_xml);
		if (ret.get("code") == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
			log.error("supplyEventReq.parseXml is error!");
			return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
		}
		SupplyOrder supplyOrder = (SupplyOrder) ret.get("supplyOrder");
		String siteId = (String) ret.get("siteId");
		//根据补货时间判断该补货单是否已经上报
		List<SupplyOrder> supplyOrderList = supplyOrderDao.findAll(supplyOrder);
		if(supplyOrderList != null && supplyOrderList.size() > 0){
			return errorCodeDao.getErrorRespJson(GConstent.Order_Is_Exsit);
		}
		Vending vending = vendingDao.findBySiteId(siteId);
		if(vending == null){
			return errorCodeDao.getErrorRespJson(GConstent.Site_No_Define);
		}
		//插入补货记录
		supplyOrder.setLogid(DateUtil.getLogid());
		supplyOrder.setsOrderId(vending.getCorpId() + "-" + this.sequenceIdDao.findNextVal(vending.getCorpId(), "supply_order_id", 7));
		supplyOrder.setCorpId(vending.getCorpId());
		supplyOrder.setSupplyFTime(supplyOrder.getStateTime());
		supplyOrder.setLineId(vending.getLineId());
		supplyOrder.setNum(1);
		supplyOrder.setMaxPNum(vending.getpMaxNum());
		if(supplyOrder.getSupplyNum() + supplyOrder.getCurPNum() != supplyOrder.getMaxPNum()){
			supplyOrder.setType(GParameter.supplyOrderType_warnSupply);
		}else{
			supplyOrder.setType(GParameter.supplyOrderType_allSupply);
		}
		supplyOrder.setCurState(GParameter.supplyOrderState_finish);
		supplyOrder.setFinshState(GParameter.supplyOrderFinishState_normal);
		supplyOrder.setStockState(GParameter.supplyOrderStockState_alreadyOut);
		supplyOrder.setsStateTime(supplyOrder.getStateTime());
		supplyOrder.setCreateTime(DateUtil.getNow());
		supplyOrderDao.insert(supplyOrder);
		//插入补货站点商品信息
		List<SupplyVproduct> supplyVproductList = supplyOrder.getSupplyVproductList();
		for(SupplyVproduct supplyVproduct : supplyVproductList){
			supplyVproduct.setLogid(DateUtil.getLogid());
			supplyVproduct.setsVendingId(vending.getCorpId() + "-" + this.sequenceIdDao.findNextVal(vending.getCorpId(), "supply_vproduct_id", 7));
			supplyVproduct.setsOrderId(supplyOrder.getsOrderId());
			supplyVproduct.setCorpId(supplyOrder.getCorpId());
			supplyVproduct.setDistrictId(vending.getDistrictId());
			supplyVproduct.setLineId(vending.getLineId());
			supplyVproduct.setPointId(vending.getPointId());
			supplyVproduct.setSupplyNum(supplyVproduct.getrSupplyNum());
			supplyVproduct.setFinshState(GParameter.supplyVproductFinishState_in);
			supplyVproduct.setCurState(GParameter.supplyVproductState_finish);
			supplyVproduct.setStateTime(supplyOrder.getStateTime());
			supplyVproduct.setInvalidState(GParameter.productOverState_no);
			supplyVproduct.setCreateTime(DateUtil.getNow());
			supplyVproductDao.insert(supplyVproduct);
			//更新售货机货道商品库存
			VendingLanep vendingLanepT = new VendingLanep();
			vendingLanepT.setSiteId(supplyVproduct.getSiteId());
			vendingLanepT.setLaneSId(supplyVproduct.getLaneSId());
			vendingLanepT.setLaneEId(supplyVproduct.getLaneEId());
			VendingLanep vendingLanep = vendingLanepDao.findOne(vendingLanepT);
			if(vendingLanep != null){
				vendingLanep.setCurCap(vendingLanep.getCurCap() + supplyVproduct.getrSupplyNum());
				vendingLanepDao.update(vendingLanep);
			}
			//更新售货机商品库存
			VendingStock vendingStockP = new VendingStock();
			vendingStockP.setSiteId(supplyVproduct.getSiteId());
			vendingStockP.setProductId(supplyVproduct.getProductId());
			VendingStock vendingStock = vendingStockDao.findOne(vendingStockP);
			if(vendingStock != null){
				vendingStock.setNum(vendingStock.getNum() + supplyVproduct.getrSupplyNum());
				vendingStockDao.update(vendingStock);
			}
		}
		//插入补货商品记录信息
		List<SupplyVproduct> supplyVproductListR = supplyVproductDao.findProductBySOrderId(supplyOrder.getsOrderId());
		if(supplyVproductListR != null && supplyVproductListR.size() > 0){
			for(SupplyVproduct supplyVproductP : supplyVproductListR){
				SupplyProduct supplyProduct = new SupplyProduct();
				supplyProduct.setLogid(DateUtil.getLogid());
				supplyProduct.setsProductId(vending.getCorpId() + "-" + this.sequenceIdDao.findNextVal(vending.getCorpId(), "supply_product_id", 7));
				supplyProduct.setsOrderId(supplyOrder.getsOrderId());
				supplyProduct.setCorpId(supplyOrder.getCorpId());
				supplyProduct.setProductId(supplyVproductP.getProductId());
				supplyProduct.setSupplyNum(supplyVproductP.getrSupplyNum());
				supplyProduct.setOutNum(supplyVproductP.getrSupplyNum());
				supplyProduct.setCurState(GParameter.supplyVproductState_finish);
				supplyProduct.setStateTime(supplyOrder.getStateTime());
				supplyProduct.setCreateTime(DateUtil.getNow());
				supplyProductDao.insert(supplyProduct);
			}
		}
		//更新各库存
		//更新补货售货库存
		int stockLevel = 3;
		SupplyVending supplyVendingP = new SupplyVending();
		supplyVendingP.setSiteId(siteId);
		List<SupplyVending> supplyVendingList = supplyVendingDao.findAll(supplyVendingP);
		if(supplyVendingList != null && supplyVendingList.size() > 0){
			for(SupplyVending supplyVending : supplyVendingList){
				supplyVending.setCurPNum(supplyVending.getCurPNum() + supplyOrder.getSupplyNum());
				int stockLevelV = supplyVending.getCurPNum()/supplyVending.getMaxPNum()*100;
				if(Integer.parseInt(supplyVending.getFristlevel().split("-")[0]) <= stockLevelV && stockLevelV <= Integer.parseInt(supplyVending.getFristlevel().split("-")[1])){
					supplyVending.setStoryLevel(1);
				}else if(Integer.parseInt(supplyVending.getTwolevel().split("-")[0]) <= stockLevelV && stockLevelV <= Integer.parseInt(supplyVending.getTwolevel().split("-")[1])){
					supplyVending.setStoryLevel(2);
				}else if(Integer.parseInt(supplyVending.getThreelevel().split("-")[0]) <= stockLevelV && stockLevelV <= Integer.parseInt(supplyVending.getThreelevel().split("-")[1])){
					supplyVending.setStoryLevel(3);
				}
				supplyVendingDao.update(supplyVending);
				stockLevel = supplyVending.getStoryLevel();
				//更新补货配置库存
				SupplyConfig supplyConfig = supplyConfigDao.findOneBySupplyId(supplyVending.getSupplyId());
				if(supplyConfig != null){
					supplyConfig.setPendingNum((supplyConfig.getPendingNum() - 1) > 0 ? (supplyConfig.getPendingNum() - 1) : 0);
					supplyConfig.setFinshNum(supplyConfig.getFinshNum() + 1);
					supplyConfig.setSupplyPnum(supplyConfig.getSupplyPnum() + supplyOrder.getSupplyNum());
					supplyConfig.setSproductNum(supplyConfig.getSproductNum() + supplyVproductListR.size());
					supplyConfig.setCurPNum(supplyConfig.getCurPNum() + supplyOrder.getSupplyNum());
					int stockLevelC = supplyConfig.getCurPNum()/supplyConfig.getMaxPNum()*100;
					if(Integer.parseInt(supplyConfig.getFristlevel().split("-")[0]) <= stockLevelC && stockLevelC <= Integer.parseInt(supplyConfig.getFristlevel().split("-")[1])){
						supplyConfig.setStoryLevel(1);
					}else if(Integer.parseInt(supplyConfig.getTwolevel().split("-")[0]) <= stockLevelC && stockLevelC <= Integer.parseInt(supplyConfig.getTwolevel().split("-")[1])){
						supplyConfig.setStoryLevel(2);
					}else if(Integer.parseInt(supplyConfig.getThreelevel().split("-")[0]) <= stockLevelC && stockLevelC <= Integer.parseInt(supplyConfig.getThreelevel().split("-")[1])){
						supplyConfig.setStoryLevel(3);
					}
					supplyConfigDao.update(supplyConfig);
				}
			}
		}
		//更新站点库存
		vending.setpCurNum(vending.getpCurNum() + supplyOrder.getSupplyNum());
		vending.setStockLevel(stockLevel);
		vendingDao.update(vending);
		//插入补货账单信息
		if(supplyOrder.getCurState().equals(GParameter.supplyOrderState_finish)){
			SupplyVproduct supplyVproductT = new SupplyVproduct();
			supplyVproductT.setsOrderId(supplyOrder.getsOrderId());
			List<SupplyVproduct> supplyVproductListT = supplyVproductDao.findAll(supplyVproductT);
			if(supplyVproductListT != null && supplyVproductListT.size() > 0){
				for(SupplyVproduct supplyVproductR : supplyVproductListT){
					int seqId = 0;
					StatementProduct statementProductP = new StatementProduct();
					statementProductP.setSiteId(supplyVproductR.getSiteId());
					statementProductP.setLaneSId(supplyVproductR.getLaneSId());
					statementProductP.setLaneEId(supplyVproductR.getLaneEId());
					StatementProduct statementProductR = statementProductDao.findOne(statementProductP);
					if(statementProductR != null){
						seqId = statementProductR.getSeqId();
					}
					for(int i =0 ; i<supplyVproductR.getrSupplyNum() ; i++){
						//插入补货账单售出明细信息
						seqId = seqId + 1;
						StatementProduct statementProduct = new StatementProduct();
						statementProduct.setLogid(DateUtil.getLogid());
						statementProduct.setStatementPid(supplyVproductR.getCorpId() + "-" + this.sequenceIdDao.findNextVal(supplyVproductR.getCorpId(), "statement_product_id", 7));
						statementProduct.setProductId(supplyVproductR.getProductId());
						statementProduct.setSiteId(supplyVproductR.getSiteId());
						statementProduct.setLaneSId(supplyVproductR.getLaneSId());
						statementProduct.setLaneEId(supplyVproductR.getLaneEId());
						statementProduct.setSeqId(seqId);
						statementProduct.setBuyMoney(supplyVproductR.getBuyPrice());
						statementProduct.setCurState(GParameter.statementProductCurState_in);
						statementProduct.setCorpId(supplyVproductR.getCorpId());
						statementProduct.setCreateTime(DateUtil.getNow());
						statementProductDao.insert(statementProduct);
					}
				}
			}
			//插入补货账单信息
			List<SupplyVproduct> supplyVproductListP = supplyVproductDao.findSiteInfoBySOrderId(supplyOrder.getsOrderId());
			if(supplyVproductListP != null && supplyVproductListP.size() > 0){
				for(SupplyVproduct supplyVproductTemp : supplyVproductListP){
					StatementSupply statementSupply = new StatementSupply();
					statementSupply.setLogid(DateUtil.getLogid());
					statementSupply.setsOrderId(supplyOrder.getsOrderId());
					statementSupply.setSiteId(supplyVproductTemp.getSiteId());
					statementSupply.setCorpId(supplyOrder.getCorpId());
					statementSupply.setName(supplyOrder.getName());
					statementSupply.setDistrictId(vending.getDistrictId());
					statementSupply.setLineId(supplyOrder.getLineId());
					statementSupply.setWmId(supplyOrder.getWmId());
					SupplyVproduct supplyVproductS = supplyVproductDao.findSupplyTotalBySiteId(supplyVproductTemp.getSiteId());
					if(supplyVproductS != null){
						statementSupply.setSupplyNum(supplyVproductS.getrSupplyNum());
						statementSupply.setBuyMoney(supplyVproductS.getBuyPrice());
					}
					statementSupply.setSalteState(GParameter.statementSupplySaleState_unSale);
					statementSupply.setStatementState(GParameter.statementSupplyState_waitSaleOut);
					statementSupply.setCurState(GParameter.statementSupplyCurState_unSubmit);
					statementSupply.setCreateTime(DateUtil.getNow());
					statementSupplyDao.insert(statementSupply);
				}
			}
		}
		return supplyEventResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public void setSupplyEventReq(SupplyEventReq supplyEventReq) {
		this.supplyEventReq = supplyEventReq;
	}

	public void setSupplyEventResp(SupplyEventResp supplyEventResp) {
		this.supplyEventResp = supplyEventResp;
	}

	public void setSupplyOrderDao(SupplyOrderDao supplyOrderDao) {
		this.supplyOrderDao = supplyOrderDao;
	}

	public void setSupplyConfigDao(SupplyConfigDao supplyConfigDao) {
		this.supplyConfigDao = supplyConfigDao;
	}

	public void setSupplyVendingDao(SupplyVendingDao supplyVendingDao) {
		this.supplyVendingDao = supplyVendingDao;
	}

	public void setSupplyVproductDao(SupplyVproductDao supplyVproductDao) {
		this.supplyVproductDao = supplyVproductDao;
	}

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
	}

	public void setSupplyProductDao(SupplyProductDao supplyProductDao) {
		this.supplyProductDao = supplyProductDao;
	}

	public void setVendingLanepDao(VendingLanepDao vendingLanepDao) {
		this.vendingLanepDao = vendingLanepDao;
	}

	public void setVendingStockDao(VendingStockDao vendingStockDao) {
		this.vendingStockDao = vendingStockDao;
	}

	public void setSequenceIdDao(SequenceIdDao sequenceIdDao) {
		this.sequenceIdDao = sequenceIdDao;
	}

	public void setStatementProductDao(StatementProductDao statementProductDao) {
		this.statementProductDao = statementProductDao;
	}

	public void setStatementSupplyDao(StatementSupplyDao statementSupplyDao) {
		this.statementSupplyDao = statementSupplyDao;
	}

}
