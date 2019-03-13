package com.lmxf.post.core.utils.encode;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Arrays;

public abstract class BaseEncrypt implements Encrypt {

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	protected static final int SALT_BYTE_SIZE = 16;

	protected static final int hashIterations = 1024;

	public static String getSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);
		return toHex(salt);
	}
	

	public static Boolean slowEquals(String tokenCredentials,
			String accountCredentials) {
		if(tokenCredentials==null||accountCredentials==null)
			return false;
		byte[] a = tokenCredentials.getBytes(), b = accountCredentials.getBytes();
		return Arrays.equals(a, b);
	}

	protected static String toHex(byte[] array) {
		char[] encodedChars = encode(array);
		return new String(encodedChars);
	}

	private static char[] encode(byte[] data) {
		int l = data.length;
		char[] out = new char[l << 1];
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}
		return out;
	}

	public static void main(String[] args) {
		
	}
	
	protected static String buildpwd(String keyStr) {
		byte[] key = new byte[64];
		byte[] temp = null;
		try {
			temp = keyStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (key.length > temp.length) {
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return new String(key);
	}
	
	protected static String exchange(char[] random,char[] pwd){
		StringBuffer buffer = new StringBuffer();
		int pwdLen = pwd.length;
		int randomLen = random.length;
		int i = 0;
		while (i < pwdLen) {
			if (i > pwdLen)
				break;
			buffer.append(pwd[i]);
			if (i < randomLen)
				buffer.append(random[i]);
			i++;
		}
		int flag = randomLen - pwdLen;
		while (flag-- > 0) {
			buffer.append(random[i]);
			i++;
		}

		return buffer.toString();
	}
}
