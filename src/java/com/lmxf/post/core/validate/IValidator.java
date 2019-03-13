package com.lmxf.post.core.validate;

public interface IValidator {
	/**
	 * 通过关键字 ruleHandler 查询校验规则来校验数据。
	 */
	public boolean isValid(Object value, String ruleHandler);

	/**
	 * 通过关键字 ruleHandler 查询校验规则来校验数据。返回异常。
	 */
	public void validate(Object value, String ruleHandler) throws DataValidationException;

	/**
	 * 直接指定校验规则类来校验数据。
	 */
	public boolean isValid(Object value, IVRule rule);

	/**
	 * 直接指定校验规则类来校验数据，返回异常。
	 */
	public void validate(Object value, IVRule rule) throws Exception;
}
