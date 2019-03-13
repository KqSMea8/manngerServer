package com.lmxf.post.service.partner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.MsgPushUtils;
import com.lmxf.post.entity.product.ProductLunder;
import com.lmxf.post.entity.product.ProductUnder;
import com.lmxf.post.entity.product.ProductVUnder;
import com.lmxf.post.entity.statement.StatementProduct;
import com.lmxf.post.entity.statement.StatementSupply;
import com.lmxf.post.entity.stock.StockInfo;
import com.lmxf.post.entity.stock.StockProduct;
import com.lmxf.post.entity.stock.StockWarehouse;
import com.lmxf.post.entity.supply.SupplyConfig;
import com.lmxf.post.entity.supply.SupplyProduct;
import com.lmxf.post.entity.supply.SupplyVending;
import com.lmxf.post.entity.supply.SupplyVproduct;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingCmd;
import com.lmxf.post.entity.vending.VendingLanep;
import com.lmxf.post.entity.vending.VendingStock;
import com.lmxf.post.repository.parameter.SequenceIdDao;
import com.lmxf.post.repository.product.ProductLunderDao;
import com.lmxf.post.repository.product.ProductUnderDao;
import com.lmxf.post.repository.product.ProductVUnderDao;
import com.lmxf.post.repository.statement.StatementProductDao;
import com.lmxf.post.repository.statement.StatementSupplyDao;
import com.lmxf.post.repository.stock.StockInfoDao;
import com.lmxf.post.repository.stock.StockProductDao;
import com.lmxf.post.repository.stock.StockWarehouseDao;
import com.lmxf.post.repository.supply.SupplyConfigDao;
import com.lmxf.post.repository.supply.SupplyProductDao;
import com.lmxf.post.repository.supply.SupplyVendingDao;
import com.lmxf.post.repository.supply.SupplyVproductDao;
import com.lmxf.post.repository.vending.VendingCmdDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.repository.vending.VendingLanepDao;
import com.lmxf.post.repository.vending.VendingStockDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.UnderResultReq;
import com.lmxf.post.tradepkg.partner.UnderResultResp;

public class TradeUnderResultService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeSupplyResultService.class);
	private UnderResultReq underResultReq;
	private UnderResultResp underResultResp;
	private ProductUnderDao productUnderDao;
	private ProductLunderDao productLunderDao;
	private VendingDao vendingDao;
	private VendingLanepDao vendingLanepDao;
	private VendingStockDao vendingStockDao;
	private SequenceIdDao sequenceIdDao;
	private SupplyVendingDao supplyVendingDao;
	private SupplyConfigDao supplyConfigDao;
	private StatementProductDao statementProductDao;
	private StatementSupplyDao statementSupplyDao;
	private VendingCmdDao vendingCmdDao;
	private SupplyProductDao supplyProductDao;
	private SupplyVproductDao supplyVproductDao;
	private ProductVUnderDao productVUnderDao;
	private StockProductDao stockProductDao;
	private StockWarehouseDao stockWarehouseDao;
	private StockInfoDao stockInfoDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Map ret = underResultReq.parseXml(apply_xml);
		if (ret.get("code") == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
			log.error("underResultReq.parseXml is error!");
			return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
		}
		List<ProductLunder> productLunderList = (List<ProductLunder>) ret.get("productLunderList");
		String vUnderId = (String) ret.get("vUnderId");
		String isVFinshP = (String) ret.get("isFinsh");// 站点是否完成补货
		if (productLunderList.size() <= 0)
			return errorCodeDao.getErrorRespJson(GConstent.Product_Under_No_Exist);
		// 查询站点下架信息
		ProductVUnder productVUnder = this.productVUnderDao.findOneByVUnderId(vUnderId);
		if (productVUnder == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Site_No_Define);
		}
		String siteId = productVUnder.getSiteId();
		// 查询站点商品下架信息
		ProductUnder productUnderP = new ProductUnder();
		productUnderP.setvUnderId(vUnderId);
		List<ProductUnder> productUnderList = this.productUnderDao.findAll(productUnderP);
		if (productUnderList == null && productUnderList.size() <= 0) {
			return errorCodeDao.getErrorRespJson(GConstent.Product_Under_No_Exist);
		}
		// 查询站点货道商品下架信息
		ProductLunder productLunderP = new ProductLunder();
		productLunderP.setvUnderId(vUnderId);
		List<ProductLunder> productLunderRList = this.productLunderDao.findAll(productLunderP);
		if (productLunderRList == null && productLunderRList.size() <= 0) {
			return errorCodeDao.getErrorRespJson(GConstent.Product_Under_No_Exist);
		}
		// 查询站点信息
		Vending vending = vendingDao.findBySiteId(siteId);
		if (vending == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Site_No_Define);
		}
		// 查询货道商品库存信息
		VendingLanep vendingLanepP = new VendingLanep();
		vendingLanepP.setSiteId(siteId);
		List<VendingLanep> vendingLanepList = this.vendingLanepDao.findAll(vendingLanepP);
		if (vendingLanepList == null && vendingLanepList.size() <= 0) {
			return errorCodeDao.getErrorRespJson(GConstent.Site_Lane_No_Found);
		}
		// 查询补货站点信息
		SupplyVending supplyVendingP = new SupplyVending();
		supplyVendingP.setSiteId(siteId);
		SupplyVending supplyVending = supplyVendingDao.findOne(supplyVendingP);
		if (supplyVending == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Supply_Vending_No_Found);
		}
		// 下架处理
		int underNumR = 0;
		// 获取下架的商品信息
		Map<String, Integer> underProductMap = new HashMap<String, Integer>();
		// 已下架的商品信息
		Map<String, Integer> underProductAlMap = new HashMap<String, Integer>();
		for (ProductLunder productLunder : productLunderList) {
			if(productLunder.getUnderNum()==0){
				continue;
			}
			for (ProductLunder productLunderR : productLunderRList) {
				if (productLunder.getLunderId().equals(productLunderR.getLunderId())) {
					VendingLanep vendingLanep = null;
					for (VendingLanep vendingLanepT : vendingLanepList) {
						if (vendingLanepT.getLaneSId() == productLunder.getLaneSId() && vendingLanepT.getLaneEId() == productLunder.getLaneEId()) {
							vendingLanep = vendingLanepT;
							break;
						}
					}
					if (vendingLanep == null) {
						log.error("补货模块 站点编号:" + productLunder.getSiteId() + " 站点补货编号:" + vUnderId + " 货道编号:" + productLunder.getLaneSId() + " 无货道配置.");
						continue;
					}
					if (GParameter.productLunderCurState_alUnder.equals(productLunderR.getCurState())) {
						log.error("补货模块 站点编号:" + productLunder.getSiteId() + " 站点补货编号:" + vUnderId + " 货道编号:" + productLunder.getLaneSId() + " 已下架.LunderId:" + productLunderR.getLunderId());
						continue;
					}
					ProductUnder productUnder = null;
					for (ProductUnder productUnderR : productUnderList) {
						if (productUnderR.getProductId().equals(productLunderR.getProductId())) {
							productUnder = productUnderR;
							continue;
						}
					}
					if (productUnder == null) {
						log.error("补货模块 站点编号:" + productLunder.getSiteId() + " 站点补货编号:" + vUnderId + " 商品编号:" + productLunderR.getProductId() + " 无站点商品补货信息.");
						continue;
					}
					// 插入站点货道商品下架记录
					productLunderR.setCurState(GParameter.productLunderCurState_alUnder);
					productLunderR.setUnderNum(productLunder.getUnderNum());
					productLunderDao.update(productLunderR);

					// 更新补货记录 查找货道这件商品补货的所有未完成销售的补货记录 设置为已出库
					SupplyVproduct supplyVproductP = new SupplyVproduct();
					supplyVproductP.setSiteId(productLunderR.getSiteId());
					supplyVproductP.setLaneSId(productLunderR.getLaneSId());
					supplyVproductP.setLaneEId(productLunderR.getLaneEId());
					supplyVproductP.setProductId(productLunderR.getProductId());
					List<SupplyVproduct> supplyVProductList = supplyVproductDao.findNoOutVproduct(supplyVproductP);
					for (SupplyVproduct supplyVproduct : supplyVProductList) {
						int outNum = supplyVproduct.getrSupplyNum() - supplyVproduct.getSaleNum();// 实际出库数量
						supplyVproduct.setSaleNum(supplyVproduct.getrSupplyNum());
						supplyVproduct.setFinshState(GParameter.supplyVproductFinishState_out);
						this.supplyVproductDao.update(supplyVproduct);
						// 减少补货商品记录出库数
						SupplyProduct supplyProductP = new SupplyProduct();
						supplyProductP.setsOrderId(supplyVproduct.getsOrderId());
						supplyProductP.setProductId(supplyVproduct.getProductId());
						supplyProductP.setOutNum(outNum);
						SupplyProduct supplyProduct = this.supplyProductDao.findOne(supplyProductP);
						if (supplyProduct != null) {
							supplyProduct.setOutNum(outNum + supplyProduct.getOutNum());
							this.supplyProductDao.update(supplyProduct);
						}
						// 查询补货对账信息
						StatementSupply statementSupplyP = new StatementSupply();
						statementSupplyP.setSiteId(siteId);
						statementSupplyP.setsOrderId(supplyVproduct.getsOrderId());
						StatementSupply statementSupply = statementSupplyDao.findOne(statementSupplyP);
						// 修改补货对账信息
						if (statementSupply != null) {
							statementSupply.setUnderNum(outNum);
							statementSupply.setSaleUMoney(outNum * supplyVproduct.getBuyPrice());
							if(outNum+statementSupply.getSaleNum()>=statementSupply.getSupplyNum()){
								statementSupply.setSalteState(GParameter.statementSupplySaleState_saleOut);
								statementSupply.setStatementState(GParameter.statementSupplyState_waitStatement);
								if(outNum+statementSupply.getSaleNum()>statementSupply.getSupplyNum()){
									log.error("下架模块 站点补货记录下架数加售出数大于补货数量 补货对账编号:"+statementSupply.getsOrderId()+" 站点编号:"+statementSupply.getSiteId()+" 下架数量:"+outNum+"  售出数量:"+statementSupply.getSaleNum());
								}
							}
							this.statementSupplyDao.updateUnder(statementSupply);
						}
					}
					// 更新补货账单售出明细信息
					StatementProduct statementProductP = new StatementProduct();
					statementProductP.setProductId(siteId);
					statementProductP.setSiteId(productLunderR.getSiteId());
					statementProductP.setLaneSId(productLunderR.getLaneSId());
					statementProductP.setLaneEId(productLunderR.getLaneEId());
					statementProductP.setOutType(GParameter.outType_under);
					statementProductP.setCurState(GParameter.statementProductCurState_out);
					int updateNum = statementProductDao.updateOutProduct(statementProductP);

					underProductMap.put(productLunderR.getProductId(), 0);
					int underRNum = underProductAlMap.get(productLunderR.getProductId()) == null ? productLunder.getUnderNum() : (underProductAlMap.get(productLunderR.getProductId()) + productLunder.getUnderNum());
					underProductAlMap.put(productLunderR.getProductId(), underRNum);
					// 当前货道库存设置0
					vendingLanep.setCurCap(0);
					vendingLanep.setProductSate(GParameter.vendingLanePProductSate_under);
					this.vendingLanepDao.update(vendingLanep);
					// 更新站点商品下架信息
					productUnder.setUnderNum(productUnder.getUnderNum() + productLunder.getUnderNum());
					this.productUnderDao.update(productUnder);
				}
			}
		}
		// 计算下架站点是否完成
		boolean isFinsh = true;
		int underNum = 0;
		Map<String, String> noFinshProductMap = new HashMap();
		for (ProductLunder productLunderR : productLunderRList) {
			underNum += productLunderR.getUnderNum();
			//如果是一键完成下架
			if (GParameter.productVunderCurState_alUnder.equals(isVFinshP)) {
				if (GParameter.productLunderCurState_wait.equals(productLunderR.getCurState())) {
					productLunderR.setCurState(GParameter.productLunderCurState_alUnder);
					productLunderR.setUnderNum(0);
					productLunderDao.update(productLunderR);
				}
			}
			if (GParameter.productLunderCurState_wait.equals(productLunderR.getCurState())) {
				isFinsh = false;
				noFinshProductMap.put(productLunderR.getProductId(), productLunderR.getProductId());
				continue;
			}
		}
		if (isFinsh) {// 完成则设置站点补货完成
			productVUnder.setCurState(GParameter.productVunderCurState_alUnder);
			productVUnder.setStateTime(DateUtil.getNow());
		}
		productVUnder.setUnderNum(underNum);
		this.productVUnderDao.update(productVUnder);
		// 检查站点商品信息是否完成
		for (ProductUnder productUnder : productUnderList) {
			if (noFinshProductMap.get(productUnder.getProductId()) == null) {
				productUnder.setCurState(GParameter.productUnderState_finish);
				this.productUnderDao.update(productUnder);
			}
		}
		// 重新计算库存
		int supplyCurNumToal = 0;
		for (VendingLanep vendingLanep : vendingLanepList) {
			supplyCurNumToal += vendingLanep.getCurCap();
			if (underProductMap.get(vendingLanep.getProductId()) != null) {
				underProductMap.put(vendingLanep.getProductId(), underProductMap.get(vendingLanep.getProductId()) + vendingLanep.getCurCap());
			}
		}
		// 更新各库存
		// 更新补货售货机库存
		int stockLevel = 3;
		if (supplyVending != null) {
			supplyVending.setCurPNum(supplyCurNumToal);
			int stockLevelV = (int) (((double) supplyVending.getCurPNum() / (double) supplyVending.getMaxPNum()) * 100);
			if (Integer.parseInt(supplyVending.getFristlevel().split("-")[0]) <= stockLevelV && stockLevelV <= Integer.parseInt(supplyVending.getFristlevel().split("-")[1])) {
				supplyVending.setStoryLevel(1);
			} else if (Integer.parseInt(supplyVending.getTwolevel().split("-")[0]) <= stockLevelV && stockLevelV <= Integer.parseInt(supplyVending.getTwolevel().split("-")[1])) {
				supplyVending.setStoryLevel(2);
			} else if (Integer.parseInt(supplyVending.getThreelevel().split("-")[0]) <= stockLevelV && stockLevelV <= Integer.parseInt(supplyVending.getThreelevel().split("-")[1])) {
				supplyVending.setStoryLevel(3);
			}
			supplyVendingDao.update(supplyVending);
			stockLevel = supplyVending.getStoryLevel();
			// 更新补货配置库存
			SupplyConfig supplyConfig = supplyConfigDao.findOneBySupplyId(supplyVending.getSupplyId());
			if (supplyConfig != null) {
				supplyConfig.setCurPNum(supplyConfig.getCurPNum() - underNumR);
				int stockLevelC = (int) (((double) supplyConfig.getCurPNum() / (double) supplyConfig.getMaxPNum()) * 100);
				if (Integer.parseInt(supplyConfig.getFristlevel().split("-")[0]) <= stockLevelC && stockLevelC <= Integer.parseInt(supplyConfig.getFristlevel().split("-")[1])) {
					supplyConfig.setStoryLevel(1);
				} else if (Integer.parseInt(supplyConfig.getTwolevel().split("-")[0]) <= stockLevelC && stockLevelC <= Integer.parseInt(supplyConfig.getTwolevel().split("-")[1])) {
					supplyConfig.setStoryLevel(2);
				} else if (Integer.parseInt(supplyConfig.getThreelevel().split("-")[0]) <= stockLevelC && stockLevelC <= Integer.parseInt(supplyConfig.getThreelevel().split("-")[1])) {
					supplyConfig.setStoryLevel(3);
				}
				supplyConfigDao.update(supplyConfig);
			}
		}
		// 更新站点库存
		if (vending != null) {
			vending.setpCurNum(supplyCurNumToal);
			vending.setStockLevel(stockLevel);
			vendingDao.update(vending);
		}
		// 更新已下架的商品库存信息
		for (String productId : underProductMap.keySet()) {
			// 更新售货机商品库存
			VendingStock vendingStockP = new VendingStock();
			vendingStockP.setSiteId(siteId);
			vendingStockP.setProductId(productId);
			VendingStock vendingStock = vendingStockDao.findOne(vendingStockP);
			if (vendingStock != null) {
				vendingStock.setNum(underProductMap.get(productId));
				vendingStockDao.update(vendingStock);
			}
		}
		// 分析站点商品库存是否都已下架
		boolean isVendingUnder = true;
		for (ProductUnder productUnder : productUnderList) {
			boolean isUnder = true;
			for (VendingLanep vendingLanepS : vendingLanepList) {
				if (!GParameter.vendingLanePProductSate_under.equals(vendingLanepS.getProductSate()) && productUnder.getProductId().equals(vendingLanepS.getProductId())) {
					isUnder = false;
					isVendingUnder = false;
				}
			}
			if (isUnder) {
				productUnder.setCurState(GParameter.productUnderState_finish);
				productUnder.setStateTime(DateUtil.getNow());
				productUnderDao.update(productUnder);
			}
		}
		if (isVendingUnder) {
			// 设置站点已下架
			vending.setUnderState(GParameter.vendingUnderState_finshUnder);
			vending.setUnderSTime(DateUtil.getNow());
			vendingDao.update(vending);
		}
		// 修改公司商品库存和仓库商品库存+下架商品数
		StockProduct stockProduct = null;
		StockWarehouse stockWarehouse = null;
		// 查找区域下的仓库信息
		StockInfo stockInfoP = new StockInfo();
		stockInfoP.setDistrictId(vending.getDistrictId());
		StockInfo stockInfo = this.stockInfoDao.findOne(stockInfoP);
		for (String productId : underProductAlMap.keySet()) {
			stockProduct = new StockProduct();
			stockProduct.setCorpId(vending.getCorpId());
			stockProduct.setProductId(productId);
			stockProduct.setCurNum(underProductAlMap.get(productId));
			this.stockProductDao.updateAddStock(stockProduct);
			if (stockInfo != null) {
				stockWarehouse = new StockWarehouse();
				stockWarehouse.setCorpId(vending.getCorpId());
				stockWarehouse.setCurNum(underProductAlMap.get(productId));
				stockWarehouse.setStockId(stockInfo.getStockId());
				stockWarehouse.setProductId(productId);
				this.stockWarehouseDao.updateAddStock(stockWarehouse);
			} else {
				log.error("补货模块 补货站点:" + vending.getSiteId() + " 商品编号:" + productId + " 站点归属区域没有找到对应仓库.");
			}
		}
		// 推送货道商品库存信息
		VendingCmd vendingCmdP = new VendingCmd();
		vendingCmdP.setCmd("0205");
		vendingCmdP.setCmdCode(vending.getSiteId());
		vendingCmdP.setCmdId(vending.getCorpId() + "-" + this.sequenceIdDao.findNextVal(vending.getCorpId(), "as_vending_cmd", 7));
		vendingCmdP.setCmdLazy("1");
		vendingCmdP.setCmdType("02");
		vendingCmdP.setCont("");
		vendingCmdP.setCorpId(vending.getCorpId());
		vendingCmdP.setCreateTime(DateUtil.getNow());
		vendingCmdP.setLogid(DateUtil.getLogid());
		vendingCmdP.setResult("wait");
		vendingCmdP.setSiteId(vending.getSiteId());
		vendingCmdP.setState("0");
		vendingCmdP.setStateTime(DateUtil.getNow());
		this.vendingCmdDao.insert(vendingCmdP);
		MsgPushUtils.push(CreateIssueJson(vendingCmdP), vending.getSiteId(), DateUtil.getLogid());
		return underResultResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public String CreateIssueJson(VendingCmd vendingCmdP) {
		try {
			JSONObject rootObject = new JSONObject();

			JSONObject JSONObject = new JSONObject();

			JSONObject JSONHeaderObject = new JSONObject();
			JSONHeaderObject.put("BCode", "03");
			JSONHeaderObject.put("TCode", "1227");
			JSONHeaderObject.put("Version", "01");
			JSONHeaderObject.put("IStart", "1");

			JSONObject.put(GConstent.ZxmlHead, JSONHeaderObject);

			JSONObject JSONBody = new JSONObject();
			JSONBody.put("SiteId", vendingCmdP.getSiteId());
			JSONBody.put("CmdId", vendingCmdP.getCmdId());
			JSONBody.put("CmdCode", vendingCmdP.getCmdCode());
			JSONBody.put("CmdType", vendingCmdP.getCmdType());
			JSONBody.put("Cmd", vendingCmdP.getCmd());
			JSONBody.put("Cont", vendingCmdP.getCont());
			JSONBody.put("CreateTime", vendingCmdP.getCreateTime());

			JSONObject.put(GConstent.ZxmlBody, JSONBody);

			rootObject.put(GConstent.ZxmlRoot, JSONObject);
			return rootObject.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public void setUnderResultReq(UnderResultReq underResultReq) {
		this.underResultReq = underResultReq;
	}

	public void setUnderResultResp(UnderResultResp underResultResp) {
		this.underResultResp = underResultResp;
	}

	public void setProductUnderDao(ProductUnderDao productUnderDao) {
		this.productUnderDao = productUnderDao;
	}

	public void setProductLunderDao(ProductLunderDao productLunderDao) {
		this.productLunderDao = productLunderDao;
	}

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
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

	public void setSupplyVendingDao(SupplyVendingDao supplyVendingDao) {
		this.supplyVendingDao = supplyVendingDao;
	}

	public void setSupplyConfigDao(SupplyConfigDao supplyConfigDao) {
		this.supplyConfigDao = supplyConfigDao;
	}

	public void setStatementProductDao(StatementProductDao statementProductDao) {
		this.statementProductDao = statementProductDao;
	}

	public void setStatementSupplyDao(StatementSupplyDao statementSupplyDao) {
		this.statementSupplyDao = statementSupplyDao;
	}

	public void setVendingCmdDao(VendingCmdDao vendingCmdDao) {
		this.vendingCmdDao = vendingCmdDao;
	}

	public void setSupplyProductDao(SupplyProductDao supplyProductDao) {
		this.supplyProductDao = supplyProductDao;
	}

	public void setSupplyVproductDao(SupplyVproductDao supplyVproductDao) {
		this.supplyVproductDao = supplyVproductDao;
	}

	public void setProductVUnderDao(ProductVUnderDao productVUnderDao) {
		this.productVUnderDao = productVUnderDao;
	}

	public void setStockWarehouseDao(StockWarehouseDao stockWarehouseDao) {
		this.stockWarehouseDao = stockWarehouseDao;
	}

	public void setStockProductDao(StockProductDao stockProductDao) {
		this.stockProductDao = stockProductDao;
	}

	public void setStockInfoDao(StockInfoDao stockInfoDao) {
		this.stockInfoDao = stockInfoDao;
	}

}
