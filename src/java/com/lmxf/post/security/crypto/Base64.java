package com.lmxf.post.security.crypto;

import java.util.Arrays;

public class Base64 {
	private static final char[] encode = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
			.toCharArray();
	private static final int[] decode = new int[128];
	private static final char pad = 0;

	static {
		Arrays.fill(decode, -1);
		for (int i = 0; i < encode.length; i++) {
			decode[encode[i]] = i;
		}
		decode[pad] = 0;
	}

	public static byte[] decode(char[] chars) {
		return decode(chars, decode, pad);
	}

	public static char[] encode(byte[] bytes) {
		return encode(bytes, encode, pad);
	}

	public static byte[] decode(char[] src, int[] table, char pad) {
		int len = src.length;

		if (len == 0)
			return new byte[0];

		int padCount = (src[len - 1] == pad ? (src[len - 2] == pad ? 2 : 1) : 0);
		int bytes = (len * 6 >> 3) - padCount;
		int blocks = (bytes / 3) * 3;

		byte[] dst = new byte[bytes];
		int si = 0, di = 0;

		while (di < blocks) {
			int n = table[src[si++]] << 18 | table[src[si++]] << 12
					| table[src[si++]] << 6 | table[src[si++]];
			dst[di++] = (byte) (n >> 16);
			dst[di++] = (byte) (n >> 8);
			dst[di++] = (byte) n;
		}

		if (di < bytes) {
			int n = 0;
			switch (len - si) {
			case 4:
				n |= table[src[si + 3]];
			case 3:
				n |= table[src[si + 2]] << 6;
			case 2:
				n |= table[src[si + 1]] << 12;
			case 1:
				n |= table[src[si]] << 18;
			}
			for (int r = 16; di < bytes; r -= 8) {
				dst[di++] = (byte) (n >> r);
			}
		}

		return dst;
	}

	public static char[] encode(byte[] src, char[] table, char pad) {
		int len = src.length;

		if (len == 0)
			return new char[0];

		int blocks = (len / 3) * 3;
		int chars = ((len - 1) / 3 + 1) << 2;
		int tail = len - blocks;
		if (pad == 0 && tail > 0)
			chars -= 3 - tail;

		char[] dst = new char[chars];
		int si = 0, di = 0;

		while (si < blocks) {
			int n = (src[si++] & 0xff) << 16 | (src[si++] & 0xff) << 8
					| (src[si++] & 0xff);
			dst[di++] = table[(n >>> 18) & 0x3f];
			dst[di++] = table[(n >>> 12) & 0x3f];
			dst[di++] = table[(n >>> 6) & 0x3f];
			dst[di++] = table[n & 0x3f];
		}

		if (tail > 0) {
			int n = (src[si] & 0xff) << 10;
			if (tail == 2)
				n |= (src[++si] & 0xff) << 2;

			dst[di++] = table[(n >>> 12) & 0x3f];
			dst[di++] = table[(n >>> 6) & 0x3f];
			if (tail == 2)
				dst[di++] = table[n & 0x3f];

			if (pad != 0) {
				if (tail == 1)
					dst[di++] = pad;
				dst[di] = pad;
			}
		}

		return dst;
	}
}
