package com.cdfive.mp3.mapper;

import com.cdfive.core.vo.common.IdNameVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapperCustom {
    int findCountByName(@Param("name") String name);
    
    List<IdNameVo> findRoleList();
    
    void truncate();
}