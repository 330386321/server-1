package com.lawu.eshop.order.srv.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.order.dto.PayOrderDTO;
import com.lawu.eshop.order.dto.PayOrderIdDTO;
import com.lawu.eshop.order.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.order.param.PayOrderListParam;
import com.lawu.eshop.order.param.PayOrderParam;
import com.lawu.eshop.order.srv.bo.PayOrderBO;
import com.lawu.eshop.order.srv.bo.ThirdPayCallBackQueryPayOrderBO;
import com.lawu.eshop.order.srv.converter.PayOrderConverter;
import com.lawu.eshop.order.srv.service.PayOrderService;
import com.lawu.eshop.utils.BeanUtil;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
@RestController
@RequestMapping(value = "payOrder")
public class PayOrderController extends BaseController {
    @Autowired
    private PayOrderService payOrderService;

    /**
     * 新增买单记录
     *
     * @param memberId
     * @param param
     * @return
     */
    @RequestMapping(value = "savePayOrderInfo/{memberId}", method = RequestMethod.POST)
    public Result<PayOrderIdDTO> savePayOrderInfo(@PathVariable("memberId") Long memberId, @RequestBody PayOrderParam param) {
        if (memberId == null || param == null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Long id = payOrderService.savePayOrderInfo(memberId, param);
        if (id == null || id < 0) {
            return successCreated(ResultCode.SAVE_FAIL);
        }
        PayOrderIdDTO orderIdDTO = new PayOrderIdDTO();
        orderIdDTO.setId(id);
        return successCreated(orderIdDTO);
    }

    @RequestMapping(value = "getpayOrderList/{memberId}", method = RequestMethod.POST)
    public Result<Page<PayOrderDTO>> getpayOrderList(@PathVariable("memberId") Long memberId, @RequestBody PayOrderListParam param) {
        if (memberId == null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Page<PayOrderBO> boPage = payOrderService.getpayOrderList(memberId, param);
        if (boPage == null || boPage.getRecords().isEmpty()) {
            return successGet(ResultCode.RESOURCE_NOT_FOUND);
        }
        List<PayOrderDTO> payOrderDTOS = new ArrayList<>();
        Page<PayOrderDTO> payOrderDTOPage = new Page<>();
        for (PayOrderBO payOrderBO : boPage.getRecords()) {
            PayOrderDTO payOrderDTO = PayOrderConverter.coverDTO(payOrderBO);
            payOrderDTOS.add(payOrderDTO);
        }
        payOrderDTOPage.setRecords(payOrderDTOS);
        payOrderDTOPage.setTotalCount(boPage.getTotalCount());
        payOrderDTOPage.setCurrentPage(boPage.getCurrentPage());
        return successGet(payOrderDTOPage);
    }

    @SuppressWarnings({ "rawtypes", "deprecation" })
	@RequestMapping(value = "delPayOrderInfo/{id}", method = RequestMethod.DELETE)
    public Result delPayOrderInfo(@PathVariable("id") Long id){
        if(id == null){
            return successDelete(ResultCode.REQUIRED_PARM_EMPTY);
        }
        payOrderService.delPayOrderInfo(id);
        return successDelete();
    }

    /** 第三方支付时获取买单的实际总金额，用于调用第三方支付平台
	 * @param orderIds
	 * @return
	 * @author Yangqh
     * @throws Exception 
	 */
	@RequestMapping(value = "selectThirdPayCallBackQueryPayOrder", method = RequestMethod.GET)
	public ThirdPayCallBackQueryPayOrderDTO selectThirdPayCallBackQueryPayOrder(@RequestParam String orderId) throws Exception {
		if(orderId == null || "".equals(orderId.trim())){
			return null;
		}
		ThirdPayCallBackQueryPayOrderBO bo = payOrderService.selectThirdPayCallBackPayOrder(orderId);
		ThirdPayCallBackQueryPayOrderDTO dto = new ThirdPayCallBackQueryPayOrderDTO();
		BeanUtil.copyProperties(bo, dto);
		return dto;
	}
}
