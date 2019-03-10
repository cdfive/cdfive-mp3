package com.cdfive.mp3.controller;

import com.cdfive.core.base.BaseController;
import com.cdfive.core.base.BaseResponse;
import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.mp3.constant.Mp3UriConstant;
import com.cdfive.mp3.service.ArticleService;
import com.cdfive.mp3.vo.article.ArticleDetailVo;
import com.cdfive.mp3.vo.article.ArticleJqGridRequest;
import com.cdfive.mp3.vo.article.ArticleListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("")
@Controller
public class ArticleController extends BaseController {

	@Autowired
	private ArticleService articleService;
	
	@RequestMapping(Mp3UriConstant.ARTICLE_TOP_LIST)
	@ResponseBody
	public BaseResponse<List<ArticleListVo>> topList(Integer num) {
		List<ArticleListVo> list = articleService.findArticleTopList(num);
		return succ(list);
	}
	
	@RequestMapping(Mp3UriConstant.ARTICLE_LIST)
	@ResponseBody
	public JqGridResponse<ArticleListVo> list(ArticleJqGridRequest request) {
		JqGridResponse<ArticleListVo> list = articleService.findArticleJqList(request);
		return list;
	}
	
	@RequestMapping(Mp3UriConstant.ARTICLE_DETAIL)
	@ResponseBody
	public BaseResponse<ArticleDetailVo> detail(String id) {
		ArticleDetailVo articleDetailVo = articleService.findArticleDetail(id);
		return succ(articleDetailVo);
	}
	
	@RequestMapping(Mp3UriConstant.ARTICLE_ADD)
	@ResponseBody
	public BaseResponse<?> add(String catalogId, String title, String content, Integer isOri, String oriUrl, Integer isTop) {
		articleService.addArticle(catalogId, title, content, isOri, oriUrl, isTop);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.ARTICLE_UPDATE)
	@ResponseBody
	public BaseResponse<?> update(String id, String title, String content, Integer isOri, String oriUrl, Integer isTop) {
		articleService.updateArticle(id, title, content, isOri, oriUrl, isTop);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.ARTICLE_DEL)
	@ResponseBody
	public BaseResponse<?> del(String id) {
		articleService.delArticle(id);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.ARTICLE_ORI_CLICK)
	@ResponseBody
	public BaseResponse<?> oriClick(String id) {
		articleService.oriClick(id);
		return succ();
	}
	
}
