package com.lmxf.post.service.partner;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.emp.User;
import com.lmxf.post.repository.emp.UserDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.UserInfoReq;
import com.lmxf.post.tradepkg.partner.UserInfoResp;

public class TradeUserInfoService extends TradeProcess{

	private final static Log log = LogFactory.getLog(TradeEmpInfoService.class);
	private UserInfoReq userInfoReq;
	private UserInfoResp userInfoResp;
	private UserDao userDao;
	
	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		User emp = null;
		Map ret = null;
		try {
			ret = userInfoReq.parseXml(apply_xml);
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
		emp = (User) ret.get("emp");
		User empR = userDao.findOne(emp);
		if(null == empR){
			return errorCodeDao.getErrorRespJson(GConstent.Emp_Info_No_Found);
		}
		if(GParameter.userState_invalid.equals(empR.getDel_flag())){
			return errorCodeDao.getErrorRespJson(GConstent.Emp_Info_No_Found);
		}
		return userInfoResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1,empR);
		
	}
	
	public void setUserInfoReq(UserInfoReq userInfoReq) {
		this.userInfoReq = userInfoReq;
	}

	public void setUserInfoResp(UserInfoResp userInfoResp) {
		this.userInfoResp = userInfoResp;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


}
