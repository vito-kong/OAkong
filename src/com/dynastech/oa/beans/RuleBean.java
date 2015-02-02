package com.dynastech.oa.beans;

public class RuleBean {

	private String RuleType;
	private boolean IsResultOK;
	private String ResultString;
	
	public String getRuleType() {
		return RuleType;
	}
	public void setRuleType(String ruleType) {
		RuleType = ruleType;
	}
	public boolean isIsResultOK() {
		return IsResultOK;
	}
	public void setIsResultOK(boolean isResultOK) {
		IsResultOK = isResultOK;
	}
	public String getResultString() {
		return ResultString;
	}
	public void setResultString(String resultString) {
		ResultString = resultString;
	}
	
}
