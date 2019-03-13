package com.lmxf.post.core.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lmxf.post.core.utils.pay.CommonUtil;
import com.lmxf.post.core.utils.pay.MD5SignUtil;
import com.lmxf.post.core.utils.pay.PrintUtils;
import com.lmxf.post.core.utils.pay.SDKRuntimeException;
import com.lmxf.post.core.utils.pay.WxPayHelper;
import com.lmxf.post.entity.pay.WinXin;

public class MyXmlUtil {
	/**
	 * 切割字符串，获取数据
	 * @param xml
	 * @param tag
	 * @return
	 */
	public static String xmlToString(String xml,String tag){
		String string = xml.substring(xml.indexOf(tag)+tag.length(), xml.indexOf(tag.replace("<", "</")));
		if(string.contains("<![CDATA[") && string.contains("]]>")){
			string = string.substring(string.indexOf("<![CDATA[")+"<![CDATA[".length(), string.indexOf("]]>"));
		}
		return string;
	}
	
	/**
	 * 生成javabean
	 * 
	 */
	
	public static WinXin getWinXin(String xmlString){
		WinXin winXin=new WinXin();
		if(xmlString.contains("<OpenId>") && xmlString.contains("</OpenId>")){
			winXin.setOpenId(MyXmlUtil.xmlToString(xmlString, "<OpenId>"));
		}		
		if(xmlString.contains("<AppId>") && xmlString.contains("</AppId>")){
			winXin.setAppId(MyXmlUtil.xmlToString(xmlString, "<AppId>"));
		}		
		if(xmlString.contains("<IsSubscribe>") && xmlString.contains("</IsSubscribe>")){
			winXin.setIsSubscribe( MyXmlUtil.xmlToString(xmlString, "<IsSubscribe>"));
		}
		if(xmlString.contains("<ProductId>") && xmlString.contains("<ProductId>")){
			winXin.setProductId(MyXmlUtil.xmlToString(xmlString, "<ProductId>"));
		}
		if(xmlString.contains("<TimeStamp>") && xmlString.contains("<TimeStamp>")){
			winXin.setTimeStamp(MyXmlUtil.xmlToString(xmlString, "<TimeStamp>"));
		}
		if(xmlString.contains("<NonceStr>") && xmlString.contains("</NonceStr>")){
			winXin.setNonceStr(MyXmlUtil.xmlToString(xmlString, "<NonceStr>"));
		}
		if(xmlString.contains("<AppSignature>") && xmlString.contains("</AppSignature>")){
			winXin.setAppSignature(MyXmlUtil.xmlToString(xmlString, "<AppSignature>"));
		}
		if(xmlString.contains("<SignMethod>") && xmlString.contains("</SignMethod>")){
			winXin.setSignMethod(MyXmlUtil.xmlToString(xmlString, "<SignMethod>"));
		}
		return winXin;
	}
	
	
	/**
	 * 获取签名进行比对，返回布尔值
	 * @param wxPayHelper
	 * @param winXin
	 * @return
	 */
	public static boolean judgeAppSignature(WxPayHelper wxPayHelper,WinXin winXin){
		boolean tag = true;
		//获取签名
		try {
			HashMap<String, String> nativeObj = new HashMap<String, String>();
			nativeObj.put("appid", winXin.getAppId());
			if(null != winXin.getProductId()){
				nativeObj.put("productid", winXin.getProductId());
			}
			nativeObj.put("timestamp", winXin.getTimeStamp());
			nativeObj.put("noncestr", winXin.getNonceStr());
			nativeObj.put("openid", winXin.getOpenId());
			nativeObj.put("issubscribe", winXin.getIsSubscribe());
			String appSignature = wxPayHelper.GetBizSign(nativeObj);
			if(!appSignature.equals(winXin.getAppSignature())){
				PrintUtils.OUT("签名错误~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				PrintUtils.OUT("winxinAppSignature~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+winXin.getAppSignature());
				PrintUtils.OUT("myAppSignature~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+appSignature);
				tag = false;
			}
		} catch (SDKRuntimeException e) {
			tag = false;
			e.printStackTrace();
		}
		return tag;
	}
	
	
	/**
	 * 根据参数输出sign值，进行比对
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean signComparison(Map map, String partnerKey,String sign){
		boolean tag = true;
		try {
			Set keySet = map.entrySet();
			Iterator<Map.Entry<String,String[]>> iterator = keySet.iterator();
			HashMap<String, String> parameters = new HashMap<String,String>();
			while(iterator.hasNext()){
				Map.Entry<String, String[]> entry = (Entry<String, String[]>) iterator.next();
				if("sign".equals(entry.getKey())){
					iterator.remove();
				}else{
					parameters.put(entry.getKey(), entry.getValue()[0]);
				}
			}
			
			PrintUtils.OUT("parameters==============================>"+parameters);
			
			//按照ASCII码进行排序，然后拼接成字符串
			String unSignParaString = CommonUtil.FormatBizQueryParaMap(parameters,false);
			
			PrintUtils.OUT("unSignParaString===================================>"+unSignParaString);
			//进行MD5加密
			String md5Sign = MD5SignUtil.Sign(unSignParaString, partnerKey);
			PrintUtils.OUT("sign==================================?"+sign);
			PrintUtils.OUT("md5Sign==================================?"+md5Sign);
			if(!sign.equals(md5Sign)){
				tag = false;
			}
		} catch (SDKRuntimeException e) {
			e.printStackTrace();
			tag = false;
			throw new RuntimeException(e);
		}
		return tag;
	}
	
	
	public static String getReturnXml(HttpServletRequest request,HttpServletResponse resp){
		String xmlString ="";
		ServletInputStream in = null;
		try {
			in = request.getInputStream();
			byte[] byt = new byte[1024];
			//循环读取字节转化成字符串
			while((in.read(byt))>0){
				String string = new String(byt,"UTF-8");
				xmlString+=string;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return xmlString;
	}
	
	public static String getXml(HttpServletRequest request,HttpServletResponse resp){
		StringBuffer xml =new StringBuffer("");
		ServletInputStream in = null;
		try {
			in = request.getInputStream();
			byte[] byt = new byte[1024];
			//循环读取字节转化成字符串
			while((in.read(byt))>0){
				xml.append(new String(byt));
				//xml.append(new String(byt,"UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return xml.toString().trim();
	}
}
