package com.lmxf.post.core.security;


public class AesCrypto extends BaseCrypto {
	static	String	alg="AES";
	static	String	param="/CBC/PKCS5Padding";	
	private static String	DEF_key="ca6834fbab85dbcdab30b06946743bdb84799409f3d5b3de431c63cd591a0e59";
	private static String	DEF_salt="958c6fb3c9976e18005ff48e91081b70";
	
	public	AesCrypto(){
		this.setKey(DEF_key);
		this.setSalt(DEF_salt);
	}
	
	protected	String getAlg(){
		return alg;
	}
	protected	String getMode(){
		return param;
	}
	
	

	public	static	void	main(String[] args){
		try{
			AesCrypto enc=new AesCrypto();
			String e1=enc.encrypt("Hello world");
			String d1=enc.decrypt(e1);
			System.out.println(e1);
			System.out.println(d1);			
			String e2=enc.encrypt("Hello world","sdsd");
			String d2=enc.decrypt(e2,"sdsd");
			System.out.println(e2);
			System.out.println(d2);
			/*
			AesCrypto enc=new AesCrypto();
			byte[] key=enc.genkey("168888");
			System.out.println(HexUtil.bin2hex(key));
			*/
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
