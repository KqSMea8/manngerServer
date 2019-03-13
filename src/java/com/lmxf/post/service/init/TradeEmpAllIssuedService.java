package com.lmxf.post.service.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.web.Page;

import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.entity.emp.Emp;
import com.lmxf.post.entity.emp.User;
import com.lmxf.post.entity.parameter.Dict;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingPerson;
import com.lmxf.post.repository.emp.EmpDao;
import com.lmxf.post.repository.emp.UserDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.repository.vending.VendingPersonDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.init.EmpAllIssuedReq;
import com.lmxf.post.tradepkg.init.EmpAllIssuedResp;

public class TradeEmpAllIssuedService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeEmpAllIssuedService.class);
	private EmpAllIssuedReq empAllIssuedReq;
	private EmpAllIssuedResp empAllIssuedResp;
	private UserDao userDao;
    private VendingDao vendingDao;
	
	public String tradeProcess(String apply_xml) {
		List<User> list = new ArrayList<User>();
		Map ret = null;
		try {
			ret = empAllIssuedReq.parseXml(apply_xml);
			if (ret.get("code") == null) {
				return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
			} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
				log.error("empAllIssuedReq.parseXml is error!");
				return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
			}
		} catch (Exception e) {
			log.error("---parseXml  error-----", e);
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		}
		
		String siteId = (String) ret.get("siteId");
		Vending vending=vendingDao.findBySiteId(siteId);
		if(vending==null){
			return errorCodeDao.getErrorRespJson(GConstent.Terminal_Site_No_Match);
		}
		Page page = new Page("IssuedVendingPerson");
		try {
			Dict dict=(Dict) CacheUtils.get(CacheUtils.errorCodeCache,"issuedPage_size");
			String pageNum = dict!=null?dict.getDictValue():null;
			page.setCurrentPage(Integer.parseInt((String) ret.get("istart")));
			page.setPageNum(20);
			if (pageNum != null && !"".equals(pageNum)) {
				try {
					page.setPageNum(Integer.parseInt(pageNum));
				} catch (Exception e) {
					return errorCodeDao.getErrorRespJson(GConstent.Program_App_Execp);
				}
			}
			User vendingPerson = new User();
			vendingPerson.setCorp_Id(vending.getCorpId());
			list = this.userDao.findAll(vendingPerson, page);
		} catch (Exception e) {
			log.error("---findvendingPersonIssued error---:" + e.getMessage());
			return errorCodeDao.getErrorRespJson(GConstent.Program_Query_Execp);
		}
		
		if (list != null && list.size() < 1) {
			log.error("---vendingPerson.findIssuedvendingPerson  nodata ---:");
			return errorCodeDao.getErrorRespJson(GConstent.Query_No_Data);
		}
		return empAllIssuedResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, page.getTotalCount(), list.size(), list);
	}

	public void setEmpAllIssuedReq(EmpAllIssuedReq empAllIssuedReq) {
		this.empAllIssuedReq = empAllIssuedReq;
	}

	public void setEmpAllIssuedResp(EmpAllIssuedResp empAllIssuedResp) {
		this.empAllIssuedResp = empAllIssuedResp;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
	}

	
}
