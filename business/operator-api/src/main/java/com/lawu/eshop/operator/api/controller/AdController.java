package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.AuditEnum;
import com.lawu.eshop.ad.constants.PutWayEnum;
import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.param.AdFindParam;
import com.lawu.eshop.ad.param.ListAdParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.operator.api.service.AdService;
import com.lawu.eshop.operator.api.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：广告管理
 *
 * @author zhangrc
 * @date 2017/04/5
 */
@Api(tags = "ad")
@RestController
@RequestMapping(value = "ad/")
public class AdController extends BaseController {

    @Autowired
    private AdService adService;

    @Autowired
    private RegionService regionService;

    @ApiOperation(value = "广告列表", notes = "广告列表,[]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectList", method = RequestMethod.POST)
    public Result<Page<AdDTO>> selectListByMerchant(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                    @ModelAttribute @ApiParam(value = "查询信息") AdFindParam adPlatParam) {
        Result<Page<AdDTO>> pageDTOS = adService.selectListByPlatForm(adPlatParam);
        return pageDTOS;
    }

    @ApiOperation(value = "广告列表", notes = "查询广告列表。（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @PageBody
    @RequestMapping(value = "listAd", method = RequestMethod.POST)
    public Result<Page<AdDTO>> listAd(@RequestBody @ApiParam ListAdParam listAdParam) {
        if (listAdParam.getTypeEnum().val == AdTypeEnum.AD_TYPE_PACKET.val) {
            listAdParam.setTypeEnum(null);
        }
        if (listAdParam.getPutWayEnum().val == PutWayEnum.PUT_WAY_COMMON.val) {
            listAdParam.setPutWayEnum(null);
        }
        if (listAdParam.getStatusEnum().val == AdStatusEnum.AD_STATUS_PUTED.val) {
            listAdParam.setStatusEnum(null);
        }
        return adService.listAd(listAdParam);
    }

    @ApiOperation(value = "广告详情", notes = "查询广告详情。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getAd/{id}", method = RequestMethod.GET)
    public Result<AdDTO> getAd(@PathVariable @ApiParam(value = "ID") Long id) {
        Result<AdDTO> result = adService.getAdById(id);
        AdDTO adDTO = result.getModel();
        if (adDTO != null && adDTO.getPutWayEnum().val == PutWayEnum.PUT_WAY_AREAS.val) {
            if (StringUtils.isEmpty(adDTO.getAreas())) {
                adDTO.setAreas("全国");
                return result;
            }
            StringBuffer stringBuffer = new StringBuffer();
            String[] areasArr = adDTO.getAreas().split(",");
            for (String areas : areasArr) {
                Result<String> regionFullName = regionService.getRegionFullName(Integer.valueOf(areas));
                stringBuffer.append(regionFullName.getModel()).append(",");
            }
            adDTO.setAreas(stringBuffer.substring(0, stringBuffer.length() - 1));
        }
        return result;
    }


    @ApiOperation(value = "广告操作下架", notes = "广告操作下架,[5001]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "updateStatus/{id}", method = RequestMethod.PUT)
    public Result updateStatus(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result rs = adService.updateStatus(id);
        return rs;
    }


    @ApiOperation(value = "广告操作删除", notes = "广告操作删除,[]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "remove/{id}", method = RequestMethod.PUT)
    public Result remove(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result rs = adService.remove(id);
        return rs;
    }
    
    @ApiOperation(value = "广告审核通过", notes = "广告审核,[]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "auditVideoPass/{id}", method = RequestMethod.PUT)
    public Result auditVideoPass(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
    	Result rs= adService.auditVideo(id,AuditEnum.AD_AUDIT_PASS);
    	return rs;
    }

    @ApiOperation(value = "广告审核不通过", notes = "广告审核,[]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "auditVideoUnPass/{id}", method = RequestMethod.PUT)
    public Result auditVideoUnPass(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
    	Result rs= adService.auditVideo(id,AuditEnum.AD_AUDIT_PASS);
    	return rs;
    }


}
