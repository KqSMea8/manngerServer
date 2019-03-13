package com.lmxf.post.core.validate;

public class EmptyVRule implements IVRule {
	/**
	 * 提供boolean校验返回值
	 */
	
	public boolean isValid(Object value) {
		if (value instanceof String) {
			if (value == null || "".equals(value.toString().trim())) {
				return false;
			}
			return true;
			
		} else if (value instanceof Integer) {
			Integer value1 = (Integer) value;
			if (value == null || value1 == 0) {
				return false;
			} 
			return true;
		}
		return true;
	}

	/**
	 * 提供异常类返回
	 */
	public void validate(Object value) throws DataValidationException {

	}

}
