package com.lmxf.post.service.partner;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.emp.Emp;
import com.lmxf.post.repository.emp.EmpDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.EmpEditReq;
import com.lmxf.post.tradepkg.partner.EmpEditResp;

public class TradeEmpEditService extends TradeProcess{

	private final static Log log = LogFactory.getLog(TradeEmpEditService.class);
	private EmpEditReq empEditReq;
	private EmpEditResp empEditResp;
	private EmpDao empDao;
	
	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Emp emp = null;
		Map ret = null;
		try {
			ret = empEditReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("empEditReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		}catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		emp = (Emp) ret.get("emp");
		Emp empP = new Emp();
		empP.setLoginId(emp.getLoginId()); 
		Emp empR = this.empDao.findOne(empP);
		if(empR == null){
			return errorCodeDao.getErrorRespJson(GConstent.Emp_Info_No_Found);
		}
		empR.setMobile(emp.getMobile());
		empR.setLoginName(emp.getLoginName());
		empR.setPassWord(emp.getPassWord());
		empR.setEncodeType(emp.getEncodeType());
		empR.setSalt(emp.getSalt());
		int i = this.empDao.update(empR);
		return empEditResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public void setEmpEditReq(EmpEditReq empEditReq) {
		this.empEditReq = empEditReq;
	}
	
	public void setEmpEditResp(EmpEditResp empEditResp) {
		this.empEditResp = empEditResp;
	}

	public void setEmpDao(EmpDao empDao) {
		this.empDao = empDao;
	}
	



	
}
