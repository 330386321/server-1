package com.lawu.eshop.user.srv.controller;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshoop.upload.UploadFileService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.dto.CertifTypeEnum;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.MerchantStoreTypeEnum;
import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.srv.bo.MerchantStoreInfoBO;
import com.lawu.eshop.user.srv.bo.MerchantStoreProfileBO;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;

import com.lawu.eshop.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * 商家门店
 * Created by Administrator on 2017/3/24.
 */
@RestController
@RequestMapping(value = "merchantStore/")
public class MerchantStoreController extends BaseController{

    @Autowired
    private MerchantStoreInfoService merchantStoreInfoService;

    /**
     * 门店信息查询
     * @param id
     * @return
     */
    @RequestMapping(value = "findMerchantStoreInfo/{id}", method = RequestMethod.GET)
    public Result<MerchantStoreDTO> selectMerchantStore(@PathVariable("id") Long id){

        MerchantStoreInfoBO merchantStoreBO =merchantStoreInfoService.selectMerchantStore(id);
        if(merchantStoreBO == null){
           return  successGet();
        }
        MerchantStoreDTO merchantStoreDTO = MerchantStoreConverter.coverDTO(merchantStoreBO);

        return successGet(merchantStoreDTO);


    }

    @RequestMapping(value = "saveMerchantStoreInfo/{merchantId}", method = RequestMethod.POST)
    public Result saveMerchantStoreInfo(@PathVariable("merchantId") Long merchantId,
                                        @RequestBody MerchantStoreParam merchantStoreParam,
                                        @RequestParam("storeType") MerchantStoreTypeEnum storeType, @RequestParam("certifType")CertifTypeEnum certifType){

        //判断门店是否存在

        MerchantStoreInfoBO merchantStoreInfoBO = merchantStoreInfoService.selectMerchantStore(merchantId);
        if(merchantStoreInfoBO != null){
           return  failVerify(ResultCode.RECORD_EXIST);
        }

            switch (storeType){
                case ENTITY_MERCHANT:
                    if("".equals(merchantStoreParam.getStoreUrl())){
                        return failVerify(ResultCode.IMAGE_WRONG_UPLOAD_STORE);
                    }
                    if("".equals(merchantStoreParam.getEnvironmentUrl())){
                        return failVerify(ResultCode.IMAGE_WRONG_UPLOAD_STORE_ENVIRONMENT);
                    }
                    break;
               default:
                   break;
            }
        switch (certifType){
            case CERTIF_TYPE_LICENSE:
                if("".equals(merchantStoreParam.getLicenseUrl())){
                    return failVerify(ResultCode.IMAGE_WRONG_UPLOAD_LICENSE);
                }
                break;
            case CERTIF_TYPE_IDCARD:
                if("".equals(merchantStoreParam.getOperatorCardId())){
                    return failVerify(ResultCode.IMAGE_WRONG_UPLOAD_IDCARD);
                }
                if(!ValidateUtil.isIDCard(merchantStoreParam.getOperatorCardId())){
                    return failVerify(ResultCode.USER_WRONG_IDCARD);
                }
                break;
                default:break;
        }
        //判断该营业执照是否存在相同记录
        if(!StringUtils.isEmpty(merchantStoreParam.getRegNumber())){
            MerchantStoreProfileBO merchantStoreProfileBO = merchantStoreInfoService.selectStoreInfoByExample(merchantStoreParam.getRegNumber(),1);
                if(merchantStoreProfileBO != null){
                    return failVerify(ResultCode.RECORD_EXIST);
                }
        }

        //判断该身份证号是否存在相同记录
        if(!StringUtils.isEmpty(merchantStoreParam.getOperatorCardId())){
            MerchantStoreProfileBO merchantStoreProfileBO = merchantStoreInfoService.selectStoreInfoByExample(merchantStoreParam.getRegNumber(),2);
            if(merchantStoreProfileBO != null){
                return failVerify(ResultCode.RECORD_EXIST);
            }
        }


        merchantStoreInfoService.saveMerchantStoreInfo(merchantId,merchantStoreParam);

        return successCreated();
    }

    @RequestMapping(value = "testUpload", method = RequestMethod.POST)
    public  void upload(HttpServletRequest request){
        Map<String,String> mapResult =  UploadFileService.uploadStoreImages(request);
    }

}
