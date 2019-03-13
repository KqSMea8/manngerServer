package com.lmxf.post.core.utils.pay;

import java.io.ByteArrayOutputStream;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.lmxf.post.tradepkg.common.RespHead;

public class ErrorResp extends RespHead {

	public String CreateXml() {
		String xml=null;
		try {
			Document document = this.getDocument();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			OutputFormat format = new OutputFormat("  ", true, this.getCharSet());
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			xml = out.toString(this.getCharSet());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return xml;
	}

	@Override
	public String CreateJson(Object... parm) {
		// TODO Auto-generated method stub
		return null;
	}


}
