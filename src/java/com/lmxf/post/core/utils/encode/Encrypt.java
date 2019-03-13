package com.lmxf.post.core.utils.encode;

public interface Encrypt {
	
	/**
	 * 加密方式
	 * @param encodeType
	 * @return
	 */
	String doEncrypt(String plainText,String salt);
}
