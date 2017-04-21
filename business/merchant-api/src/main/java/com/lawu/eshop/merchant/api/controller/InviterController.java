package com.lawu.eshop.merchant.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.merchant.api.service.InviterService;
import com.lawu.eshop.merchant.api.service.MemberProfileService;
import com.lawu.eshop.merchant.api.service.MemberService;
import com.lawu.eshop.merchant.api.service.MerchantInviterService;
import com.lawu.eshop.user.dto.EfriendDTO;
import com.lawu.eshop.user.dto.InviteeMechantCountDTO;
import com.lawu.eshop.user.dto.InviteeMemberCountDTO;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.MerchantInviterDTO;
import com.lawu.eshop.user.param.MemberQuery;
import com.lawu.eshop.user.param.MerchantInviterParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author meishuquan
 * @date 2017/3/30
 */
@Api(tags = "inviter")
@RestController
@RequestMapping(value = "inviter/")
public class InviterController extends BaseController {

	@Autowired
    private MerchantInviterService merchantInviterService;

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private InviterService inviterService;
    
    @Autowired
    private MemberProfileService memberProfileService; 

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "查询邀请人", notes = "根据账号查询邀请人信息。[1002](梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getInviter/{account}", method = RequestMethod.GET)
    public Result<InviterDTO> getInviterByAccount(@PathVariable @ApiParam(required = true, value = "邀请人账号") String account) {
        return inviterService.getInviterByAccount(account);
    }
    
    
    /**
     * 我的商家推荐总数量
     * @param id
     * @return
    */
    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "我的商家推荐总数量", notes = "我的商家推荐总数量,[]（张荣成）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getMerchantCount", method = RequestMethod.GET)
    public Result<InviteeMechantCountDTO> getMerchantCount(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
	   Long id=UserUtil.getCurrentUserId(getRequest());
	   Result<InviteeMechantCountDTO>  count=memberProfileService.getMerchantCount(id);
	   return count;
    }
    
    /**
	 * 我的E友总数量
	 * @return
	 */
    @Audit(date = "2017-04-01", reviewer = "孙林青")
	@ApiOperation(value = "我的E友总数量", notes = "我的E友总数量,[]（张荣成）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getMemberCount", method = RequestMethod.GET)
    public Result<InviteeMemberCountDTO> getMemberCount(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
	   Long id=UserUtil.getCurrentUserId(getRequest());
	   Result<InviteeMemberCountDTO>  count=memberProfileService.getMemberCount(id);
	   return count;
    }
	
	/**
	 * 我推荐的商家
	 * @param token
	 * @param pageQuery
	 * @return
	 */
    @ApiOperation(value = "我推荐的商家", notes = "我推荐的商家查询,[]（张荣成）", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectInviteeMerchant", method = RequestMethod.POST)
    public Result<Page<MerchantInviterDTO>> selectInviteeMerchant(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                                 @ModelAttribute @ApiParam( value = "查询信息") MerchantInviterParam pageQuery) {
    	Long userId=UserUtil.getCurrentUserId(getRequest());
    	Byte merchantType=2;
    	Result<Page<MerchantInviterDTO>>  pageDTOS=merchantInviterService.getMerchantByInviter(userId,pageQuery,merchantType);
    	return pageDTOS;
    }
    
    /**
     * 我的E友
     * @param token
     * @param query
     * @return
     */
    @ApiOperation(value = "我的E友", notes = "我的E有查询,[]（张荣成）", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectInviterMember", method = RequestMethod.POST)
    public Result<Page<EfriendDTO>> findMemberListByUser(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(required = true, value = "查询信息") MemberQuery query) {
        Long userId = UserUtil.getCurrentUserId(getRequest());
        Byte inviterType=2;
        Result<Page<EfriendDTO>> page = memberService.findMemberListByUser(userId, query,inviterType);
        return page;
    }

}
