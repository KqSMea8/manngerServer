package com.lmxf.post.service.partner;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.MsgPushUtils;
import com.lmxf.post.entity.emp.Emp;
import com.lmxf.post.entity.emp.User;
import com.lmxf.post.entity.vending.VendingPerson;
import com.lmxf.post.repository.emp.EmpDao;
import com.lmxf.post.repository.emp.UserDao;
import com.lmxf.post.repository.vending.VendingPersonDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.dataSynch.EmpIssuedResp;
import com.lmxf.post.tradepkg.partner.EmpDeleteReq;
import com.lmxf.post.tradepkg.partner.EmpDeleteResp;

public class TradeEmpDeleteService extends TradeProcess {

	private final static Log log = LogFactory.getLog(TradeEmpDeleteService.class);
	private EmpDeleteReq empDeleteReq;
	private EmpDeleteResp empDeleteResp;
	private EmpIssuedResp empIssuedResp;
	private UserDao userDao;
	private VendingPersonDao vendingPersonDao;
	
	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		User user = null;
		Map ret = null;
		try {
			ret = empDeleteReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("empDeleteReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
			user=(User) ret.get("emp");
		}catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		User empP = new User();
		empP.setLogin_name(user.getLogin_name()); 
		User empR = this.userDao.findOne(empP);
		if(empR == null){
			return errorCodeDao.getErrorRespJson(GConstent.Emp_Info_No_Found);
		}
		VendingPerson vendingPersonP = new VendingPerson();
		vendingPersonP.setLoginId(user.getLogin_name());
		List<VendingPerson> vendingPersonList = vendingPersonDao.findAll(vendingPersonP);
		if(null != vendingPersonList && vendingPersonList.size() > 0){
			for (VendingPerson vendingPerson : vendingPersonList) {
				vendingPerson.setCurState(GParameter.userState_invalid);;
				vendingPerson.setDownState(GParameter.pushState_unpush);
				vendingPerson.setDownTime(DateUtil.getNow());
				this.vendingPersonDao.update(vendingPerson);
				vendingPerson.setUser(empR);
				//人员推送
				String content = empIssuedResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1, vendingPerson);
				String deviceId = vendingPerson.getSiteId();
				String msgId = vendingPerson.getLogid();
				int result = MsgPushUtils.push(content, deviceId, msgId);
				if(result == 0){
					vendingPerson.setDownState(GParameter.pushState_pushed);
					vendingPerson.setDownTime(DateUtil.getNow());
					this.vendingPersonDao.update(vendingPerson);
				}
			}
		}
		return empDeleteResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public void setEmpDeleteReq(EmpDeleteReq empDeleteReq) {
		this.empDeleteReq = empDeleteReq;
	}

	public void setEmpDeleteResp(EmpDeleteResp empDeleteResp) {
		this.empDeleteResp = empDeleteResp;
	}



	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setEmpIssuedResp(EmpIssuedResp empIssuedResp) {
		this.empIssuedResp = empIssuedResp;
	}

	public void setVendingPersonDao(VendingPersonDao vendingPersonDao) {
		this.vendingPersonDao = vendingPersonDao;
	}
	
}
