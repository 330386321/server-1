package com.lawu.eshop.user.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.core.page.PageParam;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MerchantDTO;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.converter.MerchantConverter;
import com.lawu.eshop.user.srv.service.MerchantService;
import com.lawu.eshop.utils.MD5;

/**
 * @author meishuquan
 * @date 2017/3/22
 */
@RestController
@RequestMapping(value = "merchant/")
public class MerchantController extends BaseController {

    @Autowired
    private MerchantService merchantService;

    /**
     * 修改登录密码
     *
     * @param id          主键
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     */
    @RequestMapping(value = "updateLoginPwd", method = RequestMethod.PUT)
    public Result updateLoginPwd(@RequestParam Long id, @RequestParam String originalPwd, @RequestParam String newPwd) {

        MerchantBO merchantBO = merchantService.getMerchantBOById(id);
        if (!MD5.MD5Encode(originalPwd).equals(merchantBO.getPwd())) {
            return failVerify();
        }
        merchantService.updateLoginPwd(id, originalPwd, newPwd);
        return successCreated();
    }

    /**
     * 根据账号查询商户信息
     *
     * @param account 商户账号
     */
    @RequestMapping(value = "getMerchantByAccount", method = RequestMethod.GET)
    public Result getMerchantByAccount(@RequestParam String account) {
        MerchantBO merchantBO = merchantService.getMerchantByAccount(account);
        MerchantDTO merchantDTO= MerchantConverter.convertDTO(merchantBO);
        return successGet(merchantDTO);
    }

    /**
     * 推荐商家
     *@author zhangrc
     *@date 2017/03/23
     *@param inviterId 用户id
     *@return
     */
    @RequestMapping(value = "getMerchantByInviter", method = RequestMethod.GET)
    public Page<MerchantDTO> getMerchantByInviter(@RequestParam Long inviterId,@RequestParam String account,@RequestBody PageParam pageQuery) {
    	Page<MerchantBO> pageBO=merchantService.getMerchantByInviter(inviterId, account, pageQuery);
    	Page<MerchantDTO> pageDTOS=MerchantConverter.convertPageDOTS(pageBO);
        return pageDTOS;
    }

    /**
     * 商户注册
     *
     * @param registerParam 商户注册信息
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result register(@RequestBody RegisterParam registerParam) {
        merchantService.register(registerParam);
        return successCreated();
    }
}
