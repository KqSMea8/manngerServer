package com.lmxf.post.core.security;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class BaseCrypto implements ICrypto {
	private String	key;
	private	String	salt;

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	protected	abstract	String getAlg();
	protected	abstract	String getMode();
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	public String encrypt(String plain) throws Exception {
		Cipher cipher = Cipher.getInstance(getAlg()+getMode());
		SecretKeySpec skeySpec=new  SecretKeySpec(HexUtil.hex2bin(this.getKey()), getAlg());
		IvParameterSpec iv = new javax.crypto.spec.IvParameterSpec(HexUtil.hex2bin(this.getSalt()));
		SecureRandom sr=new SecureRandom();
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec,iv);
		byte[] encrypted = cipher.doFinal(plain.getBytes());
		return Base64.encodeBytes(encrypted);
	}
	

	protected byte[] genkey(String passwd){
		byte[] key1=HexUtil.hex2bin(this.getKey());
		if(passwd==null||passwd.length()<1)
			passwd="pansky";
		byte[]	s=passwd.getBytes();
		int	len=s.length;
		for(int i=0;i<key1.length;i++){
			key1[i]^=s[i%len];
		}
		
		return key1;
	}
	
	@SuppressWarnings("unused")
	public String encrypt(String plain,String passwd) throws Exception {
		Cipher cipher = Cipher.getInstance(getAlg()+getMode());
		byte[]	key1=genkey(passwd);
		SecretKeySpec skeySpec=new  SecretKeySpec(key1, getAlg());
		IvParameterSpec iv = new javax.crypto.spec.IvParameterSpec(HexUtil.hex2bin(this.getSalt()));
		SecureRandom sr=new SecureRandom();
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec,iv);
		byte[] encrypted = cipher.doFinal(plain.getBytes());
		return Base64.encodeBytes(encrypted);
	}	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	public String decrypt(String encpypted)  throws Exception {
		Cipher cipher = Cipher.getInstance(getAlg()+getMode());
		SecretKeySpec skeySpec=new  SecretKeySpec(HexUtil.hex2bin(this.getKey()), getAlg());
		
		IvParameterSpec iv = new javax.crypto.spec.IvParameterSpec(HexUtil.hex2bin(this.getSalt()));
		SecureRandom sr=new SecureRandom();
		cipher.init(Cipher.DECRYPT_MODE, skeySpec,iv);
		byte[]	in=Base64.decode(encpypted);
		byte[] plaint = cipher.doFinal(in);			
		return new String(plaint);
	}
	
	@SuppressWarnings("unused")
	public String decrypt(String encpypted,String passwd)  throws Exception {
		Cipher cipher = Cipher.getInstance(getAlg()+getMode());
		byte[]	key1=genkey(passwd);
		SecretKeySpec skeySpec=new  SecretKeySpec(key1, getAlg());
		IvParameterSpec iv = new javax.crypto.spec.IvParameterSpec(HexUtil.hex2bin(this.getSalt()));
		SecureRandom sr=new SecureRandom();
		cipher.init(Cipher.DECRYPT_MODE, skeySpec,iv);
		byte[]	in=Base64.decode(encpypted);
		byte[] plaint = cipher.doFinal(in);			
		return new String(plaint);
	}	
	
}
