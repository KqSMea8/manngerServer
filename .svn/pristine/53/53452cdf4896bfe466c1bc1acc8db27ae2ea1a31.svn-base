package com.lmxf.post.core.validate;

import java.util.HashMap;
import java.util.Map;

public class VRuleAssemblerFactory {
	@SuppressWarnings("rawtypes")
	private Map rules = new HashMap();

	/**
	 * 查找校验规则 Rule。
	 */
	public IVRule lookupVRule(String ruleHandler) {
		if (ruleHandler == null)
			return null;
		IVRule rule = null;
		if (rules.containsKey(ruleHandler))
			rule = (IVRule) rules.get(ruleHandler);
		return rule;
	}

	/**
	 * 注册 / 加入一个校验规则 Rule.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addVRule(String ruleHandler, Class ruleClass) {
		if ((ruleHandler != null) && (ruleClass != null)) {
			try {
				rules.put(ruleHandler, ruleClass.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public Map getRules() {
		return rules;
	}

	@SuppressWarnings("rawtypes")
	public void setRules(Map rules) {
		this.rules = rules;
	}

}
