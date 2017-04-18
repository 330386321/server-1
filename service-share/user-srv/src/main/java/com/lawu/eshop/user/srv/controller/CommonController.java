package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.srv.bo.InviterBO;
import com.lawu.eshop.user.srv.converter.InviterConverter;
import com.lawu.eshop.user.srv.service.CommonService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meishuquan
 * @date 2017/3/23
 */
@RestController
@RequestMapping(value = "user/common/")
public class CommonController extends BaseController {

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "getInviter/{account}", method = RequestMethod.GET)
    public Result<InviterDTO> getInviterByAccount(@PathVariable String account) {
        InviterBO inviterBO = commonService.getInviterByAccount(account);
        if (inviterBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(InviterConverter.convertDTO(inviterBO));
    }
    
    /**
     * 根据被邀请人查询出该人所有level邀请人编号
     * @param invitedUserNum
     * @param level
     * @return
     */
    @RequestMapping(value = "selectHigherLevelInviters/{invitedUserNum}", method = RequestMethod.GET)
    public List<String> selectHigherLevelInviters(@PathVariable String invitedUserNum, int level) {
    	List<String> userNumList = commonService.selectHigherLevelInviters(invitedUserNum,level);
        return userNumList;
    }
}
