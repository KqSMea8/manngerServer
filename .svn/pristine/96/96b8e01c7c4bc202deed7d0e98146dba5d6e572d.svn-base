package com.lmxf.post.tradepkg.common;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public abstract class ClientReqHead {
	
	private String charSet="utf-8";
	protected String bcode="";
	protected String tcode="";
	protected String auth_name;
	protected String auth_id;
	protected int istart=1;
	protected int iend=1;
	protected int iflag=1;
	abstract public  String CreateXml();
	protected  Document getDocument()
	{
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding(charSet);
		Element zmsg = document.addElement("ZMSG");
		Element zhead = zmsg.addElement("ZHEAD");
		Element bcodeE=zhead.addElement("bcode");
		bcodeE.setText(bcode);
		Element tcodeE=zhead.addElement("tcode");
		tcodeE.setText(tcode);
		Element istartE=zhead.addElement("istart");
		istartE.setText(""+istart);
		Element iendE=zhead.addElement("iend");
		iendE.setText(""+iend);
		Element iflagE=zhead.addElement("iflag");
		iflagE.setText(""+iflag);
		return document;
	} 
		
	public String getCharSet() {
		return charSet;
	}
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
	public String getBcode() {
		return bcode;
	}
	public void setBcode(String bcode) {
		this.bcode = bcode;
	}
	public String getTcode() {
		return tcode;
	}
	public void setTcode(String tcode) {
		this.tcode = tcode;
	}
	public int getIstart() {
		return istart;
	}
	public void setIstart(int istart) {
		this.istart = istart;
	}
	public int getIend() {
		return iend;
	}
	public void setIend(int iend) {
		this.iend = iend;
	}
	public int getIflag() {
		return iflag;
	}
	public void setIflag(int iflag) {
		this.iflag = iflag;
	}
	public String getAuth_name() {
		return auth_name;
	}
	public void setAuth_name(String auth_name) {
		this.auth_name = auth_name;
	}
	public String getAuth_id() {
		return auth_id;
	}
	public void setAuth_id(String auth_id) {
		this.auth_id = auth_id;
	}
	
}
