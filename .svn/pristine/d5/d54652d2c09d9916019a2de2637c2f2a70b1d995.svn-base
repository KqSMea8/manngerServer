package com.lmxf.post.core.security;

public class DesCrypto  extends BaseCrypto{
	static	String	alg="DES";
	static	String	param="/CBC/PKCS5Padding";	
	private String	DEF_key ="ca6834fbab85dbcd";
	private	String	DEF_salt="532051f48e91081b";
	public DesCrypto(){
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
			DesCrypto enc=new DesCrypto();
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
