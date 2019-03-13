package com.lmxf.post.core.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.security.AesCrypto;
import com.lmxf.post.core.security.TriDesCrypto;

/**
 * 
 * 此类描述的是：
 * 
 * @author: wusijie
 * @version: 1.0
 */
public class VerifiCodeUtils {
	private final static Log log = LogFactory.getLog(VerifiCodeUtils.class);
	private static VerifiCodeUtils instance = new VerifiCodeUtils();
	private static List<String> filterpw = new ArrayList<String>();
	
	private static String pattern=null;
	private static DecimalFormat decimalFormat=null;
	
	private static int index = 0;
	
	static {
		filterpw.add("12345678");
		filterpw.add("87654321");
		filterpw.add("12590");
		filterpw.add("88448");
	}

	private VerifiCodeUtils() {
	}

	public static VerifiCodeUtils getInstance() {
		if (instance == null) {
			instance = new VerifiCodeUtils();
			return instance;
		}
		return instance;
	}
	/**
	 * 
	 * 此方法描述的是：
	 * 
	 * @author: wusijie
	 * @version: 1.0
	 */
	public synchronized static String getVerifiCode() {
		getInstance();
		if(pattern==null){
			pattern=getFormatPattern(4,"0");
			decimalFormat=new DecimalFormat(pattern);
		}
		if(index>9999){
			index=0;
		}
		index=index+1;
		int my_index=index;
		Random rd1 = new Random();
		String before=rd1.nextInt(10000)+"";
		before=getFormatRandomValue(before,3);
		String after=my_index+"";		
		after=getFormatRandomValue(after,3);
		return before+after;
		
	}

	private static String getFormatRandomValue(String s,int max_len) {
		int len=s.length();
		if(len>=max_len){
			return s;
		}
		int diff_len=max_len-len;
		String diff_value="";
		for (int i = 0; i < diff_len; i++) {
			Random rd1 = new Random();
			diff_value+=rd1.nextInt(10)+"";
		}
		if(diff_value.length()!=diff_len){
			return decimalFormat.format(s);
		}
		return diff_value+s;
	}

	private static String getFormatPattern(int max_len, String string) {
		String s="";
		for (int i = 0; i <max_len; i++) {
			s+=string;
		}
		return s;
	}

	public String getPassword() {
		String pw = VerifiCodeUtils.getVerifiCode();
		boolean b = true;
		while (b) {
			boolean s = true;
			for (String str : filterpw) {
				if (pw.contains(str))
					s = false;
			}
			if (s) {
				b = false;
			} else {
				pw = VerifiCodeUtils.getVerifiCode();
			}
		}
		return pw;
	}

	public static String getEncrypt(String encodeType, String password) {
		getInstance();
		String e1 = "";
		if (encodeType.equals(GParameter.encodeType_none))
			return password;
		if (encodeType.equals(GParameter.encodeType_3Des)) {
			try {
				TriDesCrypto enc = new TriDesCrypto();
				e1 = enc.encrypt(password);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("TriDesCrypto enc.encrypt is error" + e.getMessage());
				return null;
			}
			return e1;
		}
		if (encodeType.equals(GParameter.encodeType_aes)) {
			try {
				AesCrypto enc = new AesCrypto();
				e1 = enc.encrypt(password);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("AesCrypto  enc.encrypt is error" + e.getMessage());
				return null;
			}
			return e1;
		}
		return null;
	}

	/**
	 * 测试方法
	 * @param str
	 */
//	public static void main(String[] str) {
//		List<String> okPassword=new ArrayList<String>();
//		List<Long> exce_time=new ArrayList<Long>();
//		for (int i = 0; i<100000; i++) {
//			Date a=new Date();
//			String password=VerifiCodeUtils.getInstance().getVerifiCode();
//			Date b=new Date();
//			long len=b.getTime()-a.getTime();
//			if(len>0){
//				System.out.println("i="+i+",len="+len);
//			}
//			exce_time.add(len);
//			if(!okPassword.contains(password)){
//				okPassword.add(password);
//			}else{
//				System.out.println("i="+i+",not_ok_password="+password);
//			}
//		}
//		
//		System.out.println("okPasswords size is :"+okPassword.size());
//	}
}
