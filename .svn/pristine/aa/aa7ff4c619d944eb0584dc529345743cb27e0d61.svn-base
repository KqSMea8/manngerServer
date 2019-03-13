package com.lmxf.post.core.security.protocol;

import javax.crypto.*;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.putils.utils.Md5Util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class ThreeDesUtils {
	// / <summary>
	// / 3des解码
	// / </summary>
	// / <param name="value">待解密字符串</param>
	// / <param name="key">原始密钥字符串</param>
	// / <returns></returns>
	private static ThreeDesUtils threeDesUtils = null;

	public static ThreeDesUtils getInstance() {
		if (threeDesUtils == null)
			threeDesUtils = new ThreeDesUtils();
		return threeDesUtils;
	}

	public String Decrypt3DES(String value, String key) throws Exception {
		byte[] b = decryptMode(GetKeyBytes(key), Base64.decode(value));
		return new String(b);
	}

	public String Encrypt3DES(String value, String key) throws Exception {
		String str = byte2Base64(encryptMode(GetKeyBytes(key), value.getBytes()));
		return str;
	}

	// 计算24位长的密码byte值,首先对原始密钥做MD5算hash值，再用前8位数据对应补全后8位
	public byte[] GetKeyBytes(String strKey) throws Exception {
		if (null == strKey || strKey.length() < 1)
			throw new Exception("key is null or empty!");
		java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5");
		alg.update(strKey.getBytes());
		byte[] bkey = alg.digest();
		int start = bkey.length;
		byte[] bkey24 = new byte[24];
		for (int i = 0; i < start; i++) {
			bkey24[i] = bkey[i];
		}
		for (int i = start; i < 24; i++) {// 为了与.net16位key兼容
			bkey24[i] = bkey[i - start];
		}
		return bkey24;
	}

	private static final String Algorithm = "DESede"; // 定义 加密算法,可用

	// DES,DESede,Blowfish
	// keybyte为加密密钥，长度为24字节
	// src为被加密的数据缓冲区（源）
	public byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	public byte[] decryptMode(byte[] keybyte, byte[] src) {
		try { // 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 转换成base64编码
	public String byte2Base64(byte[] b) {
		return Base64.encode(b);
	}

	// 转换成十六进制字符串
	public String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();

	}

	public static void main(String[] args) throws Exception {
//		SecretKey deskey = new SecretKeySpec("523b1bd1f39a11e397a394de80cb8f2e".getBytes(), Algorithm); // 加密
//		Cipher c1 = Cipher.getInstance(Algorithm);
//		c1.init(Cipher.ENCRYPT_MODE, deskey);
//		for(byte s:c1.doFinal("123456".getBytes())){
//		System.out.print( s+" ");
//		}
		 System.out.println("字符:"+"  123456"+ "密匙:523b1bd1f39a11e397a394de80cb8f2e"+ "   加密："+ThreeDesUtils.getInstance().Encrypt3DES("123456","523b1bd1f39a11e397a394de80cb8f2e"));
		 System.out.println("徐大维   MD5值:"+Md5Util.MD5("徐大维").toUpperCase());
//		 
		SecurityKeyAlgo securityKeyAlgo = SecurityKeyAlgo.getInstance();
		System.out.println(securityKeyAlgo.getSecurityKey("523b1bd1f39a11e397a394de80cb8f2e"));
//		;

	}
}
