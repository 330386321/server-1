package com.lawu.eshop.operator.api.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.common.constants.MessageTypeEnum;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.mall.param.MessageTempParam;
import com.lawu.eshop.operator.api.service.LogService;
import com.lawu.eshop.operator.api.service.MerchantAuditService;
import com.lawu.eshop.operator.api.service.MerchantService;
import com.lawu.eshop.operator.api.service.MessageService;
import com.lawu.eshop.operator.api.service.UserService;
import com.lawu.eshop.operator.constants.LogTitleEnum;
import com.lawu.eshop.operator.constants.ModuleEnum;
import com.lawu.eshop.operator.constants.OperationTypeEnum;
import com.lawu.eshop.operator.dto.UserListDTO;
import com.lawu.eshop.operator.param.LogParam;
import com.lawu.eshop.user.constants.MerchantAuditStatusEnum;
import com.lawu.eshop.user.dto.MerchantSNSDTO;
import com.lawu.eshop.user.dto.MerchantStoreAuditDTO;
import com.lawu.eshop.user.param.ListStoreAuditParam;
import com.lawu.eshop.user.param.MerchantAuditParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import om.lawu.eshop.shiro.util.UserUtil;

/**
 * @author zhangyong
 * @date 2017/3/31.
 */
@Api(tags = "merchantAudit")
@RestController
@RequestMapping(value = "merchantAudit/")
public class MerchantAuditController extends BaseController {

    @Autowired
    private MerchantAuditService merchantAuditService;

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MerchantService merchantService;

    /**
     * 门店审核列表
     *
     * @param auditParam
     * @return
     */
    @ApiOperation(value = "门店列表", notes = "查询门店列表。（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @PageBody
    @RequestMapping(value = "listStoreAudit", method = RequestMethod.POST)
    @RequiresPermissions("storeAudit:list")
    public Result<Page<MerchantStoreAuditDTO>> listStoreAudit(@RequestBody @ApiParam ListStoreAuditParam auditParam) {
        Result<Page<MerchantStoreAuditDTO>> result = merchantAuditService.listStoreAudit(auditParam);
        List<MerchantStoreAuditDTO> list = result.getModel().getRecords();
        if (list != null && !list.isEmpty()) {
            for (MerchantStoreAuditDTO merchantStoreAuditDTO : list) {
                if (merchantStoreAuditDTO.getAuditorId() != null && merchantStoreAuditDTO.getAuditorId() > 0) {
                    Result<UserListDTO> userListDTOResult = userService.findUserById(merchantStoreAuditDTO.getAuditorId());
                    if (isSuccess(userListDTOResult)) {
                        merchantStoreAuditDTO.setAuditorName(userListDTOResult.getModel().getName());
                    }
                }
            }
        }
        return result;
    }

    /**
     * 门店审核详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "门店审核详情", notes = "查询门店审核详情。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequiresPermissions("storeAudit:detail")
    @RequestMapping(value = "getMerchantStoreAudit/{id}", method = RequestMethod.GET)
    public Result<MerchantStoreAuditDTO> getMerchantStoreAudit(@PathVariable @ApiParam(required = true, value = "门店审核ID") Long id) {
        return merchantAuditService.getMerchantStoreAuditById(id);
    }

    /**
     * 门店审核接口
     *
     * @param storeAuditId
     * @param auditParam
     * @return
     */
    @ApiOperation(value = "门店审核信息", notes = "根据门店审核记录ID更新对应信息  [1000]（章勇）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("storeAudit:audit")
    @RequestMapping(value = "updateMerchantAudit", method = RequestMethod.POST)
    public Result updateMerchantAudit(@RequestParam @ApiParam(required = true, value = "门店审核ID") Long storeAuditId,
                                      @ModelAttribute @ApiParam MerchantAuditParam auditParam) {
        Integer auditorId = 0;
        Result<UserListDTO> userResult = userService.getUserByAccount(UserUtil.getCurrentUserAccount());
        if(isSuccess(userResult)){
            auditorId = userResult.getModel().getId();
        }
        auditParam.setAuditorId(auditorId);
        Result result = merchantAuditService.updateMerchantAudit(storeAuditId, auditParam);
        if(!isSuccess(result)){
            return result;
        }

        //发送站内消息
        Result<MerchantStoreAuditDTO> storeAuditDTOResult = merchantAuditService.getMerchantStoreAuditById(storeAuditId);
        Result<MerchantSNSDTO> merchantSNSDTOResult = merchantService.selectMerchantInfo(storeAuditDTOResult.getModel().getMerchantId());

        MessageInfoParam messageInfoParam = new MessageInfoParam();
        MessageTempParam messageTempParam = new MessageTempParam();
        messageTempParam.setStoreName(storeAuditDTOResult.getModel().getName());
        messageInfoParam.setRelateId(storeAuditDTOResult.getModel().getStoreId());
        if (auditParam.getAuditStatusEnum().val == MerchantAuditStatusEnum.MERCHANT_AUDIT_STATUS_CHECKED.val) {
            messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_CHECK_STORE_SUCCESS);
        } else if (auditParam.getAuditStatusEnum().val == MerchantAuditStatusEnum.MERCHANT_AUDIT_STATUS_CHECK_FAILED.val) {
            messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_CHECK_STORE_FAIL);
            messageTempParam.setFailReason(auditParam.getRemark());
        }
        messageInfoParam.setMessageParam(messageTempParam);
        messageService.saveMessage(merchantSNSDTOResult.getModel().getNum(), messageInfoParam);

        //保存操作日志
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", auditParam.getAuditStatusEnum().val);
        jsonObject.put("type", auditParam.getTypeEnum().val);
        jsonObject.put("remark", auditParam.getRemark());
        LogParam logParam = new LogParam();
        logParam.setAccount(UserUtil.getCurrentUserAccount());
        logParam.setTypeEnum(OperationTypeEnum.UPDATE);
        logParam.setModuleEnum(ModuleEnum.STORE);
        logParam.setBusinessId(String.valueOf(storeAuditId));
        if (auditParam.getAuditStatusEnum().val == MerchantAuditStatusEnum.MERCHANT_AUDIT_STATUS_CHECKED.val) {
            logParam.setChangeTitle(LogTitleEnum.STORE_AUDIT_SUCCESS.getName());
        } else {
            logParam.setChangeTitle(LogTitleEnum.STORE_AUDIT_FAIL.getName());
        }
        logParam.setChangeContent(jsonObject.toString());
        logService.saveLog(logParam);
        return result;
    }
}
