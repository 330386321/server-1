package com.lawu.eshop.merchant.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import util.UploadFileUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
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
        StringBuffer idCardUrls = new StringBuffer();        //身份证照
        StringBuffer storeUrls = new StringBuffer();        //门店照
        StringBuffer environmentUrls = new StringBuffer();        //店内环境照
        StringBuffer storeLogoUrls = new StringBuffer();    //店铺logo
        StringBuffer licenseUrls = new StringBuffer();        //营业执照
        StringBuffer otherUrls = new StringBuffer();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                String fileName = file.getName();
                Map<String, String> map = UploadFileUtil.uploadOnePic(request, file, FileDirConstant.DIR_STORE);
                String flag = map.get("resultFlag");
                if ("0".equals(flag)) {
                    //有图片上传成功返回,拼接图片url
                    String imgUrl = map.get("imgUrl");
                    if (fileName.contains("store") && !"".equals(imgUrl)) {
                        storeUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains("environment") && !"".equals(imgUrl)) {
                        environmentUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains("logo") && !"".equals(imgUrl)) {
                        storeLogoUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains("idcard") && !"".equals(imgUrl)) {
                        idCardUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains("licence") && !"".equals(imgUrl)) {
                        licenseUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains("other") && !"".equals(imgUrl)) {
                        otherUrls.append(imgUrl + ",");
                    }
                } else {
                    return failCreated(Integer.valueOf(flag));
                }
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

            merchantStoreParam.setStoreUrl(storeUrls.toString());
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
        StringBuffer idCardUrls = new StringBuffer();        //身份证照
        StringBuffer storeUrls = new StringBuffer();        //门店照
        StringBuffer environmentUrls = new StringBuffer();        //店内环境照
        StringBuffer storeLogoUrls = new StringBuffer();    //店铺logo
        StringBuffer licenseUrls = new StringBuffer();        //营业执照
        StringBuffer otherUrls = new StringBuffer();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                String fileName = file.getName();
                Map<String, String> map = UploadFileUtil.uploadOnePic(request, file, FileDirConstant.DIR_STORE);
                String flag = map.get("resultFlag");
                if ("0".equals(flag)) {
                    //有图片上传成功返回,拼接图片url
                    String imgUrl = map.get("imgUrl");
                    if (fileName.contains("store") && !"".equals(imgUrl)) {
                        storeUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains("environment") && !"".equals(imgUrl)) {
                        environmentUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains("logo") && !"".equals(imgUrl)) {
                        storeLogoUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains("idcard") && !"".equals(imgUrl)) {
                        idCardUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains("licence") && !"".equals(imgUrl)) {
                        licenseUrls.append(imgUrl + ",");
                    }
                    if (fileName.contains("other") && !"".equals(imgUrl)) {
                        otherUrls.append(imgUrl + ",");
                    }

                } else {
                    return failCreated(Integer.valueOf(flag));
                }
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
            return merchantStoreService.updateMerchantStoreInfo(merchantId, merchantStoreParam, storeType, certifType);
        }

        return failCreated(ResultCode.FAIL);
    }

}
