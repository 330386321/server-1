package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.common.constants.MemberGradeEnum;
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
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.PropertyInfoService;
import com.lawu.eshop.member.api.service.ShoppingOrderService;
import com.lawu.eshop.order.dto.foreign.MemberMineInfoForeignDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderNumberOfOrderStatusDTO;
import com.lawu.eshop.property.dto.PropertyLoveAccountDTO;
import com.lawu.eshop.user.dto.MemberMineInfoDTO;

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
    private PropertyInfoService propertyInfoService;

    @Autowired
    private ShoppingOrderService shoppingOrderService;
    
    @Autowired
    private MemberService memberService;

    @Audit(date = "2017-04-21", reviewer = "孙林青")
    @ApiOperation(value = "我的页面", notes = "返回我的页面所需要的资料[](蒋鑫俊)", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "mine", method = RequestMethod.GET)
    public Result<MemberMineInfoForeignDTO> findMemberInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
    	
        long memberId = UserUtil.getCurrentUserId(getRequest());
        String memberNum = UserUtil.getCurrentUserNum(getRequest());
        
        // 查询用户信息
        Result<MemberMineInfoDTO> resultMemberMineInfoDTO = memberService.findMemberMineInfo(memberId);
        if (!isSuccess(resultMemberMineInfoDTO)) {
        	return successGet(resultMemberMineInfoDTO.getRet()); 
        }
        
        //查询订单状态的数量
        Result<ShoppingOrderNumberOfOrderStatusDTO> resultShoppingOrderNumberOfOrderStatusDTO = shoppingOrderService.numberOfOrderStartus(memberId);
        if (!isSuccess(resultShoppingOrderNumberOfOrderStatusDTO)) {
        	return successGet(resultShoppingOrderNumberOfOrderStatusDTO.getRet());
        }
        
        // 查询爱心账户
        Result<PropertyLoveAccountDTO> resultPropertyLoveAccountDTO = propertyInfoService.selectLoveAccount(memberNum);
        if (!isSuccess(resultPropertyLoveAccountDTO)) {
        	return successGet(resultPropertyLoveAccountDTO.getRet());
        }
        
        MemberMineInfoDTO memberMineInfoDTO = resultMemberMineInfoDTO.getModel();
        MemberMineInfoForeignDTO memberMineInfoForeignDTO = new MemberMineInfoForeignDTO();
        memberMineInfoForeignDTO.setNickname(memberMineInfoDTO.getNickname());
        memberMineInfoForeignDTO.setLevel(memberMineInfoDTO.getLevel());
        memberMineInfoForeignDTO.setHeadimg(memberMineInfoDTO.getHeadimg());
        memberMineInfoForeignDTO.setBeShippedCount(resultShoppingOrderNumberOfOrderStatusDTO.getModel().getBeShippedCount());
        memberMineInfoForeignDTO.setEvaluationCount(resultShoppingOrderNumberOfOrderStatusDTO.getModel().getEvaluationCount());
        memberMineInfoForeignDTO.setPendingPaymentCount(resultShoppingOrderNumberOfOrderStatusDTO.getModel().getPendingPaymentCount());
        memberMineInfoForeignDTO.setRefundingCount(resultShoppingOrderNumberOfOrderStatusDTO.getModel().getRefundingCount());
        memberMineInfoForeignDTO.setToBeReceivedCount(resultShoppingOrderNumberOfOrderStatusDTO.getModel().getToBeReceivedCount());
        memberMineInfoForeignDTO.setLoveAccount(resultPropertyLoveAccountDTO.getModel().getLoveAccount());
        memberMineInfoForeignDTO.setInviteeMemberCount(memberMineInfoDTO.getInviteMemberCount());
        memberMineInfoForeignDTO.setInviteeMechantCount(memberMineInfoDTO.getInviteMerchantCount());
        memberMineInfoForeignDTO.setGradeEnum(MemberGradeEnum.getEnum(memberMineInfoDTO.getGrade()));
        memberMineInfoForeignDTO.setGrade(memberMineInfoDTO.getGrade());
        resultMemberMineInfoDTO = null;
        resultShoppingOrderNumberOfOrderStatusDTO = null;
        resultPropertyLoveAccountDTO = null;
        
        return successGet(memberMineInfoForeignDTO);
    }

}
