package com.lmxf.post.core.utils.encode;

public class EncryptProvider {

	public static Encrypt getEncrypt(int type) {
		
		switch (type) {
		case 1:
			return new PlainEncrypt();
		case 2:
			return new DESEncrypt();
		case 3:
			return new AESEncrypt();
		case 4:
			return new SHA256Encrypt();
		default:
			return new MD5Encrypt();
		}
	}
	
	public static void main(String[] str){
		System.out.println(EncryptProvider.getEncrypt(4).doEncrypt("wusijie", "095a32e040c10d0c9c7bdf124c03f23f"));
	}
}
