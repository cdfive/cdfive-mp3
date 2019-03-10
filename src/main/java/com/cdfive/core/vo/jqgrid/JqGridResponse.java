package com.cdfive.core.vo.jqgrid;

import java.util.List;

//jqgrid返回列表格式
public class JqGridResponse<T> {
	private Integer records;//总行数
	private Integer page;//页码，即第几页，从1开始
	private Integer pagesize;//页大小，即每页行数
	private Integer total;//总页数
	private List<T> rows;//列表
	
	public JqGridResponse(JqGridRequest request, Integer records, List<T> rows) {
		this.records = records;
		this.page = request.getPage();
		this.pagesize = request.getRows();
		this.total = records % this.pagesize == 0 ? records / this.pagesize : records / this.pagesize + 1;
		this.rows = rows;
	}
	
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
