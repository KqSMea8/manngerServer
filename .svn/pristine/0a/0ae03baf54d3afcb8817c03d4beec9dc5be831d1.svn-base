package com.lmxf.post.core.utils.pay;

import java.io.ByteArrayOutputStream;

import org.apache.commons.putils.utils.DateUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.lmxf.post.alipay.entity.Alipay;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.tradepkg.common.RespHead;

public class PayApplyResp extends RespHead {
	private Alipay alipay;
	public String CreateXml() {
		try {
			Document document = this.getDocument();
			Element root = document.getRootElement();
			Element zbody = root.addElement(GConstent.ZxmlBody);
			Element e = zbody.addElement("apply_info");
			e.setText("apply_info");
			Element e2 = zbody.addElement("apply_time");
			e2.setText(DateUtil.getNow());
			if (null!=alipay) {
				e = zbody.addElement("action");
				e.setText(alipay.getAction());
				e = zbody.addElement("method");
				e.setText(alipay.getMethod());
				e = zbody.addElement("target");
				e.setText(alipay.getTarget());
				e = zbody.addElement("body");
				e.setText(alipay.getBody());
				e = zbody.addElement("logistics_type");
				e.setText(alipay.getLogistics_type());
				e = zbody.addElement("logistics_fee");
				e.setText(alipay.getLogistics_fee());
				e = zbody.addElement("subject");
				e.setText(alipay.getSubject());
				e = zbody.addElement("sign_type");
				e.setText(alipay.getSign_type());
				e = zbody.addElement("receive_address");
				e.setText(alipay.getReceive_address());
				e = zbody.addElement("receive_phone");
				e.setText(alipay.getReceive_phone());
				e = zbody.addElement("receive_name");
				e.setText(alipay.getReceive_name());
				e = zbody.addElement("notify_url");
				e.setText(alipay.getNotify_url());
				e = zbody.addElement("out_trade_no");
				e.setText(alipay.getOut_trade_no());
				e = zbody.addElement("return_url");
				e.setText(alipay.getReturn_url());
				e = zbody.addElement("sign");
				e.setText(alipay.getSign());
				e = zbody.addElement("_input_charset");
				e.setText(alipay.get_input_charset());
				e = zbody.addElement("service");
				e.setText(alipay.getService());
				e = zbody.addElement("price");
				e.setText(alipay.getPrice());
				e = zbody.addElement("receive_mobile");
				e.setText(alipay.getReceive_mobile());
				e = zbody.addElement("quantity");
				e.setText(alipay.getQuantity());
				e = zbody.addElement("partner");
				e.setText(alipay.getPartner());
				e = zbody.addElement("seller_email");
				e.setText(alipay.getSeller_email());
				e = zbody.addElement("receive_zip");
				e.setText(alipay.getReceive_zip());
				e = zbody.addElement("logistics_payment");
				e.setText(alipay.getLogistics_payment());
				e = zbody.addElement("payment_type");
				e.setText(alipay.getPayment_type());
				e = zbody.addElement("show_url");
				e.setText(alipay.getShow_url());
				e = zbody.addElement("payType");
				e.setText(alipay.getPayType());
				e = zbody.addElement("total_fee");
				e.setText(alipay.getTotal_fee());
				e = zbody.addElement("qrcode");
				e.setText(alipay.getQrcode());
				if ("tenPay".equals(alipay.getPayType())) {
					String time_expire = alipay.getTime_expire();
					e = zbody.addElement("time_expire");
					e.setText(time_expire);
				}
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			OutputFormat format = new OutputFormat("  ", true, this.getCharSet());
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			return out.toString(this.getCharSet());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void setAlipay(Alipay alipay) {
		this.alipay = alipay;
	}

	public String CreateJson() {
		if (null==alipay) {
			return this.getJsonHead("找不到查询账户，请您先进行充值创建账户");
		}
		StringBuffer json=new StringBuffer();
		json.append("{\"apply_info\":\""+"apply_info"+"\",\"action\":\""+alipay.getAction()+"\"" +
				",\"method\":\""+alipay.getMethod()+"\",\"target\":\""+alipay.getTarget()+"\"" +
				",\"body\":\""+alipay.getBody()+"\",\"logistics_type\":\""+alipay.getLogistics_type()+"\"" +
				"\"logistics_fee\":\""+alipay.getLogistics_fee()+"\",\"subject\":\""+alipay.getSubject()+"\"" +
				"\"sign_type\":\""+alipay.getSign_type()+"\",\"receive_address\":\""+alipay.getReceive_address()+"\"" +
				"\"receive_phone\":\""+alipay.getReceive_phone()+"\",\"receive_name\":\""+alipay.getReceive_name()+"\"" +
				"\"notify_url\":\""+alipay.getNotify_url()+"\",\"out_trade_no\":\""+alipay.getOut_trade_no()+
				"\"return_url\":\""+alipay.getReturn_url()+"\",\"sign\":\""+alipay.getSign()+"\"" +
				"\"_input_charset\":\""+alipay.get_input_charset()+"\",\"service\":\""+alipay.getService()+"\"" +
				"\"price\":\""+alipay.get_input_charset()+"\",\"receive_mobile\":\""+alipay.getService()+"\"" +
				"\"quantity\":\""+alipay.get_input_charset()+"\",\"partner\":\""+alipay.getService()+"\"" +
				"\"seller_email\":\""+alipay.get_input_charset()+"\",\"receive_zip\":\""+alipay.getService()+"\"" +
				"\"logistics_payment\":\""+alipay.get_input_charset()+"\",\"payment_type\":\""+alipay.getService()+"\"" +
				"\"show_url\":\""+alipay.get_input_charset()+"\",\"payType\":\""+alipay.getService()+"\"" +
				"\"total_fee\":\""+alipay.get_input_charset()+"\",\"qrcode\":\""+alipay.getService()+"\"");
		if("tenPay".equals(alipay.getPayType())){
			json.append("\"time_expire\":\""+alipay.get_input_charset());
		}
		json.append("\"}");
		return  this.getJsonHead(json.toString());
	}

	@Override
	public String CreateJson(Object... parm) {
		// TODO Auto-generated method stub
		return null;
	}	


}
