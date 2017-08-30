package com.lawu.eshop.user.srv.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.constants.UserTypeEnum;
import com.lawu.eshop.user.dto.UserIncomeExpenditureUserInfoWrapperDTO;
import com.lawu.eshop.user.param.UserIncomeExpenditureUserInfoQueryParam;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.converter.UserIncomeExpenditureUserInfoConverter;
import com.lawu.eshop.user.srv.service.MemberService;
import com.lawu.eshop.user.srv.service.MerchantService;

/**
 * 用户公用服务
 * 
 * @author Sunny
 * @date 2017年7月4日
 */
@RestController
@RequestMapping(value = "user/")
public class UserController extends BaseController {

    @Autowired
    private MerchantService merchantService;
    
    @Autowired
    private MemberService memberService;

    /**
     * 获取用户账号
     * @param param
     * @return
     */
    @RequestMapping(value = "account", method = RequestMethod.PUT)
    public Result<UserIncomeExpenditureUserInfoWrapperDTO> selectAccount(@RequestBody UserIncomeExpenditureUserInfoQueryParam param) {
    	UserIncomeExpenditureUserInfoWrapperDTO rtn = new UserIncomeExpenditureUserInfoWrapperDTO();
    	rtn.setUserInfoList(new ArrayList<>());
    	for (String userNum : param.getUserNums()) {
    		switch (UserTypeEnum.getEnum(userNum)) {
				case MEMBER:
					MemberBO memberBO = memberService.getMemberByNum(userNum);
					rtn.getUserInfoList().add(UserIncomeExpenditureUserInfoConverter.convert(memberBO));
					break;
				case MEMCHANT:
					MerchantBO merchantBO = merchantService.getMerchantByNum(userNum);
					rtn.getUserInfoList().add(UserIncomeExpenditureUserInfoConverter.convert(merchantBO));
					break;
				default:
					break;
    		}
    	}
        return successCreated(rtn);
    }
}
