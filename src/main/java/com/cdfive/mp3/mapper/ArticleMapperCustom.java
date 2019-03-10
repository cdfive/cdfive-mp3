package com.cdfive.mp3.mapper;

import com.cdfive.mp3.vo.article.ArticleJqGridRequest;
import com.cdfive.mp3.vo.article.ArticleListVo;

import java.util.List;

public interface ArticleMapperCustom {
	
	int findCount();

	List<ArticleListVo> findArticleTopList(Integer num);
	
    int findArticleJqGridListCount(ArticleJqGridRequest request);
    
    List<ArticleListVo> findArticleJqGridList(ArticleJqGridRequest request);
}