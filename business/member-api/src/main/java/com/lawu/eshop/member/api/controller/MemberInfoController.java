package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.lawu.eshop.member.api.service.MemberProfileService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.PropertyInfoService;
import com.lawu.eshop.member.api.service.ShoppingOrderService;
import com.lawu.eshop.order.dto.foreign.MemberMineInfoForeignDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderNumberOfOrderStatusDTO;
import com.lawu.eshop.property.dto.PropertyLoveAccountDTO;
import com.lawu.eshop.user.dto.InviteeMechantCountDTO;
import com.lawu.eshop.user.dto.InviteeMemberCountDTO;
import com.lawu.eshop.user.dto.MemberDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * 用户个人中心页面数据接口
 * 
 * @author Sunny
 * @date 2017年4月21日
 */
@Api(tags = "memberInfo")
@RestController
@RequestMapping(value = "memberInfo/")
public class MemberInfoController extends BaseController {

    @Autowired
    private MemberProfileService memberProfileService;

    @Autowired
    private PropertyInfoService propertyInfoService;

    @Autowired
    private ShoppingOrderService shoppingOrderService;
    
    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "我的页面", notes = "返回我的页面所需要的资料[](蒋鑫俊)", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "mine", method = RequestMethod.GET)
    public Result<MemberMineInfoForeignDTO> findMemberInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
    	
        long memberId = UserUtil.getCurrentUserId(getRequest());
        String memberNum = UserUtil.getCurrentUserNum(getRequest());
        
        // 查询用户信息
        Result<MemberDTO> resultMemberDTO = memberService.findMemberInfoById(memberId);
        if (!isSuccess(resultMemberDTO)) {
        	return successGet(resultMemberDTO.getRet()); 
        }
        
        //查询订单状态的数量
        Result<ShoppingOrderNumberOfOrderStatusDTO> resultShoppingOrderNumberOfOrderStatusDTO = shoppingOrderService.numberOfOrderStartus(memberId);
        if (!isSuccess(resultShoppingOrderNumberOfOrderStatusDTO)) {
        	return successGet(resultShoppingOrderNumberOfOrderStatusDTO.getRet());
        }
        
        //查询我的E友
        Result<InviteeMemberCountDTO> resultInviteeMemberCountDTO = memberProfileService.getMemberCount(memberId);
        if (!isSuccess(resultInviteeMemberCountDTO)) {
        	return successGet(resultInviteeMemberCountDTO.getRet());
        }
        
        // 查询我推荐的商家
        Result<InviteeMechantCountDTO> resultInviteeMechantCountDTO = memberProfileService.getMerchantCount(memberId);
        if (!isSuccess(resultInviteeMechantCountDTO)) {
        	return successGet(resultInviteeMechantCountDTO.getRet());
        }
        
        // 查询爱心账户
        Result<PropertyLoveAccountDTO> resultPropertyLoveAccountDTO = propertyInfoService.selectLoveAccount(memberNum);
        if (!isSuccess(resultPropertyLoveAccountDTO)) {
        	return successGet(resultPropertyLoveAccountDTO.getRet());
        }
        
        MemberMineInfoForeignDTO memberMineInfoForeignDTO = new MemberMineInfoForeignDTO();
        memberMineInfoForeignDTO.setNickname(resultMemberDTO.getModel().getNickname());
        memberMineInfoForeignDTO.setLevel(resultMemberDTO.getModel().getLevel());
        memberMineInfoForeignDTO.setHeadimg(resultMemberDTO.getModel().getHeadimg());
        memberMineInfoForeignDTO.setBeShippedCount(resultShoppingOrderNumberOfOrderStatusDTO.getModel().getBeShippedCount());
        memberMineInfoForeignDTO.setEvaluationCount(resultShoppingOrderNumberOfOrderStatusDTO.getModel().getEvaluationCount());
        memberMineInfoForeignDTO.setPendingPaymentCount(resultShoppingOrderNumberOfOrderStatusDTO.getModel().getPendingPaymentCount());
        memberMineInfoForeignDTO.setRefundingCount(resultShoppingOrderNumberOfOrderStatusDTO.getModel().getRefundingCount());
        memberMineInfoForeignDTO.setToBeReceivedCount(resultShoppingOrderNumberOfOrderStatusDTO.getModel().getToBeReceivedCount());
        memberMineInfoForeignDTO.setInviteeMechantCount(resultInviteeMechantCountDTO.getModel().getInviteeMechantCount());
        memberMineInfoForeignDTO.setInviteeMemberCount(resultInviteeMemberCountDTO.getModel().getInviteeMemberCount());
        memberMineInfoForeignDTO.setLoveAccount(resultPropertyLoveAccountDTO.getModel().getLoveAccount());
        
        return successGet(memberMineInfoForeignDTO);
    }

}
