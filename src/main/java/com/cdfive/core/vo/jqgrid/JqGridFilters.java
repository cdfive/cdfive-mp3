package com.cdfive.core.vo.jqgrid;

import java.util.List;

public class JqGridFilters {
	private String groupOp;
	private List<JqGridRule> rules;

	public String getGroupOp() {
		return groupOp;
	}

	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}

	public List<JqGridRule> getRules() {
		return rules;
	}

	public void setRules(List<JqGridRule> rules) {
		this.rules = rules;
	}

}
