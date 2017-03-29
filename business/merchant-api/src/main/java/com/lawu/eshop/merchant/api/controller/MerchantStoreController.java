package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshoop.upload.UploadFileService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.merchant.api.service.MerchantStoreService;
import com.lawu.eshop.user.dto.CertifTypeEnum;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.dto.MerchantStoreTypeEnum;
import com.lawu.eshop.user.param.MerchantStoreParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 商家扩展信息
 * Created by Administrator on 2017/3/23.
 */
@Api(tags = "merchantStore")
@RestController
@RequestMapping(value = "/")
public class MerchantStoreController extends BaseController {

    @Autowired
    private MerchantStoreService merchantStoreService;


    @ApiOperation(value = "根据商家id查询门店信息", notes = "根据商家id查询门店信息，成功返回门店信息。（章勇）", httpMethod = "GET")
    //@Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findMerchantStoreInfo/{id}", method = RequestMethod.GET)
    public Result<MerchantStoreDTO> selectMerchantStore(@PathVariable("id") @ApiParam(required = true, value = "商家主键") Long id) {
        Result<MerchantStoreDTO> result = merchantStoreService.selectMerchantStore(id);
        return result;
    }

    @ApiOperation(value = "新增门店信息", notes = "错误信息 [1012]（章勇）", httpMethod = "POST")
    //@Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveMerchantStoreInfo/{merchantId}", method = RequestMethod.POST)
    public Result saveMerchantStoreInfo(@PathVariable("merchantId") Long merchantId, @ModelAttribute @ApiParam MerchantStoreParam merchantStoreParam, @RequestParam("storeType") MerchantStoreTypeEnum storeType,
                                        @RequestParam("certifType") CertifTypeEnum certifType) {
        HttpServletRequest request = getRequest();
        Map<String, String> map = UploadFileService.uploadStoreImages(request);
        //上传返回成功/失败
        String flag = map.get("resultFlag");
        if ("OK".equals(flag)) {
            String storeUrl = map.get("storeUrl");
            String environmentUrl = map.get("environmentUrl");
            String logoUrl = map.get("logoUrl");
            String idcardUrl = map.get("idcardUrl");
            String licenceUrl = map.get("licenceUrl");
            String otherUrl = map.get("otherUrl");
            if (!"".equals(storeUrl)) {
                merchantStoreParam.setStoreUrl(storeUrl);
            }
            if (!"".equals(environmentUrl)) {
                merchantStoreParam.setEnvironmentUrl(environmentUrl);
            }
            if (!"".equals(logoUrl)) {
                merchantStoreParam.setLogoUrl(logoUrl);
            }
            if (!"".equals(idcardUrl)) {
                merchantStoreParam.setIdcardUrl(idcardUrl);
            }
            if (!"".equals(licenceUrl)) {
                merchantStoreParam.setLicenseUrl(licenceUrl);
            }
            if (!"".equals(otherUrl)) {
                merchantStoreParam.setOtherUrl(otherUrl);
            }
            return merchantStoreService.saveMerchantStoreInfo(merchantId, merchantStoreParam, storeType, certifType);

        }
        return failCreated(ResultCode.FAIL);
    }

    @ApiOperation(value = "修改门店信息", notes = "错误信息 [1012]（章勇）", httpMethod = "PUT")
    //@Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateMerchantStoreInfo/{merchantId}", method = RequestMethod.PUT)
    public Result updateMerchantStoreInfo(@PathVariable("merchantId") Long merchantId, @ModelAttribute @ApiParam MerchantStoreParam merchantStoreParam, @RequestParam("storeType") MerchantStoreTypeEnum storeType,
                                          @RequestParam("certifType") CertifTypeEnum certifType) {
        HttpServletRequest request = getRequest();
        Map<String, String> urlMap = UploadFileService.uploadStoreImages(request);
        //上传返回成功/失败
        String urlFlag = urlMap.get("resultFlag");
        if ("OK".equals(urlFlag)) {
            String storeUrl = urlMap.get("storeUrl");
            String environmentUrl = urlMap.get("environmentUrl");
            String logoUrl = urlMap.get("logoUrl");
            String idcardUrl = urlMap.get("idcardUrl");
            String licenceUrl = urlMap.get("licenceUrl");
            String otherUrl = urlMap.get("otherUrl");
            if (!"".equals(storeUrl) && "".equals(merchantStoreParam.getStoreUrl())) {
                merchantStoreParam.setStoreUrl(storeUrl);
            }
            if (!"".equals(environmentUrl) && "".equals(merchantStoreParam.getEnvironmentUrl())) {
                merchantStoreParam.setEnvironmentUrl(environmentUrl);
            }
            if (!"".equals(logoUrl) && "".equals(merchantStoreParam.getLogoUrl())) {
                merchantStoreParam.setLogoUrl(logoUrl);
            }
            if (!"".equals(idcardUrl) && "".equals(merchantStoreParam.getIdcardUrl())) {
                merchantStoreParam.setIdcardUrl(idcardUrl);
            }
            if (!"".equals(licenceUrl) && "".equals(merchantStoreParam.getLicenseUrl())) {
                merchantStoreParam.setLicenseUrl(licenceUrl);
            }
            if (!"".equals(otherUrl) && "".equals(merchantStoreParam.getOtherUrl())) {
                merchantStoreParam.setOtherUrl(otherUrl);
            }


            return merchantStoreService.updateMerchantStoreInfo(merchantId, merchantStoreParam, storeType, certifType);

        } else
            return failCreated(ResultCode.FAIL);
    }

   /* @ApiOperation(value = "测试上传", notes = "错误信息 [1012]（章勇）", httpMethod = "POST")
    //@Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "testUpload", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Result testUpload(@RequestParam("file") MultipartFile file) {
        HttpServletRequest request = getRequest();
        Map<String,String> map =  UploadFileService.uploadOnePic(request,file);
        String flag = map.get("resultFlag");
        if("OK".equals(flag)){
            String url = map.get("imgUrl");
        }
        Result r = new Result();
        return r;

    }*/

}
