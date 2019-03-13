package com.lmxf.post.service.partner;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.exception.SystemException;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.order.OrderBox;
import com.lmxf.post.entity.vending.VendingLsdhandle;
import com.lmxf.post.entity.vending.VendingLsdiffer;
import com.lmxf.post.repository.order.OrderBoxDao;
import com.lmxf.post.repository.parameter.SequenceIdDao;
import com.lmxf.post.repository.vending.VendingCmdDao;
import com.lmxf.post.repository.vending.VendingLsdhandleDao;
import com.lmxf.post.repository.vending.VendingLsdifferDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.SitelsdhandleResp;
import com.lmxf.post.tradepkg.partner.SitelsdhandleReq;

/**
 * 订单出库状态矫正
 * 
 * @author Administrator
 * 
 */
public class TradeSitelsdhandleService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeOrderApplyService.class);
	private SitelsdhandleReq sitelsdhandleReq;
	private SitelsdhandleResp sitelsdhandleResp;
	private VendingLsdifferDao vendingLsdifferDao;
	private SequenceIdDao sequenceIdDao;
	private OrderBoxDao orderBoxDao;
	private VendingLsdhandleDao vendingLsdhandleDao;

	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) throws SystemException {
		List<VendingLsdhandle> vendingLsdifferList = null;
		Map ret = null;
		try {
			ret = sitelsdhandleReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("sitelsdhandleReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		vendingLsdifferList = (List<VendingLsdhandle>) ret.get("vendingLsdifferList");
		String orderId = (String) ret.get("orderId");
		String operId = (String) ret.get("operId");
		for (VendingLsdhandle vendingLsdhandle : vendingLsdifferList) {
			VendingLsdiffer vendingLsdifferP = new VendingLsdiffer();
			vendingLsdifferP.setLsdifferId(vendingLsdhandle.getLsdifferId());
			VendingLsdiffer vendingLsdiffer = vendingLsdifferDao.findOne(vendingLsdifferP);
			if (vendingLsdiffer == null) {
				return errorCodeDao.getErrorRespJson(GConstent.VendingLsdiffer_No_Found);
			}
			OrderBox orderBoxP = new OrderBox();
			orderBoxP.setProboxId(vendingLsdhandle.getProboxId());
			OrderBox orderBox = orderBoxDao.findOneByProBoxId(orderBoxP);
			if (orderBox == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Order_Box_No_Exsit);
			}
			// 处理订单库存信息
			if (!orderBox.getOutState().equals(vendingLsdhandle.getHandleType()) && GParameter.OrderBoxCorrectState_no.equals(orderBox.getCorrectState())) {
				orderBox.setCorrectState(GParameter.OrderBoxCorrectState_yes);
				orderBox.setOutState(vendingLsdhandle.getHandleType());
				// 记录库存差异处理信息
				vendingLsdhandle.setLsdhandleId(orderBox.getCorpId() + "-" + sequenceIdDao.findNextVal(orderBox.getCorpId(), "as_vending_lsdhandle", 7));
				vendingLsdhandle.setLogid(DateUtil.getLogid());
				vendingLsdhandle.setCorpId(orderBox.getCorpId());
				vendingLsdhandle.setOperId(operId);
				vendingLsdhandle.setCreateTime(DateUtil.getNow());
				vendingLsdhandle.setOperTime(DateUtil.getNow());
				vendingLsdhandle.setSiteId(orderBox.getSiteId());
				vendingLsdhandle.setLaneSId(orderBox.getLaneSId());
				vendingLsdhandle.setLaneEId(orderBox.getLaneEId());
				vendingLsdhandle.setProductId(orderBox.getProductId());
				vendingLsdiffer.setHandlerNum(vendingLsdiffer.getHandlerNum() + 1);
				if (vendingLsdiffer.getHandlerNum() == vendingLsdiffer.getDifferNum()) {
					vendingLsdiffer.setCurState(GParameter.VendingLsdifferCurState_alhandle);
				}
				this.vendingLsdhandleDao.insert(vendingLsdhandle);
				this.vendingLsdifferDao.update(vendingLsdiffer);
				this.orderBoxDao.update(orderBox);
			} else {
				return errorCodeDao.getErrorRespJson(GConstent.Order_No_Exsit);
			}
		}
		return sitelsdhandleResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public void setSitelsdhandleReq(SitelsdhandleReq sitelsdhandleReq) {
		this.sitelsdhandleReq = sitelsdhandleReq;
	}

	public void setSitelsdhandleResp(SitelsdhandleResp sitelsdhandleResp) {
		this.sitelsdhandleResp = sitelsdhandleResp;
	}

	public void setVendingLsdifferDao(VendingLsdifferDao vendingLsdifferDao) {
		this.vendingLsdifferDao = vendingLsdifferDao;
	}

	public void setSequenceIdDao(SequenceIdDao sequenceIdDao) {
		this.sequenceIdDao = sequenceIdDao;
	}

	public void setOrderBoxDao(OrderBoxDao orderBoxDao) {
		this.orderBoxDao = orderBoxDao;
	}

	public void setVendingLsdhandleDao(VendingLsdhandleDao vendingLsdhandleDao) {
		this.vendingLsdhandleDao = vendingLsdhandleDao;
	}

}
