package com.cdfive.mp3.mapper;

import com.cdfive.core.vo.common.NameCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RequestLogMapperCustom {
	List<NameCountVo> findDateStat();

	List<NameCountVo> findDateIpStat(@Param("date") String date);

	List<NameCountVo> findDateIpUriStat(@Param("date") String date, @Param("ip") String ip);
}