package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.ad.param.RedPacketParam;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.merchant.api.service.PropertyInfoService;
import com.lawu.eshop.merchant.api.service.RedPacketService;
import com.lawu.eshop.property.dto.PropertyPointDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 红包
 * @author zhangrc
 * @date 2017/4/8
 *
 */
@Api(tags = "redPacket")
@RestController
@RequestMapping(value = "redPacket/")
public class RedPacketController extends BaseController{
	
	@Autowired
	private RedPacketService redPacketService;
	
	@Autowired
    private PropertyInfoService propertyInfoService;
	
	//@Authorization
    @ApiOperation(value = "红包生成", notes = "红包生成[5000,5003]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(/*@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,*/@ModelAttribute @ApiParam(required = true, value = "红包信息") RedPacketParam param) {
    	Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	Result<PropertyPointDTO>  rs=propertyInfoService.getPropertyPoint(userNum);
    	PropertyPointDTO propertyPointDTO=rs.getModel();
    	if(param.getTotlePoint().compareTo(propertyPointDTO.getPoint())==1){
    		return successCreated(ResultCode.AD_POINT_NOT_ENOUGH);
    	}
    	Result redPacket=redPacketService.save(param, merchantId,userNum);
    	return redPacket;
    	
    }

}
