package com.cdfive.mp3.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdfive.core.constant.enums.StatusEnum;
import com.cdfive.mp3.mapper.UploadMapper;
import com.cdfive.mp3.po.Upload;
import com.cdfive.mp3.service.Mp3BaseService;
import com.cdfive.mp3.service.UploadService;

@Service
public class UploadServiceImpl extends Mp3BaseService implements UploadService {

	@Autowired
	private UploadMapper uploadMapper;
	
	@Override
	public void addUpload(String type, String oriName, String saveName, String path) {
		addBizLog("上传文件");
		Upload upload = new Upload();
		upload.setId(uuid());
		upload.setUserId(getSessionUserId());
		upload.setType(type);
		upload.setOriName(oriName);
		upload.setSaveName(saveName);
		upload.setPath(path);
		upload.setCreateTime(now());
		upload.setUpdateTime(now());
		upload.setStatus(StatusEnum.NORMAL.getCode());
		uploadMapper.insertSelective(upload);
	}

}
