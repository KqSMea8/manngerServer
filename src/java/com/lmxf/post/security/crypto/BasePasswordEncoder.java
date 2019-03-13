package com.lmxf.post.security.crypto;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePasswordEncoder implements PasswordEncoder {
	private static final int PREFIXSIZE = 3;

	protected String demergePasswordAndSalt(String mergedPasswordSalt) {
		if ((mergedPasswordSalt == null) || "".equals(mergedPasswordSalt)) {
			throw new IllegalArgumentException("不能传递一个null或空字符串");
		}

		String password = mergedPasswordSalt;

		int saltBegins = mergedPasswordSalt.lastIndexOf("{");

		if ((saltBegins != -1)
				&& ((saltBegins + 1) < mergedPasswordSalt.length())) {
			password = mergedPasswordSalt.substring(0, saltBegins);
		}

		return password;
	}

	public static String reduction(String content, int size) {
		char[] chars = content.substring(PREFIXSIZE).toCharArray();
		List<Character> arrChars = new ArrayList<Character>();
		for (char c : chars) {
			arrChars.add(c);
		}
		int i = 0;
		int pwdLen = chars.length - size;
		int x = chars.length - pwdLen;
		if (pwdLen > x) {
			while (i < x) {
				int n = (i * 2) + 1;
				if (n > x * 2)
					break;
				arrChars.remove(n == 0 ? n : n - i);
				i++;
			}
			return lsittoString(arrChars);
		} else {
			String pwd = "";
			while (i < pwdLen) {
				int n = (i * 2);
				pwd += arrChars.get(n);
				i++;
			}
			return pwd;
		}
	}

	private static String lsittoString(List<Character> arr) {
		StringBuffer buf = new StringBuffer();
		for (Character c : arr) {
			buf.append(c);
		}
		return buf.toString();
	}
}
