package com.lmxf.post.core.utils.encode;

import org.apache.shiro.crypto.hash.Sha256Hash;

public class SHA256Encrypt extends BaseEncrypt {

	public String doEncrypt(String plainText, String salt) {
		synchronized (plainText) {
			if (plainText == null)
				return "";
			Sha256Hash sha256 = new Sha256Hash(plainText, salt, hashIterations);
			return sha256.toHex();
		}
	}
}
