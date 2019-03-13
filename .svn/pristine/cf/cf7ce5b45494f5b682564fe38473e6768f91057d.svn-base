package com.lmxf.post.tradepkg.common;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

abstract public class ClientRespHead {
	private String charSet="utf-8";
	protected  String retcode;
	protected String retmsg;
	protected int totnum;
	protected int curnum;
	protected String xml;
	protected Document document;
	
	protected  int parseHead(){
	  if(null==xml) return -1;
	  try {
	   document=DocumentHelper.parseText(xml); 
	   Element root = document.getRootElement();
	   Iterator iter = root.elementIterator("ZHEAD"); 
	   while (iter.hasNext()) {
		   Element recordE = (Element) iter.next();
		   retcode = recordE.elementTextTrim("retcode");
		   retmsg=recordE.elementTextTrim("retmsg");
		   totnum=Integer.parseInt(recordE.elementTextTrim("totnum"));
		   curnum=Integer.parseInt(recordE.elementTextTrim("curnum"));
	    }
	   } catch (DocumentException e) {
		   e.printStackTrace();
		  }
	  if(!retcode.equals("0000"))  return -1;
	  return 0;
	}
	abstract public int parseXml( );
	
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
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}

}
