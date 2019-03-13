package com.lmxf.post.core.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lmxf.post.core.security.AesCrypto;
import com.lmxf.post.core.security.TriDesCrypto;
import com.lmxf.post.core.utils.GParameter;
import com.lmxf.post.core.utils.VerifiCodeUtils;

public class PasswordGenerator {
	private final static Log log = LogFactory.getLog(PasswordGenerator.class);
	public static List<String> filterpw = new ArrayList<String>();
	static {
		filterpw.add("12345678");
		filterpw.add("87654321");
		filterpw.add("12590");
		filterpw.add("88448");
	}

	public String getPassword(int box_id, String passwd_type) {

		String pw = VerifiCodeUtils.getVerifiCode();
		boolean b = true;
		while (b) {
			boolean s = true;
			for (String str : filterpw) {
				if (pw.contains(str))
					s = false;
			}
			if (pw.length() != 6) {
				s = false;
			}
			if (s) {
				b = false;
			} else {
				pw = VerifiCodeUtils.getVerifiCode();
			}
		}
		return pw;
	}

	public static String getPassword() {

		String pw = VerifiCodeUtils.getVerifiCode();
		boolean b = true;
		while (b) {
			boolean s = true;
			for (String str : filterpw) {
				if (pw.contains(str))
					s = false;
			}
			if (pw.length() != 6) {
				s = false;
			}
			if (s) {
				b = false;
			} else {
				pw = VerifiCodeUtils.getVerifiCode();
			}
		}
		return pw;
	}

	public String getEncrypt(String encodeType, String password) {
		String e1 = "";
		if (encodeType.equals(GParameter.encodeType_none))
			return password;
		if (encodeType.equals(GParameter.encodeType_3Des)) {
			try {
				TriDesCrypto enc = new TriDesCrypto();
				e1 = enc.encrypt(password);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("TriDesCrypto enc.encrypt is error" + e.getMessage());
				return null;
			}
			return e1;
		}
		if (encodeType.equals(GParameter.encodeType_aes)) {
			try {
				AesCrypto enc = new AesCrypto();
				e1 = enc.encrypt(password);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("AesCrypto  enc.encrypt is error" + e.getMessage());
				return null;
			}
			return e1;
		}
		return null;
	}

	public static void main(String[] args) {
		String pw = VerifiCodeUtils.getVerifiCode();
		boolean b = true;
		while (b) {
			boolean s = true;
			for (String str : filterpw) {
				if (pw.contains(str))
					s = false;
			}
			if (pw.length() != 8) {
				s = false;
			}
			if (s) {
				b = false;
			} else {
				pw = VerifiCodeUtils.getVerifiCode();
			}
		}
		System.out.println(pw);
	}

}
