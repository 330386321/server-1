package com.lawu.eshop.user.srv.controller;


import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.srv.bo.MerchantStoreInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreProfileBO;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;
import com.lawu.eshop.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 商家门店
 * Created by Administrator on 2017/3/24.
 */
@RestController
@RequestMapping(value = "merchantStore/")
public class MerchantStoreController extends BaseController {

    @Autowired
    private MerchantStoreInfoService merchantStoreInfoService;

    /**
     * 门店信息查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "findMerchantStoreInfo/{id}", method = RequestMethod.GET)
    public Result<MerchantStoreDTO> selectMerchantStore(@PathVariable("id") Long id) {

        MerchantStoreInfoBO merchantStoreBO = merchantStoreInfoService.selectMerchantStore(id);
        if (merchantStoreBO == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        MerchantStoreDTO merchantStoreDTO = MerchantStoreConverter.coverDTO(merchantStoreBO);

        return successGet(merchantStoreDTO);


    }

    /**
     * 新增门店信息
     *
     * @param merchantId         商家id
     * @param merchantStoreParam 门店信息（）
     * @param storeType          经营类型
     * @param certifType         证件类型
     * @return
     */
    @RequestMapping(value = "saveMerchantStoreInfo/{merchantId}", method = RequestMethod.POST)
    public Result saveMerchantStoreInfo(@PathVariable("merchantId") Long merchantId,
                                        @RequestBody MerchantStoreParam merchantStoreParam) {

        //判断门店是否存在
        MerchantStoreInfoBO merchantStoreInfoBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantId);
        if (merchantStoreInfoBO != null) {
            return failCreated(ResultCode.RECORD_EXIST);
        }

        switch (merchantStoreParam.getManageType()) {
            case ENTITY_MERCHANT:
                if ("".equals(merchantStoreParam.getStoreUrl())) {
                    return failCreated(ResultCode.IMAGE_WRONG_UPLOAD_STORE);
                }
                if ("".equals(merchantStoreParam.getEnvironmentUrl())) {
                    return failCreated(ResultCode.IMAGE_WRONG_UPLOAD_STORE_ENVIRONMENT);
                }
                break;
            default:
                break;
        }
        switch (merchantStoreParam.getCertifType()) {
            case CERTIF_TYPE_LICENSE:
                if ("".equals(merchantStoreParam.getLicenseUrl())) {
                    return failCreated(ResultCode.IMAGE_WRONG_UPLOAD_LICENSE);
                }
                break;
            case CERTIF_TYPE_IDCARD:
                if ("".equals(merchantStoreParam.getOperatorCardId())) {
                    return failCreated(ResultCode.IMAGE_WRONG_UPLOAD_IDCARD);
                }
                if (!ValidateUtil.isIDCard(merchantStoreParam.getOperatorCardId())) {
                    return failCreated(ResultCode.USER_WRONG_IDCARD);
                }
                break;
            default:
                break;
        }
        //判断该营业执照是否存在相同记录
        if (!StringUtils.isEmpty(merchantStoreParam.getRegNumber())) {
            MerchantStoreProfileBO merchantStoreProfileBO = merchantStoreInfoService.selectStoreInfoByExample(merchantStoreParam.getRegNumber(), 1);
            if (merchantStoreProfileBO != null) {
                return failCreated(ResultCode.RECORD_EXIST);
            }
        }

        //判断该身份证号是否存在相同记录
        if (!StringUtils.isEmpty(merchantStoreParam.getOperatorCardId())) {
            MerchantStoreProfileBO merchantStoreProfileBO = merchantStoreInfoService.selectStoreInfoByExample(merchantStoreParam.getRegNumber(), 2);
            if (merchantStoreProfileBO != null) {
                return failCreated(ResultCode.RECORD_EXIST);
            }
        }

        merchantStoreInfoService.saveMerchantStoreInfo(merchantId, merchantStoreParam);

        return successCreated();
    }

    /**
     * 修改门店信息
     *
     * @param merchantId         商家id
     * @param merchantStoreParam 门店信息
     * @return
     */
    @RequestMapping(value = "updateMerchantStoreInfo/{merchantStoreId}", method = RequestMethod.PUT)
    public Result updateMerchantStoreInfo(@PathVariable("merchantStoreId") Long merchantStoreId, @RequestParam("merchantId") Long merchantId, @RequestBody MerchantStoreParam merchantStoreParam) {
        MerchantStoreInfoBO merchantStoreInfoBO = merchantStoreInfoService.selectMerchantStore(merchantStoreId);
        if (merchantStoreInfoBO == null) {
            return failCreated(ResultCode.RESOURCE_NOT_FOUND);
        }

        merchantStoreInfoService.updateMerchantStoreInfo(merchantId, merchantStoreParam, merchantStoreId);

        return successCreated();
    }

    /**
     * 根据商家ID获取商家门店的名称
     *
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "getNameBymerchantId/{merchantId}", method = RequestMethod.GET)
    public Result<String> getNameByMerchantId(@PathVariable("merchantId") Long merchantId) {

        MerchantStoreInfoBO merchantStoreInfoBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantId);

        if (merchantStoreInfoBO == null || StringUtils.isEmpty(merchantStoreInfoBO.getName())) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }

        return successGet(merchantStoreInfoBO.getName());
    }

    /**
     * 增加门店审核信息记录
     *
     * @param merchantStoreId
     * @param merchantId
     * @param merchantStoreParam
     * @param storeType
     * @param certifType
     * @return
     */
    @RequestMapping(value = "saveMerchantStoreAuditInfo/{merchantStoreId}", method = RequestMethod.POST)
    public Result saveMerchantStoreAuditInfo(@PathVariable("merchantStoreId") Long merchantStoreId, @RequestParam("merchantId") Long merchantId, @RequestBody MerchantStoreParam merchantStoreParam) {
        //判断门店是否存在
        MerchantStoreInfoBO merchantStoreInfoBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantId);
        if (merchantStoreInfoBO != null) {
            return failCreated(ResultCode.RECORD_EXIST);
        }
        merchantStoreInfoService.saveMerchantStoreAuditInfo(merchantId, merchantStoreParam, merchantStoreId);
        return successCreated();
    }

    /**
     * 根据商家ID查询该商家是否支持七天无理由退货
     *
     * @param merchantId
     * @return
     * @author Yangqh
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "findIsNoReasonReturnById", method = RequestMethod.GET)
    public Result findIsNoReasonReturnById(@RequestParam Long merchantId) {

        if (merchantId == null || merchantId == 0L) {
            return successCreated(ResultCode.ID_EMPTY);
        }
        MerchantStoreInfoBO storeBO = merchantStoreInfoService.selectMerchantStoreByMId(merchantId);
        if (storeBO == null) {
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        return successCreated(storeBO.getIsNoReasonReturn());
    }
}
