package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.MerchantDTO;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.converter.MemberConverter;
import com.lawu.eshop.user.srv.converter.MerchantConverter;
import com.lawu.eshop.user.srv.service.MerchantService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meishuquan
 * @date 2017/3/22
 */
@RestController
@RequestMapping(value = "merchant/")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    /**
     * 商户修改密码
     *
     * @param id  主键
     * @param pwd 密码
     */
    @RequestMapping(value = "updatePwd", method = RequestMethod.POST)
    public void updatePwd(@RequestParam Long id, @RequestParam String pwd) {
        merchantService.updatePwd(id, pwd);
    }

    /**
     * 根据账号查询商户信息
     *
     * @param account 商户账号
     */
    @RequestMapping(value = "getMerchantByAccount", method = RequestMethod.GET)
    public MerchantDTO getMerchantByAccount(@RequestParam String account) {
        MerchantBO merchantBO = merchantService.getMerchantByAccount(account);
        return MerchantConverter.convertDTO(merchantBO);
    }
    
    /**
     * 推荐商家
     *@author zhangrc
     *@date 2017/03/23
     *@param inviterId 用户id
     *@return
     */
    @RequestMapping(value = "findMemberListByUserId", method = RequestMethod.GET)
    public List<MerchantDTO> findMemberListByUserId(@RequestParam Long inviterId) {
        List<MerchantBO> merchantBOS = merchantService.getMerchantByInviterId(inviterId);
        return MerchantConverter.convertListDOTS(merchantBOS);
    }
}
