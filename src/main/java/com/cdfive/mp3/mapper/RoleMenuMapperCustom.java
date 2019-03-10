package com.cdfive.mp3.mapper;

import com.cdfive.mp3.po.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuMapperCustom {
	List<String> findMenuIdListByRoleId(@Param("roleId") String roleId);
	
    int batchInsert(List<RoleMenu> list);
    
    int deleteByRoleId(@Param("roleId") String roleId);
    
    void truncate();
}