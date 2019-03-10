package com.cdfive.mp3.controller;

import com.cdfive.core.base.BaseController;
import com.cdfive.core.base.BaseResponse;
import com.cdfive.core.util.FileUtil;
import com.cdfive.mp3.constant.Mp3PropConstant;
import com.cdfive.mp3.constant.Mp3UriConstant;
import com.cdfive.mp3.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@RequestMapping("")
@Controller
public class UploadController extends BaseController {
	
	@Autowired
	private UploadService uploadService;
	
	@ResponseBody
    @RequestMapping(Mp3UriConstant.UPLOAD)
    public BaseResponse<String> upload(HttpServletRequest request) {
		log.info("上传文件开始");
//		String uploadType = request.getParameter("uploadType");
//		if (StringUtils.isEmpty(uploadType)) {
//			fail("非法上传");
//		}
//		if (!"mp3".equals(uploadType)) {
//			fail("非法上传");
//		}
		
		if (!(request instanceof MultipartHttpServletRequest)) {
			fail("请发送HTTP的multipart/form-data请求上传文件");
		}
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
		if (fileMap == null || fileMap.size() == 0) {
			fail("至少上传一个文件");
		}
		if (fileMap.size() > 1) {
			fail("一次只能传一个文件");
		}
		Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
		Entry<String, MultipartFile> entry = entrySet.iterator().next();
		String fileKey = entry.getKey();
		String[] mp3FileKeys = new String[] {"fileMp3","fileLrc","fileKrc"};
		if (!Arrays.asList(mp3FileKeys).contains(fileKey)) {
			fail("非法上传文件");
		}
		
		MultipartFile multipartFile = entry.getValue();
		
		String oriFileName = multipartFile.getOriginalFilename();
		log.info("原文件名=" + oriFileName);
		
		String oriFileNameWithoutExt = FileUtil.getFileNameWithoutExt(oriFileName);
		String[] tmpNames = oriFileNameWithoutExt.split("-");
		String name = tmpNames[0].trim();
		String path = "/mp3" + "/" + name.length() + "/" + oriFileName;
		String savefilePath = Mp3PropConstant.file_upload_path() + "/" + name.length() + "/" + oriFileName;
		
		log.info("保存路径=" + savefilePath);
		log.info("访问路径=" + path);
		
		File file = new File(savefilePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		log.info("保存文件开始");
		try {
			multipartFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			log.error("上传文件传输错误", e);
			fail("上传文件传输错误");
		}
		log.info("保存文件结束");
		
		log.info("记录到数据库");
		String uploadType = FileUtil.getFileNameExt(oriFileName);
		uploadService.addUpload(uploadType, oriFileName, oriFileName, path);
		
		log.info("上传文件结束");
        return succ(path);
    }
}
