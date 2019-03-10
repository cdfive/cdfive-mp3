package com.cdfive.core.vo.jqgrid;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class JqGridRequest {

//	_search:false
//	nd:1485487069067
//	rows:20
//	page:1
//	sidx:
//	sord:asc
//	filters:{"groupOp":"AND","rules":[{"field":"cName","op":"cn","data":"1"}]}
//	searchField:
//	searchString:
//	searchOper:
	
	private Boolean _search;
	private String nd;
	private Integer rows = 10;
	private Integer page = 1;
	private String sidx;
	private String sord;
	private JqGridFilters filters;
	
	public String getSqlWhere() {
//		if (_search != null && _search.equals(Boolean.TRUE)) {
			if (filters != null) {
				StringBuilder sqlWhere = new StringBuilder();
				String groupOp = filters.getGroupOp();
				List<JqGridRule> rules = filters.getRules();
				int index = 0;
				for (JqGridRule rule : rules) {
					index++;
					String field = rule.getField();
					String op = rule.getOp();
					String data = rule.getData();
					if (StringUtils.isWhitespace(data)) {
		                continue;
					}
					sqlWhere.append(field);
					switch (op) {
					case "eq":
						sqlWhere.append("=");
						sqlWhere.append("'" + data + "'");
						break;
					case "ne":
						sqlWhere.append("!=");
						sqlWhere.append("'" + data + "'");
						break;
					case "lt":
						sqlWhere.append("<");
						sqlWhere.append("'" + data + "'");
						break;
					case "le":
						sqlWhere.append("<=");
						sqlWhere.append("'" + data + "'");
						break;
					case "gt":
						sqlWhere.append(">");
						sqlWhere.append("'" + data + "'");
						break;
					case "ge":
						sqlWhere.append(">=");
						sqlWhere.append("'" + data + "'");
						break;
					case "bw":
						sqlWhere.append(" LIKE");
						sqlWhere.append("'" + data + "%'");
						break;
					case "bn":
						sqlWhere.append(" NOT LIKE '");
						sqlWhere.append("'" + data + "%'");
						break;
					case "in":
						sqlWhere.append(" IN ");
						sqlWhere.append("( " + data + " )");
						break;
					case "ni":
						sqlWhere.append(" NOT IN ");
						sqlWhere.append("('" + data + "')");
						break;
					case "ew":
						sqlWhere.append(" LIKE ");
						sqlWhere.append("'%" + data + "'");
						break;
					case "en":
						sqlWhere.append(" NOT LIKE ");
						sqlWhere.append("'%" + data + "'");
						break;
					case "nc":
						sqlWhere.append(" NOT LIKE ");
						sqlWhere.append("'%" + data + "%'");
						break;
					case "cn":
						sqlWhere.append(" LIKE ");
						sqlWhere.append("'%" + data + "%'");
						break;
					case "nu":
						sqlWhere.append(" IS NULL");
						break;
					case "nn":
						sqlWhere.append(" IS NOT NULL");
						break;
					}
					
					if (index != rules.size()) {
						sqlWhere.append(" " + groupOp + " ");
					}
				}
				return sqlWhere.toString();
			}
//		}
		return null;
	}
	
	public String getSqlOrder() {
		if (!StringUtils.isEmpty(sidx)) {

			return sidx + " " + sord;
		}
		return "";
	}
	
	public Integer getPageStart() {
		return (page - 1) * rows;
	}
	
	public Integer getPageSize() {
		return rows;
	}

	public Boolean get_search() {
		return _search;
	}

	public void set_search(Boolean _search) {
		this._search = _search;
	}

	public String getNd() {
		return nd;
	}

	public void setNd(String nd) {
		this.nd = nd;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public JqGridFilters getFilters() {
		return filters;
	}

	public void setFilters(JqGridFilters filters) {
		this.filters = filters;
	}
	
}
