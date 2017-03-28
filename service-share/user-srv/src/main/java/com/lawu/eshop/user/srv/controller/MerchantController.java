package com.lawu.eshop.user.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.dto.LoginUserDTO;
import com.lawu.eshop.user.dto.MerchantDTO;
import com.lawu.eshop.user.dto.MerchantInviterDTO;
import com.lawu.eshop.user.param.RegisterParam;
import com.lawu.eshop.user.query.MerchantInviterParam;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.bo.MerchantInviterBO;
import com.lawu.eshop.user.srv.converter.LoginUserConverter;
import com.lawu.eshop.user.srv.converter.MerchantConverter;
import com.lawu.eshop.user.srv.converter.MerchantInviterConverter;
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

    @RequestMapping(value = "withPwd/{account}", method = RequestMethod.GET)
    public Result<LoginUserDTO> find(@PathVariable String account, @RequestParam String pwd) {
        MerchantBO merchantBO = merchantService.find(account, pwd);
        if (merchantBO == null) {
            return successGet(ResultCode.MEMBER_WRONG_PWD);
        }
        return successGet(LoginUserConverter.convert(merchantBO));
    }


    /**
     * 修改登录密码
     *
     * @param id          主键
     * @param originalPwd 原始密码
     * @param newPwd      新密码
     */
    @RequestMapping(value = "updateLoginPwd/{id}", method = RequestMethod.PUT)
    public Result updateLoginPwd(@PathVariable Long id, @RequestParam String originalPwd, @RequestParam String newPwd) {

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
    @RequestMapping(value = "getMerchant/{account}", method = RequestMethod.GET)
    public Result getMerchantByAccount(@PathVariable String account) {
        MerchantBO merchantBO = merchantService.getMerchantByAccount(account);
        MerchantDTO merchantDTO = MerchantConverter.convertDTO(merchantBO);
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
    public Result<Page<MerchantInviterDTO>> getMerchantByInviter(@RequestBody MerchantInviterParam pageQuery) {
    	Page<MerchantInviterBO> pageBO=merchantService.getMerchantByInviter(pageQuery);
    	Page<MerchantInviterDTO> pageDTOS=MerchantInviterConverter.convertPageMIDOTS(pageBO);
        return successGet(pageDTOS);
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
