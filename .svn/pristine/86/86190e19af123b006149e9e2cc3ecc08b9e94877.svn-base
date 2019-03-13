package com.lmxf.post.core.utils.pay;

import java.io.ByteArrayOutputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.lmxf.post.core.utils.DateUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.pay.Order;
import com.lmxf.post.entity.pay.WinXin;
import com.lmxf.post.tradepkg.common.RespHead;

public class WeixinPayApplyResp extends RespHead {
	String apply_info;
	String apply_time;
	private WinXin winxin;

	public String CreateXml() {
		String xml = null;
		try {
			Document document = this.getDocument();
			Element root = document.getRootElement();
			Element zbody = root.addElement(GConstent.ZxmlBody);
			Element e = zbody.addElement("apply_info");
			e.setText(this.apply_info);

			if (winxin != null) {
				String appId = winxin.getAppId();
				String timeStamp = winxin.getTimeStamp();
				String nonceStr = winxin.getNonceStr();
				String packageInfo = "<![CDATA[" + winxin.getPackageInfo() + "]]>";
				String signType = winxin.getSignMethod();
				String paySign = winxin.getPaySign();

				e = zbody.addElement("appId");
				e.setText(appId);
				e = zbody.addElement("timeStamp");
				e.setText(timeStamp);
				e = zbody.addElement("nonceStr");
				e.setText(nonceStr);
				e = zbody.addElement("package_str");
				e.setText(packageInfo);
				e = zbody.addElement("signType");
				e.setText(signType);
				e = zbody.addElement("paySign");
				e.setText(paySign);
			}

			if ("01".equals(this.apply_info)) {
				String qrcodeImg = winxin.getQrcodeImg();
				e = zbody.addElement("qrcodeImg");
				e.setText(qrcodeImg);
				e = zbody.addElement("apply_time");
				e.setText(this.apply_time);
				e = zbody.addElement("packageInfo");
				e.setText(winxin.getPackageInfo());
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			OutputFormat format = new OutputFormat("  ", true, this.getCharSet());
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			xml = out.toString(this.getCharSet());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return xml;
	}

	public String CreateJson() {
		if(winxin==null){
			return getJsonHead("xml parse error");
		}
		String appId = winxin.getAppId();
		String timeStamp = winxin.getTimeStamp();
		String nonceStr = winxin.getNonceStr();
		String packageInfo = "<![CDATA[" + winxin.getPackageInfo() + "]]>";
		String signType = winxin.getSignMethod();
		String paySign = winxin.getPaySign();
		String ZBODY=null;
		if ("01".equals(this.apply_info)) {
			String qrcodeImg = winxin.getQrcodeImg();
			ZBODY="{\"appId\":\""+appId+"\"" +
					",\"timeStamp\":\""+timeStamp+"\"" +
					",\"nonceStr\":\""+nonceStr+"\"" +
					",\"package_str\":\""+packageInfo+"\"" +
					",\"signType\":\""+signType+"\"" +
					",\"qrcodeImg\":\""+qrcodeImg+"\"" +
					",\"apply_time\":\""+this.apply_time+"\"" +
					",\"packageInfo\":\""+winxin.getPackageInfo()+"\"" +
					 ",\"paySign\":\""+paySign+"\"}";
		}else{
			ZBODY="{\"appId\":\""+appId+"\"" +
					",\"timeStamp\":\""+timeStamp+"\"" +
					",\"nonceStr\":\""+nonceStr+"\"" +
					",\"package_str\":\""+packageInfo+"\"" +
					",\"signType\":\""+signType+"\"" +
					 ",\"paySign\":\""+paySign+"\"}";
		}
		
		String xml=this.getJsonHead(ZBODY);
		return xml;
	}
	
	// 创建响应报文
	public static String WxPayXml(Order vo) {
	   vo.setNonce_str(Order.CreateNoncestr());//UUID.randomUUID().toString()
	   vo.setTime_end(String.valueOf(System.currentTimeMillis()/1000));
	   if("".equalsIgnoreCase(vo.getAuth_name())){
		   
	   }
		StringBuffer b = new StringBuffer();
        b.append("appId="+vo.getAppid());
        b.append("&nonceStr="+vo.getNonce_str());
        b.append("&package=prepay_id="+vo.getPrepay_id());
        b.append("&signType=MD5");
        b.append("&timeStamp="+vo.getTime_end());
        b.append("&key="+vo.getAppKey());      
        
        String sign=Order.MD5(b.toString());
        vo.setCode_url(sign);        
		b=new StringBuffer("<?xml version='1.0' encoding='utf-8'?>");
		b.append("<ZMSG><ZHEAD>");
		b.append("<retcode>0000</retcode><retmsg>success</retmsg>");
		b.append("<totnum>1</totnum><curnum>1</curnum>");
		b.append("</ZHEAD><ZBODY>");
		b.append("<appId>"+vo.getAppid()+"</appId>");
		b.append("<timeStamp>"+vo.getTime_end()+"</timeStamp>");
		b.append("<nonceStr>"+vo.getNonce_str()+"</nonceStr>");
		b.append("<pckage>prepay_id="+vo.getPrepay_id()+"</pckage>");
		b.append("<signType>MD5</signType>");
		b.append("<paySign>"+sign+"</paySign>");
		b.append("<out_trade_no>"+vo.getOut_trade_no()+"</out_trade_no>");
		b.append("<prepay_id>"+vo.getPrepay_id()+"</prepay_id>");
		b.append("<limit_time>"+vo.getLimit_time()+"</limit_time>");
		b.append("<pay_state>"+vo.getPay_state()+"</pay_state>");
		b.append("<pay_time>"+vo.getPay_time()+"</pay_time>");
		b.append("<order_id>"+vo.getOrder_id()+"</order_id>");
		b.append("<openid>"+vo.getOpenid()+"</openid>");
		b.append("<limit_pay>"+null==vo.getLimit_pay()?"":vo.getLimit_pay()+"</limit_pay>");		
		b.append("<resp_time>"+DateUtils.DateToString()+"</resp_time>");
		b.append("</ZBODY></ZMSG>");
		//TODO INIT ORDER
		StringBuffer c = new StringBuffer();
		c.append("<appId>"+vo.getAppid()+"</appId>");
		c.append("<timeStamp>"+vo.getTime_end()+"</timeStamp>");
		c.append("<nonceStr>"+vo.getNonce_str()+"</nonceStr>");
		vo.setPay_url(c.toString());
		return b.toString();
	}
	
	public WinXin getWinxin() {
		return winxin;
	}

	public void setWinxin(WinXin winxin) {
		this.winxin = winxin;
	}

	public String getApply_info() {
		return apply_info;
	}

	public void setApply_info(String apply_info) {
		this.apply_info = apply_info;
	}

	public String getApply_time() {
		return apply_time;
	}

	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}

	@Override
	public String CreateJson(Object... parm) {
		// TODO Auto-generated method stub
		return null;
	}
}
