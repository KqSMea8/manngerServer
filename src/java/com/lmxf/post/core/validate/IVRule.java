package com.lmxf.post.core.validate;

/**
 * 
 * 此类描述的是：验证规则
 * 
 * @author: wusijie
 * @version: 2014-4-19 下午05:07:11
 */
public interface IVRule {
	/**
	 * validate value by domain rule.
	 */
	public boolean isValid(Object value);

	/**
	 * validate value by domain rule.
	 */
	public void validate(Object value) throws DataValidationException;
}
