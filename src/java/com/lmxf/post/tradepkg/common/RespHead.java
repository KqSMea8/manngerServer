package com.lmxf.post.tradepkg.common;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lmxf.post.core.utils.GConstent;

public abstract class RespHead {

	private String charSet = "utf-8";
	protected String retcode = "0000";
	protected String retmsg = "";
	protected int totnum = 1;
	protected int curnum = 1;
	private int language;

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

	protected Document getDocument() {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding(charSet);
		Element zmsg = document.addElement(GConstent.ZxmlRoot);
		Element zhead = zmsg.addElement(GConstent.ZxmlHead);
		Element retcodeE = zhead.addElement("retcode");
		retcodeE.setText(retcode);
		Element retmsgE = zhead.addElement("retmsg");
		retmsgE.setText(retmsg);
		Element totnumE = zhead.addElement("totnum");
		totnumE.setText("" + totnum);
		Element curnumE = zhead.addElement("curnum");
		curnumE.setText("" + curnum);
		return document;
	}

	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public String getRetcode() {
		return retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	public String getRetmsg() {
		return retmsg;
	}

	public void setRetmsg(String retmsg) {
		this.retmsg = retmsg;
	}

	public int getTotnum() {
		return totnum;
	}

	public void setTotnum(int totnum) {
		this.totnum = totnum;
	}

	public int getCurnum() {
		return curnum;
	}

	public void setCurnum(int curnum) {
		this.curnum = curnum;
	}

	/**
	 * 校验null,"",及"null"
	 * 
	 * @param str
	 * @return
	 */
	public boolean validateEmpty(String str) {
		if (null == str || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim()))
			return true;
		else
			return false;
	}
	
	abstract public String CreateJson(Object... parm);

	protected JSONObject getJSONObject(String retcode, String retmsg, int totnum, int curnum) {
		JSONObject rootObject = new JSONObject();

		JSONObject JSONObject = new JSONObject();

		JSONObject JSONHeaderObject = new JSONObject();
		JSONHeaderObject.put("RetCode", retcode);
		JSONHeaderObject.put("RetMsg", retmsg);
		JSONHeaderObject.put("TotNum", totnum);
//		JSONHeaderObject.put("CurNum", curnum);

		JSONObject.put(GConstent.ZxmlHead, JSONHeaderObject);
		
		rootObject.put(GConstent.ZxmlRoot, JSONObject);

		return rootObject;
	}
	
	protected JSONObject getIssueJSONObject(String BCode, String tCode, String version, String iStart) {
		JSONObject rootObject = new JSONObject();

		JSONObject JSONObject = new JSONObject();

		JSONObject JSONHeaderObject = new JSONObject();
		JSONHeaderObject.put("BCode", BCode);
		JSONHeaderObject.put("TCode", tCode);
		JSONHeaderObject.put("Version", version);
		JSONHeaderObject.put("IStart", iStart);

		JSONObject.put(GConstent.ZxmlHead, JSONHeaderObject);
		
		rootObject.put(GConstent.ZxmlRoot, JSONObject);

		return rootObject;
	}
	
	protected String getJsonHead(String ZBODY) {
		if(ZBODY==null||"".equals(ZBODY)){
			ZBODY="{}";
		}
		String	headJson="{\"ZMSG\":{\"ZHEAD\":{\"RetCode\":\""+retcode+"\",\"RetMsg\":\""+retmsg+"\",\"TotNum\":\""+totnum+"\",\"CurNum\":\""+curnum+"\"},\"ZBODY\":"+ZBODY+"}}";
		return headJson;
	}
}
