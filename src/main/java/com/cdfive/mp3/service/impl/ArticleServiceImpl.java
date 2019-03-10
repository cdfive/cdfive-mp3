package com.cdfive.mp3.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdfive.core.constant.enums.StatusEnum;
import com.cdfive.core.constant.enums.YesNoEnum;
import com.cdfive.core.util.StringUtil;
import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.mp3.constant.Mp3PropConstant;
import com.cdfive.mp3.mapper.ArticleMapper;
import com.cdfive.mp3.mapper.ArticleMapperCustom;
import com.cdfive.mp3.mapper.CatalogMapper;
import com.cdfive.mp3.po.Article;
import com.cdfive.mp3.po.Catalog;
import com.cdfive.mp3.service.ArticleService;
import com.cdfive.mp3.service.Mp3BaseService;
import com.cdfive.mp3.vo.article.ArticleDetailVo;
import com.cdfive.mp3.vo.article.ArticleJqGridRequest;
import com.cdfive.mp3.vo.article.ArticleListVo;

@Service
public class ArticleServiceImpl extends Mp3BaseService implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private ArticleMapperCustom articleMapperCustom;
	@Autowired
	private CatalogMapper catalogMapper; 

	@Override
	public List<ArticleListVo> findArticleTopList(Integer num) {
		addBizLog("查询文章首页列表");
		checkNotEmpty(num, "数量");
		if (num < 0) {
			fail("数量范围1-10");
		}
		if (num > 10) {
			num = 10;
		}
		List<ArticleListVo> list = articleMapperCustom.findArticleTopList(num);
		for (ArticleListVo articleListVo : list) {
			String title = articleListVo.getTitle();
			title = StringUtil.delHTMLTag(title);
			articleListVo.setTitle(title);
		}
		return list;
	}
	
	@Override
	public JqGridResponse<ArticleListVo> findArticleJqList(ArticleJqGridRequest request) {
		addBizLog("查询文章列表");
		int records = articleMapperCustom.findArticleJqGridListCount(request);
		List<ArticleListVo> rows = articleMapperCustom.findArticleJqGridList(request);
		JqGridResponse<ArticleListVo> jqGridList = new JqGridResponse<ArticleListVo>(request, records, rows);
		return jqGridList;
	}

	@Override
	public ArticleDetailVo findArticleDetail(String id) {
		addBizLog("查询文章详情");
		checkNotEmpty(id, "编号");
		Article article = checkArticle(id);
		ArticleDetailVo articleDetailVo = new ArticleDetailVo();
		articleDetailVo.setId(article.getId());
		articleDetailVo.setTitle(article.getTitle());
		articleDetailVo.setContent(article.getContent());
		articleDetailVo.setIsOri(article.getIsOri());
		articleDetailVo.setOriUrl(article.getOriUrl());
		articleDetailVo.setOriUrlClickCount(article.getOriUrlClickCount());
		articleDetailVo.setIsTop(article.getIsTop());
		articleDetailVo.setClickCount(article.getClickCount());
		articleDetailVo.setCreateTime(article.getCreateTime());
		articleDetailVo.setUpdateTime(article.getUpdateTime());
		return articleDetailVo;
	}
	
	@Override
	public void addArticle(String catalogId, String title, String content, Integer isOri, String oriUrl, Integer isTop) {
		addBizLog("新增文章");
		if (Mp3PropConstant.catalog_funny_id().equals(catalogId) || Mp3PropConstant.catalog_juzimi_id().equals(catalogId)) {
			if (isEmpty(title)) {
				String tmpContent = StringUtil.delHTMLTag(content);
				if (tmpContent != null && tmpContent.length() > 20) {
					title = tmpContent.substring(0, 20) + "...";
				} else {
					title = tmpContent;
				}
			}
		} else {
			checkNotEmpty(title, "标题");
		}
		checkRange(isOri, new Integer[] { YesNoEnum.YES.getCode(), YesNoEnum.NO.getCode() });
		checkRange(isTop, new Integer[] { YesNoEnum.YES.getCode(), YesNoEnum.NO.getCode() });
		if (catalogId != null) {
			Catalog catalog = catalogMapper.selectByPrimaryKey(catalogId);
			if (catalog == null || StatusEnum.DELETED.getCode().equals(catalog.getStatus())) {
				fail("分类不存在或已删除");
			}
		}
		
		Article article = new Article();
		article.setId(uuid());
		article.setCatalogId(catalogId);
		article.setTitle(title);
		article.setContent(content);
		article.setIsOri(isOri);
		article.setOriUrl(oriUrl);
		article.setOriUrlClickCount(0);
		article.setIsTop(isTop);
		article.setClickCount(0);
		article.setCreateUserId(getSessionUserId());
		article.setUpdateUserId(getSessionUserId());
		article.setCreateTime(now());
		article.setUpdateTime(now());
		article.setStatus(StatusEnum.NORMAL.getCode());
		articleMapper.insertSelective(article);
	}

	@Override
	public void updateArticle(String id, String title, String content, Integer isOri, String oriUrl, Integer isTop) {
		addBizLog("修改文章");
		checkNotEmpty(id, "编号");
		checkNotEmpty(title, "标题");
		checkRange(isOri, new Integer[] { YesNoEnum.YES.getCode(), YesNoEnum.NO.getCode() });
		checkRange(isTop, new Integer[] { YesNoEnum.YES.getCode(), YesNoEnum.NO.getCode() });
		Article article = checkArticle(id);
		article.setTitle(title);
		article.setContent(content);
		article.setIsOri(isOri);
		article.setOriUrl(oriUrl);
		article.setIsTop(isTop);
		article.setUpdateUserId(getSessionUserId());
		article.setUpdateTime(now());
		articleMapper.updateByPrimaryKey(article);
	}

	@Override
	public void delArticle(String id) {
		addBizLog("删除文章");
		Article article = checkArticle(id);
		article.setUpdateUserId(getSessionUserId());
		article.setUpdateTime(now());
		article.setStatus(StatusEnum.DELETED.getCode());
		articleMapper.updateByPrimaryKeySelective(article);
	}
	
	@Override
	public void oriClick(String id) {
		addBizLog("文章原链接点击");
		checkNotEmpty(id, "编号");
		Article article = checkArticle(id);
		if (YesNoEnum.YES.equals(article.getIsOri())) {
			return;
		}
		if (isEmpty(article.getOriUrl())) {
			return;
		}
		Integer oriUrlClickCount = article.getOriUrlClickCount();
		oriUrlClickCount = (oriUrlClickCount == null || oriUrlClickCount == 0) ? 1 : (oriUrlClickCount + 1);
		article.setOriUrlClickCount(oriUrlClickCount);
		articleMapper.updateByPrimaryKeySelective(article);
	}
	
	private Article checkArticle(String id) {
		Article article = articleMapper.selectByPrimaryKey(id);
		if (article == null || StatusEnum.DELETED.getCode().equals(article.getStatus())) {
			fail("文章不存在或已删除");
		}
		return article;
	}
	
}
