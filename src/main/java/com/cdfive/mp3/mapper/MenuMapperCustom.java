package com.cdfive.mp3.mapper;

import com.cdfive.core.vo.ztree.ZtreeNodeVo;
import com.cdfive.mp3.po.Menu;
import com.cdfive.mp3.vo.menu.ButtonJqGridRequest;
import com.cdfive.mp3.vo.menu.ButtonListVo;
import com.cdfive.mp3.vo.menu.MenuTreeNodeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapperCustom {
	Menu findByPIdAndTypeAndName(@Param("pId") String pId, @Param("type") Integer type, @Param("name") String name);
	
	Integer findMaxSortByPIdAndType(@Param("pId") String pId, @Param("type") Integer type);
	
	int findCountByPIdAndType(@Param("pId") String pId, @Param("type") Integer type);
	
    List<ZtreeNodeVo> findMenuTreeList();
    
    List<MenuTreeNodeVo> findMenuTreeNodeList(@Param("pId") String pId, @Param("type") Integer type);
    
    List<MenuTreeNodeVo> findUserMenuTreeNodeList(@Param("userId") String userId, @Param("pId") String pId, @Param("type") Integer type);
    
    int findButtonJqGridListCount(ButtonJqGridRequest request);
    
    List<ButtonListVo> findButtonJqGridList(ButtonJqGridRequest request);
    
    int addAfterSort(@Param("pId") String pId, @Param("type") Integer type, @Param("sort") Integer sort);
    
    int minusAfterSort(@Param("pId") String pId, @Param("type") Integer type, @Param("sort") Integer sort);
    
    void truncate();
}