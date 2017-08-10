package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.FansInviteContentService;
import com.lawu.eshop.user.dto.FansInviteContentDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
/**
 * @author hongqm
 * @date 2017/8/4.
 */
@Api(tags = "fansInviteContent")
@RestController
@RequestMapping(value = "fansInviteContent/")
public class FansInviteContentController extends BaseController{

	
	@Autowired
	private FansInviteContentService fansInviteContentService;

    @Audit(date = "2017-08-10", reviewer = "孙林青")
	@ApiOperation(value = "查询邀请粉丝详情", notes = "查询邀请粉丝详情。 [] (洪钦明)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "selectInviteContentById/{id}/{relateId}", method = RequestMethod.GET)
    public Result<FansInviteContentDTO> selectInviteContentById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,@PathVariable("id") Long id,@PathVariable("relateId") Long relateId) {
		Result<FansInviteContentDTO> result = fansInviteContentService.selectInviteContentById(id,relateId);
		return result;
    }
}
