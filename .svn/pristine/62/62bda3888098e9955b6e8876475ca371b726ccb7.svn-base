package com.lmxf.post.service.partner;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.emp.Emp;
import com.lmxf.post.repository.emp.EmpDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.EmpInfoReq;
import com.lmxf.post.tradepkg.partner.EmpInfoResp;

public class TradeEmpInfoService extends TradeProcess{

	private final static Log log = LogFactory.getLog(TradeEmpInfoService.class);
	private EmpInfoReq empInfoReq;
	private EmpInfoResp empInfoResp;
	private EmpDao empDao;
	
	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Emp emp = null;
		Map ret = null;
		try {
			ret = empInfoReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("empInfoReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		emp = (Emp) ret.get("emp");
		Emp empR = empDao.findOne(emp);
		if(null == empR){
			return errorCodeDao.getErrorRespJson(GConstent.Emp_Info_No_Found);
		}
		// 濡傛灉鏈夊敮涓�爣璇嗙爜杩囨潵鍒欐洿鏂版爣璇嗙爜
		if (validateEmpty(empR.getAccessCode()) && !validateEmpty(emp.getAccessCode())) {
			empR.setAccessCode(emp.getAccessCode());
			empDao.updateAccessCode(empR);
		}
		return empInfoResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1,empR);
		
	}
	
	public void setEmpInfoReq(EmpInfoReq empInfoReq) {
		this.empInfoReq = empInfoReq;
	}

	public void setEmpInfoResp(EmpInfoResp empInfoResp) {
		this.empInfoResp = empInfoResp;
	}

	public void setEmpDao(EmpDao empDao) {
		this.empDao = empDao;
	}

}
