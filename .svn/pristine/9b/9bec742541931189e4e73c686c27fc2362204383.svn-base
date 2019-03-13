package com.lmxf.post.service.order;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.JSONUtils;
import com.lmxf.post.core.utils.VerifiCodeUtils;
import com.lmxf.post.core.utils.encode.BaseEncrypt;
import com.lmxf.post.core.utils.encode.Encrypt;
import com.lmxf.post.core.utils.encode.EncryptProvider;
import com.lmxf.post.core.utils.pay.PayUtils;
import com.lmxf.post.entity.add.FavourableObject;
import com.lmxf.post.entity.order.OrderApply;
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.entity.order.OrderChange;
import com.lmxf.post.entity.order.OrderProduct;
import com.lmxf.post.entity.parameter.Dict;
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
import com.lmxf.post.tradepkg.order.OrderDeliveryPayReq;
import com.lmxf.post.tradepkg.order.OrderDeliveryPayResp;
public class TradeSCMOrderDeliveryPayService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeOrderDeliveryService.class);
	private OrderDeliveryPayReq orderDeliveryPayReq;
	private OrderDeliveryPayResp orderDeliveryPayResp;
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
		try {
			ret = orderDeliveryPayReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("OrderDeliveryPayReq.parseXml is error!");
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
					vendingStock.setProductId(orderProduct.getProductId());
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
			orderApply.setCurState(GParameter.orderState_apply);
			orderApply.setStateTime(DateUtil.getNow());
			orderApply.setAbnomarlState(GParameter.abnormalType_none);
			orderApply.setaStateTime(DateUtil.getNow());
			orderApply.setOrderType(GParameter.orderType_normal);
			orderApply.setCreateTime(DateUtil.getNow());
			// 生成密码
			String password = VerifiCodeUtils.getInstance().getPassword();
			orderApply.setPassWord(password);
			dict=(Dict) CacheUtils.get(CacheUtils.errorCodeCache,"encodeType_password");
			String encodeType = dict!=null?dict.getDictValue():null;
			if (encodeType == null || "".equals(encodeType)) {
				encodeType = "1";//1:NONE 2:3DES 3:AES 4:SHA256 5:MD5
			}
			orderApply.setEncryptionType(encodeType);
			orderApply.setSlat(BaseEncrypt.getSalt());
			int type = Integer.parseInt(encodeType);
			Encrypt encrypt = EncryptProvider.getEncrypt(type);
			String pwd = encrypt.doEncrypt(orderApply.getPassWord(), orderApply.getSlat());
			if (null != pwd) {
				orderApply.setPassWord(pwd);
			}
			//判断60天内订单密码是否重复，重复则重新生成
			OrderApply orderApplyT = new OrderApply();
			orderApplyT.setPassWord(orderApply.getPassWord());
			String create_time = DateUtil.formatDate(DateUtil.getDate(new Date(), -60));
			orderApplyT.setCreateTime(create_time);
			List<OrderApply> list = orderApplyDao.findAll(orderApplyT);
			if(null != list && list.size() > 1){
				password = VerifiCodeUtils.getInstance().getPassword();
				orderApply.setPassWord(password);
				pwd = encrypt.doEncrypt(orderApply.getPassWord(), orderApply.getSlat());
				if (null != pwd) {
					orderApply.setPassWord(pwd);
				} 
			}
			orderApply.setSalePrice(salePrice);
			//查询站点优惠信息
			FavourableObject favourableObjectP = new FavourableObject();
			favourableObjectP.setCorpId(vending.getCorpId());
			favourableObjectP.setFavObjId(vending.getSiteId());
			favourableObjectP.setValidSTime(DateUtil.getDate());
			favourableObjectP.setFavType(GParameter.favType_vending);
			favourableObjectP.setPayType(orderApply.getPayType());
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
					//查询采购价
					SupplyVproduct supplyVproductP = new SupplyVproduct();
					supplyVproductP.setSiteId(orderApply.getSiteId());
					supplyVproductP.setProductId(orderProduct.getProductId());
					SupplyVproduct supplyVproduct = supplyVproductDao.findOne(supplyVproductP);
					if(null != supplyVproduct){
						buyPrice = supplyVproduct.getBuyPrice() * orderProduct.getSaleNum() + buyPrice;
					}
					//添加订单商品货道信息
					List<OrderBox> orderBoxList = orderProduct.getOrderBoxList();
					for(OrderBox orderBox : orderBoxList){
						orderBox.setLogid(DateUtil.getLogid());
						orderBox.setProboxId(orderApply.getCorpId() + "-" + this.sequenceIdDao.findNextVal(orderApply.getCorpId(), "order_box_id", 7));
						orderBox.setCorpId(orderApply.getCorpId());
						orderBox.setProdetailId(orderProduct.getProdetailId());
						orderBox.setOrderId(orderApply.getOrderId());
						orderBox.setTorderId(orderApply.getTorderId());
						orderBox.setSiteId(orderApply.getSiteId());
						orderBox.setSiteName(orderApply.getSiteName());
						orderBox.setProductName(orderProduct.getProductName());
						if(null != supplyVproduct){
							orderBox.setBuyPrice(supplyVproduct.getBuyPrice());
						}
						orderBox.setSalePrice(orderProduct.getNormalPrice());
						orderBox.setPayPrice(orderProduct.getSalePrice());
						orderBox.setFavPrice(orderBox.getSalePrice() - orderBox.getPayPrice());
						orderBox.setProfitMoney(orderBox.getPayPrice() - orderBox.getBuyPrice());
						orderBox.setOutState(GParameter.orderBoxOutState_in);
						orderBox.setStateTime(DateUtil.getNow());
						orderBox.setCreateTime(DateUtil.getNow());
						orderBoxDao.insert(orderBox);
					
					}
				}
			}
			String xml = "";
			orderApply.setPayMethod(orderApply.getPayType());
			try {
				xml = PayUtils.tradeHand(orderApply);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//微信扫码支付
			if(GParameter.payType_weChat.equals(orderApply.getPayType())){
				JSONObject JSONBody = JSONUtils.getHeader(xml);
				String retcode = JSONBody.getString("RetCode");
				String retmsg = JSONBody.getString("RetMsg");
				JSONBody = JSONUtils.getBody(xml);
				String out_trade_no = JSONBody.getString("out_trade_no");
				String img_url = JSONBody.getString("img_url");
				String code_url = JSONBody.getString("qr_code");
				if("0000".equals(retcode)){
					orderApply.setTitle("0000");
					orderApply.setpTradeNo(out_trade_no);
					orderApply.setImgUrl(img_url);
					orderApply.setCodeUrl(code_url);
				}else{
					orderApply.setTitle(retmsg);
				}
			}
			//支付宝扫码支付
			if(GParameter.payType_Alipay.equals(orderApply.getPayType())){
				JSONObject JSONBody = JSONUtils.getHeader(xml);
				String retcode = JSONBody.getString("RetCode");
				String retmsg = JSONBody.getString("RetMsg");
				JSONBody = JSONUtils.getBody(xml);
				String out_trade_no = JSONBody.getString("out_trade_no");
				String img_url = JSONBody.getString("img_url");
				String code_url = JSONBody.getString("qr_code");
				if("0000".equals(retcode)){
					orderApply.setTitle("0000");
					orderApply.setpTradeNo(out_trade_no);
					orderApply.setImgUrl(img_url);
					orderApply.setCodeUrl(code_url);
				}else{
					orderApply.setTitle(retmsg);
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
			orderChange.setOperAction(GParameter.orderChange_apply);
			orderChange.setOperTime(DateUtil.getNow());
			orderChange.setOperCont("售货机:" + orderApply.getSiteName() + " 订单编号:" + orderApply.getOrderId() + " 申请成功!");
			orderChange.setCreateTime(DateUtil.getNow());
			orderChange.setPocState(GParameter.OrderChangeProc_waitPush);
			orderChange.setPocTimes(0);
			orderChange.setPocResult("wait push");
			orderChange.setProState(GParameter.OrderChangeProState_waitFinsh);
			this.orderChangeDao.insert(orderChange);
		}
		return orderDeliveryPayResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1,orderApply);
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



	public void setOrderDeliveryPayReq(OrderDeliveryPayReq orderDeliveryPayReq) {
		this.orderDeliveryPayReq = orderDeliveryPayReq;
	}

	public void setOrderDeliveryPayResp(OrderDeliveryPayResp orderDeliveryPayResp) {
		this.orderDeliveryPayResp = orderDeliveryPayResp;
	}

	public void setOrderBoxDao(OrderBoxDao orderBoxDao) {
		this.orderBoxDao = orderBoxDao;
	}

}
