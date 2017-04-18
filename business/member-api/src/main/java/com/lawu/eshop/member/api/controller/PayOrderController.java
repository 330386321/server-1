package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.constants.MerchantFavoredTypeEnum;
import com.lawu.eshop.mall.dto.MerchantFavoredDTO;
import com.lawu.eshop.member.api.service.MerchantFavoredService;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.member.api.service.PayOrderService;
import com.lawu.eshop.order.dto.PayOrderDTO;
import com.lawu.eshop.order.dto.PayOrderIdDTO;
import com.lawu.eshop.order.dto.PayOrderInfoDTO;
import com.lawu.eshop.order.param.PayOrderListParam;
import com.lawu.eshop.order.param.PayOrderParam;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
@Api(tags = "payOrder")
@RestController
@RequestMapping(value = "payOrder/")
public class PayOrderController extends BaseController {

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private MerchantStoreService merchantStoreService;

    @Autowired
    private MerchantFavoredService merchantFavoredService;

    @ApiOperation(value = "新增买单记录", notes = "新增买单记录  [1004,1005,1000]", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    // @Authorization
    @RequestMapping(value = "savePayOrderInfo", method = RequestMethod.POST)
    public Result<PayOrderIdDTO> savePayOrderInfo(@ModelAttribute PayOrderParam param, @RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        Long memberId = UserUtil.getCurrentUserId(getRequest());
        //查询优惠信息记录
        double realFavoredAmount = 0;//实际优惠金额
        double canFavoredAmount = param.getTotalAmount()-param.getNotFavoredAmount();//参与优惠金额
        if(param.getMerchantFavoredId() !=null && param.getMerchantFavoredId() >0){
            Result<MerchantFavoredDTO> favoredDTOResult = merchantFavoredService.findFavoredById(param.getMerchantFavoredId());
            if(favoredDTOResult.getModel() !=null){
                if(favoredDTOResult.getModel().getTypeEnum().val == MerchantFavoredTypeEnum.TYPE_FULL.val){
                    //每满xxx减xxx
                    realFavoredAmount = (canFavoredAmount/(favoredDTOResult.getModel().getReachAmount().doubleValue()))*favoredDTOResult.getModel().getFavoredAmount().doubleValue();
                }else if(favoredDTOResult.getModel().getTypeEnum().val == MerchantFavoredTypeEnum.TYPE_FULL_REDUCE.val){
                    //满xxx减xxx
                    if((canFavoredAmount-favoredDTOResult.getModel().getReachAmount().doubleValue()) >=0){
                        realFavoredAmount = favoredDTOResult.getModel().getFavoredAmount().doubleValue();
                    }
                }else if(favoredDTOResult.getModel().getTypeEnum().val == MerchantFavoredTypeEnum.TYPE_DISCOUNT.val){
                    realFavoredAmount = canFavoredAmount*((10-favoredDTOResult.getModel().getDiscountRate().doubleValue())/10);
                }
                BigDecimal realFavoredAmount2 =   new   BigDecimal(realFavoredAmount);
                double rfa2  =  realFavoredAmount2.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                if(rfa2 != param.getFavoredAmount()){
                    return successCreated(ResultCode.PAY_ORDER_FAVORED_AMOUNT_UNEQUAL);
                }
            }
        }
        Result<PayOrderIdDTO> result = payOrderService.savePayOrderInfo(memberId, param);
        return result;
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
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
                payOrderInfoDTO.setImgUrl(storeInfo.getStoreUrl());
            }
            payOrderInfoDTOS.add(payOrderInfoDTO);
        }
        Page<PayOrderInfoDTO> list = new Page<>();
        list.setRecords(payOrderInfoDTOS);
        list.setTotalCount(pageResult.getModel().getTotalCount());
        list.setCurrentPage(pageResult.getModel().getCurrentPage());
        return successGet(list);
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
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
