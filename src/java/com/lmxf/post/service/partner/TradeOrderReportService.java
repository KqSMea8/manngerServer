package com.lmxf.post.service.partner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.add.FavourableObject;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.entity.order.OrderChange;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.entity.parameter.Dict;
import com.lmxf.post.entity.product.ProductInfo;
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
import com.lmxf.post.repository.product.ProductInfoDao;
import com.lmxf.post.repository.product.ProductOnlineDao;
import com.lmxf.post.repository.supply.SupplyVproductDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.repository.vending.VendingStockDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.OrderReportReq;
import com.lmxf.post.tradepkg.partner.OrderReportResp;

public class TradeOrderReportService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeOrderReportService.class);
	private OrderReportReq orderReportReq;
	private OrderReportResp orderReportResp;
	private OrderApplyDao orderApplyDao;
	private VendingDao vendingDao;
	private VendingStockDao vendingStockDao;
	private SequenceIdDao sequenceIdDao;
	private FavourableObjectDao favourableObjectDao;
	private ProductOnlineDao productOnlineDao;
	private OrderProductDao orderProductDao;
	private SupplyVproductDao supplyVproductDao;
	private OrderBoxDao orderBoxDao;
	private OrderChangeDao orderChangeDao;
	private ProductInfoDao productInfoDao;
	
	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		OrderApply orderApply = null;
		Map ret = null;
		try {
			ret = orderReportReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("orderReportReqReq.parseXml is error!");
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
		if(null != orderApplyList && orderApplyList.size() > 0){
			return errorCodeDao.getErrorRespJson(GConstent.Unpaid_Order_Exist);
		}else {
			Vending vending = vendingDao.findBySiteId(orderApply.getSiteId());
			if(vending == null){
				return errorCodeDao.getErrorRespJson(GConstent.Site_No_Define);
			}
			//判断购买商品数是否大于商品可售卖库存
			float salePrice = 0;
			List<OrderProduct> orderProductList = orderApply.getOrderProductList();
			if(null != orderProductList && orderProductList.size() > 0){
				for (OrderProduct orderProduct : orderProductList) {
					VendingStock vendingStock = new VendingStock();
					vendingStock.setSiteId(orderApply.getSiteId());
					ProductInfo productInfo=productInfoDao.findByProductCode(orderProduct.getProductCode());
					if(productInfo==null){
						return errorCodeDao.getErrorRespJson(GConstent.Product_Info_No_Found);
					}
					orderProduct.setProductId(productInfo.getProductId());
					vendingStock.setProductId(productInfo.getProductId());
					VendingStock vendingStockR = vendingStockDao.findOne(vendingStock);
					if(null != vendingStockR){
						if(orderProduct.getSaleNum() > vendingStockR.getNum()){
							return errorCodeDao.getErrorRespJson(GConstent.Product_Num_Lack);
						}
					}else{
						return errorCodeDao.getErrorRespJson(GConstent.Product_NumZero);
					}
					salePrice = vendingStockR.getSalePrice() * orderProduct.getSaleNum() + salePrice;
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
			orderApply.setpNum(orderApply.getOrderProductList().size());
			orderApply.setPayType(GParameter.payType_weChat);
			orderApply.setPayState(GParameter.payState_wait);
			orderApply.setReturnType(GParameter.returnType_none);
			orderApply.setPushState(GParameter.pushState_pushSuccess);
			orderApply.setpStateTime(DateUtil.getNow());
			Dict dict=(Dict) CacheUtils.get(CacheUtils.errorCodeCache,"fetchTime_over");
		    String fetch_overTime = dict!=null?dict.getDictValue():null;
			if(null == fetch_overTime || "".equals(fetch_overTime)){
				fetch_overTime = GParameter.fetchTime_over;
			}
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.HOUR_OF_DAY, Integer.parseInt(fetch_overTime));
			fetch_overTime = df.format(c.getTime());
			orderApply.setFetchOverTime(fetch_overTime);
			orderApply.setCurState(GParameter.orderState_customerfetch);
			orderApply.setStateTime(DateUtil.getNow());
			orderApply.setAbnomarlState(GParameter.abnormalType_none);
			orderApply.setaStateTime(DateUtil.getNow());
			orderApply.setOrderType(GParameter.orderType_close);
			orderApply.setCreateTime(DateUtil.getNow());
			
			orderApply.setSalePrice(salePrice);
			//查询站点优惠信息
			FavourableObject favourableObjectP = new FavourableObject();
			favourableObjectP.setCorpId(vending.getCorpId());
			favourableObjectP.setFavObjId(vending.getSiteId());
			favourableObjectP.setValidSTime(DateUtil.getDate());
			favourableObjectP.setFavType(GParameter.favType_vending);
			favourableObjectP.setPayType("0"+orderApply.getPayType());
			FavourableObject favourableObjectSite = favourableObjectDao.findOne(favourableObjectP);
			if(favourableObjectSite != null){
				orderApply.setFavWay(favourableObjectSite.getFavWay());
				if(favourableObjectSite.getFavWay().equals(GParameter.favWay_discount)){
					orderApply.setPayPrice(orderApply.getSalePrice() * favourableObjectSite.getDiscount());
				}else if(favourableObjectSite.getFavWay().equals(GParameter.favWay_reduce)){
					orderApply.setPayPrice(orderApply.getSalePrice() - favourableObjectSite.getDiscount());
				}
				orderApply.setFavPrice(orderApply.getSalePrice() - orderApply.getPayPrice());
			}
			//添加订单商品信息
			float buyPrice = 0;
			float payPrice = 0;
			if(null != orderProductList && orderProductList.size() > 0){
				for (OrderProduct orderProduct : orderProductList) {
					orderProduct.setLogid(DateUtil.getLogid());
					orderProduct.setProdetailId(orderApply.getCorpId() + "-" + this.sequenceIdDao.findNextVal(orderApply.getCorpId(), "order_product_id", 7));
					orderProduct.setCorpId(orderApply.getCorpId());
					orderProduct.setOrderId(orderApply.getOrderId());
					orderProduct.setTorderId(orderApply.getTorderId());
					ProductOnline productOnline = productOnlineDao.findByProductId(orderProduct.getProductId());
					if(null != productOnline){
						orderProduct.setProductName(productOnline.getName());
						orderProduct.setProductTypeId(productOnline.getTypeId());
						orderProduct.setFactoryId(productOnline.getFactoryName());;
						orderProduct.setNormalPrice(productOnline.getSalePrice());
						orderProduct.setSalePrice(productOnline.getSalePrice());
					}
					if(null == favourableObjectSite){
						favourableObjectP.setCorpId(vending.getCorpId());
						favourableObjectP.setFavObjId(orderProduct.getProductId());
						favourableObjectP.setValidSTime(DateUtil.getDate());
						favourableObjectP.setFavType(GParameter.favType_product);
						favourableObjectP.setPayType(orderApply.getPayType());
						FavourableObject favourableObjectProduct = favourableObjectDao.findOne(favourableObjectP);
						if(favourableObjectProduct != null){
							orderApply.setFavWay(favourableObjectProduct.getFavWay());
							if(favourableObjectProduct.getFavWay().equals(GParameter.favWay_discount)){
								orderProduct.setSalePrice(orderProduct.getSalePrice() * favourableObjectProduct.getDiscount());
							}else if(favourableObjectProduct.getFavWay().equals(GParameter.favWay_reduce)){
								orderProduct.setSalePrice(orderProduct.getSalePrice() - favourableObjectProduct.getDiscount());
							}
						}
						orderProduct.setSaleNum(1);
						payPrice = orderProduct.getSalePrice() * orderProduct.getSaleNum()+ payPrice;
						orderApply.setPayPrice(payPrice);
						orderApply.setFavPrice(orderApply.getSalePrice() - orderApply.getPayPrice());
					}else{
						orderProduct.setSalePrice(orderProduct.getSalePrice() - (orderApply.getFavPrice()/(float)orderApply.getpNum()/(float)orderProduct.getSaleNum()));
					}
					orderProduct.setCreateTime(DateUtil.getNow());
					orderProductDao.insert(orderProduct);
					//更新库存
					VendingStock vendingStock = new VendingStock();
					vendingStock.setSiteId(orderApply.getSiteId());
					vendingStock.setProductId(orderProduct.getProductId());
					VendingStock vendingStockR = vendingStockDao.findOne(vendingStock);
					if(null != vendingStockR){
						vendingStockR.setNum((vendingStockR.getNum() - orderProduct.getSaleNum()) > 0 ? (vendingStockR.getNum() - orderProduct.getSaleNum()) : 0);
						vendingStockDao.update(vendingStockR);
					}
					int saleNum  = orderProduct.getSaleNum();
					while(saleNum > 0){
						//查询采购价
						SupplyVproduct supplyVproductP = new SupplyVproduct();
						supplyVproductP.setSiteId(orderApply.getSiteId());
						supplyVproductP.setProductId(orderProduct.getProductId());
						SupplyVproduct supplyVproduct = supplyVproductDao.findOne(supplyVproductP);
						if(null != supplyVproduct){
							buyPrice = supplyVproduct.getBuyPrice() * orderProduct.getSaleNum() + buyPrice;
							//插入订单商品货道信息
							if(supplyVproduct.getrSupplyNum() - supplyVproduct.getSaleNum() >= saleNum){
								for(int i =1 ; i <= orderProduct.getSaleNum() ; i++){
									OrderBox orderBox = new OrderBox();
									orderBox.setLogid(DateUtil.getLogid());
									orderBox.setProboxId(orderApply.getCorpId() + "-" + this.sequenceIdDao.findNextVal(orderApply.getCorpId(), "order_box_id", 7));
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
									orderBox.setFavPrice(orderBox.getSalePrice() - orderBox.getPayPrice());
									orderBox.setProfitMoney(orderBox.getPayPrice() - orderBox.getBuyPrice());
									orderBox.setSupplyId(supplyVproduct.getsOrderId());
									orderBox.setOutIndex(i);
									orderBox.setOutState(GParameter.outState_yes);
									orderBox.setStateTime(DateUtil.getNow());
									orderBox.setCreateTime(DateUtil.getNow());
									orderBoxDao.insert(orderBox);
								}
								saleNum = 0;
							}else{
								for(int i =1 ; i <= (supplyVproduct.getrSupplyNum() - supplyVproduct.getSaleNum()) ; i++){
									OrderBox orderBox = new OrderBox();
									orderBox.setLogid(DateUtil.getLogid());
									orderBox.setProboxId(orderApply.getCorpId() + "-" + this.sequenceIdDao.findNextVal(orderApply.getCorpId(), "order_box_id", 7));
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
									orderBox.setFavPrice(orderBox.getSalePrice() - orderBox.getPayPrice());
									orderBox.setProfitMoney(orderBox.getPayPrice() - orderBox.getBuyPrice());
									orderBox.setSupplyId(supplyVproduct.getsOrderId());
									orderBox.setOutIndex(i);
									orderBox.setOutState(GParameter.outState_yes);
									orderBox.setStateTime(DateUtil.getNow());
									orderBox.setCreateTime(DateUtil.getNow());
									orderBoxDao.insert(orderBox);
								}
								saleNum = saleNum - (supplyVproduct.getrSupplyNum() - supplyVproduct.getSaleNum());
								
							}
						}
					}
				}
			}
			orderApply.setBuyPrice(buyPrice);
			orderApply.setProfitMoney(orderApply.getPayPrice() - orderApply.getBuyPrice());
			orderApplyDao.insert(orderApply);
			//插入订单状态信息表
			OrderChange orderChange = new OrderChange();
			orderChange.setLogid(DateUtil.getLogid());
			orderChange.setChangeId(orderApply.getCorpId() + "-" + this.sequenceIdDao.findNextVal(orderApply.getCorpId(), "order_change_id", 7));
			orderChange.setCorpId(orderApply.getCorpId());
			orderChange.setOrderId(orderApply.getOrderId());
			orderChange.setOperId(orderApply.getLoginId());
			orderChange.setOperName(orderApply.getLoginName());
			orderChange.setSiteId(orderApply.getSiteId());
			orderChange.setSiteName(orderApply.getSiteName());
			orderChange.setOperAction(GParameter.orderChange_customerFetch);
			orderChange.setOperTime(DateUtil.getNow());
			orderChange.setOperCont("售货机:" + orderApply.getSiteName() + " 订单编号:" + orderApply.getOrderId() + " 客户已取货!");
			orderChange.setCreateTime(DateUtil.getNow());
			orderChange.setPocState(GParameter.OrderChangeProc_waitPush);
			orderChange.setPocTimes(0);
			orderChange.setPocResult("wait push");
			orderChange.setProState(GParameter.OrderChangeProState_waitFinsh);
			this.orderChangeDao.insert(orderChange);
		}
		return orderReportResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1,orderApply);
	}

	public void setOrderReportReq(OrderReportReq orderReportReq) {
		this.orderReportReq = orderReportReq;
	}

	public void setOrderReportResp(OrderReportResp orderReportResp) {
		this.orderReportResp = orderReportResp;
	}

	public void setOrderApplyDao(OrderApplyDao orderApplyDao) {
		this.orderApplyDao = orderApplyDao;
	}

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
	}

	public void setVendingStockDao(VendingStockDao vendingStockDao) {
		this.vendingStockDao = vendingStockDao;
	}

	public void setSequenceIdDao(SequenceIdDao sequenceIdDao) {
		this.sequenceIdDao = sequenceIdDao;
	}

	public void setFavourableObjectDao(FavourableObjectDao favourableObjectDao) {
		this.favourableObjectDao = favourableObjectDao;
	}

	public void setProductOnlineDao(ProductOnlineDao productOnlineDao) {
		this.productOnlineDao = productOnlineDao;
	}

	public void setOrderProductDao(OrderProductDao orderProductDao) {
		this.orderProductDao = orderProductDao;
	}

	public void setSupplyVproductDao(SupplyVproductDao supplyVproductDao) {
		this.supplyVproductDao = supplyVproductDao;
	}

	public void setOrderBoxDao(OrderBoxDao orderBoxDao) {
		this.orderBoxDao = orderBoxDao;
	}

	public void setOrderChangeDao(OrderChangeDao orderChangeDao) {
		this.orderChangeDao = orderChangeDao;
	}

	public void setProductInfoDao(ProductInfoDao productInfoDao) {
		this.productInfoDao = productInfoDao;
	}
	
}
