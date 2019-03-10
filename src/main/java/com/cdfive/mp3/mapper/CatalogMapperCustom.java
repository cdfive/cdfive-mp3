package com.cdfive.mp3.mapper;

import com.cdfive.core.vo.ztree.ZtreeNodeVo;
import com.cdfive.mp3.po.Catalog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CatalogMapperCustom {
	
	int findCount();
	
    List<ZtreeNodeVo> findCatalogTreeList();
    
    Catalog findByPIdAndName(@Param("pId") String pId, @Param("name") String name);
    
    int findCountByPId(@Param("pId") String pId);
    
    int findMaxSortByPId(@Param("pId") String pId);
    
    int addAfterSort(@Param("pId") String pId, @Param("sort") Integer sort);
    
    int minusAfterSort(@Param("pId") String pId, @Param("sort") Integer sort);
    
    void truncate();
}