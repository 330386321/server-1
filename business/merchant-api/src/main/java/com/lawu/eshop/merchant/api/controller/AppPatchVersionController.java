package com.lawu.eshop.merchant.api.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.util.HeaderUtil;
import com.lawu.eshop.mall.constants.AppTypeEnum;
import com.lawu.eshop.mall.dto.AppPatchVersionDTO;
import com.lawu.eshop.merchant.api.MerchantApiConfig;
import com.lawu.eshop.merchant.api.service.AppPatchVersionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * @author meishuquan
 * @date 2017/12/12.
 */
@Api(tags = "appPatchVersion")
@RestController
@RequestMapping(value = "appPatchVersion/")
public class AppPatchVersionController extends BaseController {

    @Autowired
    private AppPatchVersionService appPatchVersionService;

    @Autowired
    private MerchantApiConfig merchantApiConfig;

    @ApiOperation(value = "获取APP补丁版本", notes = "获取APP补丁版本。（梅述全）", httpMethod = "GET")
    @RequestMapping(value = "getAppPatchVersion", method = RequestMethod.GET)
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    public Result<AppPatchVersionDTO> getAppPatchVersion() {
        String channel = HeaderUtil.getRequestChannel(getRequest());
        String platform = HeaderUtil.getRequestPlatform(getRequest());
        String appVersion = HeaderUtil.getRequestAppVersion(getRequest());
        if (StringUtils.isEmpty(platform) || StringUtils.isEmpty(appVersion)) {
            return successGet(ResultCode.GET_HEADER_ERROR);
        }

        AppPatchVersionDTO versionDTO = new AppPatchVersionDTO();
        Result<Integer> result = appPatchVersionService.getAppPatchVersion(AppTypeEnum.MERCHANT, Byte.valueOf(platform), appVersion);
        if (result.getModel() == 0) {
            return successGet(versionDTO);
        }

        String suffix = appVersion.substring(appVersion.length() - 1, appVersion.length());
        if (StringUtils.isNumeric(suffix)) {
            appVersion = appVersion + "." + result.getModel();
        } else {
            appVersion = appVersion.substring(0, appVersion.length() - 1) + result.getModel() + "." + suffix;
        }
        String downUrl = merchantApiConfig.getDownloadUrl();
        downUrl = downUrl.replace("{channel}", channel).replace("{version}", appVersion);
        versionDTO.setDownloadUrl(downUrl);
        return successGet(versionDTO);
    }

}
