package com.lawu.eshop.user.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.dto.FansMerchantDTO;
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
     * @param merchantId
     * @param regionPath
     * @return
     */
    @RequestMapping(value = "listInviteFans/{merchantId}", method = RequestMethod.GET)
    public Result<List<FansMerchantDTO>> listInviteFans(@PathVariable Long merchantId, @RequestParam String regionPath) {
        List<FansMerchantBO> fansMerchantBOList = fansMerchantService.listInviteFans(merchantId, regionPath);
        if (fansMerchantBOList == null || fansMerchantBOList.isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successGet(FansMerchantConverter.convertDTO(fansMerchantBOList));
    }

    /**
     * 粉丝列表
     *
     * @param merchantId
     * @param listFansParam
     * @return
     */
    @RequestMapping(value = "listFans/{merchantId}", method = RequestMethod.POST)
    public Result<Page<FansMerchantDTO>> listFans(@PathVariable Long merchantId, @RequestBody ListFansParam listFansParam) {
        Page<FansMerchantBO> fansMerchantBOPage = fansMerchantService.listFans(merchantId, listFansParam);
        Page<FansMerchantDTO> page = new Page<>();
        page.setCurrentPage(fansMerchantBOPage.getCurrentPage());
        page.setTotalCount(fansMerchantBOPage.getTotalCount());
        page.setRecords(FansMerchantConverter.convertDTO(fansMerchantBOPage.getRecords()));
        return successGet(page);
    }

    /**
     * 查询会员是否是商家粉丝
     *
     * @param memberId
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "isFansMerchant/{merchantId}", method = RequestMethod.GET)
    public Result<Boolean> isFansMerchant(@PathVariable Long merchantId, @RequestParam Long memberId) {
        FansMerchantBO fansMerchantBO = fansMerchantService.getFansMerchant(memberId, merchantId);
        if (fansMerchantBO == null) {
            return successGet(false);
        }
        return successGet(true);
    }
    
    /**
     * 粉丝列表
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "findMerchant", method = RequestMethod.GET)
    public List<Long> listFans(@RequestParam Long memberId) {
    	List<Long> merchantIds = fansMerchantService.findMerchant(memberId);
        return merchantIds;
    }
    
    /**
     * 当前用户属于的商家
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "findFensCount", method = RequestMethod.GET)
    public  Integer  findFensCount(@RequestParam Long memberId) {
    	 Integer  count = fansMerchantService.findFensCount(memberId);
        return count;
    }

}
