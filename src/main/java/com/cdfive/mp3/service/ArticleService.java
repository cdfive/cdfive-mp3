package com.cdfive.mp3.service;

import java.util.List;

import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.mp3.vo.article.ArticleDetailVo;
import com.cdfive.mp3.vo.article.ArticleJqGridRequest;
import com.cdfive.mp3.vo.article.ArticleListVo;

public interface ArticleService {
	
	List<ArticleListVo> findArticleTopList(Integer num);
	
	JqGridResponse<ArticleListVo> findArticleJqList(ArticleJqGridRequest request);
	
	ArticleDetailVo findArticleDetail(String id);
	
	void addArticle(String catalogId, String title, String content, Integer isOri, String oriUrl, Integer isTop);
	
	void updateArticle(String id, String title, String content, Integer isOri, String oriUrl, Integer isTop);
	
	void delArticle(String id);
	
	void oriClick(String id);
}
