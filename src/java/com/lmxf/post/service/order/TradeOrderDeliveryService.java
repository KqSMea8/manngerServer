package com.lmxf.post.service.order;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.exception.SystemException;
import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.core.utils.VerifiCodeUtils;
import com.lmxf.post.core.utils.encode.BaseEncrypt;
import com.lmxf.post.core.utils.encode.Encrypt;
import com.lmxf.post.core.utils.encode.EncryptProvider;
import com.lmxf.post.core.utils.pay.OrderPayUtils;
import com.lmxf.post.core.utils.pay.PayUtils;
import com.lmxf.post.entity.add.FavourableObject;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.entity.order.OrderChange;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.entity.parameter.Dict;
import com.lmxf.post.entity.parameter.SequenceId;
import com.lmxf.post.entity.product.ProductOnline;
import com.lmxf.post.entity.supply.SupplyVproduct;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingStock;
import com.lmxf.post.repository.add.FavourableObjectDao;
import com.lmxf.post.repository.order.OrderApplyDao;
import com.lmxf.post.repository.order.OrderBoxDao;
import com.lmxf.post.repository.order.OrderChangeDao;
import com.lmxf.post.repository.order.OrderProductDao;
import com.lmxf.post.repository.parameter.SequenceIdDao;
import com.lmxf.post.repository.product.ProductOnlineDao;
import com.lmxf.post.repository.supply.SupplyVproductDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.repository.vending.VendingStockDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.order.OrderDeliveryReq;
import com.lmxf.post.tradepkg.order.OrderDeliveryResp;

public class TradeOrderDeliveryService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeOrderDeliveryService.class);
	private OrderDeliveryReq orderDeliveryReq;
	private OrderDeliveryResp orderDeliveryResp;
	private OrderApplyDao orderApplyDao;
	private SequenceIdDao sequenceIdDao;
	private VendingDao vendingDao;
	private OrderChangeDao orderChangeDao;
	private OrderProductDao orderProductDao;
	private VendingStockDao vendingStockDao;
	private FavourableObjectDao favourableObjectDao;
	private SupplyVproductDao supplyVproductDao;
	private ProductOnlineDao productOnlineDao;
	private OrderBoxDao orderBoxDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		OrderApply orderApply = null;
		Map ret = null;
		List<OrderBox> orderBoxList=new ArrayList();
		try {
			ret = orderDeliveryReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("orderDeliveryReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		orderApply = (OrderApply) ret.get("orderApply");
		OrderApply orderApplyP = new OrderApply();
		orderApplyP.setLoginId(orderApply.getLoginId());
		orderApplyP.setPayState(GParameter.payState_wait);
		orderApplyP.setOrderType(GParameter.orderType_normal);
		List<OrderApply> orderApplyList = orderApplyDao.findAll(orderApplyP);
		if (null != orderApplyList && orderApplyList.size() > 0) {
			return errorCodeDao.getErrorRespJson(GConstent.Unpaid_Order_Exist);
		} else {
			Vending vending = vendingDao.findBySiteId(orderApply.getSiteId());
			if (vending == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Site_No_Define);
			}
			// 判断购买商品数是否大于商品可售卖库存
			float salePrice = 0;
			List<OrderProduct> orderProductList = orderApply.getOrderProductList();
			if (null != orderProductList && orderProductList.size() > 0) {
				for (OrderProduct orderProduct : orderProductList) {
					VendingStock vendingStock = new VendingStock();
					vendingStock.setSiteId(orderApply.getSiteId());
					vendingStock.setProductId(orderProduct.getProductId());
					VendingStock vendingStockR = vendingStockDao.findOne(vendingStock);
					if (null != vendingStockR) {
						if (orderProduct.getSaleNum() > vendingStockR.getNum()) {
							return errorCodeDao.getErrorRespJson(GConstent.Product_Num_Lack);
						}
					} else {
						return errorCodeDao.getErrorRespJson(GConstent.Product_NumZero);
					}
					// 支付价
					BigDecimal salePriceB = new BigDecimal(vendingStockR.getSalePrice() + "");
					BigDecimal saleNumB = new BigDecimal(orderProduct.getSaleNum() + "");
					salePriceB = salePriceB.multiply(saleNumB);
					BigDecimal salePriceTotalB = new BigDecimal(salePrice + "");
					salePriceTotalB = salePriceTotalB.add(salePriceB);
					salePrice = salePriceTotalB.floatValue();
				}
			}
			// 添加订单信息
			orderApply.setLogid(DateUtil.getLogid());
			orderApply.setOrderId(vending.getCorpId() + "-" + this.sequenceIdDao.findNextVal(vending.getCorpId(), "order_apply_id", 7));
			orderApply.setTorderId("");
			orderApply.setCorpId(vending.getCorpId());
			orderApply.setDistrictId(vending.getDistrictId());
			orderApply.setLineId(vending.getLineId());
			orderApply.setPointId(vending.getPointId());
			orderApply.setSiteId(vending.getSiteId());
			orderApply.setSiteName(vending.getSiteName());
			orderApply.setOutState(GParameter.outState_no);
			orderApply.setPayState(GParameter.payState_wait);
			orderApply.setReturnType(GParameter.returnType_none);
			orderApply.setPushState(GParameter.pushState_pushSuccess);
			orderApply.setpStateTime(orderApply.getCreateTime());
			Dict dict = (Dict) CacheUtils.get(CacheUtils.errorCodeCache, "fetchTime_over");
			String fetch_overTime = dict != null ? dict.getDictValue() : null;
			if (null == fetch_overTime || "".equals(fetch_overTime)) {
				fetch_overTime = GParameter.fetchTime_over;
			}
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.HOUR_OF_DAY, Integer.parseInt(fetch_overTime));
			fetch_overTime = df.format(c.getTime());
			orderApply.setFetchOverTime(fetch_overTime);
			orderApply.setCurState(GParameter.orderState_apply);
			orderApply.setStateTime(orderApply.getCreateTime());
			orderApply.setAbnomarlState(GParameter.abnormalType_none);
			orderApply.setaStateTime(orderApply.getCreateTime());
			orderApply.setOrderType(GParameter.orderType_normal);
			// 生成密码
			String password = VerifiCodeUtils.getInstance().getPassword();
			orderApply.setPassWord(password);
			dict = (Dict) CacheUtils.get(CacheUtils.errorCodeCache, "encodeType_password");
			String encodeType = dict != null ? dict.getDictValue() : null;
			if (encodeType == null || "".equals(encodeType)) {
				encodeType = "1";// 1:NONE 2:3DES 3:AES 4:SHA256 5:MD5
			}
			orderApply.setEncryptionType(encodeType);
			orderApply.setSlat(BaseEncrypt.getSalt());
			int type = Integer.parseInt(encodeType);
			Encrypt encrypt = EncryptProvider.getEncrypt(type);
			String pwd = encrypt.doEncrypt(orderApply.getPassWord(), orderApply.getSlat());
			if (null != pwd) {
				orderApply.setPassWord(pwd);
			}
			// 判断60天内订单密码是否重复，重复则重新生成
			OrderApply orderApplyT = new OrderApply();
			orderApplyT.setPassWord(orderApply.getPassWord());
			String create_time = DateUtil.formatDate(DateUtil.getDate(new Date(), -60));
			orderApplyT.setCreateTime(create_time);
			List<OrderApply> list = orderApplyDao.findAll(orderApplyT);
			if (null != list && list.size() > 1) {
				password = VerifiCodeUtils.getInstance().getPassword();
				orderApply.setPassWord(password);
				pwd = encrypt.doEncrypt(orderApply.getPassWord(), orderApply.getSlat());
				if (null != pwd) {
					orderApply.setPassWord(pwd);
				}
			}
			orderApply.setSalePrice(salePrice);
			// 查询站点优惠信息
			FavourableObject favourableObjectP = new FavourableObject();
			favourableObjectP.setCorpId(vending.getCorpId());
			favourableObjectP.setFavObjId(vending.getSiteId());
			favourableObjectP.setValidSTime(DateUtil.getDate());
			favourableObjectP.setFavType(GParameter.favType_vending);
			favourableObjectP.setPayType(orderApply.getPayType());
			FavourableObject favourableObjectSite = favourableObjectDao.findOne(favourableObjectP);
			if (favourableObjectSite != null) {
				orderApply.setFavWay(favourableObjectSite.getFavWay());
				if (favourableObjectSite.getFavWay().equals(GParameter.favWay_discount)) {
					orderApply.setPayPrice(orderApply.getSalePrice() * favourableObjectSite.getDiscount());
				} else if (favourableObjectSite.getFavWay().equals(GParameter.favWay_reduce)) {
					orderApply.setPayPrice(orderApply.getSalePrice() - favourableObjectSite.getDiscount());
				}
				orderApply.setFavPrice(orderApply.getSalePrice() - orderApply.getPayPrice());
			}
			// 添加订单商品信息
			BigDecimal buyPriceTotal = new BigDecimal("0.0");
			BigDecimal payPriceTotal = new BigDecimal("0.0");
			SequenceId orderProductId = this.sequenceIdDao.findOne(orderApply.getCorpId(), "order_product_id");
			SequenceId orderBoxId = this.sequenceIdDao.findOne(orderApply.getCorpId(), "order_box_id");

			for (OrderProduct orderProduct : orderProductList) {
				orderProduct.setLogid(DateUtil.getLogid());
				orderProduct.setProdetailId(orderApply.getCorpId() + "-" + super.getGenPriKey(orderProductId.getPriKey()));
				orderProduct.setCorpId(orderApply.getCorpId());
				orderProduct.setOrderId(orderApply.getOrderId());
				orderProduct.setTorderId(orderApply.getTorderId());
				ProductOnline productOnline = productOnlineDao.findByProductId(orderProduct.getProductId());
				if (null != productOnline) {
					orderProduct.setProductName(productOnline.getName());
					orderProduct.setProductTypeId(productOnline.getTypeId());
					orderProduct.setFactoryId(productOnline.getFactoryName());
					orderProduct.setNormalPrice(productOnline.getSalePrice());
					orderProduct.setSalePrice(productOnline.getSalePrice());
				}
				// 如果站点优惠是空则继续查找商品优惠
				if (null == favourableObjectSite) {
					favourableObjectP.setCorpId(vending.getCorpId());
					favourableObjectP.setFavObjId(orderProduct.getProductId());
					favourableObjectP.setValidSTime(DateUtil.getDate());
					favourableObjectP.setFavType(GParameter.favType_product);
					favourableObjectP.setPayType(orderApply.getPayType());
					FavourableObject favourableObjectProduct = favourableObjectDao.findOne(favourableObjectP);
					if (favourableObjectProduct != null) {
						orderApply.setFavWay(favourableObjectProduct.getFavWay());
						if (favourableObjectProduct.getFavWay().equals(GParameter.favWay_discount)) {
							BigDecimal salePriceB = new BigDecimal(orderProduct.getSalePrice() + "");
							BigDecimal numB = new BigDecimal(favourableObjectProduct.getDiscount() + "");
							salePriceB = salePriceB.multiply(numB);
							orderProduct.setSalePrice(salePriceB.floatValue());
						} else if (favourableObjectProduct.getFavWay().equals(GParameter.favWay_reduce)) {
							BigDecimal salePriceB = new BigDecimal(orderProduct.getSalePrice() + "");
							BigDecimal numB = new BigDecimal(favourableObjectProduct.getDiscount() + "");
							salePriceB = salePriceB.subtract(numB);
							orderProduct.setSalePrice(salePriceB.floatValue());
						}
					}
					BigDecimal salePriceB = new BigDecimal(orderProduct.getSalePrice() + "");
					BigDecimal numB = new BigDecimal(orderProduct.getSaleNum() + "");
					salePriceB = salePriceB.multiply(numB);
					payPriceTotal = payPriceTotal.add(salePriceB);
					orderApply.setPayPrice(payPriceTotal.floatValue());

					BigDecimal salePriceBB = new BigDecimal(orderApply.getSalePrice() + "");
					BigDecimal numBB = new BigDecimal(orderApply.getPayPrice() + "");
					orderApply.setFavPrice(salePriceBB.subtract(numBB).floatValue());
				} else {
					BigDecimal favPrice = new BigDecimal(orderApply.getSalePrice() + "");
					BigDecimal pNum = new BigDecimal(orderApply.getSalePrice() + "");
					BigDecimal saleNum = new BigDecimal(orderApply.getSalePrice() + "");
					BigDecimal salePriceB = new BigDecimal(orderProduct.getSalePrice() + "");
					favPrice = favPrice.multiply(pNum).multiply(saleNum);
					orderProduct.setSalePrice(salePriceB.subtract(favPrice).floatValue());
				}
				orderProduct.setCreateTime(DateUtil.getNow());
				orderProductDao.insert(orderProduct);
			}
			for (OrderBox orderBoxLane : orderApply.getOrderLaneList()) {
				// 订单商品信息
				OrderProduct orderProduct = null;
				for (OrderProduct orderProductR : orderProductList) {
					if (orderProductR.getProductId().equals(orderBoxLane.getProductId())) {
						orderProduct = orderProductR;
						continue;
					}
				}
				// 查询补货货道信息来得出进价
				SupplyVproduct supplyVproductP = new SupplyVproduct();
				supplyVproductP.setSiteId(orderApply.getSiteId());
				supplyVproductP.setProductId(orderBoxLane.getProductId());
				supplyVproductP.setCurState(GParameter.supplyVproductState_finish);
				supplyVproductP.setInvalidState(GParameter.supplyVproductInvalidState_noOver);
				supplyVproductP.setFinshState(GParameter.supplyVproductFinishState_in);
				supplyVproductP.setLaneSId(orderBoxLane.getLaneSId());
				supplyVproductP.setLaneEId(orderBoxLane.getLaneEId());
				List<SupplyVproduct> supplyVproductList = supplyVproductDao.findSupplyVproductByLane(supplyVproductP);
				int noOutTotalNum = 0;// 计算未出库总数
				for (SupplyVproduct supplyVproduct : supplyVproductList) {
					noOutTotalNum += supplyVproduct.getrSupplyNum() - supplyVproduct.getSaleNum();
				}
				if (noOutTotalNum < orderBoxLane.getSaleNum()) {
					log.error("订单申请模块  站点编号:" + orderApply.getSiteId() + " 商品编号:" + orderBoxLane.getProductId() + " 在货道补货记录流水中没有足够的库存 库存数为:" + noOutTotalNum + " 无法分配出库货道.");
					throw new SystemException(GConstent.SupplyVProduct_No_Product, "" + GConstent.SupplyVProduct_No_Product);
				}
				int vproductIndex = 0;
				SupplyVproduct supplyVproduct = supplyVproductList.get(vproductIndex);
				// 查询未出库的货道出库数量
				int noOutNum = supplyVproduct.getrSupplyNum() - supplyVproduct.getSaleNum();
				// 插入订单商品货道信息
				for (int i = 1; i <= orderBoxLane.getSaleNum(); i++) {
					// 查询采购价
					if (noOutNum == 0 && i > 1) {
						vproductIndex++;
						supplyVproduct = supplyVproductList.get(vproductIndex);
					}
					noOutNum--;
					OrderBox orderBox = new OrderBox();
					orderBox.setLogid(DateUtil.getLogid());
					orderBox.setProboxId(orderApply.getCorpId() + "-" + super.getGenPriKey(orderBoxId.getPriKey()));
					orderBox.setCorpId(orderApply.getCorpId());
					orderBox.setProdetailId(orderProduct.getProdetailId());
					orderBox.setOrderId(orderApply.getOrderId());
					orderBox.setTorderId(orderApply.getTorderId());
					orderBox.setSiteId(orderApply.getSiteId());
					orderBox.setSiteName(orderApply.getSiteName());
					orderBox.setLaneSId(supplyVproduct.getLaneSId());
					orderBox.setLaneEId(supplyVproduct.getLaneEId());
					orderBox.setProductId(orderProduct.getProductId());
					orderBox.setProductName(orderProduct.getProductName());
					orderBox.setBuyPrice(supplyVproduct.getBuyPrice());
					orderBox.setSalePrice(orderProduct.getNormalPrice());
					orderBox.setPayPrice(orderProduct.getSalePrice());
					BigDecimal salePriceB = new BigDecimal(orderBox.getSalePrice() + "");
					BigDecimal payPriceB = new BigDecimal(orderBox.getPayPrice() + "");
					BigDecimal buyPriceB = new BigDecimal(orderBox.getBuyPrice() + "");
					orderBox.setFavPrice(salePriceB.subtract(payPriceB).floatValue());
					orderBox.setProfitMoney(payPriceB.subtract(buyPriceB).floatValue());
					orderBox.setSupplyId(supplyVproduct.getsOrderId());
					orderBox.setOutIndex(0);
					orderBox.setOutState(GParameter.orderBoxOutState_in);
					orderBox.setStateTime(DateUtil.getNow());
					orderBox.setCreateTime(DateUtil.getNow());
					orderBoxDao.insert(orderBox);
					buyPriceTotal = buyPriceTotal.add(buyPriceB);
					orderBoxList.add(orderBox);
				}
			}
			this.sequenceIdDao.update(orderProductId);
			this.sequenceIdDao.update(orderBoxId);
			if (GParameter.payType_noPay.equals(orderApply.getPayType())) {
				Random rand = new Random();
				if (rand.nextBoolean()) {
					orderApply.setPayType(GParameter.payType_Alipay);
				} else {
					orderApply.setPayType(GParameter.payType_weChat);
				}
				orderApply.setPayState(GParameter.payState_success);
			} else {
				// 申请支付
				Map<String, String> payResult = OrderPayUtils.applyOrderPayInfo(orderApply);
				if (payResult.get("code").equals("0000")) {
					if (orderApply.getPayType().equals(GParameter.payType_weChat)) {
						orderApply.setImgUrl(payResult.get("QRUrl"));
						orderApply.setCodeUrl(payResult.get("QRCode"));
					}
					if (orderApply.getPayType().equals(GParameter.payType_Alipay)) {
						orderApply.setImgUrl(payResult.get("QRUrl"));
						orderApply.setCodeUrl(payResult.get("QRCode"));
					}
					if (orderApply.getPayType().equals(GParameter.payType_weChatG)) {
						orderApply.setAppId(payResult.get("AppId"));
						orderApply.setTimeStamp(payResult.get("TimeStamp"));
						orderApply.setNonceStr(payResult.get("NonceStr"));
						orderApply.setPckage(payResult.get("Pckage"));
						orderApply.setSignType(payResult.get("SignType"));
						orderApply.setPaySign(payResult.get("PaySign"));
					}
					if (orderApply.getPayType().equals(GParameter.payType_weChatApp)) {
						orderApply.setAppId(payResult.get("AppId"));
						orderApply.setTimeStamp(payResult.get("TimeStamp"));
						orderApply.setNonceStr(payResult.get("NonceStr"));
						orderApply.setPckage(payResult.get("Pckage"));
						orderApply.setSignType(payResult.get("SignType"));
						orderApply.setPaySign(payResult.get("PaySign"));
					}
				} else {
					throw new SystemException(GConstent.OrderApply_PayInfo_Error, "" + GConstent.OrderApply_PayInfo_Error);
				}
			}
			orderApply.setBuyPrice(buyPriceTotal.floatValue());
			orderApply.setProfitMoney(payPriceTotal.subtract(buyPriceTotal).floatValue());
			orderApplyDao.insert(orderApply);
			//减站点库存
			if(null != orderProductList && orderProductList.size() > 0){
				for (OrderProduct orderProductP : orderProductList) {
					VendingStock vendingStock = new VendingStock();
					vendingStock.setSiteId(orderApply.getSiteId());
					vendingStock.setProductId(orderProductP.getProductId());
					VendingStock vendingStockR = vendingStockDao.findOne(vendingStock);
					if(null != vendingStockR){
						vendingStockR.setNum(vendingStockR.getNum() - orderProductP.getSaleNum());
						vendingStockDao.update(vendingStockR);
					}
				}
			}
			// 插入订单状态信息表
			OrderChange orderChange = new OrderChange();
			orderChange.setLogid(DateUtil.getLogid());
			orderChange.setChangeId(orderApply.getCorpId() + "-" + this.sequenceIdDao.findNextVal(orderApply.getCorpId(), "order_change_id", 7));
			orderChange.setCorpId(orderApply.getCorpId());
			orderChange.setOrderId(orderApply.getOrderId());
			orderChange.setOperId(orderApply.getLoginId());
			orderChange.setOperName(orderApply.getLoginName());
			orderChange.setSiteId(orderApply.getSiteId());
			orderChange.setSiteName(orderApply.getSiteName());
			orderChange.setOperAction(GParameter.orderChange_apply);
			orderChange.setOperTime(orderApply.getCreateTime());
			orderChange.setOperCont("售货机:" + orderApply.getSiteName() + " 订单编号:" + orderApply.getOrderId() + " 申请成功!");
			orderChange.setCreateTime(DateUtil.getNow());
			orderChange.setPocState(GParameter.OrderChangeProc_Pushed);
			orderChange.setPocTimes(0);
			orderChange.setPocResult("wait push");
			orderChange.setProState(GParameter.OrderChangeProState_waitFinsh);
			this.orderChangeDao.insert(orderChange);
		}
		return orderDeliveryResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1, orderApply,orderBoxList);
	}

	public void setOrderApplyDao(OrderApplyDao orderApplyDao) {
		this.orderApplyDao = orderApplyDao;
	}

	public void setSequenceIdDao(SequenceIdDao sequenceIdDao) {
		this.sequenceIdDao = sequenceIdDao;
	}

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
	}

	public void setOrderChangeDao(OrderChangeDao orderChangeDao) {
		this.orderChangeDao = orderChangeDao;
	}

	public void setOrderProductDao(OrderProductDao orderProductDao) {
		this.orderProductDao = orderProductDao;
	}

	public void setVendingStockDao(VendingStockDao vendingStockDao) {
		this.vendingStockDao = vendingStockDao;
	}

	public void setFavourableObjectDao(FavourableObjectDao favourableObjectDao) {
		this.favourableObjectDao = favourableObjectDao;
	}

	public void setSupplyVproductDao(SupplyVproductDao supplyVproductDao) {
		this.supplyVproductDao = supplyVproductDao;
	}

	public void setProductOnlineDao(ProductOnlineDao productOnlineDao) {
		this.productOnlineDao = productOnlineDao;
	}

	public void setOrderDeliveryReq(OrderDeliveryReq orderDeliveryReq) {
		this.orderDeliveryReq = orderDeliveryReq;
	}

	public void setOrderDeliveryResp(OrderDeliveryResp orderDeliveryResp) {
		this.orderDeliveryResp = orderDeliveryResp;
	}

	public void setOrderBoxDao(OrderBoxDao orderBoxDao) {
		this.orderBoxDao = orderBoxDao;
	}

}
