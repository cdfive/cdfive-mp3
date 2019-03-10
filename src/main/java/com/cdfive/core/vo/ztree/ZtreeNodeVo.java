package com.cdfive.core.vo.ztree;

import java.util.List;

public class ZtreeNodeVo {
	private String id;// 编号
	private String pId;// 父编号
	private String name;// 节点名称
	private String description;// 节点描述
	private String url;// 节点地址
	private Integer sort;// 排序
	private Integer childrenNum;// 子节点数量
	private List<ZtreeNodeVo> children;// 子节点数量
	private boolean isParent;// 是否是父节点 true-是 false-否
	private boolean open;// 是否展开 true-是 false-否
	private boolean checked;// 是否勾选 true-是 false-否
	private boolean halfCheck;// 是否半勾选 true-是 false-否

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(Integer childrenNum) {
		this.childrenNum = childrenNum;
	}

	public List<ZtreeNodeVo> getChildren() {
		return children;
	}

	public void setChildren(List<ZtreeNodeVo> children) {
		this.children = children;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isHalfCheck() {
		return halfCheck;
	}

	public void setHalfCheck(boolean halfCheck) {
		this.halfCheck = halfCheck;
	}

}
