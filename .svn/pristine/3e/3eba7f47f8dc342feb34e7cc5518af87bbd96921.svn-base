package com.lmxf.post.core.security;

/**
 */
public final class HexUtil {
	static char[] hexstr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * Convert byte value to hexadecimal string representation (for trace dumps)
	 * 
	 * @return java.lang.String
	 * @param value
	 *            byte
	 * @param digits
	 *            int
	 */
	public static String tohex(byte value, int digits) {
		return tohex((int) value, digits);
	}

	/**
	 * Convert byte value to hexadecimal string representation (for trace dumps)
	 * 
	 * @return java.lang.String
	 * @param value
	 *            int
	 * @param digits
	 *            int
	 */
	public static String tohex(int value, int digits) {
		char[] ret = new char[digits];
		int n;
		byte a;

		for (n = 0; n < digits; n++) {
			a = (byte) (value & 0x0F);
			value >>= 4;

			ret[digits - n - 1] = hexstr[a];
		}

		return String.valueOf(ret);
	}

	/**
	 * Dumps data block in hexadecimal representation (part of trace action)
	 * 
	 * @param bytes
	 *            byte[]
	 */
	public static String xdump(byte[] bytes) {
		if (bytes == null)
			return "null";
		int len = bytes.length;
		int ofs = 0, count = 0;
		int n;
		StringBuffer sb = new StringBuffer(80);
		StringBuffer outstr = new StringBuffer(5 * len);

		while (ofs < len) {
			count = (len - ofs) < 16 ? (len - ofs) : 16;

			sb.setLength(0);

			sb.append(tohex(ofs, 4));
			sb.append(": ");

			// First, the hex bytes
			for (n = 0; n < count; n++) {
				if (n == 8)
					sb.append("- ");
				sb.append(tohex((int) bytes[ofs + n], 2));
				sb.append(' ');
			}

			// Then fill up with spaces
			for (n = count; n < 16; n++)
				sb.append("   ");

			if (count < 9) // Add the '- ' if we need it.
				sb.append("  ");

			// Seperate hex bytes from ascii chars
			sb.append(" ");

			// And last, the ascii characters
			for (n = 0; n < count; n++) {
				char b = (char) bytes[ofs + n];
				if (b >= (char) 32 && (char) b <= 127)
					sb.append(b);
				else
					sb.append('.');
			}
			sb.append("\r\n");
			outstr.append(sb.toString());
			// log(sb.toString());
			ofs += count;
		}

		return outstr.toString();
	}

	public static String xdump(byte[] bytes, int len) {
		if (bytes == null)
			return "null";
		int ofs = 0, count = 0;
		int n;
		StringBuffer sb = new StringBuffer(80);
		StringBuffer outstr = new StringBuffer(5 * len);

		while (ofs < len) {
			count = (len - ofs) < 16 ? (len - ofs) : 16;

			sb.setLength(0);

			sb.append(tohex(ofs, 4));
			sb.append(": ");

			// First, the hex bytes
			for (n = 0; n < count; n++) {
				if (n == 8)
					sb.append("- ");
				sb.append(tohex((int) bytes[ofs + n], 2));
				sb.append(' ');
			}

			// Then fill up with spaces
			for (n = count; n < 16; n++)
				sb.append("   ");

			if (count < 9) // Add the '- ' if we need it.
				sb.append("  ");

			// Seperate hex bytes from ascii chars
			sb.append(" ");

			// And last, the ascii characters
			for (n = 0; n < count; n++) {
				char b = (char) bytes[ofs + n];
				if (b >= (char) 32 && (char) b <= 127)
					sb.append(b);
				else
					sb.append('.');
			}
			sb.append("\r\n");
			outstr.append(sb.toString());
			// log(sb.toString());
			ofs += count;
		}

		return outstr.toString();
	}

	static byte tobyte(char c1, char c2) {
		int lbyte;
		int hbyte;
		if (c1 >= '0' && c1 <= '9') {
			lbyte = (int) (c1 - '0');
		} else {
			lbyte = (int) (c1 - 'A' + 10);
		}
		if (c2 >= '0' && c2 <= '9') {
			hbyte = (int) (c2 - '0');
		} else {
			hbyte = (int) (c2 - 'A' + 10);
		}
		hbyte <<= 4;
		return (byte) ((hbyte & 0xF0) + (lbyte & 0x0F));
	}

	@SuppressWarnings("unused")
	public static byte[] hex2bin(String in) {
		int len = in.length();
		byte[] out = new byte[len / 2];
		StringBuffer sb = new StringBuffer();
		in = in.toUpperCase();
		for (int i = 0, opos = 0; i < len; i++) {
			char c1 = in.charAt(i);
			char c2 = in.charAt(i + 1);
			out[opos++] = tobyte(c2, c1);
			i++;
		}
		return out;
	}

	public static String bin2hex(byte[] in) {
		StringBuffer sb = new StringBuffer();
		int len = in.length;
		for (int i = 0; i < len; i++) {
			byte c = in[i];
			int lbyte = (int) (c & 0x0F);
			int hbyte = (int) (c & 0xf0) >> 4;
			sb.append(hexstr[hbyte]);
			sb.append(hexstr[lbyte]);
		}
		return sb.toString();
	}

	public static String bin2hex(byte[] in, int pos, int len) {
		StringBuffer sb = new StringBuffer();
		// int len=in.length;
		for (int i = pos; i < len; i++) {
			byte c = in[i];
			int lbyte = (int) (c & 0x0F);
			int hbyte = (int) (c & 0xf0) >> 4;
			sb.append(hexstr[hbyte]);
			sb.append(hexstr[lbyte]);
		}
		return sb.toString();
	}

	public static String bin2hexX(byte[] in, int pos, int len) {
		StringBuffer sb = new StringBuffer();
		// int len=in.length;
		for (int i = pos; i < len; i++) {
			byte c = in[i];
			int lbyte = (int) (c & 0x0F);
			int hbyte = (int) (c & 0xf0) >> 4;
			// out[opos++]=hexstr[lbyte];
			// out[opos++]=hexstr[hbyte];
			sb.append("\\x");
			sb.append(hexstr[hbyte]);
			sb.append(hexstr[lbyte]);
			sb.append("|");
		}
		return sb.toString();

	}

	public static String bin2hexX(byte[] in) {
		return bin2hexX(in, 0, in.length);
	}
}