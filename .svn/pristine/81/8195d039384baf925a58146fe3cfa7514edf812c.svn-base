package com.lmxf.post.tradepkg.common;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lmxf.post.core.utils.GConstent;
public abstract class HeadResp {
	
	private String charSet="utf-8";
	protected String retcode="";
	protected String retmsg="";
	protected  int totnum=1;
	protected  int curnum=1;
	 
	abstract public  String CreateXml(Object o);
	protected  Document getDocument()
	{
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding(charSet);
		Element zmsg = document.addElement(GConstent.ZxmlRoot);
		Element zhead = zmsg.addElement(GConstent.ZxmlHead);
		Element retcodeE=zhead.addElement("RetCode");
		retcodeE.setText(retcode);
		Element retmsgE=zhead.addElement("retmsg");
		retmsgE.setText(retmsg);
		Element totnumE=zhead.addElement("totnum");
		totnumE.setText(""+totnum);
		Element curnumE=zhead.addElement("curnum");
		curnumE.setText(""+curnum);
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
}
