package com.cdfive.mp3.service;

import java.util.List;

import com.cdfive.core.vo.ztree.ZtreeNodeVo;

public interface CatalogService {
	List<ZtreeNodeVo> findCatalogTreeList();
	
	ZtreeNodeVo addCatalog(String pId, String prevId, String name, String description);
	
	void updateCatalog(String id, String name, String description);
	
	void delCatalog(String id);
}
