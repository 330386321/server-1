package com.lawu.eshop.mall.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.MerchantFavoredTypeEnum;
import com.lawu.eshop.mall.dto.MerchantFavoredDTO;
import com.lawu.eshop.mall.param.MerchantFavoredParam;
import com.lawu.eshop.mall.srv.bo.MerchantFavoredBO;
import com.lawu.eshop.mall.srv.converter.MerchantFavoredConverter;
import com.lawu.eshop.mall.srv.service.MerchantFavoredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
@RestController
@RequestMapping(value = "merchantFavored")
public class MerchantFavoredController extends BaseController {

    @Autowired
    private MerchantFavoredService merchantFavoredService;

    /**
     * 增加优惠变更
     *
     * @param merchantId
     * @param param
     * @return
     */
    @RequestMapping(value = "saveMerchantFavoredInfo/{merchantId}", method = RequestMethod.POST)
    public Result saveMerchantFavoredInfo(@PathVariable("merchantId") Long merchantId, @RequestBody MerchantFavoredParam param) {
        if (merchantId == null || param == null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        MerchantFavoredBO merchantFavoredBO = merchantFavoredService.findFavoredByMerchantId(merchantId);
        if (merchantFavoredBO != null) {
            return successCreated(ResultCode.RECORD_EXIST);
        }
        Integer id = merchantFavoredService.saveMerchantFavoredInfo(merchantId, param);
        if (id == null || id < 0) {
            return successCreated(ResultCode.SAVE_FAIL);
        }
        return successCreated(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "findFavoredByMerchantId/{merchantId}", method = RequestMethod.GET)
    public Result<MerchantFavoredDTO> findFavoredByMerchantId(@PathVariable("merchantId") Long merchantId) {
        if (merchantId == null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        MerchantFavoredBO merchantFavoredBO = merchantFavoredService.findFavoredByMerchantId(merchantId);
        if (merchantFavoredBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        MerchantFavoredDTO merchantFavoredDTO = MerchantFavoredConverter.coverDTO(merchantFavoredBO);
        if (merchantFavoredDTO.getTypeEnum().val == MerchantFavoredTypeEnum.TYPE_DISCOUNT.val) {
            merchantFavoredDTO.setReachAmount(null);
            merchantFavoredDTO.setFavoredAmount(null);
        } else {
            merchantFavoredDTO.setDiscountRate(null);
        }

        return successGet(merchantFavoredDTO);
    }

    @RequestMapping(value = "delMerchantFavoredInfo/{id}", method = RequestMethod.DELETE)
    public Result delMerchantFavoredInfo(@PathVariable("id") Long id) {
        if (id == null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        MerchantFavoredBO merchantFavoredBO = merchantFavoredService.findFavoredById(id);
        if (merchantFavoredBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        merchantFavoredService.delMerchantFavoredInfoById(id);
        return successDelete(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "updateMerchantFavoredInfo/{merchantId}", method = RequestMethod.PUT)
    public Result updateMerchantFavoredInfo(@PathVariable("merchantId") Long merchantId, @RequestBody MerchantFavoredParam param) {
        if (merchantId == null || param == null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        MerchantFavoredBO merchantFavoredBO = merchantFavoredService.findFavoredByMerchantId(merchantId);
        if (merchantFavoredBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        Integer row = merchantFavoredService.updateMerchantFavoredInfo(merchantId, param);
        if (row == null || row < 0) {
            return successCreated(ResultCode.UPDATE_FAIL);
        }
        return successCreated(ResultCode.SUCCESS);
    }

}
