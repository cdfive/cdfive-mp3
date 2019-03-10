package com.cdfive.mp3.controller;

import com.cdfive.core.base.BaseController;
import com.cdfive.core.base.BaseResponse;
import com.cdfive.core.vo.ztree.ZtreeNodeVo;
import com.cdfive.mp3.constant.Mp3UriConstant;
import com.cdfive.mp3.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("")
@Controller
public class CatalogController extends BaseController {

	@Autowired
	private CatalogService catalogService;
	
	@RequestMapping(Mp3UriConstant.CATALOG_LIST)
	@ResponseBody
	public List<ZtreeNodeVo> list() {
		List<ZtreeNodeVo> list = catalogService.findCatalogTreeList();
		return list;
	}
	
	@RequestMapping(Mp3UriConstant.CATALOG_ADD)
	@ResponseBody
	public BaseResponse<ZtreeNodeVo> add(String pId, String prevId, String name, String description) {
		ZtreeNodeVo ztreeNodeVo = catalogService.addCatalog(pId, prevId, name, description);
		return succ(ztreeNodeVo);
	}
	
	@RequestMapping(Mp3UriConstant.CATALOG_UPDATE)
	@ResponseBody
	public BaseResponse<?> update(String id, String name, String description) {
		catalogService.updateCatalog(id, name, description);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.CATALOG_DEL)
	@ResponseBody
	public BaseResponse<?> del(String id) {
		catalogService.delCatalog(id);
		return succ();
	}
	
}
