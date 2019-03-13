package com.lmxf.post.repository.config;

import com.lmxf.post.entity.config.ErrorCode;
import com.lmxf.post.tradepkg.common.ErrorResp;

import org.apache.commons.putils.repository.BaseDao;
import org.apache.commons.putils.web.Page;

import java.util.List;

public class ErrorCodeDao extends BaseDao {
	public ErrorCode checkOne(ErrorCode errorCode) {
		ErrorCode error = super.getSqlSession().selectOne("ErrorCode.checkOne", errorCode);
		return error;
	}

	public ErrorCode findError(String code) {
		ErrorCode error = super.getSqlSession().selectOne("ErrorCode.findError", code);
		return error;
	}

	public static String ErrorInfo(String error_code, String err_info) {
		ErrorResp errorResp = new ErrorResp();
		errorResp.setRetcode(error_code + "");
		errorResp.setRetmsg(err_info);
		return errorResp.CreateJson(error_code,err_info,1,1);
	}

	public String getErrorRespJson(int error_code) {
		try {
			String err_code = Integer.toString(error_code);
			String err_desc = getErrorDesc(err_code);
			if (null == err_desc) {
				err_desc = "No define error code";
			}
			ErrorResp errorResp = new ErrorResp();
			return errorResp.CreateJson(err_code,err_desc,1,1);
		} catch (Exception e) {
			return null;
		}
	}

	public String getErrorDesc(String error_code) {
		ErrorCode error = this.findError(error_code);
		if (null == error)
			return null;
		return error.getRetDesc();
	}

	public List<ErrorCode> findAll() {
		return super.getSqlSession().selectList("ErrorCode.findAll");
	}

	public List<ErrorCode> findAll(ErrorCode errorCode, Page page) {
		return super.findDataPage("ErrorCode.findAll", errorCode, page);
	}

}
