package com.lmxf.post.core.utils.pay;

import java.io.ByteArrayOutputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.entity.pay.Order;
import com.lmxf.post.tradepkg.common.RespHead;

/**
 * 扫码请求支付响应报文封装
 * @author Administrator
 *
 */
public class QrcodePayApplyResp extends RespHead {
	private Order order;

	public String CreateXml() {
		String xml = null;
		Document document = null;
		Element zbody = null;
		Element e = null;
		ByteArrayOutputStream out = null;
		OutputFormat format = null;
		XMLWriter writer = null;
		try {
			document = this.getDocument();
			zbody = document.getRootElement().addElement(GConstent.ZxmlBody);
			if (order != null) {
				e = zbody.addElement("pay_url");
				e.setText(null==order.getPay_url()?"":order.getPay_url());
				e = zbody.addElement("qr_code");
				e.setText(order.getCode_url()+"");
				e = zbody.addElement("img_url");
				e.setText(null==order.getQrcodeimg_url()?"":order.getQrcodeimg_url());
				e = zbody.addElement("order_id");
				e.setText(order.getOrder_id()+"");
				e = zbody.addElement("out_trade_no");
				e.setText(order.getOut_trade_no()+"");				
				e = zbody.addElement("total_fee");
				e.setText(order.getTotal_fee()+"");
				e = zbody.addElement("pay_type");
				e.setText(order.getPay_type()+"");
				e = zbody.addElement("product_id");
				e.setText(order.getProduct_id()+"");
				
				e = zbody.addElement("box_id");
				e.setText(order.getProduct_typeid()+"");
				
				if(null!=order.getPrepay_id()){
					e = zbody.addElement("prepay_id");
					e.setText(order.getPrepay_id());
				}
				e = zbody.addElement("order_desc");
				e.setText(order.getOrder_desc()+"");
				
				e = zbody.addElement("limit_time");
				e.setText(order.getLimit_time()+"");
				e = zbody.addElement("pay_state");
				e.setText(order.getPay_state()+"");
				e = zbody.addElement("pay_time");
				e.setText(order.getPay_time()+"");
				e = zbody.addElement("resp_time");
				e.setText(order.getCreate_time()+"");
				
			}
			out = new ByteArrayOutputStream();
			format = new OutputFormat("  ", true, this.getCharSet());
			writer = new XMLWriter(out, format);
			writer.write(document);
			xml = out.toString(this.getCharSet());
		} catch (Exception ex) {
			ex.printStackTrace();
			xml = null;
		}
		out = null;
		writer = null;
		format = null;
		document = null;
		zbody = null;
		e = null;
		return xml;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}

	public String CreateJson() {		
		if(null==order){
			return this.getJsonHead("");
		}
		String zbody="{\"pay_url\":\""+order.getPay_url()+"\"" +
				",\"qr_code\":\""+order.getCode_url()+"\"" +
				",\"img_url\":\""+order.getQrcodeimg_url()+"\"" +
				",\"order_id\":\""+order.getOrder_id()+"\"" +
				",\"out_trade_no\":\""+order.getOut_trade_no()+"\"" +
				",\"total_fee\":\""+order.getTotal_fee()+"\"" +
				",\"pay_type\":\""+order.getPay_type()+"\"" +
				",\"product_id\":\""+order.getProduct_id()+"\"" +
				",\"box_id\":\""+order.getProduct_typeid()+"\"" +
				",\"prepay_id\":\""+order.getPrepay_id()+"\"" +
				",\"limit_time\":\""+order.getLimit_time()+"\"" +
				",\"pay_state\":\""+order.getPay_state()+"\"" +
				",\"pay_time\":\""+order.getPay_time()+"\"" +				
				 ",\"resp_time\":\""+order.getCreate_time()+"\"}";			
		return this.getJsonHead(zbody);
	}

	@Override
	public String CreateJson(Object... parm) {
		// TODO Auto-generated method stub
		return null;
	}
}
