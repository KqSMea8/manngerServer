package com.lmxf.post.core.utils.encode;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.shiro.crypto.hash.Md5Hash;

public class AESEncrypt extends BaseEncrypt {

	private static final String KEY_ALGORITHM = "AES";

	
	public String doEncrypt(String plainText, String salt) {
		if(plainText==null)
			return "";
		byte[] keySpe = new Md5Hash(plainText,hashIterations).toHex().substring(0, 16).getBytes();
		//byte[] enc = exchange(new String(keySpe).toCharArray(), plainText.toCharArray()).getBytes();
		try {
			SecretKeySpec key = new SecretKeySpec(keySpe, KEY_ALGORITHM);
			Cipher c1 = Cipher.getInstance(KEY_ALGORITHM);
			c1.init(Cipher.ENCRYPT_MODE, key);
			return buildpwd(toHex(c1.doFinal(plainText.getBytes())));
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			return null;
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
			return null;
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
			return null;
		}
	}
public static void main(String[] args) {
	AESEncrypt AESEncrypt = new AESEncrypt();
	System.out.println(AESEncrypt.doEncrypt("aaa", null));
}	
}
