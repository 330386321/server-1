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
import com.lawu.eshop.user.dto.MerchantStoreDTO;
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


    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @Authorization
    @ApiOperation(value = "根据商家门店id查询门店信息", notes = "根据商家门店id查询门店信息，成功返回门店信息。（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findMerchantStoreInfo/{merchantStoreId}", method = RequestMethod.GET)
    public Result<MerchantStoreDTO> selectMerchantStore(@PathVariable("merchantStoreId") @ApiParam(required = true, value = "门店ID") Long merchantStoreId) {
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
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                String fileName = file.getName();
                Map<String, String> map = UploadFileUtil.uploadOnePic(request, file, FileDirConstant.DIR_STORE);
                String flag = map.get("resultFlag");
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
            return merchantStoreService.saveMerchantStoreInfo(merchantId, merchantStoreParam);
        }

        return successCreated(ResultCode.FAIL);
    }

    @Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "修改门店信息TO审核", notes = "错误信息 [1012]（章勇）", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "saveMerchantStoreAuditInfo", method = RequestMethod.PUT)
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
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                String fileName = file.getName();
                Map<String, String> map = UploadFileUtil.uploadOnePic(request, file, FileDirConstant.DIR_STORE);
                String flag = map.get("resultFlag");
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

        return successCreated(ResultCode.FAIL);
    }

}
