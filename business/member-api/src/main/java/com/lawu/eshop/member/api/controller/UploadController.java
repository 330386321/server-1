/**
 * 
 */
package com.lawu.eshop.member.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.csource.fastdfs.ClientParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.MemberApiConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import util.FastDFSResult;
import util.FastDFSUploadUtils;
import util.FileUploadDTO;
import util.UploadParam;

/**
 * 统一上传接口
 * 
 * @author lihj
 * @date 2017年7月26日
 */
@Api(tags = "upload", value = "统一上传接口")
@RestController
@RequestMapping(value="upload/")
public class UploadController extends BaseController {

	@Autowired
    private MemberApiConfig memberApiConfig;
	
	@Audit(date="2017-07-26",reviewer="李洪军")
	@Authorization
	@ApiOperation(value="统一上传接口",notes="上传接口(李洪军)",httpMethod="POST")
	@ApiResponse(code=HttpCode.SC_CREATED,message ="success")
	@RequestMapping(value="uploadFile",method=RequestMethod.POST)
	public Result<FileUploadDTO> uploadFile(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@RequestParam @ApiParam(required =true,value="上传类型图片(img)，视频(video),其他(file)") String type){
		HttpServletRequest request = getRequest();
		UploadParam uparam =new UploadParam();
		uparam.setBaseImageDir(memberApiConfig.getImageUploadUrl());
		uparam.setDir(FileDirConstant.DIR_HEAD);
		uparam.setType(type);
		ClientParams cp =new ClientParams();
		cp.setTrackerServer(memberApiConfig.getTrackerServers());
		cp.setTrackerTttpPport(memberApiConfig.getTrackerTttpPport());
		uparam.setCparam(cp);
		FastDFSResult fastResult = FastDFSUploadUtils.upload(request, uparam);
		Result<FileUploadDTO> result =new Result<FileUploadDTO>();
		switch(fastResult.getFenum().getVal()) {
			case 1:
				result.setRet(HttpCode.SC_CREATED);
				result.setModel(new FileUploadDTO(fastResult.getFileUrl(),fastResult.getCutImgUrl()));
				break;
			case 2:
				result.setRet(HttpCode.SC_INTERNAL_SERVER_ERROR);
				result.setMsg(ResultCode.get(ResultCode.FD_FILE_ERROR));
				break;
			case 3:
				result.setRet(HttpCode.SC_INTERNAL_SERVER_ERROR);
				result.setMsg(ResultCode.get(ResultCode.FD_FILE_IMG_BIG));
				break;
			case 4:
				result.setRet(HttpCode.SC_INTERNAL_SERVER_ERROR);
				result.setMsg(ResultCode.get(ResultCode.UPLOAD_SIZE_BIGER));
				break;
			case 5:
				result.setRet(HttpCode.SC_INTERNAL_SERVER_ERROR);
				result.setMsg(ResultCode.get(ResultCode.FD_FILE_CUT_ERROR));
				break;
		}
		return successCreated(result.getModel());
	}
	
	
}