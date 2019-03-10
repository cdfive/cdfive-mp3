package com.cdfive.mp3.mapper;

import com.cdfive.core.vo.jqgrid.JqGridRequest;
import com.cdfive.mp3.po.User;
import com.cdfive.mp3.vo.user.UserListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapperCustom {
	
	int findCount();
	
    User findByUserName(@Param("userName") String userName);
    
    int findUserJqGridListCount(JqGridRequest request);
    
    List<UserListVo> findUserJqGridList(JqGridRequest request);
    
    void truncate();
}