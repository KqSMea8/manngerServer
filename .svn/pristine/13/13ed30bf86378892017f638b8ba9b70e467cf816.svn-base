package com.lmxf.post.entity.pay;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lmxf.post.core.utils.CheckUtils;

/**
 * 为性能日志和报文日志定义的中间转换信息  实体类
 * @author zzw
 *
 */
public class MsgLog implements Serializable{
	private static final long serialVersionUID = 7759403316414128938L;
	private String  logid;
	private String  auth_name;
	private String  auth_id;
	private String  plat_code;
	private String trade_code;
	private String trade_name;
	private String begin_time;
	private String ret_code;
	private float exec_time;
	private long start_time;
	private String reqXml;
	private String respXml;
	private String protocal;
	private String hostname;
	private String ip;
	private String path;//项目路径
    private String contextPath;//
	
	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	private String source;
	private String gateway_type;
	private String access_ip;// varchar(20) DEFAULT NULL COMMENT '访问ip',
	private String port;//访问的端口
	private String operType;//操作类型
	private String operCont;//操作内容
	private int ipflag;//记录ip访问的标示
	private String corp_id;
	private String corp_name;
	
	public String getCorp_id() {
		return corp_id;
	}
	public void setCorp_id(String corp_id) {
		this.corp_id = corp_id;
	}
	public String getCorp_name() {
		return corp_name;
	}
	public void setCorp_name(String corp_name) {
		this.corp_name = corp_name;
	}
	/**
	 * 记录ip访问的标示,值2和4时代表要记录，operType状态为值减二.
	 * @param ipflag
	 */
	public int getIpflag() {
		return ipflag;
	}
	/**
	 * 记录ip访问的标示 设置2和4时代表要记录
	 * @param ipflag
	 */
	public void setIpflag(int ipflag) {
		this.ipflag = ipflag;
	}
	public String getOperCont() {
		return operCont;
	}
	public void setOperCont(String operCont) {
		this.operCont = operCont;
	}
	private int hostInfo=0;	  
	public int getHostInfo() {
		return hostInfo;
	}
	public void setHostInfo(int hostInfo) {
		this.hostInfo = hostInfo;
	}
	  
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getAccess_ip() {
		return access_ip;
	}
	public void setAccess_ip(String access_ip) {
		this.access_ip = access_ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getGateway_type() {
		return gateway_type;
	}
	public void setGateway_type(String gateway_type) {
		this.gateway_type = gateway_type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getProtocal() {
		return protocal;
	}
	public String getHostname() {
		return hostname;
	}
	public String getIp() {
		return ip;
	}
	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAuth_id() {
		return auth_id;
	}
	public void setAuth_id(String auth_id) {
		this.auth_id = auth_id;
	}
	public String getRespXml() {
		return respXml;
	}
	public void setRespXml(String respXml) {		
		this.respXml =stripNonValidXMLCharacters(respXml);
	}
	
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public String getAuth_name() {
		return auth_name;
	}
	public void setAuth_name(String auth_name) {
		this.auth_name = auth_name;
	}
	public String getPlat_code() {
		return plat_code;
	}
	public void setPlat_code(String plat_code) {
		this.plat_code = plat_code;
	}
	public String getTrade_code() {
		return trade_code;
	}
	public void setTrade_code(String trade_code) {
		this.trade_code = trade_code;
	}
	public String getTrade_name() {
		return trade_name;
	}
	public void setTrade_name(String trade_name) {
		this.trade_name = trade_name;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getRet_code() {
		return ret_code;
	}
	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}
	public float getExec_time() {
		return exec_time;
	}
	public void setExec_time(float exec_time) {
		this.exec_time = exec_time;
	}
	public long getStart_time() {
		return start_time;
	}
	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}
	public String getReqXml() {
		return reqXml;
	}
	public void setReqXml(String reqXml) {
		this.reqXml =stripNonValidXMLCharacters(reqXml);
	}
	
	/**
	 * 过滤不可见字符
	 */
	public static String stripNonValidXMLCharacters(String input) {
		if (input == null || ("".equals(input)))
			return "";
		StringBuilder out = new StringBuilder();
		char current;
		for (int i = 0; i < input.length(); i++) {
			current = input.charAt(i);
			if ((current == 0x9) || (current == 0xA) || (current == 0xD)
					|| ((current >= 0x20) && (current <= 0xD7FF))
					|| ((current >= 0xE000) && (current <= 0xFFFD))
					|| ((current >= 0x10000) && (current <= 0x10FFFF)))
				out.append(current);
		}
		// Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Pattern p = Pattern.compile("\\t|\\r|\\n");
		Matcher m = p.matcher(out.toString());
		String desc = m.replaceAll("").trim();
		p = Pattern.compile("\\s+");
		m = p.matcher(desc);
		desc = m.replaceAll(" ").trim();
		return desc;
	}

	
	
}
