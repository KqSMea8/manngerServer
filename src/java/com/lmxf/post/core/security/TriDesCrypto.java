package com.lmxf.post.core.security;

public class TriDesCrypto extends BaseCrypto {
	static	String	alg="DESede";
	static	String	param="/CBC/PKCS5Padding";	
	private static String	DEF_key ="ca6834fbab85dbcdd5b3de431c63cd591a0e59cdab30b069";
	private	static String	DEF_salt="3F035ff48e91081b";
	public TriDesCrypto(){
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
			TriDesCrypto enc=new TriDesCrypto();
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
