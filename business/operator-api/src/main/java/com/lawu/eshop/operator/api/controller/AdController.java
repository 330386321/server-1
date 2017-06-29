package com.lawu.eshop.operator.api.controller;

import com.alibaba.fastjson.JSONObject;
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
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.mall.param.MessageTempParam;
import com.lawu.eshop.operator.api.service.*;
import com.lawu.eshop.operator.constants.LogTitleEnum;
import com.lawu.eshop.operator.constants.ModuleEnum;
import com.lawu.eshop.operator.constants.OperationTypeEnum;
import com.lawu.eshop.operator.param.LogParam;
import com.lawu.eshop.user.dto.MerchantSNSDTO;
import com.lawu.eshop.user.dto.MerchantViewDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import om.lawu.eshop.shiro.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    @Autowired
    private LogService logService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MerchantService merchantService;

    @ApiOperation(value = "广告列表", notes = "广告列表,[]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectList", method = RequestMethod.POST)
    public Result<Page<AdDTO>> selectListByMerchant(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                    @ModelAttribute @ApiParam(value = "查询信息") AdFindParam adPlatParam) {
        return adService.selectListByPlatForm(adPlatParam);
    }

    @ApiOperation(value = "广告列表", notes = "查询广告列表。（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @PageBody
    @RequiresPermissions("adAudit:list")
    @RequestMapping(value = "listAd", method = RequestMethod.POST)
    public Result<Page<AdDTO>> listAd(@RequestBody @ApiParam ListAdParam listAdParam) {
        if (listAdParam.getTypeEnum().val.byteValue() == AdTypeEnum.AD_TYPE_PACKET.val) {
            listAdParam.setTypeEnum(null);
        }
        if (listAdParam.getPutWayEnum().val.byteValue() == PutWayEnum.PUT_WAY_COMMON.val) {
            listAdParam.setPutWayEnum(null);
        }
        if (listAdParam.getStatusEnum().val.byteValue() == AdStatusEnum.AD_STATUS_PUTED.val) {
            listAdParam.setStatusEnum(null);
        }
        Result<Page<AdDTO>> result = adService.listAd(listAdParam);
        if(result != null && !result.getModel().getRecords().isEmpty()){
            for(AdDTO adDTO : result.getModel().getRecords()){
                Result<MerchantViewDTO> merchantViewDTOResult = merchantService.getMerchantView(adDTO.getMerchantId());
                if(isSuccess(merchantViewDTOResult)){
                    adDTO.setName(merchantViewDTOResult.getModel().getStoreName());
                    adDTO.setAccount(merchantViewDTOResult.getModel().getAccount());
                }
            }
        }
        return result;
    }

    @ApiOperation(value = "广告详情", notes = "查询广告详情。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("adAudit:detail")
    @RequestMapping(value = "getAd/{id}", method = RequestMethod.GET)
    public Result<AdDTO> getAd(@PathVariable @ApiParam(value = "ID") Long id) {
        Result<AdDTO> result = adService.getAdById(id);
        AdDTO adDTO = result.getModel();
        if (adDTO != null && adDTO.getPutWayEnum().val == PutWayEnum.PUT_WAY_AREAS.val) {
            if (StringUtils.isEmpty(adDTO.getAreas())) {
                adDTO.setAreas("全国");
                return result;
            }
            StringBuilder stringBuilder = new StringBuilder();
            String[] areasArr = adDTO.getAreas().split(",");
            for (String areas : areasArr) {
                Result<String> regionFullName = regionService.getRegionFullName(Integer.valueOf(areas));
                stringBuilder.append(regionFullName.getModel()).append(",");
            }
            adDTO.setAreas(stringBuilder.substring(0, stringBuilder.length() - 1));
        }
        return result;
    }


    @ApiOperation(value = "广告操作下架", notes = "广告操作下架,[5001]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("adAudit:soldOut")
    @RequestMapping(value = "adDown/{id}", method = RequestMethod.PUT)
    public Result adDown(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result rs = adService.operatorUpdateAdStatus(id, AdStatusEnum.AD_STATUS_OUT);
        if(!isSuccess(rs)){
            return rs;
        }

        //发送站内消息
        Result<AdDTO> adDTOResult = adService.getAdById(id);
        Result<MerchantSNSDTO> merchantSNSDTOResult = merchantService.selectMerchantInfo(adDTOResult.getModel().getMerchantId());

        MessageInfoParam messageInfoParam = new MessageInfoParam();
        MessageTempParam messageTempParam = new MessageTempParam();
        messageTempParam.setAdName(adDTOResult.getModel().getTitle());
        messageTempParam.setAdTypeName(adDTOResult.getModel().getTypeEnum().getName());
        messageInfoParam.setRelateId(id);
        messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_AD_FORCE_DOWN);
        messageInfoParam.setMessageParam(messageTempParam);
        messageService.saveMessage(merchantSNSDTOResult.getModel().getNum(), messageInfoParam);

        //保存操作日志
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", AdStatusEnum.AD_STATUS_OUT.val);
        LogParam logParam = new LogParam();
        logParam.setAccount(UserUtil.getCurrentUserAccount());
        logParam.setTypeEnum(OperationTypeEnum.UPDATE);
        logParam.setModuleEnum(ModuleEnum.AD);
        logParam.setBusinessId(String.valueOf(id));
        logParam.setChangeTitle(LogTitleEnum.AD_DOWN.getName());
        logParam.setChangeContent(jsonObject.toString());
        logService.saveLog(logParam);
        return rs;
    }


    @ApiOperation(value = "广告操作删除", notes = "广告操作删除,[]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("adAudit:del")
    @RequestMapping(value = "adDelete/{id}", method = RequestMethod.PUT)
    public Result adDelete(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result rs = adService.operatorUpdateAdStatus(id, AdStatusEnum.AD_STATUS_DELETE);
        if(!isSuccess(rs)){
            return rs;
        }

        //保存操作日志
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", AdStatusEnum.AD_STATUS_DELETE.val);
        LogParam logParam = new LogParam();
        logParam.setAccount(UserUtil.getCurrentUserAccount());
        logParam.setTypeEnum(OperationTypeEnum.DELETE);
        logParam.setModuleEnum(ModuleEnum.AD);
        logParam.setBusinessId(String.valueOf(id));
        logParam.setChangeTitle(LogTitleEnum.AD_DELETE.getName());
        logParam.setChangeContent(jsonObject.toString());
        logService.saveLog(logParam);
        return rs;
    }

    @ApiOperation(value = "广告审核通过", notes = "广告审核,[]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("adAudit:pass")
    @RequestMapping(value = "auditVideoPass/{id}", method = RequestMethod.PUT)
    public Result auditVideoPass(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result rs = adService.auditVideo(id, AuditEnum.AD_AUDIT_PASS);
        if(!isSuccess(rs)){
            return rs;
        }

        //发送站内消息
        Result<AdDTO> adDTOResult = adService.getAdById(id);
        Result<MerchantSNSDTO> merchantSNSDTOResult = merchantService.selectMerchantInfo(adDTOResult.getModel().getMerchantId());

        MessageInfoParam messageInfoParam = new MessageInfoParam();
        MessageTempParam messageTempParam = new MessageTempParam();
        messageTempParam.setAdTypeName(AdTypeEnum.AD_TYPE_VIDEO.getName());
        messageTempParam.setAdName(adDTOResult.getModel().getTitle());
        messageInfoParam.setRelateId(id);
        messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_CHECK_AD_SUCCESS);
        messageInfoParam.setMessageParam(messageTempParam);
        messageService.saveMessage(merchantSNSDTOResult.getModel().getNum(), messageInfoParam);

        //保存操作日志
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", AdStatusEnum.AD_STATUS_ADD.val);
        LogParam logParam = new LogParam();
        logParam.setAccount(UserUtil.getCurrentUserAccount());
        logParam.setTypeEnum(OperationTypeEnum.UPDATE);
        logParam.setModuleEnum(ModuleEnum.AD);
        logParam.setBusinessId(String.valueOf(id));
        logParam.setChangeTitle(LogTitleEnum.AD_VIDEO_AUDIT_SUCCESS.getName());
        logParam.setChangeContent(jsonObject.toString());
        logService.saveLog(logParam);
        return rs;
    }

    @ApiOperation(value = "广告审核不通过", notes = "广告审核,[]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("adAudit:unpass")
    @RequestMapping(value = "auditVideoUnPass/{id}", method = RequestMethod.PUT)
    public Result auditVideoUnPass(@PathVariable @ApiParam(required = true, value = "广告id") Long id) {
        Result rs = adService.auditVideo(id, AuditEnum.AD_AUDIT_UN_PASS);
        if(!isSuccess(rs)){
            return rs;
        }

        //发送站内消息
        Result<AdDTO> adDTOResult = adService.getAdById(id);
        Result<MerchantSNSDTO> merchantSNSDTOResult = merchantService.selectMerchantInfo(adDTOResult.getModel().getMerchantId());

        MessageInfoParam messageInfoParam = new MessageInfoParam();
        MessageTempParam messageTempParam = new MessageTempParam();
        messageTempParam.setAdTypeName(AdTypeEnum.AD_TYPE_VIDEO.getName());
        messageTempParam.setAdName(adDTOResult.getModel().getTitle());
        messageInfoParam.setRelateId(id);
        messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_CHECK_AD_FAIL);
        messageInfoParam.setMessageParam(messageTempParam);
        messageService.saveMessage(merchantSNSDTOResult.getModel().getNum(), messageInfoParam);

        //保存操作日志
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", AdStatusEnum.AD_STATUS_AUDIT_FAIL.val);
        LogParam logParam = new LogParam();
        logParam.setAccount(UserUtil.getCurrentUserAccount());
        logParam.setTypeEnum(OperationTypeEnum.UPDATE);
        logParam.setModuleEnum(ModuleEnum.AD);
        logParam.setBusinessId(String.valueOf(id));
        logParam.setChangeTitle(LogTitleEnum.AD_VIDEO_AUDIT_FAIL.getName());
        logParam.setChangeContent(jsonObject.toString());
        logService.saveLog(logParam);
        return rs;
    }


}
