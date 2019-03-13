package com.lmxf.post.core.validate;

public class AssemblyValidator implements IValidator {
	private VRuleAssemblerFactory vRuleAssemblerFactory;

	public VRuleAssemblerFactory getvRuleAssemblerFactory() {
		return vRuleAssemblerFactory;
	}

	public void setvRuleAssemblerFactory(VRuleAssemblerFactory vRuleAssemblerFactory) {
		this.vRuleAssemblerFactory = vRuleAssemblerFactory;
	}

	public boolean isValid(Object value, String ruleHandler) {
		boolean valid = false;
		IVRule rule = vRuleAssemblerFactory.lookupVRule(ruleHandler);
		if (rule != null)
			valid = rule.isValid(value);
		return valid;
	}

	public void validate(Object value, String ruleHandler) throws DataValidationException {
		IVRule rule = vRuleAssemblerFactory.lookupVRule(ruleHandler);
		if (rule != null)
			rule.validate(value);
	}

	public boolean isValid(Object value, IVRule rule) {
		boolean valid = false;
		if (rule != null)
			valid = rule.isValid(value);

		return valid;
	}

	public void validate(Object value, IVRule rule) throws Exception {
		if (rule != null)
			rule.validate(value);
	}

}
