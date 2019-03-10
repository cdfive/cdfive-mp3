package com.cdfive.mp3.vo.article;

import java.util.Date;

public class ArticleDetailVo {
	private String id;
	private String title;
	private String content;
	private Integer isOri;
	private String oriUrl;
	private Integer oriUrlClickCount;
	private Integer isTop;
	private Integer clickCount;
	private Date createTime;
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIsOri() {
		return isOri;
	}

	public void setIsOri(Integer isOri) {
		this.isOri = isOri;
	}

	public String getOriUrl() {
		return oriUrl;
	}

	public void setOriUrl(String oriUrl) {
		this.oriUrl = oriUrl;
	}

	public Integer getOriUrlClickCount() {
		return oriUrlClickCount;
	}

	public void setOriUrlClickCount(Integer oriUrlClickCount) {
		this.oriUrlClickCount = oriUrlClickCount;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
