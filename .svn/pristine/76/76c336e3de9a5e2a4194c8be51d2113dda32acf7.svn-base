package com.lmxf.post.security.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import com.lmxf.post.core.service.ReadFile;

public class DesPasswordEncoder extends BasePasswordEncoder {

	private static final int RANDOMSIZE = 8;

	private static char[] HEX_CRYPT_KEY = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static byte[] _STANDARD_ALPHABET = { (byte) 'A', (byte) 'B',
			(byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G',
			(byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L',
			(byte) 'M', (byte) 'N', (byte) 'O', (byte) 'P', (byte) 'Q',
			(byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U', (byte) 'V',
			(byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z', (byte) 'a',
			(byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f',
			(byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k',
			(byte) 'l', (byte) 'm', (byte) 'n', (byte) 'o', (byte) 'p',
			(byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u',
			(byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z',
			(byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4',
			(byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9',
			(byte) '+', (byte) '/' };

	private static byte[] _STANDARD_DECODABET = { -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61,
			-9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
			12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9,
			-9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
			40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,
			-9, -9, -9, -9, -9, -9, -9, -9, -9 };

	static private byte index_64[] = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, 0, 1, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, -1, -1, -1,
			-1, -1, -1, -1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
			17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, -1, -1, -1, -1, -1, -1,
			28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44,
			45, 46, 47, 48, 49, 50, 51, 52, 53, -1, -1, -1, -1, -1 };

	private final Cipher decryptor;

	private static final String CRYPT_ALGORITHM = "DESede/ECB/PKCS5Padding";

	private static final String ALGORITHM = "DESede";

	private static Object PASSWORD_CRYPT_KEY = "$#584ATe2#a199s*5A^&4*bng*3bPw%6";

	private boolean isDynamicKey = true;

	public DesPasswordEncoder() {
		decryptor = newCipher();
	}

	public String decrypt(String encrypted) {
		synchronized (decryptor) {
			initCipher(decryptor, Cipher.DECRYPT_MODE, newSecretKey());
			byte[] bytes = Base64.decode(reduction(encrypted, RANDOMSIZE)
					.toCharArray());
			byte[] decrypt = doFinal(decryptor, bytes);
			String pwd = demergePasswordAndSalt(new String(decrypt));
			return Utf8.decode(Base64.decode(pwd.toCharArray()));
		}
	}

	public static char[] getHEX_CRYPT_KEY() {
		return HEX_CRYPT_KEY;
	}

	public static void setHEX_CRYPT_KEY(char[] hEX_CRYPT_KEY) {
		HEX_CRYPT_KEY = hEX_CRYPT_KEY;
	}

	public static byte[] get_STANDARD_ALPHABET() {
		return _STANDARD_ALPHABET;
	}

	public static void set_STANDARD_ALPHABET(byte[] _STANDARD_ALPHABET) {
		DesPasswordEncoder._STANDARD_ALPHABET = _STANDARD_ALPHABET;
	}

	public static byte[] get_STANDARD_DECODABET() {
		return _STANDARD_DECODABET;
	}

	public static void set_STANDARD_DECODABET(byte[] _STANDARD_DECODABET) {
		DesPasswordEncoder._STANDARD_DECODABET = _STANDARD_DECODABET;
	}

	public static byte[] getIndex_64() {
		return index_64;
	}

	public static void setIndex_64(byte[] index_64) {
		DesPasswordEncoder.index_64 = index_64;
	}

	public static Object getPASSWORD_CRYPT_KEY() {
		return PASSWORD_CRYPT_KEY;
	}

	public static void setPASSWORD_CRYPT_KEY(Object pASSWORD_CRYPT_KEY) {
		PASSWORD_CRYPT_KEY = pASSWORD_CRYPT_KEY;
	}

	public void initCipher(Cipher cipher, int mode, SecretKey secretKey) {
		try {
			cipher.init(mode, secretKey);
		} catch (InvalidKeyException e) {
			throw new IllegalArgumentException("无法初始化，无效的密钥", e);
		}
	}

	public boolean isDynamicKey() {
		return isDynamicKey;
	}

	public void setDynamicKey(boolean isDynamicKey) {
		this.isDynamicKey = isDynamicKey;
	}

	public SecretKey newSecretKey() {
		Object o = PASSWORD_CRYPT_KEY;

		if (!isDynamicKey()) {
			String KEY = "Ma$#!15AbYRDv%$@(04=*12%=";
			return new SecretKeySpec(build3DesKey(Base64.decode(KEY
					.toCharArray())), ALGORITHM);
		}
		if (o == null) {
			String msg = "PASSWORD_CRYPT_KEY cannot be null.";
			throw new IllegalArgumentException(msg);
		}
		if (o instanceof byte[]) {
			return new SecretKeySpec((byte[]) o, ALGORITHM);
		} else if (o instanceof String) {
			return new SecretKeySpec(build3DesKey(Utf8.encode((String) o)),
					ALGORITHM);
		} else {
			ReadFile rf = (ReadFile) o;
			return new SecretKeySpec(rf.getBuf(), ALGORITHM);
		}
	}

	public Cipher newCipher() {
		try {
			return Cipher.getInstance(CRYPT_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("无效的加密算法", e);
		} catch (NoSuchPaddingException e) {
			throw new IllegalStateException("Should not happen", e);
		}
	}

	public static byte[] build3DesKey(byte[] temp) {
		byte[] key = new byte[24];
		if (key.length > temp.length) {
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}

	public byte[] doFinal(Cipher cipher, byte[] input) {
		try {
			return cipher.doFinal(input);
		} catch (IllegalBlockSizeException e) {
			throw new IllegalStateException("无法调用，密码分块不匹配", e);
		} catch (BadPaddingException e) {
			throw new IllegalStateException("无法调用密码，由于填充有误", e);
		}
	}

}
