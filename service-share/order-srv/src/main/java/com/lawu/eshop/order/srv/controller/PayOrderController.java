package com.lawu.eshop.order.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.order.dto.*;
import com.lawu.eshop.order.param.MerchantPayOrderListParam;
import com.lawu.eshop.order.param.OperatorPayOrderParam;
import com.lawu.eshop.order.param.PayOrderListParam;
import com.lawu.eshop.order.param.PayOrderParam;
import com.lawu.eshop.order.srv.bo.PayOrderBO;
import com.lawu.eshop.order.srv.bo.ThirdPayCallBackQueryPayOrderBO;
import com.lawu.eshop.order.srv.converter.PayOrderConverter;
import com.lawu.eshop.order.srv.service.PayOrderService;
import com.lawu.eshop.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public Result<PayOrderIdDTO> savePayOrderInfo(@PathVariable("memberId") Long memberId, @RequestBody PayOrderParam param,@RequestParam("param") String numNum) {
        if (memberId == null || param == null) {
            return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
        }
        PayOrderBO orderBO = payOrderService.savePayOrderInfo(memberId, param,numNum);
        if (orderBO == null) {
            return successCreated(ResultCode.SAVE_FAIL);
        }
        PayOrderIdDTO orderIdDTO = new PayOrderIdDTO();
        orderIdDTO.setOrderNum(orderBO.getOrderNum());
        orderIdDTO.setOrderId(orderBO.getId());
        return successCreated(orderIdDTO);
    }

	@RequestMapping(value = "getpayOrderList/{memberId}", method = RequestMethod.POST)
	public Result<Page<PayOrderDTO>> getpayOrderList(@PathVariable("memberId") Long memberId,
			@RequestBody PayOrderListParam param) {
		if (memberId == null) {
			return successGet(ResultCode.REQUIRED_PARM_EMPTY);
		}
		Page<PayOrderBO> boPage = payOrderService.getpayOrderList(memberId, param);
		if (boPage == null || boPage.getRecords().isEmpty()) {
			return successGet(new Page<>());
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
	public Result delPayOrderInfo(@PathVariable("id") Long id) {
		if (id == null) {
			return successDelete(ResultCode.REQUIRED_PARM_EMPTY);
		}
		payOrderService.delPayOrderInfo(id);
		return successDelete();
	}

	/**
	 * 第三方支付时获取买单的实际总金额，用于调用第三方支付平台
	 * 
	 * @param orderId
	 * @return
	 * @author Yangqh
	 * @throws Exception
	 */
	@RequestMapping(value = "selectThirdPayCallBackQueryPayOrder", method = RequestMethod.GET)
	public ThirdPayCallBackQueryPayOrderDTO selectThirdPayCallBackQueryPayOrder(@RequestParam String orderId)
			throws Exception {
		if (orderId == null || "".equals(orderId.trim())) {
			return null;
		}
		ThirdPayCallBackQueryPayOrderBO bo = payOrderService.selectThirdPayCallBackPayOrder(orderId);
		ThirdPayCallBackQueryPayOrderDTO dto = new ThirdPayCallBackQueryPayOrderDTO();
		BeanUtil.copyProperties(bo, dto);
		return dto;
	}

	/**
	 * 查询未计算提成的买单
	 * 
	 * @return
	 * @throws Exception
	 * @author yangqh
	 */
	@RequestMapping(value = "selectNotCommissionOrder", method = RequestMethod.GET)
	public Result<List<ShoppingOrderCommissionDTO>> selectNotCommissionOrder() {
		List<ShoppingOrderCommissionDTO> dtos = payOrderService.selectNotCommissionOrder();
		return successCreated(dtos);
	}

	/**
	 * 修改为已计算提成
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updatePayOrderCommissionStatus", method = RequestMethod.PUT)
	public Result updatePayOrderCommissionStatus(@RequestParam("ids") List<Long> ids) {

		int resultCode = payOrderService.updateCommissionStatus(ids);
		if (resultCode != ResultCode.SUCCESS) {
			return successCreated(resultCode);
		}
		return successCreated();
	}

	/**
	 * 商家端买单列表
	 * @param userId 商家id
	 * @param param 分页
	 * @return
	 * @author zhangy
	 */
	@RequestMapping(value = "getMerchantPayOrderList", method = RequestMethod.POST)
	public Result<Page<MerchantPayOrderListDTO>> getMerchantPayOrderList(@RequestParam("userId") Long userId, @RequestBody MerchantPayOrderListParam param) {

		Page<PayOrderBO> payOrderBOPage = payOrderService.getMerchantPayOrderList(userId,param);
		Page<MerchantPayOrderListDTO> payOrderListDTOPage = new Page<>();
		payOrderListDTOPage.setCurrentPage(payOrderBOPage.getCurrentPage());
		payOrderListDTOPage.setTotalCount(payOrderBOPage.getTotalCount());
		if(payOrderBOPage.getRecords() == null){
			payOrderListDTOPage.setRecords(new ArrayList<>());
			return successGet(payOrderListDTOPage);
		}
		List<MerchantPayOrderListDTO> merchantPayOrderListDTOS = PayOrderConverter.coverDTOS(payOrderBOPage.getRecords());
		payOrderListDTOPage.setRecords(merchantPayOrderListDTOS);
		return successGet(payOrderListDTOPage);
	}

	/**
	 * 用户买单详情
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "getOrderInfo")
	public MemberPayOrderInfoDTO getOrderInfo(@RequestParam("id") Long id) {
		PayOrderBO payOrderBO = payOrderService.getOrderInfo(id);
		if (payOrderBO == null) {
			return null;
		}
		MemberPayOrderInfoDTO memberPayOrderInfoDTO = PayOrderConverter.coverOrderInfoDTO(payOrderBO);
		return memberPayOrderInfoDTO;
	}

	/**
	 * 运营平台查询买单列表
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "getOperatorPayOrderList", method = RequestMethod.POST)
	public Result<Page<OperatorPayOrderListDTO>> getOperatorPayOrderList(@RequestBody OperatorPayOrderParam param) {
		if (param.getCurrentPage() < 1) {
			return successGet(ResultCode.REQUIRED_PARM_EMPTY);
		}
		Page<PayOrderBO> payOrderBOPage = payOrderService.getOperatorPayOrderList(param);
		Page<OperatorPayOrderListDTO> page = new Page<>();
		page.setCurrentPage(payOrderBOPage.getCurrentPage());
		page.setTotalCount(payOrderBOPage.getTotalCount());
		page.setRecords(PayOrderConverter.coverOperatorPayOrderListDTOS(
				payOrderBOPage.getRecords()));
		return successGet(page);
	}
}
