package com.lmxf.post.core.utils.encode;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.shiro.crypto.hash.Md5Hash;

public class DESEncrypt extends BaseEncrypt {
	private static final String Algorithm = "DESede";

	
	public String doEncrypt(String plainText, String salt) {
		if(plainText==null)
			return "";
		byte[] keySpe = new Md5Hash(plainText,hashIterations).toBase64().getBytes();
	//	byte[] enc = exchange(new String(keySpe).toCharArray(), plainText.toCharArray()).getBytes();
		try {
			SecretKey deskey = new SecretKeySpec(keySpe, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
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
		DESEncrypt DESEncrypt =new DESEncrypt();
		System.out.println(DESEncrypt.doEncrypt("1", "11"));
	}
}
