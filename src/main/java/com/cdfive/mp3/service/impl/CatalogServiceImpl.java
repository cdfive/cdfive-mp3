package com.cdfive.mp3.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdfive.core.constant.enums.StatusEnum;
import com.cdfive.core.vo.ztree.ZtreeNodeVo;
import com.cdfive.mp3.mapper.CatalogMapper;
import com.cdfive.mp3.mapper.CatalogMapperCustom;
import com.cdfive.mp3.po.Catalog;
import com.cdfive.mp3.service.CatalogService;
import com.cdfive.mp3.service.Mp3BaseService;

@Service
public class CatalogServiceImpl extends Mp3BaseService implements CatalogService {

	@Autowired
	private CatalogMapper catalogMapper;
	@Autowired
	private CatalogMapperCustom catalogMapperCustom;

	@Override
	public List<ZtreeNodeVo> findCatalogTreeList() {
		addBizLog("查询分类树");
		List<ZtreeNodeVo> list = catalogMapperCustom.findCatalogTreeList();
		return list;
	}

	@Override
	public ZtreeNodeVo addCatalog(String pId, String prevId, String name, String description) {
		addBizLog("新增分类");
		checkNotEmpty(name, "名称");
		String pIdOk;
		Integer sort;

		if (pId != null || (pId == null && prevId == null)) {
			if (pId != null) {
				checkCatalog(pId);
			}
			Catalog tmpCatalog = catalogMapperCustom.findByPIdAndName(pId, name);
			if (tmpCatalog != null) {
				fail("名称不能重复");
			}
			catalogMapperCustom.addAfterSort(pId, 0);
			sort = 1;
			pIdOk = pId;
		} else {
			Catalog prevCatalog = checkCatalog(prevId);
			String prevPid = prevCatalog.getpId();
			Catalog tmpCatalog = catalogMapperCustom.findByPIdAndName(prevPid, name);
			if (tmpCatalog != null) {
				fail("名称不能重复");
			}
			Integer prevSort = prevCatalog.getSort();
			catalogMapperCustom.addAfterSort(prevPid, prevSort);
			sort = prevSort + 1;
			pIdOk = prevPid;
		}

		Catalog catalog = new Catalog();
		catalog.setId(uuid());
		catalog.setpId(pIdOk);
		catalog.setName(name);
		catalog.setDescription(description);
		catalog.setClickCount(0);
		catalog.setSort(sort);
		catalog.setCreateUserId(getSessionUserId());
		catalog.setUpdateUserId(getSessionUserId());
		catalog.setCreateTime(now());
		catalog.setUpdateTime(now());
		catalog.setStatus(StatusEnum.NORMAL.getCode());
		catalogMapper.insertSelective(catalog);

		ZtreeNodeVo ztreeNodeVo = new ZtreeNodeVo();
		ztreeNodeVo.setId(catalog.getId());
		ztreeNodeVo.setpId(pIdOk);
		ztreeNodeVo.setName(name);
		ztreeNodeVo.setDescription(description);
		ztreeNodeVo.setSort(sort);
		return ztreeNodeVo;
	}

	@Override
	public void updateCatalog(String id, String name, String description) {
		addBizLog("修改分类");
		checkNotEmpty(id, "编号");
		checkNotEmpty(name, "名称");
		Catalog catalog = checkCatalog(id);
		if (!catalog.getName().equals(name)) {
			Catalog tmpCatalog = catalogMapperCustom.findByPIdAndName(catalog.getpId(), name);
			if (tmpCatalog != null) {
				fail("名称不能重复");
			}
		}
		catalog.setName(name);
		catalog.setDescription(description);
		catalog.setUpdateUserId(getSessionUserId());
		catalog.setUpdateTime(now());
		catalogMapper.updateByPrimaryKey(catalog);
	}

	@Override
	public void delCatalog(String id) {
		addBizLog("删除分类");
		checkNotEmpty(id, "编号");
		Catalog catalog = checkCatalog(id);
		int subCount = catalogMapperCustom.findCountByPId(id);
		if (subCount > 0) {
			fail("分类下有子分类");
		}
		catalog.setUpdateUserId(getSessionUserId());
		catalog.setUpdateTime(now());
		catalog.setStatus(StatusEnum.DELETED.getCode());
		catalogMapper.updateByPrimaryKeySelective(catalog);
		catalogMapperCustom.minusAfterSort(catalog.getpId(), catalog.getSort());
	}

	private Catalog checkCatalog(String id) {
		Catalog catalog = catalogMapper.selectByPrimaryKey(id);
		if (catalog == null || StatusEnum.DELETED.getCode().equals(catalog.getStatus())) {
			fail("分类不存在或已删除");
		}
		return catalog;
	}
}
