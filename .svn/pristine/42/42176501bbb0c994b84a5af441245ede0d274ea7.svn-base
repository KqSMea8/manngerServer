package com.lmxf.post.service.partner;

import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.MsgPushUtils;
import com.lmxf.post.entity.emp.Emp;
import com.lmxf.post.entity.emp.User;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingPerson;
import com.lmxf.post.repository.emp.EmpDao;
import com.lmxf.post.repository.emp.UserDao;
import com.lmxf.post.repository.parameter.SequenceIdDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.repository.vending.VendingPersonDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.dataSynch.EmpIssuedResp;
import com.lmxf.post.tradepkg.partner.VendingPersonReq;
import com.lmxf.post.tradepkg.partner.VendingPersonResp;

public class TradeVendingPersonService extends TradeProcess{

	private final static Log log = LogFactory.getLog(TradeVendingPersonService.class);
	private VendingPersonReq vendingPersonReq;
	private VendingPersonResp vendingPersonResp;
	private EmpIssuedResp empIssuedResp;
	private UserDao userDao;
	private VendingPersonDao vendingPersonDao;
	private SequenceIdDao sequenceIdDao;
	private VendingDao vendingDao;
	
	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		VendingPerson vendingPerson = null;
		Map ret = null;
		try {
			ret = vendingPersonReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("vendingPersonReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		}catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		vendingPerson = (VendingPerson) ret.get("vendingPerson");
		User empP = new User();
		empP.setLogin_name(vendingPerson.getLoginId()); 
		User empR = this.userDao.findOne(empP);
		if(empR == null){
			return errorCodeDao.getErrorRespJson(GConstent.Emp_Info_No_Found);
		}
		Vending vending = vendingDao.findBySiteId(vendingPerson.getSiteId());
		if(vending == null){
			return errorCodeDao.getErrorRespJson(GConstent.Site_No_Define);
		}
		VendingPerson vendingPersonR = vendingPersonDao.findOne(vendingPerson);
		int flag = 0;
		if(vendingPersonR == null){
			vendingPersonR = new VendingPerson();
			vendingPersonR.setLogid(DateUtil.getLogid());
			vendingPersonR.setConfigId(empR.getCorp_Id() + "-" + this.sequenceIdDao.findNextVal(empR.getCorp_Id(), "vending_person_id", 7));
			vendingPersonR.setSiteId(vendingPerson.getSiteId());
			vendingPersonR.setSiteName(vending.getSiteName());
			vendingPersonR.setLoginId(vendingPerson.getLoginId());
			vendingPersonR.setLoginName(empR.getUser_name());
			vendingPersonR.setMobile(empR.getPhonenumber());
			vendingPersonR.setEmail(empR.getEmail());
			vendingPersonR.setEmpType(empR.getRole_key());
			vendingPersonR.setCurState(vendingPerson.getCurState());
			vendingPersonR.setDownState(GParameter.pushState_unpush);
			vendingPersonR.setDownTime(DateUtil.getNow());
			vendingPersonR.setCreateTime(DateUtil.getNow());
			vendingPersonR.setCorpId(empR.getCorp_Id());
			vendingPersonR.setCorpName(empR.getCorp_Id());
			vendingPersonDao.insert(vendingPersonR);
			flag = 1;
		}else if(!vendingPerson.getCurState().equals(vendingPersonR.getCurState())){
			vendingPersonR.setCurState(vendingPerson.getCurState());;
			vendingPersonR.setDownState(GParameter.pushState_unpush);
			vendingPersonR.setDownTime(DateUtil.getNow());
			vendingPersonDao.update(vendingPersonR);
			flag = 1;
		}
		if(flag == 1){
			//人员推送
			vendingPersonR.setUser(empR);
			String content = empIssuedResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1, vendingPersonR);
			String deviceId = vendingPersonR.getSiteId();
			String msgId = vendingPersonR.getLogid();
			int result = MsgPushUtils.push(content, deviceId, msgId);
			if(result == 0){
				vendingPersonR.setDownState(GParameter.pushState_pushed);
				vendingPersonR.setDownTime(DateUtil.getNow());
				this.vendingPersonDao.update(vendingPersonR);
			}
		}
		return vendingPersonResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
		
	}

	public void setVendingPersonReq(VendingPersonReq vendingPersonReq) {
		this.vendingPersonReq = vendingPersonReq;
	}

	public void setVendingPersonResp(VendingPersonResp vendingPersonResp) {
		this.vendingPersonResp = vendingPersonResp;
	}

	public void setEmpIssuedResp(EmpIssuedResp empIssuedResp) {
		this.empIssuedResp = empIssuedResp;
	}



	public void setVendingPersonDao(VendingPersonDao vendingPersonDao) {
		this.vendingPersonDao = vendingPersonDao;
	}

	public void setSequenceIdDao(SequenceIdDao sequenceIdDao) {
		this.sequenceIdDao = sequenceIdDao;
	}

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}
