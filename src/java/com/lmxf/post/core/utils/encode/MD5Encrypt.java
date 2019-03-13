package com.lmxf.post.core.utils.encode;

import org.apache.shiro.crypto.hash.Md5Hash;

public class MD5Encrypt extends BaseEncrypt {

	
	public String doEncrypt(String plainText, String salt) {
		synchronized (plainText) {
			if(plainText==null)
				return "";
//			Md5Hash md5 = new Md5Hash(plainText, salt,hashIterations);
			Md5Hash md5 = new Md5Hash(plainText);
			return md5.toHex();
		}
	}
	public static void main(String[] args) {
	}
}
