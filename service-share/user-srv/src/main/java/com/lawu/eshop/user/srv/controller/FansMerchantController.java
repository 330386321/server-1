package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.dto.FansMerchantDTO;
import com.lawu.eshop.user.param.InviteFansParam;
import com.lawu.eshop.user.param.ListFansParam;
import com.lawu.eshop.user.srv.bo.FansMerchantBO;
import com.lawu.eshop.user.srv.converter.FansMerchantConverter;
import com.lawu.eshop.user.srv.service.FansMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@RestController
@RequestMapping(value = "fansMerchant/")
public class FansMerchantController extends BaseController {

    @Autowired
    private FansMerchantService fansMerchantService;

    /**
     * 查询可邀请的会员
     *
     * @param inviteFansParam
     * @return
     */
    @RequestMapping(value = "listInviteFans", method = RequestMethod.GET)
    public Result<List<FansMerchantDTO>> listInviteFans(@RequestBody InviteFansParam inviteFansParam) {
        List<FansMerchantBO> fansMerchantBOList = fansMerchantService.listInviteFans(inviteFansParam);
        if (fansMerchantBOList.isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(FansMerchantConverter.convertDTO(fansMerchantBOList));
    }

    @RequestMapping(value = "listFans/{merchantId}", method = RequestMethod.GET)
    public Result<List<FansMerchantDTO>> listFans(@PathVariable Long merchantId,@RequestBody ListFansParam listFansParam ) {
        List<FansMerchantBO> fansMerchantBOList = fansMerchantService.listFans(merchantId,listFansParam);
        if (fansMerchantBOList.isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(FansMerchantConverter.convertDTO(fansMerchantBOList));
    }
}
