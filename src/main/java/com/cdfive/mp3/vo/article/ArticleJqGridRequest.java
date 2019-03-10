package com.cdfive.mp3.vo.article;

import com.cdfive.core.vo.jqgrid.JqGridRequest;

public class ArticleJqGridRequest extends JqGridRequest {
	private String catalogId;

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

}
