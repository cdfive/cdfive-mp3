package com.cdfive.mp3.mapper;

import com.cdfive.mp3.po.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapperCustom {
    List<String> findRoleIdListByUserId(@Param("userId") String userId);
    
    int batchInsert(List<UserRole> list);
    
    int deleteByUserId(@Param("userId") String userId);
    
    void truncate();
}