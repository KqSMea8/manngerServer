package com.lmxf.post.core.security.protocol;

import java.util.HashMap;
import java.util.Map;

public class SecurityKeyAlgo {
	// 平移算法
	public static Integer[] translation_range = new Integer[] { 23, 14, 32, 24, 12, 8, 15, 10, 19, 29, 1, 17, 13, 2, 30, 20, 3, 22, 4, 21, 5, 18, 27, 6, 25, 7, 28, 9, 11, 31, 16, 26 };
	// 置换算法
	public static Map<String, String> displaceMap = new HashMap<String, String>();
	private static SecurityKeyAlgo securityKeyAlgo;
	static {
		displaceMap.put("1", "r");
		displaceMap.put("2", "f");
		displaceMap.put("3", "k");
		displaceMap.put("4", "9");
		displaceMap.put("5", "2");
		displaceMap.put("6", "g");
		displaceMap.put("7", "u");
		displaceMap.put("8", "1");
		displaceMap.put("e", "y");
		displaceMap.put("r", "7");
		displaceMap.put("9", "o");
		displaceMap.put("0", "3");
		displaceMap.put("q", "a");
		displaceMap.put("w", "c");
		displaceMap.put("t", "l");
		displaceMap.put("y", "i");
		displaceMap.put("u", "5");
		displaceMap.put("i", "p");
		displaceMap.put("o", "j");
		displaceMap.put("p", "n");
		displaceMap.put("a", "4");
		displaceMap.put("s", "x");
		displaceMap.put("d", "z");
		displaceMap.put("f", "6");
		displaceMap.put("g", "q");
		displaceMap.put("h", "8");
		displaceMap.put("j", "s");
		displaceMap.put("k", "m");
		displaceMap.put("l", "0");
		displaceMap.put("z", "e");
		displaceMap.put("x", "t");
		displaceMap.put("c", "w");
		displaceMap.put("v", "b");
		displaceMap.put("b", "v");
		displaceMap.put("n", "d");
		displaceMap.put("m", "h");
	}

	public String getSecurityKey(String key) {
		String securityKey = key;
		StringBuffer result=new StringBuffer();
		if (key != null && key.length() == 32) {
			for (String key_map : displaceMap.keySet()) {
				String value = displaceMap.get(key_map);
				for (int j = 1; j <= key.length(); j++) {
					String singleKey = key.substring(j - 1, j);
					if (singleKey.equals(key_map)) {
						String beforeSecurityKey = securityKey.substring(0, j - 1);
						String afterSecurityKey = securityKey.substring(j , securityKey.length());
						securityKey = beforeSecurityKey + value + afterSecurityKey;
					}
				}
			}
			System.out.println(key+"    "+securityKey);
			for (int i = 0; i < translation_range.length; i++) {
				int transIndex = translation_range[i];
				result.append(securityKey.substring(transIndex - 1, transIndex));
			}
			System.out.println(result);
			return result.toString();
		} else {
			return "";
		}
	}

	private SecurityKeyAlgo() {
	}

	public static SecurityKeyAlgo getInstance() {
		if (securityKeyAlgo == null)
			securityKeyAlgo = new SecurityKeyAlgo();
		return securityKeyAlgo;
	}

}
