package com.lmxf.post.service.partner;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.config.Corp;
import com.lmxf.post.entity.emp.Emp;
import com.lmxf.post.repository.config.CorpDao;
import com.lmxf.post.repository.emp.EmpDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.EmpAddReq;
import com.lmxf.post.tradepkg.partner.EmpAddResp;

public class TradeEmpAddService extends TradeProcess{

	private final static Log log = LogFactory.getLog(TradeEmpAddService.class);
	private EmpAddReq empAddReq;
	private EmpAddResp empAddResp;
	private EmpDao empDao;
	private CorpDao corpDao;
	
	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Emp emp = null;
		Map ret = null;
		try {
			ret = empAddReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("empAddReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		
		}catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		emp = (Emp) ret.get("emp");
		Emp empR = new Emp();
		empR.setLoginId(emp.getLoginId()); 
		List<Emp> empList = empDao.findAll(empR);
		if(null != empList && empList.size() >0){
			return errorCodeDao.getErrorRespJson(GConstent.Emp_Info_Exsit);
		}
		emp.setLogid(DateUtil.getLogid());
		emp.setCreateTime(DateUtil.getNow());
		emp.setCurState(GParameter.userState_normal);
		Corp corp = corpDao.findOne(emp.getCorpId());
		if(null != corp){
			emp.setCorpId(corp.getCorpId());
			emp.setCorpName(corp.getCorpName());
		}
		this.empDao.insert(emp);
		return empAddResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}
	
	public void setEmpAddReq(EmpAddReq empAddReq) {
		this.empAddReq = empAddReq;
	}
	
	public void setEmpAddResp(EmpAddResp empAddResp) {
		this.empAddResp = empAddResp;
	}
	
	public void setEmpDao(EmpDao empDao) {
		this.empDao = empDao;
	}

	public void setCorpDao(CorpDao corpDao) {
		this.corpDao = corpDao;
	}
	
}
