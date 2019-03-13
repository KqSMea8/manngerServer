package com.lmxf.post.service.partner;

import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.putils.utils.DateUtil;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.HttpUtils;
import com.lmxf.post.entity.parameter.NsParameter;
import com.lmxf.post.entity.vending.Vending;
import com.lmxf.post.entity.vending.VendingCmd;
import com.lmxf.post.repository.parameter.NsParameterDao;
import com.lmxf.post.repository.parameter.SequenceIdDao;
import com.lmxf.post.repository.vending.VendingCmdDao;
import com.lmxf.post.repository.vending.VendingDao;
import com.lmxf.post.service.core.TradeProcess;
import com.lmxf.post.tradepkg.partner.BoxOpenReq;
import com.lmxf.post.tradepkg.partner.BoxOpenResp;

public class TradeBoxOpenService extends TradeProcess {
	private final static Log log = LogFactory.getLog(TradeBoxOpenService.class);
	private BoxOpenReq boxOpenReq;
	private BoxOpenResp boxOpenResp;
	private NsParameterDao nsParameterDao;
	private VendingCmdDao vendingCmdDao;
	private VendingDao vendingDao;
	private SequenceIdDao sequenceIdDao;
	
	@SuppressWarnings("rawtypes")
	public String tradeProcess(String apply_xml) {
		Map ret = boxOpenReq.parseXml(apply_xml);
		if (ret.get("code") == null) {
			return errorCodeDao.getErrorRespJson(GConstent.Xml_Parse_Error);
		} else if (ret.get("code") != null && Integer.parseInt(ret.get("code").toString()) != 0) {
			log.error("boxOpenReq.parseXml is error!");
			return errorCodeDao.getErrorRespJson(Integer.parseInt(ret.get("code").toString()));
		}
		String siteId = (String) ret.get("siteId");
		String laneSId = (String) ret.get("laneSId");
		String orderId = (String) ret.get("orderId");
		NsParameter nsParameterP = new NsParameter();
		nsParameterP.setName("msgServer_url");
		NsParameter nsParameter = this.nsParameterDao.findOne(nsParameterP);
		Map<String,Object> result = null;
		String jsonR=null;
		int response=-1;
		if (nsParameter != null && nsParameter.getValue()!=null && !"".equals(nsParameter.getValue())) {
			Vending vending=vendingDao.findBySiteId(siteId);
			VendingCmd vendingCmd=new VendingCmd();
			vendingCmd.setCmd(GParameter.vendingdCmd_boxOpen);
			vendingCmd.setCmdCode(siteId);
			vendingCmd.setCmdLazy(GParameter.vendingCmdLazy_no);
			vendingCmd.setCmdType(GParameter.vendingdCmdType_box);
			JSONObject JSONboxR = new JSONObject();
			JSONboxR.put("OrderId", orderId);
			JSONboxR.put("BoxId", laneSId);
			vendingCmd.setCont(JSONboxR.toString());
			vendingCmd.setCorpId(vending.getCorpId());
			vendingCmd.setCreateTime(DateUtil.getNow());
			vendingCmd.setLogid(UUID.randomUUID().toString());
			vendingCmd.setSiteId(siteId);
			vendingCmd.setState(GParameter.vendingCmd_executing);
			vendingCmd.setStateTime(DateUtil.getNow());
			VendingCmd vendingCmdR=this.vendingCmdDao.findOneByObject(vendingCmd);
			//命令在执行则只推送
			if(vendingCmdR!=null){
				//如果前一个命令已完成 则可在生成命令
				if(GParameter.vendingCmd_failed.equals(vendingCmdR.getState())){
					vendingCmd.setCmdId(this.sequenceIdDao.findNextVal(vending.getCorpId(), "as_vending_cmd", 7));
			    	this.vendingCmdDao.insert(vendingCmd);
				}else{
					vendingCmd=vendingCmdR;
				}
			}else{
		    	vendingCmd.setCmdId(this.sequenceIdDao.findNextVal(vending.getCorpId(), "as_vending_cmd", 7));
		    	this.vendingCmdDao.insert(vendingCmd);
		    }
			JSONObject JSONbox = new JSONObject();
			JSONObject zmsgJson = new JSONObject();
			JSONObject zheadJson = new JSONObject();
			zheadJson.put("BCode", "03");
			zheadJson.put("TCode", "1227");
			zheadJson.put("Version", "01");
			zheadJson.put("IStart", "1");
			JSONObject zbodyJson = new JSONObject();
			zbodyJson.put("SiteId", vendingCmd.getSiteId());
			zbodyJson.put("CmdId", vendingCmd.getCmdId());
			zbodyJson.put("CmdCode", vendingCmd.getCmdCode());
			zbodyJson.put("CmdType", vendingCmd.getCmdType());
			zbodyJson.put("Cmd", vendingCmd.getCmd());
			zbodyJson.put("Cont", vendingCmd.getCont());
			zbodyJson.put("CreateTime", vendingCmd.getCreateTime());
			zmsgJson.put("ZHEAD", zheadJson);
			zmsgJson.put("ZBODY", zbodyJson);
			JSONbox.put("ZMSG", zmsgJson);
			String content = JSONbox.toString();
			
			//拼接请求参数
			String query_string = "content=" +  content + "&deviceId=" +  siteId; 
			String urlStr = nsParameter.getValue() + "?" + query_string; 
			log.error("请求地址:" + urlStr);
			//发送请求
			result = HttpUtils.sendPost(null, urlStr);
			log.error("消息服务器响应报文:" + result);
			response=(Integer) result.get("code");
			if(GParameter.MsgServer_Request_Success != response){
				return errorCodeDao.getErrorRespJson(GConstent.Response_MsgServer_URL_Error);
			}
			jsonR=(String) result.get("cont");
			try {
				JSONObject jsonObjectR = JSONObject.fromObject(jsonR);
				JSONObject resultInfo = jsonObjectR.getJSONObject("Content");
				if(null == resultInfo || !"0000".equals(resultInfo.getString("Code"))){
					return errorCodeDao.getErrorRespJson(GConstent.Response_MsgServer_URL_Error);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("消息服务器响应错误:" + result, e);
				return errorCodeDao.getErrorRespJson(GConstent.Response_MsgServer_URL_Error);
			}
		} else {
			return errorCodeDao.getErrorRespJson(GConstent.Config_MsgServer_URL_Error);
		}
		return boxOpenResp.CreateJson(GConstent.SUCCESS_CODE, GConstent.SUCCESS_MSG, 1, 1);
	}

	public void setBoxOpenReq(BoxOpenReq boxOpenReq) {
		this.boxOpenReq = boxOpenReq;
	}

	public void setBoxOpenResp(BoxOpenResp boxOpenResp) {
		this.boxOpenResp = boxOpenResp;
	}

	public void setNsParameterDao(NsParameterDao nsParameterDao) {
		this.nsParameterDao = nsParameterDao;
	}

	public void setVendingCmdDao(VendingCmdDao vendingCmdDao) {
		this.vendingCmdDao = vendingCmdDao;
	}

	public void setVendingDao(VendingDao vendingDao) {
		this.vendingDao = vendingDao;
	}

	public void setSequenceIdDao(SequenceIdDao sequenceIdDao) {
		this.sequenceIdDao = sequenceIdDao;
	}

}
