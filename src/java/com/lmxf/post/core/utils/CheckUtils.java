package com.lmxf.post.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtils {
	
	public static boolean isNull(String str) {
		return null == str || "".equals(str.trim())||"null".equalsIgnoreCase(str);
	}

	/**
	 * 对邮编进行验证，邮编为6位数字字符
	 * 
	 * @param post
	 * @return
	 */
	public static boolean isPostalCode(String post) {
		String regex = "([0-9]{6})+";
		return execRegex(regex, post);
	}

	/**
	 * 对座机号进行校验，正确格式为　区号－座机号 如:010-82881123
	 * 
	 * @param tel
	 * @return
	 */
	public static boolean isTel(String tel) {
		String regex = "[0]{1}[0-9]{2,3}-[0-9]{7,8}";
		return execRegex(regex, tel);
	}

	/**
	 * 对手机号进行校验，正确格式为　如:13233445566
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		String regex = "[1]{1}[3,5,8,6]{1}[0-9]{9}";
		return execRegex(regex, mobile);
	}

	/**
	 * 邮箱验证
	 * 
	 * @param mail
	 *            邮箱　
	 * @return true验证成功，false验证失败
	 */
	public static boolean isEmail(String mail) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return execRegex(regex, mail);
	}

	/**
	 * 验证执行方法
	 * 
	 * @param regex
	 * @param checkStr
	 * @return
	 */
	private static boolean execRegex(String regex, String checkStr) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(checkStr);
		return m.find();
	}

	/**
	 * 验证18位身份编码是否合法
	 * 
	 * @param idCard
	 *            身份编码
	 * @return 是否合法
	 */
	public static boolean isCardNo(String idCard) {
		boolean bTrue = false;
		if (idCard.length() == 18) {
			// 前17位
			String code17 = idCard.substring(0, 17);
			// 第18位
			String code18 = idCard.substring(17, 18);
			if (isNum(code17)) {
				char[] cArr = code17.toCharArray();
				if (cArr != null) {
					int[] iCard = converCharToInt(cArr);
					int iSum17 = getPowerSum(iCard);
					// 获取校验位
					String val = getCheckCode18(iSum17);
					if (val.length() > 0) {
						if (val.equalsIgnoreCase(code18)) {
							bTrue = true;
						}
					}
				}
			}
		}
		return bTrue;
	}
	public static boolean valideIdCard(String idCard) {
        String idCardPattern = "^\\d{17}(\\d|X)$";  // 前17位为数字，最后一位为数字或X
        String provinces = "11, 12, 13, 14, 15, 21, 22, 23, 31, 32, 33, 34, 35, 36, 37, 41, 42, 43, 44, 45, 46, 50, 51, 52, 53, 54, 61, 62, 63, 64, 65, 71, 81, 82";
        // 验证长度
        if (idCard.length() != 18) {
            return false;
        }
        // 验证格式
        if (!Pattern.matches(idCardPattern, idCard)) {
            return false;
        }
        // 验证省级代码
        if (!provinces.contains(idCard.substring(0,2))) {
            return false;
        }
        // 验证年月日
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            Date birthday = df.parse(idCard.substring(6, 14));
            Date min = df.parse("19000101");
            Date max = df.parse(df.format(new Date()));
            if (birthday.before(min) || birthday.after(max)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        char residues[] = {'1', '0', 'X', '9', '8', '7','6', '5', '4', '3', '2'};
        long sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += Integer.valueOf(idCard.substring(i, i+1)) * (Math.pow(2,(18-1-i))%11);
        }
        return idCard.charAt(17) == residues[(int)(sum % 11)];
    }
	/**
	 * 将power和值与11取模获得余数进行校验码判断
	 * 
	 * @param iSum
	 * @return 校验位
	 */
	public static String getCheckCode18(int iSum) {
		String sCode = "";
		switch (iSum % 11) {
		case 10:
			sCode = "2";
			break;
		case 9:
			sCode = "3";
			break;
		case 8:
			sCode = "4";
			break;
		case 7:
			sCode = "5";
			break;
		case 6:
			sCode = "6";
			break;
		case 5:
			sCode = "7";
			break;
		case 4:
			sCode = "8";
			break;
		case 3:
			sCode = "9";
			break;
		case 2:
			sCode = "x";
			break;
		case 1:
			sCode = "0";
			break;
		case 0:
			sCode = "1";
			break;
		}
		return sCode;
	}

	/**
	 * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
	 * 
	 * @param iArr
	 * @return 身份证编码。
	 */
	public static int getPowerSum(int[] iArr) {
		int iSum = 0;
		if (power.length == iArr.length) {
			for (int i = 0; i < iArr.length; i++) {
				for (int j = 0; j < power.length; j++) {
					if (i == j) {
						iSum = iSum + iArr[i] * power[j];
					}
				}
			}
		}
		return iSum;
	}

	public static final int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9,
			10, 5, 8, 4, 2 };

	/**
	 * 将字符数组转换成数字数组
	 * 
	 * @param ca
	 *            字符数组
	 * @return 数字数组
	 */
	public static int[] converCharToInt(char[] ca) {
		int len = ca.length;
		int[] iArr = new int[len];
		try {
			for (int i = 0; i < len; i++) {
				iArr[i] = Integer.parseInt(String.valueOf(ca[i]));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return iArr;
	}

	/**
	 * 数字验证
	 * 
	 * @param val
	 * @return 提取的数字。
	 */
	public static boolean isNum(String val) {
		return val == null || "".equals(val) ? false : val.matches("^[0-9]*$");
	}
	public static String format24(String filed){
		if(filed==null||"".equals(filed.trim())){
			return filed;
		}
		if(filed.length()==1){
			return "0"+filed;
		}else{
			return filed;
		}
	}
	/**
	 * 返回24小时制的日期
	 * @param c
	 * @return
	 */
	public static String format24Date(Calendar  c){
		return c.get(Calendar.YEAR)+"-"+format24(""+(c.get(Calendar.MONTH) + 1))+"-"+format24(c.get(Calendar.DAY_OF_MONTH)+"");
	}
	/**
	 * 返回24小时制的时间
	 * @param c
	 * @return
	 */
	public static String format24Time(Calendar  c){
		return format24(c.get(Calendar.HOUR_OF_DAY)+"")+":"+format24(""+c.get(Calendar.MINUTE))+":"+format24(c.get(Calendar.SECOND)+"");
	}
	/**
	//插入一条短信时，判断当前时间是不是出于23:00到凌晨23:59之间，如果是，则将计划发送时间改到第二天 08:00，
	//判断当前时间是不是出于00:00到凌晨07:00之间，如果是，则将计划发送时间改到今天的天 08:00，
	//上述的参数值需要做到灵活配置
	 * @return
	 */
	public static String  doNotDisturbDatetime() {
//		String beginTime = CacheUtils.getKeyValue(GParameter.doNotDisturb_key, GParameter.doNotDisturb_beginTime);
//		String endTime = CacheUtils.getKeyValue(GParameter.doNotDisturb_key, GParameter.doNotDisturb_endTime);
//		String planTime =CacheUtils.getKeyValue(GParameter.doNotDisturb_key, GParameter.doNotDisturb_planTime);
		String beginTime="23:00:00";//调试程序段
		String endTime="07:00:00";//调试程序段
		String planTime="07:30:00";//调试程序段
		if (null==beginTime) {
			beginTime="23:00:00";
		}
		if (null==endTime) {
			 endTime="07:00:00";
		}
		if (null==planTime) {
			planTime="07:30:00";
		}
		Calendar  c=Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		//c.set(2014, 0, 18, 0,10 , 0);//2014-01-18 00:10:00//调试程序段
		String date_str=format24Date(c);
		String time_str=format24Time(c);
		String datetime_str=date_str+" "+ time_str;
		String result="";
		if(time_str.compareTo(beginTime)>=0){
			if(endTime.compareTo(beginTime)>=0){
				if(time_str.compareTo(endTime)<=0){
					c.add(Calendar.DATE, 1);
					result=format24Date(c)+" "+planTime;
				}
			}else{
				if(time_str.compareTo("23:59:59")<=0){
					c.add(Calendar.DATE, 1);
					result=format24Date(c)+" "+planTime;
				}
			}
		}else{
			if(time_str.compareTo(endTime)>=0){
				result=datetime_str;
			}
		}
		if(!"".equals(result)){
			return result;
		}
		if(beginTime.compareTo(endTime)>=0){
			if(time_str.compareTo("00:00:00")>=0){
				if(time_str.compareTo(endTime)<=0){
					//c.add(Calendar.DATE, 1);
					result=format24Date(c)+" "+planTime;
				}else{
					result= datetime_str;
				}
			}else{
				result= datetime_str;
			}
		}else{
			if(time_str.compareTo(beginTime)>=0){
				if(time_str.compareTo(endTime)<=0){
					result= date_str+" "+planTime;
				}else{
					result= datetime_str;
				}
			}else{
				result= datetime_str;
			}
		}
		return  result;
	}
	public static void main(String[] args) {
		System.out.println(doNotDisturbDatetime());//调试程序段
	}
}
