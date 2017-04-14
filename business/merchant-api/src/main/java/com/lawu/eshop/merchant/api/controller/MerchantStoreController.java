package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.merchant.api.service.MerchantStoreService;
import com.lawu.eshop.user.constants.UploadFileTypeConstant;
import com.lawu.eshop.user.dto.MerchantAuditInfoDTO;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import com.lawu.eshop.user.param.MerchantStoreParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.UploadFileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.Collection;
import java.util.Map;

/**
 * 商家扩展信息
 * Created by Administrator on 2017/3/23.
 */
@Api(tags = "merchantStore")
@RestController
@RequestMapping(value = "merchantStore/")
public class MerchantStoreController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MerchantStoreController.class);
    @Autowired
    private MerchantStoreService merchantStoreService;


    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "根据商家门店id查询门店信息", notes = "根据商家门店id查询门店信息，成功返回门店信息。（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findMerchantStoreInfo/{merchantStoreId}", method = RequestMethod.GET)
    public Result<MerchantStoreDTO> selectMerchantStore(@PathVariable("merchantStoreId") @ApiParam(required = true, value = "门店ID") Long merchantStoreId, @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        Result<MerchantStoreDTO> result = merchantStoreService.selectMerchantStore(merchantStoreId);
        return result;
    }

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "新增门店信息", notes = "错误信息 [1012]（章勇）", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveMerchantStoreInfo", method = RequestMethod.POST)
    public Result saveMerchantStoreInfo(@ModelAttribute @ApiParam MerchantStoreParam merchantStoreParam, @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        HttpServletRequest request = getRequest();
        Long merchantId = UserUtil.getCurrentUserId(request);
        StringBuffer idCardUrls = new StringBuffer();        //身份证照
        StringBuffer storeUrls = new StringBuffer();        //门店照
        StringBuffer environmentUrls = new StringBuffer();        //店内环境照
        StringBuffer storeLogoUrls = new StringBuffer();    //店铺logo
        StringBuffer licenseUrls = new StringBuffer();        //营业执照
        StringBuffer otherUrls = new StringBuffer();
        Collection<Part> parts;
        try {
            parts = request.getParts();
            for (Part part : parts) {
                Map<String, String> map = UploadFileUtil.uploadImages(request, FileDirConstant.DIR_STORE, part);
                String flag = map.get("resultFlag");
                String fileName = part.getName();
                if (UploadFileTypeConstant.UPLOAD_RETURN_TYPE.equals(flag)) {
                    //有图片上传成功返回,拼接图片url
                    String imgUrl = map.get("imgUrl");
                    if (fileName.contains(UploadFileTypeConstant.IMAGE_TYPE_STORE) && !"".equals(imgUrl)) {
                        storeUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains(UploadFileTypeConstant.IMAGE_TYPE_ENVIRONMENT) && !"".equals(imgUrl)) {
                        environmentUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains(UploadFileTypeConstant.IMAGE_TYPE_LOGO) && !"".equals(imgUrl)) {
                        storeLogoUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains(UploadFileTypeConstant.IMAGE_TYPE_IDCARD) && !"".equals(imgUrl)) {
                        idCardUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains(UploadFileTypeConstant.IMAGE_TYPE_LICENCE) && !"".equals(imgUrl)) {
                        licenseUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains(UploadFileTypeConstant.IMAGE_TYPE_OTHER) && !"".equals(imgUrl)) {
                        otherUrls.append(imgUrl + ",");
                    }
                } else {
                    return successCreated(Integer.valueOf(flag));
                }
            }
        } catch (Exception e) {
            logger.info("上传失败==================");
            return successCreated(ResultCode.IMAGE_WRONG_UPLOAD);
        }
        //判断回显照片
        if (!"".equals(merchantStoreParam.getStoreUrl()) && !"null".equals(merchantStoreParam.getStoreUrl()) && merchantStoreParam.getStoreUrl() != null) {
            merchantStoreParam.setStoreUrl(otherUrls + merchantStoreParam.getStoreUrl());
        } else {
            merchantStoreParam.setStoreUrl(otherUrls.toString());
        }
        if (!"".equals(merchantStoreParam.getEnvironmentUrl()) && !"null".equals(merchantStoreParam.getEnvironmentUrl()) && merchantStoreParam.getEnvironmentUrl() != null) {
            merchantStoreParam.setEnvironmentUrl(environmentUrls + merchantStoreParam.getEnvironmentUrl());
        } else {
            merchantStoreParam.setEnvironmentUrl(environmentUrls.toString());
        }
        if (!"".equals(merchantStoreParam.getLogoUrl()) && !"null".equals(merchantStoreParam.getLogoUrl()) && merchantStoreParam.getLogoUrl() != null) {
            merchantStoreParam.setLogoUrl(storeLogoUrls + merchantStoreParam.getLogoUrl());
        } else {
            merchantStoreParam.setLogoUrl(storeLogoUrls.toString());
        }
        if (!"".equals(merchantStoreParam.getIdcardUrl()) && !"null".equals(merchantStoreParam.getIdcardUrl()) && merchantStoreParam.getIdcardUrl() != null) {
            merchantStoreParam.setIdcardUrl(idCardUrls + merchantStoreParam.getIdcardUrl());
        } else {
            merchantStoreParam.setIdcardUrl(idCardUrls.toString());
        }
        if (!"".equals(merchantStoreParam.getLicenseUrl()) && !"null".equals(merchantStoreParam.getLicenseUrl()) && merchantStoreParam.getLicenseUrl() != null) {
            merchantStoreParam.setLicenseUrl(licenseUrls + merchantStoreParam.getLicenseUrl());
        } else {
            merchantStoreParam.setLicenseUrl(licenseUrls.toString());
        }
        if (!"".equals(merchantStoreParam.getOtherUrl()) && !"null".equals(merchantStoreParam.getOtherUrl()) && merchantStoreParam.getOtherUrl() != null) {
            merchantStoreParam.setOtherUrl(otherUrls + merchantStoreParam.getOtherUrl());
        } else {
            merchantStoreParam.setOtherUrl(otherUrls.toString());
        }

        return merchantStoreService.saveMerchantStoreInfo(merchantId, merchantStoreParam);


    }

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "修改门店信息TO审核", notes = "错误信息 [1012]（章勇）", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveMerchantStoreAuditInfo", method = RequestMethod.POST)
    public Result saveMerchantStoreAuditInfo(@PathVariable("merchantStoreId") Long merchantStoreId, @ModelAttribute @ApiParam MerchantStoreParam merchantStoreParam,
                                             @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        HttpServletRequest request = getRequest();
        Long merchantId = UserUtil.getCurrentUserId(request);
        StringBuffer idCardUrls = new StringBuffer();        //身份证照
        StringBuffer storeUrls = new StringBuffer();        //门店照
        StringBuffer environmentUrls = new StringBuffer();        //店内环境照
        StringBuffer storeLogoUrls = new StringBuffer();    //店铺logo
        StringBuffer licenseUrls = new StringBuffer();        //营业执照
        StringBuffer otherUrls = new StringBuffer();

        Collection<Part> parts;
        try {
            parts = request.getParts();
            for (Part part : parts) {
                Map<String, String> map = UploadFileUtil.uploadImages(request, FileDirConstant.DIR_STORE, part);
                String flag = map.get("resultFlag");
                String fileName = part.getSubmittedFileName();
                if (UploadFileTypeConstant.UPLOAD_RETURN_TYPE.equals(flag)) {
                    //有图片上传成功返回,拼接图片url
                    String imgUrl = map.get("imgUrl");
                    if (fileName.contains(UploadFileTypeConstant.IMAGE_TYPE_STORE) && !"".equals(imgUrl)) {
                        storeUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains(UploadFileTypeConstant.IMAGE_TYPE_ENVIRONMENT) && !"".equals(imgUrl)) {
                        environmentUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains(UploadFileTypeConstant.IMAGE_TYPE_LOGO) && !"".equals(imgUrl)) {
                        storeLogoUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains(UploadFileTypeConstant.IMAGE_TYPE_IDCARD) && !"".equals(imgUrl)) {
                        idCardUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains(UploadFileTypeConstant.IMAGE_TYPE_LICENCE) && !"".equals(imgUrl)) {
                        licenseUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains(UploadFileTypeConstant.IMAGE_TYPE_OTHER) && !"".equals(imgUrl)) {
                        otherUrls.append(imgUrl + ",");
                    }
                } else {
                    return successCreated(Integer.valueOf(flag));
                }
            }
        } catch (Exception e) {
            logger.info("上传失败==================");
            return successCreated(ResultCode.IMAGE_WRONG_UPLOAD);
        }
            //判断回显照片
            if (!"".equals(merchantStoreParam.getStoreUrl())) {
                merchantStoreParam.setStoreUrl(otherUrls + merchantStoreParam.getStoreUrl());
            } else {
                merchantStoreParam.setStoreUrl(otherUrls.toString());
            }
            if (!"".equals(merchantStoreParam.getEnvironmentUrl())) {
                merchantStoreParam.setEnvironmentUrl(environmentUrls + merchantStoreParam.getEnvironmentUrl());
            } else {
                merchantStoreParam.setEnvironmentUrl(environmentUrls.toString());
            }
            if (!"".equals(merchantStoreParam.getLogoUrl())) {
                merchantStoreParam.setLogoUrl(storeLogoUrls + merchantStoreParam.getLogoUrl());
            } else {
                merchantStoreParam.setLogoUrl(storeLogoUrls.toString());
            }
            if (!"".equals(merchantStoreParam.getIdcardUrl())) {
                merchantStoreParam.setIdcardUrl(idCardUrls + merchantStoreParam.getIdcardUrl());
            } else {
                merchantStoreParam.setIdcardUrl(idCardUrls.toString());
            }
            if (!"".equals(merchantStoreParam.getLicenseUrl())) {
                merchantStoreParam.setLicenseUrl(licenseUrls + merchantStoreParam.getLicenseUrl());
            } else {
                merchantStoreParam.setLicenseUrl(licenseUrls.toString());
            }
            if (!"".equals(merchantStoreParam.getOtherUrl())) {
                merchantStoreParam.setOtherUrl(otherUrls + merchantStoreParam.getOtherUrl());
            } else {
                merchantStoreParam.setOtherUrl(otherUrls.toString());
            }

            return merchantStoreService.saveMerchantStoreAuditInfo(merchantStoreId, merchantId, merchantStoreParam);

    }

    @ApiOperation(value = "查询门店审核成功和失败审核信息", notes = "查询门店审核成功和失败审核信息 [1012]（章勇）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getMerchantAuditInfo", method = RequestMethod.GET)
    Result<MerchantAuditInfoDTO> getMerchantAuditInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token){
        Long merchantId = UserUtil.getCurrentUserId(getRequest());
        Result<MerchantAuditInfoDTO> result = merchantStoreService.getMerchantAuditInfo(merchantId);
        return result;
    }

}
