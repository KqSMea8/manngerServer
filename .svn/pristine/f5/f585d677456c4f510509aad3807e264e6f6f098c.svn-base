package com.lmxf.post.tradepkg.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.MessageSourceHelper;

public abstract class ReqHead {
	private final static Log log = LogFactory.getLog(ReqHead.class);
	private String charSet = "utf-8";
	private String bcode = "";
	private String tcode = "";
	private int istart;
	private int iend;
	private int iflag;
	private int language;//语言标识,默认是0,0中文简体1英语2中文香港繁体3中文台湾4俄文5法文
	private String version = "00";//接口环境,默认是00;00国内版开发版，国内正式版01Neopost演示版
	private String xml;
	private Document document;
	private String msg;

	/**
	 *notnull不能为空,notequals不相等，exists已存在，notexists不存在，toolong长度越界,tooshort长度不足;
	 * 
	 * @author zhengzhiwu
	 * 
	 */
	public enum errorType {
		// 增加或插入，修改或编辑，查询，删除或指量删除，其它未知名的动作类型
		notNull, notEquals, exists, notExists, tooLong,tooShort,formatError;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
			this.msg = msg;
	}
	
	public void setMsg(int code,String msg) {
		if (language==0) {
			this.msg = msg;
		}
		this.msg =MessageSourceHelper.getMsgValue(code+"",msg, language);
	}
	
	public void setMsg(String code,String msg) {
		if (language==0) {
			this.msg = msg;
		}
		this.msg =MessageSourceHelper.getMsgValue(code,msg, language);
	}

	@SuppressWarnings("rawtypes")
	protected int parseHead() {
		if (null == xml)
			return -1;
		try {
			document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			Iterator iter = root.elementIterator(GConstent.ZxmlHead);
			while (iter.hasNext()) {
				Element e1 = (Element) iter.next();
				bcode = e1.elementTextTrim("bcode");
				tcode = e1.elementTextTrim("tcode");
				istart = toInt(e1, "istart");
				iend = toInt(e1, "iend");
				iflag = toInt(e1, "iflag");
				
			}
		} catch (DocumentException e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		   
		}
		return 0;
	}

	/**
	 * 校验报文中整数字段，是整数，则返回int的值，否则抛异常摄错
	 * 
	 * @param el
	 * @param name
	 * @return
	 */
	private int tInt(Element el, String name) {
		int i = 0;
		try {
			String nameValue=el.elementTextTrim(name);
			i = Integer.parseInt(nameValue);
		} catch (NumberFormatException e) {
			log.error(name + "解析字段错误");
			i=0;
		}
		return i;
	}
	
	/**
	 * 校验报文中整数字段，是整数，则返回int的值，否则抛异常摄错
	 * 
	 * @param el
	 * @param name
	 * @return
	 */
	public int toInt(Element el, String name) {
		int i = 0;
		try {
			i = Integer.parseInt(el.elementTextTrim(name));
		} catch (NumberFormatException e) {
			log.error(name + "解析字段错误");
			if (language==0) {
				throw new RuntimeException(name + "解析字段错误");
			} 
			throw new RuntimeException(name+":"+MessageSourceHelper.getMsgValue("parseToIntError","解析字段错误", language));
		}
		return i;
	}


	public String getCharSet() {
		return charSet;
	}

	/**
	 * 接口环境,默认是00;00国内版开发版，国内正式版01Neopost演示版
	 * @return
	 */
	public String getVersion() {
		if (validateEmpty(version)) {
			return "00";
		}
		return version;
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
	
	

	/**
	 * 语言标识,默认是0,0中文简体1英语2中文香港繁体3中文台湾4俄文5法文
	 * @return
	 */
	public int getLanguge() {
		return language;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public int loadFromFile(String filename) {
		try {
			File file = new File(filename);
			if (!file.exists() || file.isDirectory())
				throw new FileNotFoundException();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String temp = null;
			StringBuffer sb = new StringBuffer();
			temp = br.readLine();
			while (temp != null) {
				sb.append(temp + " ");
				temp = br.readLine();
			}
			xml = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 简化取值，并除前后空格
	 * 
	 * @param e
	 * @param name
	 * @return
	 */
	public String eValue(Element e, String name) {
		String a = null;
		try {
			a = e.elementTextTrim(name);
			if (!validateEmpty(a)) {
				return a.trim();
			}
			return "";
		} catch (Exception e1) {
			log.error("解析字段错误,没有字段" + name, e1);

		}
		return a;
	}

	/**
	 * 将带小数点的字符串，转换成整数
	 * 
	 * @param e
	 * @param name
	 * @return
	 */
	public String eInt(Element e, String name) {
		String a = null;
		try {
			a = e.elementTextTrim(name);
			if (null == a || "".equals(a)) {
				return "";
			}
			DecimalFormat myformat = new DecimalFormat("#0");
			a = Integer.valueOf(myformat.format(Double.parseDouble(a)))	.toString();
		} catch (Exception e1) {
			log.error("解析字段错误到整数值错误" + name, e1);			
			if (language==0) {
				throw new RuntimeException(name + "解析字段错误到整数值错误");
			} 
			throw new RuntimeException(name+":"+MessageSourceHelper.getMsgValue("parseToIntError","解析字段错误", language));
		}
		return a;
	}

	/**
	 * 校验null,"",及"null"
	 * 
	 * @param str
	 * @return
	 */
	public boolean validateEmpty(String str) {
		if (null == str || "".equals(str.trim())|| "null".equalsIgnoreCase(str.trim()))
			return true;
		else
			return false;
	}
	
	public static void main(String[] args) {
		System.out.println(errorType.notNull);
	}
}
