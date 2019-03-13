package com.lmxf.post.service.dataSynch;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.vending.VendingPerson;
import com.lmxf.post.repository.vending.VendingPersonDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.dataSynch.EmpIssuedConfirmReq;
import com.lmxf.post.tradepkg.dataSynch.EmpIssuedConfirmResp;

public class TradeEmpIssuedConfirmService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeEmpIssuedConfirmService.class);
	private EmpIssuedConfirmReq empIssuedConfirmReq;
	private EmpIssuedConfirmResp empIssuedConfirmResp;
	private VendingPersonDao vendingPersonDao;
	
	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Map ret = null;
		VendingPerson vendingPerson = null;
		try {
			ret = empIssuedConfirmReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("empIssuedConfirmReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		
		vendingPerson = (VendingPerson) ret.get("vendingPerson");
		VendingPerson vendingPersonR = vendingPersonDao.findOne(vendingPerson);
		
		if(null == vendingPersonR){
			return errorCodeDao.getErrorRespJson(GConstent.Emp_Info_No_Found);
		}
		if(vendingPersonR.getDownState().equals(GParameter.pushState_pushSuccess)){
			return errorCodeDao.getErrorRespJson(GConstent.Repeat_Confirm_Error);
		}
		vendingPersonR.setDownState(GParameter.pushState_pushSuccess);
		vendingPersonR.setDownTime(DateUtil.getNow());
		int r = vendingPersonDao.updateDownTime(vendingPersonR);
		if(r < 0){
			return errorCodeDao.getErrorRespJson(GConstent.Confirm_Failed_Error);
		}
		return empIssuedConfirmResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public void setEmpIssuedConfirmReq(EmpIssuedConfirmReq empIssuedConfirmReq) {
		this.empIssuedConfirmReq = empIssuedConfirmReq;
	}

	public void setEmpIssuedConfirmResp(EmpIssuedConfirmResp empIssuedConfirmResp) {
		this.empIssuedConfirmResp = empIssuedConfirmResp;
	}

	public void setVendingPersonDao(VendingPersonDao vendingPersonDao) {
		this.vendingPersonDao = vendingPersonDao;
	}	
}
