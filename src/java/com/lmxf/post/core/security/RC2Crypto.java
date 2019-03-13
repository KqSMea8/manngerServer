package com.lmxf.post.core.security;

public class RC2Crypto extends BaseCrypto {
	static	String	alg="RC2";
	static	String	param="/CBC/PKCS5Padding";	
	private static String	DEF_key ="1a281FA32fC43e25035b44fbab85dbcdd5b3de431c63cd591a0e59cdab30b069a3ca6834fbab85db236aec12a3f3e3a2422a3f99212a2eef23eaef9e221a21efa3ca3f2e313a22eae3c2632ee19f12ca93";
	private	static String	DEF_salt="1FA32fC43e25035b";
	public RC2Crypto(){
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
			RC2Crypto enc=new RC2Crypto();
			String e1=enc.encrypt("Hello world");
			String d1=enc.decrypt(e1);
			System.out.println(e1);
			System.out.println(d1);			
			String e2=enc.encrypt("Hello world","sdsd");
			String d2=enc.decrypt(e2,"sdsd");
			System.out.println(e2);
			System.out.println(d2);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
