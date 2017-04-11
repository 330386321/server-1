package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.dto.PayOrderDTO;
import com.lawu.eshop.mall.dto.PayOrderInfoDTO;
import com.lawu.eshop.mall.param.PayOrderListParam;
import com.lawu.eshop.mall.param.PayOrderParam;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.member.api.service.PayOrderService;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
@Api(tags = "payOrder")
@RestController
@RequestMapping(value = "/")
public class PayOrderController extends BaseController {

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private MerchantStoreService merchantStoreService;

    @ApiOperation(value = "新增买单记录", notes = "新增买单记录  [1004,1005,1000]", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    // @Authorization
    @RequestMapping(value = "savePayOrderInfo", method = RequestMethod.POST)
    public Result savePayOrderInfo(@ModelAttribute PayOrderParam param, @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        Long memberId = UserUtil.getCurrentUserId(getRequest());
        Result result = payOrderService.savePayOrderInfo(memberId, param);
        return result;
    }

    @ApiOperation(value = "买单记录列表", notes = "买单记录列表  [1004,1002,1000]", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getpayOrderList", method = RequestMethod.POST)
    public Result<Page<PayOrderInfoDTO>> getpayOrderList(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute PayOrderListParam param) {

        Long memberId = UserUtil.getCurrentUserId(getRequest());
        Result<Page<PayOrderDTO>> pageResult = payOrderService.getpayOrderList(memberId, param);
        if (pageResult == null || pageResult.getModel() == null) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        List<PayOrderInfoDTO> payOrderInfoDTOS = new ArrayList<>();
        List<PayOrderDTO> payOrderDTOS = pageResult.getModel().getRecords();
        for (PayOrderDTO payOrderDTO : payOrderDTOS) {
            PayOrderInfoDTO payOrderInfoDTO = new PayOrderInfoDTO();
            payOrderInfoDTO.setEvaluationEnum(payOrderDTO.getEvaluationEnum());
            payOrderInfoDTO.setActualAmount(payOrderDTO.getActualAmount());
            payOrderInfoDTO.setFavoredAmount(payOrderDTO.getFavoredAmount());
            payOrderInfoDTO.setGmtCreate(payOrderDTO.getGmtCreate());
            payOrderInfoDTO.setId(payOrderDTO.getId());
            payOrderInfoDTO.setTotalAmount(payOrderDTO.getTotalAmount());
            //查询相关商家Id
            MerchantStoreDTO storeInfo = merchantStoreService.findStoreNameAndImgByMerchantId(payOrderDTO.getMerchantId());
            if (storeInfo != null) {
                payOrderInfoDTO.setName(storeInfo.getName());
                payOrderInfoDTO.setImgUrl(storeInfo.getPath());
            }
            payOrderInfoDTOS.add(payOrderInfoDTO);
        }
        Page<PayOrderInfoDTO> list = new Page<>();
        list.setRecords(payOrderInfoDTOS);
        list.setTotalCount(pageResult.getModel().getTotalCount());
        list.setCurrentPage(pageResult.getModel().getCurrentPage());
        return successGet(list);
    }

    @ApiOperation(value = "删除买单记录", notes = "删除买单记录  [1004,1000]", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @Authorization
    @RequestMapping(value = "delPayOrderInfo/{id}", method = RequestMethod.DELETE)
    public Result delPayOrderInfo(@PathVariable("id") Long id, @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        if (id == null) {
            return successDelete(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Result result = payOrderService.delPayOrderInfo(id);
        return successDelete(result);
    }
}
