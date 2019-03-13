package com.lmxf.post.core.validate;

public class DataValidationException extends Exception {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 * 
	 * @since Ver 1.1
	 */

	private static final long serialVersionUID = -1120936554473982791L;
	private String msg;

	public DataValidationException(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
