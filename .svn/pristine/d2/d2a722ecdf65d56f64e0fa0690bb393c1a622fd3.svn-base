package com.lmxf.post.service.core;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmxf.post.core.utils.CacheUtils;
import com.lmxf.post.core.utils.GConstent;
import com.lmxf.post.core.utils.MessageSourceHelper;
import com.lmxf.post.entity.config.ErrorCode;
import com.lmxf.post.entity.parameter.Dict;
import com.lmxf.post.repository.config.ErrorCodeDao;
import com.lmxf.post.tradepkg.common.ErrorResp;
public class ErrorCodeService {
	private final static Log log = LogFactory.getLog(ErrorCodeService.class);
	private ErrorCodeDao errorCodeDao;
	private ErrorResp errorResp;
	 
    public String getErrorDesc(String error_code){
    	ErrorCode error;
    	try{
    	  error=errorCodeDao.findError(error_code);
    	  }catch(Exception e){ 
			  log.error("---findError error---:"+e.getMessage());
			  return null;
		  }
    	if(null==error)  return null;
    	 return  error.getRetDesc();
    }
    public String getErrorDesc(int error_code){
     	String err_code=Integer.toString(error_code);
       	return getErrorDesc(err_code);
    }
    public  String getErrorRespXml(int error_code){
    	try{
       	String err_code=Integer.toString(error_code);
    	String err_desc=getErrorDesc(err_code);
    	if(null==err_desc) {
    		log.error("table error_code not define error code:"+err_code);
    		err_desc="No define error code";
    	}
       	errorResp.setRetcode(err_code);
    	errorResp.setRetmsg(err_desc);
       	return errorResp.CreateJson(err_code, err_desc, GConstent.SUCCESS_TOTNUM, GConstent.SUCCESS_CURNUM);
    	 }catch(Exception e){ 
			  log.error("---getErrorRespXml  error---:"+e.getMessage());
			  return null;
		  }  
    }
    
    public String getErrorRespJson(int error_code) {
		try {
			String err_code = Integer.toString(error_code);
			String err_desc = getErrorDesc(err_code);
			if (null == err_desc) {
				log.error("table error_code not define error code:" + err_code);
				err_desc = "No define error code";
			}
			return errorResp.CreateJson(err_code, err_desc, GConstent.SUCCESS_TOTNUM, GConstent.SUCCESS_CURNUM);
		} catch (Exception e) {
			log.error("---getErrorRespXml  error---:" + e.getMessage());
			return null;
		}
	}
    
    public static String ErrorResp(int error_code, int language) {
 		ErrorResp errorResp = new ErrorResp();
 		String err_info=MessageSourceHelper.getMessage(error_code,language);
 		errorResp.setRetcode(error_code+"");
 		errorResp.setRetmsg(err_info);
 		return errorResp.CreateJson(error_code, err_info, GConstent.SUCCESS_TOTNUM, GConstent.SUCCESS_CURNUM);
 	}
    
    public static String ErrorInfo(int error_code, String err_info) {
		ErrorResp errorResp = new ErrorResp();
		errorResp.setRetcode(error_code+"");
		errorResp.setRetmsg(err_info);
		return errorResp.CreateJson(error_code, err_info, GConstent.SUCCESS_TOTNUM, GConstent.SUCCESS_CURNUM);
	}
    
    public static String ErrorInfo(String error_code, String err_info) {
		ErrorResp errorResp = new ErrorResp();
		errorResp.setRetcode(error_code);
		errorResp.setRetmsg(err_info);
		return errorResp.CreateJson(error_code, err_info, GConstent.SUCCESS_TOTNUM, GConstent.SUCCESS_CURNUM);
	}
    
    
	public static String errorInfo(String error_code, String err_info) {
		if (null==err_info||"".equals(err_info)) {
			Dict dict=(Dict) CacheUtils.get(CacheUtils.errorCodeCache,String.valueOf(error_code));
			err_info = dict!=null?dict.getDictValue():null;
			if (null == err_info) {
				log.error("table error_code not define error code:"	+ error_code);
				err_info = "No define error code";
			}
		}
		ErrorResp errorResp = new ErrorResp();
		errorResp.setRetcode(error_code);
		errorResp.setRetmsg(err_info);
		err_info = null;
		return errorResp.CreateJson(error_code, err_info, GConstent.SUCCESS_TOTNUM, GConstent.SUCCESS_CURNUM);
	}
	
	public static String errorInfo(int error_code, String err_info) {
		if (null==err_info||"".equals(err_info)) {
			Dict dict=(Dict) CacheUtils.get(CacheUtils.errorCodeCache,String.valueOf(error_code));
			err_info = dict!=null?dict.getDictValue():null;
			if (null == err_info) {
				log.error("table error_code not define error code:"	+ error_code);
				err_info = "No define error code";
			}
		}
		ErrorResp errorResp = new ErrorResp();
		errorResp.setRetcode(error_code + "");
		errorResp.setRetmsg(err_info);
		err_info = null;
		return errorResp.CreateJson(error_code, err_info, GConstent.SUCCESS_TOTNUM, GConstent.SUCCESS_CURNUM);
	}
	
	public ErrorCodeDao getErrorCodeDao() {
		return errorCodeDao;
	}
	public void setErrorCodeDao(ErrorCodeDao errorCodeDao) {
		this.errorCodeDao = errorCodeDao;
	}
	public ErrorResp getErrorResp() {
		return errorResp;
	}
	public void setErrorResp(ErrorResp errorResp) {
		this.errorResp = errorResp;
	}
	
	

}
 