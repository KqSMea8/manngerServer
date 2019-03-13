package com.lmxf.post.service.partner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.exception.SystemException;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.MsgPushUtils;
import com.lmxf.post.entity.supply.SupplyConfig;
import com.lmxf.post.entity.supply.SupplyOrder;
import com.lmxf.post.entity.supply.SupplyProduct;
import com.lmxf.post.entity.supply.SupplyVending;
import com.lmxf.post.entity.supply.SupplyVproduct;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingCmd;
import com.lmxf.post.entity.vending.VendingLanep;
import com.lmxf.post.entity.vending.VendingLsdiffer;
import com.lmxf.post.entity.vending.VendingStock;
import com.lmxf.post.repository.parameter.SequenceIdDao;
import com.lmxf.post.repository.supply.SupplyConfigDao;
import com.lmxf.post.repository.supply.SupplyOrderDao;
import com.lmxf.post.repository.supply.SupplyProductDao;
import com.lmxf.post.repository.supply.SupplyVendingDao;
import com.lmxf.post.repository.supply.SupplyVproductDao;
import com.lmxf.post.repository.vending.VendingCmdDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.repository.vending.VendingLanepDao;
import com.lmxf.post.repository.vending.VendingLsdifferDao;
import com.lmxf.post.repository.vending.VendingStockDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.SiteLanelsdifferResp;
import com.lmxf.post.tradepkg.partner.SiteLanelsdifferReq;

/**
 * 货道库存矫正
 * 
 * @author Administrator
 * 
 */
public class TradeSiteLanelsdifferService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeOrderApplyService.class);
	private SiteLanelsdifferReq siteLanelsdifferReq;
	private SiteLanelsdifferResp siteLanelsdifferResp;
	private VendingLsdifferDao vendingLsdifferDao;
	private VendingDao vendingDao;
	private VendingLanepDao vendingLanepDao;
	private SequenceIdDao sequenceIdDao;
	private VendingCmdDao vendingCmdDao;
	private SupplyVproductDao supplyVproductDao;
	private VendingStockDao vendingStockDao;
	private SupplyOrderDao supplyOrderDao;
	private SupplyProductDao supplyProductDao;
	private SupplyVendingDao supplyVendingDao;
	private SupplyConfigDao supplyConfigDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) throws SystemException {
		List<VendingLsdiffer> vendingLsdifferList = null;
		Map ret = null;
		try {
			ret = siteLanelsdifferReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("siteLanelsdifferReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		vendingLsdifferList = (List<VendingLsdiffer>) ret.get("vendingLsdifferList");
		String siteId = (String) ret.get("siteId");
		String operId = (String) ret.get("operId");
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
		// -----------------验证数据是否需合法-----------------
		for (VendingLsdiffer vendingLsdiffer : vendingLsdifferList) {
			VendingLanep vendingLanep = null;
			for (VendingLanep vendingLanepR : vendingLanepList) {
				if (vendingLanepR.getLaneSId() == vendingLsdiffer.getLaneSId() && vendingLanepR.getLaneEId() == vendingLsdiffer.getLaneEId()) {
					vendingLanep = vendingLanepR;
					break;
				}
			}
			if (vendingLanep == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Site_Lane_No_Found);
			}
			if (vendingLanep.getCurCap() != vendingLsdiffer.getCurCap()) {
				return errorCodeDao.getErrorRespJson(GConstent.VendingLsdifferCurStockDiffer);
			}
			vendingLsdiffer.setLogid(DateUtil.getLogid());
			vendingLsdiffer.setCorpId(vending.getCorpId());
			vendingLsdiffer.setCreaterId(operId);
			vendingLsdiffer.setCreateTime(DateUtil.getNow());
			vendingLsdiffer.setCurState(GParameter.VendingLsdifferCurState_waithandle);
			vendingLsdiffer.setHandlerNum(0);
			vendingLsdiffer.setLsdifferId(vending.getCorpId() + "-" + sequenceIdDao.findNextVal(vending.getCorpId(), "as_vending_lsdiffer", 7));
			vendingLsdiffer.setProductId(vendingLanep.getProductId());
			vendingLsdiffer.setOperTime(DateUtil.getNow());
			vendingLsdiffer.setSiteId(siteId);
			vendingLsdiffer.setStateTime(DateUtil.getNow());
			vendingLsdiffer.setVendingLanep(vendingLanep);
		}
		// 重新计算货道商品库存,补货记录出柜信息
		Map<String, String> laneInfoMap = new HashMap();
		// 重新计算补货订单信息
		Map<String, String> supplyOrderMap = new HashMap();
		for (VendingLsdiffer vendingLsdiffer : vendingLsdifferList) {
			vendingLsdifferDao.insert(vendingLsdiffer);
			// 保存差异商品信息,未修改站点商品提供数据
			laneInfoMap.put(vendingLsdiffer.getProductId(), vendingLsdiffer.getProductId());
			VendingLanep vendingLanep = null;
			for (VendingLanep vendingLanepR : vendingLanepList) {
				if (vendingLanepR.getLaneSId() == vendingLsdiffer.getLaneSId() && vendingLanepR.getLaneEId() == vendingLsdiffer.getLaneEId()) {
					vendingLanep = vendingLanepR;
					break;
				}
			}
			// -----------------修改补货记录库存信息------------------
			SupplyVproduct supplyVproductPPP = new SupplyVproduct();
			supplyVproductPPP.setSiteId(siteId);
			supplyVproductPPP.setProductId(vendingLanep.getProductId());
			supplyVproductPPP.setLaneSId(vendingLanep.getLaneSId());
			supplyVproductPPP.setLaneEId(vendingLanep.getLaneEId());
			// 查询未出柜并且补货完成的补货记录
			supplyVproductPPP.setCurState(GParameter.supplyVproductState_finish);
			supplyVproductPPP.setFinshState(GParameter.supplyVproductFinishState_in);
			List<SupplyVproduct> supplyVproductNoOutList = supplyVproductDao.findNoOutVproductAll(supplyVproductPPP);
			// 货道还剩余多少未出柜的数量
			int sNum = 0;
			for (SupplyVproduct supplyVproduct : supplyVproductNoOutList) {
				sNum += supplyVproduct.getrSupplyNum() - supplyVproduct.getSaleNum();
			}
			// 如果差异数大于0表示要模拟出柜
			if (sNum > vendingLsdiffer.getResetCap()) {
				// 例如补货记录里面总共未出柜是10件(sNum),人工调整货道库存为8件(ResetCap)，则需要模拟2件出柜，第一条未完成售卖的补货记录假如是1件(supplyVproduct.getrSupplyNum()-supplyVproduct.getSaleNum())
				// 则完成第一条补货记录出柜，继续模拟后续的补货记录出柜，如果第一条补货记录未出柜数是3件则只要这条补货记录直接加2件模拟出柜
				int diffNum = sNum - vendingLsdiffer.getResetCap();
				for (SupplyVproduct supplyVproduct : supplyVproductNoOutList) {
					// 当前货道还剩余多少未出柜的减去需要出柜的数量,如果剩余数量大于需要出柜数量则只出库当前货道就行，如果小于需要出库数则继续模拟出库补货记录直到出库数为0
					int outNum = supplyVproduct.getrSupplyNum() - supplyVproduct.getSaleNum();
					if (diffNum == 0) {
						break;
					} else if (outNum <= diffNum) {// 需要出柜的数量
						supplyVproduct.setFinshState(GParameter.supplyVproductFinishState_out);
						supplyVproduct.setSaleNum(supplyVproduct.getSaleNum());
						this.supplyVproductDao.update(supplyVproduct);
						diffNum = diffNum - outNum;
						supplyOrderMap.put(supplyVproduct.getsOrderId(), supplyVproduct.getsOrderId());
					} else {
						supplyVproduct.setSaleNum(supplyVproduct.getSaleNum() + diffNum);
						this.supplyVproductDao.update(supplyVproduct);
						diffNum = 0;
						supplyOrderMap.put(supplyVproduct.getsOrderId(), supplyVproduct.getsOrderId());
					}
				}
			} else {// 如果差异数小于0则需要模拟入柜
				int diffNum = vendingLsdiffer.getResetCap() - sNum;
				for (SupplyVproduct supplyVproduct : supplyVproductNoOutList) {
					// 当前货道还剩余多少未出柜的减去需要出柜的数量,如果剩余数量大于需要出柜数量则只出库当前货道就行，如果小于需要出库数则继续模拟出库补货记录直到出库数为0
					int outNum = supplyVproduct.getSaleNum();
					if(outNum==0){
						continue;
					}
					if (diffNum == 0) {
						break;
					} else if (outNum <= diffNum) {// 需要出柜的数量
						supplyVproduct.setSaleNum(0);
						this.supplyVproductDao.update(supplyVproduct);
						diffNum = diffNum - outNum;
						supplyOrderMap.put(supplyVproduct.getsOrderId(), supplyVproduct.getsOrderId());
					} else {
						supplyVproduct.setSaleNum(supplyVproduct.getSaleNum() - diffNum);
						this.supplyVproductDao.update(supplyVproduct);
						diffNum = 0;
						supplyOrderMap.put(supplyVproduct.getsOrderId(), supplyVproduct.getsOrderId());
					}
				}
				if(diffNum>0){
					log.error("库存矫正失败 补货记录没有足够模拟入库 站点编号:"+siteId+" 货道编号:"+vendingLsdiffer.getLaneSId()+" 货道当前未购买库存:"+sNum+"  矫正后的库存:"+vendingLsdiffer.getResetCap());
					throw new SystemException(GConstent.VendingLsdiffer_NoSupply_EROOR, "" + GConstent.VendingLsdiffer_NoSupply_EROOR);
				}
			}
			// -----------------修改货道库存库存-----------------
			vendingLanep.setCurCap(vendingLsdiffer.getResetCap());
			vendingLanep.setLaneSate(GParameter.vendingLanepLaneState_nomarl);
			this.vendingLanepDao.update(vendingLanep);
		}
		// -----------------修改站点商品库存-----------------
		Map<String, Integer> productInfoMap = new HashMap();
		for (VendingLanep vendingLanepR : vendingLanepList) {
			if (productInfoMap.get(vendingLanepR.getProductId()) != null)
				productInfoMap.put(vendingLanepR.getProductId(), vendingLanepR.getCurCap() + productInfoMap.get(vendingLanepR.getProductId()));
			else
				productInfoMap.put(vendingLanepR.getProductId(), vendingLanepR.getCurCap());
		}
		for (String productId : laneInfoMap.keySet()) {
			// 更新售货机商品库存
			VendingStock vendingStockP = new VendingStock();
			vendingStockP.setSiteId(siteId);
			vendingStockP.setProductId(productId);
			VendingStock vendingStock = vendingStockDao.findOne(vendingStockP);
			if (vendingStock != null) {
				if (productInfoMap.get(productId) != null && productInfoMap.get(productId) >= 0) {
					vendingStock.setNum(productInfoMap.get(productId));
					vendingStockDao.update(vendingStock);
				} else {
					log.error("库存矫正 异常 站点库存商品:站点编号:" + siteId + "  商品编号:" + productId + " 矫正后库存数:" + productInfoMap.get(productId));
				}
			}
		}
		// -----------------同步补货单记录信息-----------------
		// 同步修改过的货道出库数量的补货单信息
		Map<String, Integer> productSupplyMap = new HashMap();
		for (String sOrderId : supplyOrderMap.keySet()) {
			SupplyVproduct supplyVproduct = new SupplyVproduct();
			supplyVproduct.setsOrderId(sOrderId);
			List<SupplyVproduct> vSupplylist = this.supplyVproductDao.findAll(supplyVproduct);
			// 补货订单的已出库数和未出库数总计
			int outNum = 0;
			int noOutNum = 0;
			for (SupplyVproduct supplyVproductR : vSupplylist) {
				// 补货单总数
				outNum += supplyVproductR.getSaleNum();
				noOutNum += supplyVproductR.getrSupplyNum() - supplyVproductR.getSaleNum();
				// 商品总数
				if (productSupplyMap.get(supplyVproductR.getProductId() + "_outNum") != null) {
					productSupplyMap.put(supplyVproductR.getProductId() + "_outNum", productSupplyMap.get(supplyVproductR.getProductId() + "_outNum") + supplyVproductR.getSaleNum());
				} else {
					productSupplyMap.put(supplyVproductR.getProductId() + "_outNum", supplyVproductR.getSaleNum());
				}
				if (productSupplyMap.get(supplyVproductR.getSiteId() + "_outNum") != null) {
					productSupplyMap.put(supplyVproductR.getSiteId() + "_outNum", productSupplyMap.get(supplyVproductR.getSiteId() + "_outNum") + supplyVproductR.getSaleNum());
				} else {
					productSupplyMap.put(supplyVproductR.getSiteId() + "_outNum", supplyVproductR.getSaleNum());
				}

				int onout = supplyVproductR.getrSupplyNum() - supplyVproductR.getSaleNum();
				if (productSupplyMap.get(supplyVproductR.getProductId() + "_noOutNum") != null) {
					productSupplyMap.put(supplyVproductR.getProductId() + "_noOutNum", productSupplyMap.get(supplyVproductR.getProductId() + "_noOutNum") + onout);
				} else {
					productSupplyMap.put(supplyVproductR.getProductId() + "_noOutNum", onout);
				}
				if (productSupplyMap.get(supplyVproductR.getSiteId() + "_noOutNum") != null) {
					productSupplyMap.put(supplyVproductR.getSiteId() + "_noOutNum", productSupplyMap.get(supplyVproductR.getSiteId() + "_noOutNum") + onout);
				} else {
					productSupplyMap.put(supplyVproductR.getSiteId() + "_noOutNum", onout);
				}
			}
			SupplyOrder supplyOrder = this.supplyOrderDao.findOneBySOrderId(sOrderId);
			if (supplyOrder != null) {
				supplyOrder.setOutNum(outNum);
				supplyOrder.setCurPNum(noOutNum);
				this.supplyOrderDao.update(supplyOrder);
			}
			// 补货商品的已出库数和未出库数总计
			SupplyProduct supplyProductP = new SupplyProduct();
			supplyProductP.setsOrderId(sOrderId);
			List<SupplyProduct> supplyProductList = this.supplyProductDao.findAll(supplyProductP);
			for (SupplyProduct supplyProduct : supplyProductList) {
				if (productSupplyMap.get(supplyProduct.getProductId() + "_outNum") != null) {
					supplyProduct.setOutNum(productSupplyMap.get(supplyProduct.getProductId() + "_outNum"));
					this.supplyProductDao.update(supplyProduct);
				} else {
					log.error("库存矫正接口 补货单:" + supplyProduct.getsOrderId() + " 商品编号:" + supplyProduct.getProductId() + " 矫正后库存:" + productSupplyMap.get(supplyProduct.getProductId() + "_outNum"));
				}
			}
		}
		//-----------------修改补货配置和补货站点数据记录信息-----------------
		// 查询补货配置站点信息
		SupplyVending supplyVendingP = new SupplyVending();
		supplyVendingP.setSiteId(siteId);
		SupplyVending supplyVending = supplyVendingDao.findOne(supplyVendingP);
		if (supplyVending != null) {
			if (productSupplyMap.get(siteId + "_noOutNum") != null) {
				int diff = productSupplyMap.get(siteId + "_noOutNum") - supplyVending.getCurPNum();
				log.info("库存矫正后 站点补货未出库数:" + productSupplyMap.get(siteId + "_noOutNum") + "  当前站点未出库数:" + supplyVending.getCurPNum() + " 差值:" + diff);
				supplyVending.setCurPNum(productSupplyMap.get(siteId + "_noOutNum"));
				this.supplyVendingDao.update(supplyVending);
				SupplyConfig supplyConfig = this.supplyConfigDao.findOneBySupplyId(supplyVending.getSupplyId());
				if (supplyConfig != null) {
					supplyConfig.setCurPNum(supplyConfig.getCurPNum() - diff);
					this.supplyConfigDao.update(supplyConfig);
				}
			} else {
				log.error("库存矫正 补货站点:" + siteId + " 补货站点库存为空:" + productSupplyMap.get(siteId + "_noOutNum"));
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
		return siteLanelsdifferResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
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

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
	}

	public void setVendingLanepDao(VendingLanepDao vendingLanepDao) {
		this.vendingLanepDao = vendingLanepDao;
	}

	public void setSiteLanelsdifferReq(SiteLanelsdifferReq siteLanelsdifferReq) {
		this.siteLanelsdifferReq = siteLanelsdifferReq;
	}

	public void setSiteLanelsdifferResp(SiteLanelsdifferResp siteLanelsdifferResp) {
		this.siteLanelsdifferResp = siteLanelsdifferResp;
	}

	public void setVendingLsdifferDao(VendingLsdifferDao vendingLsdifferDao) {
		this.vendingLsdifferDao = vendingLsdifferDao;
	}

	public void setVendingCmdDao(VendingCmdDao vendingCmdDao) {
		this.vendingCmdDao = vendingCmdDao;
	}

	public void setSequenceIdDao(SequenceIdDao sequenceIdDao) {
		this.sequenceIdDao = sequenceIdDao;
	}

	public void setSupplyVproductDao(SupplyVproductDao supplyVproductDao) {
		this.supplyVproductDao = supplyVproductDao;
	}

	public void setVendingStockDao(VendingStockDao vendingStockDao) {
		this.vendingStockDao = vendingStockDao;
	}

	public void setSupplyOrderDao(SupplyOrderDao supplyOrderDao) {
		this.supplyOrderDao = supplyOrderDao;
	}

	public void setSupplyProductDao(SupplyProductDao supplyProductDao) {
		this.supplyProductDao = supplyProductDao;
	}

	public void setSupplyVendingDao(SupplyVendingDao supplyVendingDao) {
		this.supplyVendingDao = supplyVendingDao;
	}

	public void setSupplyConfigDao(SupplyConfigDao supplyConfigDao) {
		this.supplyConfigDao = supplyConfigDao;
	}

}
