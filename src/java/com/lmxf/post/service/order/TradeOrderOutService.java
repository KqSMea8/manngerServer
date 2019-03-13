package com.lmxf.post.service.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.redis.RedisOps;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.entity.order.OrderChange;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.entity.statement.StatementProduct;
import com.lmxf.post.entity.statement.StatementSupply;
import com.lmxf.post.entity.supply.SupplyConfig;
import com.lmxf.post.entity.supply.SupplyProduct;
import com.lmxf.post.entity.supply.SupplyVending;
import com.lmxf.post.entity.supply.SupplyVproduct;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingLanep;
import com.lmxf.post.entity.vending.VendingStock;
import com.lmxf.post.repository.order.OrderApplyDao;
import com.lmxf.post.repository.order.OrderBoxDao;
import com.lmxf.post.repository.order.OrderChangeDao;
import com.lmxf.post.repository.order.OrderProductDao;
import com.lmxf.post.repository.parameter.SequenceIdDao;
import com.lmxf.post.repository.statement.StatementProductDao;
import com.lmxf.post.repository.statement.StatementSupplyDao;
import com.lmxf.post.repository.supply.SupplyConfigDao;
import com.lmxf.post.repository.supply.SupplyProductDao;
import com.lmxf.post.repository.supply.SupplyVendingDao;
import com.lmxf.post.repository.supply.SupplyVproductDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.repository.vending.VendingLanepDao;
import com.lmxf.post.repository.vending.VendingStockDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.order.OrderOutReq;
import com.lmxf.post.tradepkg.order.OrderOutResp;
import com.manage.project.system.index.vo.OneMonthReviewVo;
import com.manage.project.system.index.vo.OperateReviewVo;

public class TradeOrderOutService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeOrderOutService.class);
	private OrderOutReq orderOutReq;
	private OrderOutResp orderOutResp;
	private OrderApplyDao orderApplyDao;
	private SequenceIdDao sequenceIdDao;
	private OrderChangeDao orderChangeDao;
	private OrderProductDao orderProductDao;
	private OrderBoxDao orderBoxDao;
	private SupplyVproductDao supplyVproductDao;
	private StatementProductDao statementProductDao;
	private StatementSupplyDao statementSupplyDao;
	private SupplyVendingDao supplyVendingDao;
	private SupplyConfigDao supplyConfigDao;
	private VendingDao vendingDao;
	private VendingLanepDao vendingLanepDao;
	private VendingStockDao vendingStockDao;
	private SupplyProductDao supplyProductDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		OrderApply orderApply = null;
		Map ret = null;
		try {
			ret = orderOutReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("orderOutReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		orderApply = (OrderApply) ret.get("orderApply");
		OrderApply orderApplyR = this.orderApplyDao.findOneByOrderId(orderApply);
		if (null == orderApplyR) {
			return errorCodeDao.getErrorRespJson(GConstent.Order_No_Exsit);
		}
		if (!orderApplyR.getCurState().equals(GParameter.orderState_apply)) {
			return errorCodeDao.getErrorRespJson(GConstent.OrderState_No_Change);
		}
		// 更新订单商品货道信息
		int outTotal = 0;
		OrderBox orderBoxPP = new OrderBox();
		orderBoxPP.setOrderId(orderApply.getOrderId());
		// 查询出货信息
		List<OrderBox> orderBoxListR = this.orderBoxDao.findAll(orderBoxPP);
		// 修改出货状态
		List<OrderBox> orderBoxList = orderApply.getOrderBoxList();
		// 出货日志
		String logOut = "";
		for (OrderBox orderBox : orderBoxList) {
			// 查询出柜信息是否匹配服务器出柜信息
			OrderBox orderBoxR = null;
			for (OrderBox orderBoxT : orderBoxListR) {
				if (orderBoxT.getProboxId().equals(orderBox.getProboxId())) {
					orderBoxR = orderBoxT;
					break;
				}
			}
			if(orderBoxR.getLaneSId()!=orderBox.getLaneSId()){
				orderBoxR.setOutIndex(GParameter.outLaneProductReplace_yes);// 代表替换货道出货
			}else{
				orderBoxR.setOutIndex(GParameter.outLaneProductReplace_no);// 代表未替换货道
			}
			orderBoxR.setLaneSId(orderBox.getLaneSId());
			orderBoxR.setLaneEId(orderBox.getLaneEId());
			if (orderBoxR != null) {
				logOut += orderBox.getOrderId() + " 货道:" + orderBox.getLaneSId() + "_" + orderBox.getLaneEId() + " 出货索引:" + orderBox.getOutIndex() + " 出货成功.   ";
				// 更新补货站点商品信息
				String sOrderIdP = "";
				if (orderBox.getOutState().equals(GParameter.orderBoxOutState_normalOut) || orderBox.getOutState().equals(GParameter.orderBoxOutState_abnormalOut)) {
					orderBoxR.setOutState(orderBox.getOutState());
					orderBoxR.setSysState(orderBox.getSysState());
					SupplyVproduct supplyVproductP = new SupplyVproduct();
					supplyVproductP.setSiteId(orderApplyR.getSiteId());
					supplyVproductP.setProductId(orderBox.getProductId());
					supplyVproductP.setLaneSId(orderBox.getLaneSId());
					supplyVproductP.setLaneEId(orderBox.getLaneEId());
					SupplyVproduct supplyVproduct = supplyVproductDao.findOne(supplyVproductP);
					if (null != supplyVproduct) {
						log.debug("货道补货编号:" + supplyVproduct.getsVendingId() + " 计算后:" + supplyVproduct.getSaleNum() + " 单独计算:" + (supplyVproduct.getSaleNum() + 1) + " 补货数量:" + supplyVproduct.getrSupplyNum() + "  是否完成:"
								+ ((supplyVproduct.getSaleNum() + 1) >= supplyVproduct.getrSupplyNum()));
						if ((supplyVproduct.getSaleNum() + 1) >= supplyVproduct.getrSupplyNum()) {
							supplyVproduct.setFinshState(GParameter.supplyVproductFinishState_out);
							log.debug("货道补货编号:" + supplyVproduct.getsVendingId() + " 已完成:" + supplyVproduct.getFinshState());
						}
						supplyVproduct.setSaleNum(supplyVproduct.getSaleNum() + 1);
						supplyVproductDao.update(supplyVproduct);
						// 减少补货商品记录出库数
						SupplyProduct supplyProductP = new SupplyProduct();
						supplyProductP.setsOrderId(supplyVproduct.getsOrderId());
						supplyProductP.setProductId(supplyVproduct.getProductId());
						SupplyProduct supplyProduct = this.supplyProductDao.findOne(supplyProductP);
						if (supplyProduct != null) {
							supplyProduct.setOutNum(supplyProduct.getOutNum() + 1);
							this.supplyProductDao.update(supplyProduct);
						}

						outTotal = outTotal + 1;
						sOrderIdP = supplyVproduct.getsOrderId();
						// 更新补货账单明细信息
						StatementProduct statementProductP = new StatementProduct();
						statementProductP.setProductId(orderBox.getProductId());
						statementProductP.setSiteId(orderApplyR.getSiteId());
						statementProductP.setLaneSId(orderBox.getLaneSId());
						statementProductP.setLaneEId(orderBox.getLaneEId());
						StatementProduct statementProduct = statementProductDao.findOneOut(statementProductP);
						if (statementProduct != null) {
							statementProduct.setOrderId(orderApplyR.getOrderId());
							statementProduct.setProboxId(orderBoxR.getProboxId());
							statementProduct.setOutType(GParameter.outType_sale);
							statementProduct.setPayType(orderApplyR.getPayType());
							statementProduct.setTradeNo(orderApplyR.getpTradeNo());
							statementProduct.setSalePMoney(orderBoxR.getSalePrice());
							statementProduct.setSaleRMoney(orderBoxR.getPayPrice());
							statementProduct.setSaleFMoney(orderBoxR.getFavPrice());
							statementProduct.setpTradeNo(orderApplyR.getpTradeNo());
							statementProduct.setCurState(GParameter.statementProductCurState_out);
							statementProductDao.update(statementProduct);
						}
						// 更新补货账单信息
						StatementSupply statementSupplyP = new StatementSupply();
						statementSupplyP.setSiteId(orderApplyR.getSiteId());
						statementSupplyP.setsOrderId(sOrderIdP);
						StatementSupply statementSupply = statementSupplyDao.findOne(statementSupplyP);
						if (statementSupply != null) {
							if (statementSupply.getSaleNum() == 0) {
								statementSupply.setTradeSTime(DateUtil.getNow());
								statementSupply.setSalteState(GParameter.statementSupplySaleState_inSale);
							}
							statementSupply.setSaleNum(statementSupply.getSaleNum() + 1);
							if (statementSupply.getSaleNum() + statementSupply.getUnderNum() >= statementSupply.getSupplyNum()) {
								statementSupply.setTradeEtime(DateUtil.getNow());
								statementSupply.setSalteState(GParameter.statementSupplySaleState_saleOut);
								statementSupply.setStatementState(GParameter.statementSupplyState_waitStatement);
							}
							statementSupply.setSalePMoney(statementSupply.getSalePMoney() + orderBoxR.getSalePrice());
							statementSupply.setSaleRMoney(statementSupply.getSaleRMoney() + orderBoxR.getPayPrice());
							statementSupply.setSaleFMoney(statementSupply.getSaleFMoney() + orderBoxR.getFavPrice());
							statementSupplyDao.update(statementSupply);
						}
					}
				} else if (GParameter.orderBoxOutState_outFailed.equals(orderBox.getOutState())) {
					orderApplyR.setAbnomarlState(GParameter.abnormalType_fetchFailed);
					orderBoxR.setOutState(GParameter.orderBoxOutState_outFailed);
					this.orderApplyDao.update(orderApplyR);
					// 出货失败则站点商品库存相应加1 因为还在货道中
					VendingStock vendingStockP = new VendingStock();
					vendingStockP.setSiteId(orderApplyR.getSiteId());
					vendingStockP.setProductId(orderBoxR.getProductId());
					VendingStock vendingStockR = this.vendingStockDao.findOne(vendingStockP);
					if (vendingStockR != null) {
						vendingStockR.setNum(vendingStockR.getNum() + 1);
						this.vendingStockDao.update(vendingStockR);
					}
				}
				// 更新订单商品货道信息
				orderBoxR.setSupplyId(sOrderIdP);
				orderBoxR.setStateTime(DateUtil.getNow());
				orderBoxDao.update(orderBoxR);
				// 更新售货机货道商品库存
				VendingLanep vendingLanepT = new VendingLanep();
				vendingLanepT.setSiteId(orderBoxR.getSiteId());
				vendingLanepT.setLaneSId(orderBoxR.getLaneSId());
				vendingLanepT.setLaneEId(orderBoxR.getLaneEId());
				VendingLanep vendingLanep = vendingLanepDao.findOne(vendingLanepT);
				if (vendingLanep != null) {
					// 出库失败则不更新货道库存数量
					if (!GParameter.orderBoxOutState_outFailed.equals(orderBox.getOutState())) {
						log.debug("订单编号:" + orderApplyR.getOrderId() + " 货到号:" + orderBoxR.getLaneSId() + " 当前库存:" + vendingLanep.getCurCap() + " 减少后库存:" + ((vendingLanep.getCurCap() - 1) > 0 ? (vendingLanep.getCurCap() - 1) : 0));
						vendingLanep.setCurCap((vendingLanep.getCurCap() - 1) > 0 ? (vendingLanep.getCurCap() - 1) : 0);
						vendingLanepDao.update(vendingLanep);
					}
				}
				// 更新订单商品信息
				OrderProduct orderProductP = new OrderProduct();
				orderProductP.setOrderId(orderApplyR.getOrderId());
				List<OrderProduct> orderProductList = orderProductDao.findAll(orderProductP);
				if (orderProductList != null && orderProductList.size() > 0) {
					for (OrderProduct orderProduct : orderProductList) {
						// 查找商品出柜数量
						int outNum = 0;
						for (OrderBox orderBoxT : orderBoxListR) {
							if (GParameter.orderBoxOutState_normalOut.equals(orderBoxT.getOutState()) || GParameter.orderBoxOutState_abnormalOut.equals(orderBoxT.getOutState())) {
								outNum++;
							}
						}
						// 更新订单商品出柜数量
						orderProduct.setOutNum(outNum);
						orderProductDao.update(orderProduct);
					}
				}
				// 更新各库存
				// 更新补货售货机库存
				int stockLevel = 3;
				SupplyVending supplyVendingP = new SupplyVending();
				supplyVendingP.setSiteId(orderApplyR.getSiteId());
				List<SupplyVending> supplyVendingList = supplyVendingDao.findAll(supplyVendingP);
				if (supplyVendingList != null && supplyVendingList.size() > 0) {
					for (SupplyVending supplyVending : supplyVendingList) {
						supplyVending.setCurPNum((supplyVending.getCurPNum() - outTotal) > 0 ? (supplyVending.getCurPNum() - outTotal) : 0);
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
							supplyConfig.setCurPNum((supplyConfig.getCurPNum() - outTotal) > 0 ? (supplyConfig.getCurPNum() - outTotal) : 0);
							int stockLevelC = supplyConfig.getCurPNum() / supplyConfig.getMaxPNum() * 100;
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
				}
				// 更新站点库存
				Vending vending = vendingDao.findBySiteId(orderApplyR.getSiteId());
				if (vending != null) {
					vending.setpCurNum((vending.getpCurNum() - outTotal) > 0 ? (vending.getpCurNum() - outTotal) : 0);
					vending.setStockLevel(stockLevel);
					vendingDao.update(vending);
				}
			} else {
				logOut += orderBox.getOrderId() + " 货道:" + orderBox.getLaneSId() + "_" + orderBox.getLaneEId() + " 出货索引:" + orderBox.getOutIndex() + " 出货失败或者已出货.   ";
			}
		}
		boolean isFetch = true;
		int saleFailNum = 0;
		for (OrderBox orderBoxT : orderBoxListR) {
			if (GParameter.orderBoxOutState_in.equals(orderBoxT.getOutState())) {
				isFetch = false;
				break;
			}
			if (GParameter.orderBoxOutState_outFailed.equals(orderBoxT.getOutState())) {
				saleFailNum++;
			}
		}
		if (isFetch) {
			if (true) {
				try {
					OperateReviewVo operateReviewVo = (OperateReviewVo) RedisOps.getObject(CacheUtils.INDEX_SUMMARY_TOTALREVIEW_CACHE_ + orderApplyR.getCorpId());
					log.debug("获取公司:" + orderApplyR.getCorpId() + " 仪表盘总览缓存对象:" + operateReviewVo);
					if (operateReviewVo != null) {
						log.debug("获取公司:" + orderApplyR.getCorpId() + " 总利润:" + operateReviewVo.getTotalProfit() + " 总营业额:" + operateReviewVo.getTotalSale() + " 总销量:" + operateReviewVo.getTotalSaleNum() + " 订单利润:" + orderApplyR.getProfitMoney()
								+ " 订单销量:" + orderApplyR.getpNum() + " 订单支付价格:" + orderApplyR.getPayPrice());
						float profit = 0;
						try {
							BigDecimal hisProfit = new BigDecimal(operateReviewVo.getTotalProfit());
							BigDecimal orderProfit = new BigDecimal(orderApplyR.getProfitMoney() + "");
							profit = hisProfit.add(orderProfit).floatValue();
							log.debug("总览历史毛利:" + operateReviewVo.getTotalProfit() + "  订单毛利:" + orderApplyR.getProfitMoney() + " 总计后毛利:" + profit);
							operateReviewVo.setTotalProfit(String.valueOf(profit));
						} catch (Exception e) {
							log.error("计算公司仪表盘总览转换错误:" + orderApplyR.getCorpId() + "  历史毛利:" + operateReviewVo.getTotalProfit());
						}
						float salePrice = 0;
						try {
							BigDecimal hisProfit = new BigDecimal(operateReviewVo.getTotalSale());
							BigDecimal orderProfit = new BigDecimal(orderApplyR.getPayPrice() + "");
							salePrice = hisProfit.add(orderProfit).floatValue();
							log.debug("总览历史销售额:" + operateReviewVo.getTotalSale() + "  订单销售额:" + orderApplyR.getPayPrice() + " 总计后销售额:" + salePrice);
							operateReviewVo.setTotalSale(String.valueOf(salePrice));
						} catch (Exception e) {
							log.error("计算公司仪表盘总览转换错误:" + orderApplyR.getCorpId() + "  历史销售额:" + operateReviewVo.getTotalSale());
						}
						int saleNum = 0;
						try {
							saleNum = Integer.parseInt(operateReviewVo.getTotalSaleNum());
							saleNum += orderApplyR.getpNum() - saleFailNum;
							log.debug("总览历史销售量:" + operateReviewVo.getTotalSaleNum() + "  订单销售量:" + orderApplyR.getpNum() + "出库失败:" + saleFailNum + " 总计后销售量:" + saleNum);
							operateReviewVo.setTotalSaleNum(String.valueOf(saleNum));
						} catch (Exception e) {
							log.error("计算公司仪表盘总览毛利错误:" + orderApply.getCorpId() + "  历史销售量:" + operateReviewVo.getTotalSaleNum());
						}
						RedisOps.setObject(CacheUtils.INDEX_SUMMARY_TOTALREVIEW_CACHE_ + orderApplyR.getCorpId(), operateReviewVo);
					} else {
						log.error("获取公司的销售总览缓存对象出错:" + CacheUtils.INDEX_SUMMARY_TOTALREVIEW_CACHE_ + orderApplyR.getCorpId());
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("设置缓存总览统计出错:" + e.getMessage());
				}
				try {
					log.debug("获取公司:" + orderApplyR.getCorpId() + " 仪表盘今日汇总缓存对象:" + CacheUtils.INDEX_SUMMARY_ONEMONTH_CACHE_ + orderApplyR.getCorpId());
					List<OneMonthReviewVo> oneMonthReviewVoList = (List<OneMonthReviewVo>) RedisOps.getObject(CacheUtils.INDEX_SUMMARY_ONEMONTH_CACHE_ + orderApplyR.getCorpId());
					log.debug("获取公司:" + orderApplyR.getCorpId() + " 仪表盘今日汇总缓存对象:" + oneMonthReviewVoList != null ? oneMonthReviewVoList.size() : 0);
					OneMonthReviewVo oneMonthReviewVo = null;
					if (oneMonthReviewVoList != null) {
						for (OneMonthReviewVo oneMonthReviewVoT : oneMonthReviewVoList) {
							if (oneMonthReviewVoT.getType() == 1) {
								oneMonthReviewVo = oneMonthReviewVoT;
							}
						}
					}
					if (oneMonthReviewVo != null) {
						float profit = 0;
						try {
							BigDecimal hisProfit = new BigDecimal(oneMonthReviewVo.getTotalProfit());
							BigDecimal orderProfit = new BigDecimal(orderApplyR.getProfitMoney() + "");
							profit = hisProfit.add(orderProfit).floatValue();
							log.debug("今日历史毛利:" + oneMonthReviewVo.getTotalProfit() + "  订单毛利:" + orderApplyR.getProfitMoney() + " 总计后毛利:" + profit);
							oneMonthReviewVo.setTotalProfit(String.valueOf(profit));
						} catch (Exception e) {
							log.error("计算公司仪表盘总览转换错误:" + orderApplyR.getCorpId() + "  历史毛利:" + oneMonthReviewVo.getTotalProfit());
						}
						float salePrice = 0;
						try {
							BigDecimal hisProfit = new BigDecimal(oneMonthReviewVo.getTotalSale());
							BigDecimal orderProfit = new BigDecimal(orderApplyR.getPayPrice() + "");
							salePrice = hisProfit.add(orderProfit).floatValue();
							log.debug("今日历史销售额:" + oneMonthReviewVo.getTotalSale() + "  订单销售额:" + orderApplyR.getPayPrice() + " 总计后销售额:" + salePrice);
							oneMonthReviewVo.setTotalSale(String.valueOf(salePrice));
						} catch (Exception e) {
							log.error("计算公司仪表盘总览转换错误:" + orderApplyR.getCorpId() + "  历史销售额:" + oneMonthReviewVo.getTotalSale());
						}
						int saleNum = 0;
						try {
							saleNum = Integer.parseInt(oneMonthReviewVo.getTotalSaleNum());
							saleNum += orderApplyR.getpNum() - saleFailNum;
							log.debug("今日历史销售量:" + oneMonthReviewVo.getTotalSaleNum() + "  订单销售量:" + orderApplyR.getpNum() + " 总计后销售量:" + saleNum);
							oneMonthReviewVo.setTotalSaleNum(String.valueOf(saleNum));
						} catch (Exception e) {
							log.error("计算公司仪表盘总览毛利错误:" + orderApply.getCorpId() + "  历史销售量:" + oneMonthReviewVo.getTotalSaleNum());
						}
						RedisOps.setObject(CacheUtils.INDEX_SUMMARY_ONEMONTH_CACHE_ + orderApplyR.getCorpId(), oneMonthReviewVoList);
					} else {
						log.error("获取公司的今日总览缓存对象出错:" + CacheUtils.INDEX_SUMMARY_ONEMONTH_CACHE_ + orderApplyR.getCorpId());
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("设置缓存今日总览统计出错:" + e.getMessage());
				}
			}
			// 更新订单状态信息
			orderApplyR.setCurState(GParameter.orderState_advanceFetch);
			orderApplyR.setStateTime(orderApply.getStateTime());
			this.orderApplyDao.update(orderApplyR);
			// 插入订单状态变化信息
			OrderChange orderChange = new OrderChange();
			orderChange.setLogid(DateUtil.getLogid());
			orderChange.setChangeId(orderApplyR.getCorpId() + "-" + this.sequenceIdDao.findNextVal(orderApplyR.getCorpId(), "order_change_id", 7));
			orderChange.setCorpId(orderApplyR.getCorpId());
			orderChange.setOrderId(orderApplyR.getOrderId());
			orderChange.setOperId(orderApplyR.getLoginId());
			orderChange.setOperName(orderApplyR.getLoginName());
			orderChange.setSiteId(orderApplyR.getSiteId());
			orderChange.setSiteName(orderApplyR.getSiteName());
			orderChange.setOperAction(GParameter.orderChange_advanceFetch);
			orderChange.setOperTime(orderApply.getStateTime());
			orderChange.setOperCont("售货机:" + orderApplyR.getSiteName() + " 订单编号:" + orderApplyR.getOrderId() + " 提前出货! 出货信息:" + logOut);
			orderChange.setCreateTime(DateUtil.getNow());
			orderChange.setPocState(GParameter.OrderChangeProc_Pushed);
			orderChange.setPocTimes(0);
			orderChange.setPocResult("wait push");
			orderChange.setProState(GParameter.OrderChangeProState_waitFinsh);
			this.orderChangeDao.insert(orderChange);
		} else {
			// 插入订单状态变化信息
			OrderChange orderChange = new OrderChange();
			orderChange.setLogid(DateUtil.getLogid());
			orderChange.setChangeId(orderApplyR.getCorpId() + "-" + this.sequenceIdDao.findNextVal(orderApplyR.getCorpId(), "order_change_id", 7));
			orderChange.setCorpId(orderApplyR.getCorpId());
			orderChange.setOrderId(orderApplyR.getOrderId());
			orderChange.setOperId(orderApplyR.getLoginId());
			orderChange.setOperName(orderApplyR.getLoginName());
			orderChange.setSiteId(orderApplyR.getSiteId());
			orderChange.setSiteName(orderApplyR.getSiteName());
			orderChange.setOperAction(GParameter.orderChange_advanceFetch);
			orderChange.setOperTime(orderApply.getStateTime());
			orderChange.setOperCont("售货机:" + orderApplyR.getSiteName() + " 订单编号:" + orderApplyR.getOrderId() + " 提前出货,未全部出货完成!出货信息:" + logOut);
			orderChange.setCreateTime(DateUtil.getNow());
			orderChange.setPocState(GParameter.OrderChangeProc_waitPush);
			orderChange.setPocTimes(0);
			orderChange.setPocResult("wait push");
			orderChange.setProState(GParameter.OrderChangeProState_waitFinsh);
			this.orderChangeDao.insert(orderChange);
		}

		return orderOutResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public void setOrderOutReq(OrderOutReq orderOutReq) {
		this.orderOutReq = orderOutReq;
	}

	public void setOrderOutResp(OrderOutResp orderOutResp) {
		this.orderOutResp = orderOutResp;
	}

	public void setOrderApplyDao(OrderApplyDao orderApplyDao) {
		this.orderApplyDao = orderApplyDao;
	}

	public void setSequenceIdDao(SequenceIdDao sequenceIdDao) {
		this.sequenceIdDao = sequenceIdDao;
	}

	public void setOrderChangeDao(OrderChangeDao orderChangeDao) {
		this.orderChangeDao = orderChangeDao;
	}

	public void setOrderProductDao(OrderProductDao orderProductDao) {
		this.orderProductDao = orderProductDao;
	}

	public void setOrderBoxDao(OrderBoxDao orderBoxDao) {
		this.orderBoxDao = orderBoxDao;
	}

	public void setSupplyVproductDao(SupplyVproductDao supplyVproductDao) {
		this.supplyVproductDao = supplyVproductDao;
	}

	public void setStatementProductDao(StatementProductDao statementProductDao) {
		this.statementProductDao = statementProductDao;
	}

	public void setStatementSupplyDao(StatementSupplyDao statementSupplyDao) {
		this.statementSupplyDao = statementSupplyDao;
	}

	public void setSupplyVendingDao(SupplyVendingDao supplyVendingDao) {
		this.supplyVendingDao = supplyVendingDao;
	}

	public void setSupplyConfigDao(SupplyConfigDao supplyConfigDao) {
		this.supplyConfigDao = supplyConfigDao;
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

	public void setSupplyProductDao(SupplyProductDao supplyProductDao) {
		this.supplyProductDao = supplyProductDao;
	}

}
