package com.lawu.eshop.order.srv.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.lawu.eshop.order.dto.MemberPayOrderInfoDTO;
import com.lawu.eshop.order.dto.MerchantPayOrderListDTO;
import com.lawu.eshop.order.dto.PayOrderDTO;
import com.lawu.eshop.order.dto.PayOrderIdDTO;
import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;
import com.lawu.eshop.order.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.order.param.MerchantPayOrderListParam;
import com.lawu.eshop.order.param.PayOrderListParam;
import com.lawu.eshop.order.param.PayOrderParam;
import com.lawu.eshop.order.srv.bo.PayOrderBO;
import com.lawu.eshop.order.srv.bo.ThirdPayCallBackQueryPayOrderBO;
import com.lawu.eshop.order.srv.converter.PayOrderConverter;
import com.lawu.eshop.order.srv.exception.DataNotExistException;
import com.lawu.eshop.order.srv.exception.IllegalOperationException;
import com.lawu.eshop.order.srv.service.PayOrderService;
import com.lawu.eshop.utils.BeanUtil;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
@RestController
@RequestMapping(value = "payOrder")
public class PayOrderController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(PayOrderController.class);

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
	public Result<PayOrderIdDTO> savePayOrderInfo(@PathVariable("memberId") Long memberId, @RequestBody PayOrderParam param, @RequestParam("param") String numNum) {
		if (memberId == null || param == null) {
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY);
		}
		PayOrderBO orderBO = payOrderService.savePayOrderInfo(memberId, param, numNum);
		if (orderBO == null) {
			return successCreated(ResultCode.SAVE_FAIL);
		}
		PayOrderIdDTO orderIdDTO = new PayOrderIdDTO();
		orderIdDTO.setOrderNum(orderBO.getOrderNum());
		orderIdDTO.setOrderId(orderBO.getId());
		return successCreated(orderIdDTO);
	}

	@RequestMapping(value = "getpayOrderList/{memberId}", method = RequestMethod.POST)
	public Result<Page<PayOrderDTO>> getpayOrderList(@PathVariable("memberId") Long memberId, @RequestBody PayOrderListParam param) {
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

	/**
	 * 删除买单记录
	 * 
	 * @param id
	 *            买单id
	 * @param memberId
	 *            会员id
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月11日
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "delPayOrderInfo/{id}", method = RequestMethod.PUT)
	public Result delPayOrderInfo(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId) {
		try {
			payOrderService.delPayOrderInfo(id, memberId);
		} catch (DataNotExistException e) {
			logger.error(e.getMessage(), e);
			return successCreated(ResultCode.NOT_FOUND_DATA, e.getMessage());
		} catch (IllegalOperationException e) {
			logger.error(e.getMessage(), e);
			return successCreated(ResultCode.ILLEGAL_OPERATION, e.getMessage());
		}
		return successCreated();
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
	public ThirdPayCallBackQueryPayOrderDTO selectThirdPayCallBackQueryPayOrder(@RequestParam String orderId) throws Exception {
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
	 * 
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
	 * 
	 * @param userId
	 *            商家id
	 * @param param
	 *            分页
	 * @return
	 * @author zhangy
	 */
	@RequestMapping(value = "getMerchantPayOrderList", method = RequestMethod.POST)
	public Result<Page<MerchantPayOrderListDTO>> getMerchantPayOrderList(@RequestParam("userId") Long userId, @RequestBody MerchantPayOrderListParam param) {

		Page<PayOrderBO> payOrderBOPage = payOrderService.getMerchantPayOrderList(userId, param);
		Page<MerchantPayOrderListDTO> payOrderListDTOPage = new Page<>();
		payOrderListDTOPage.setCurrentPage(payOrderBOPage.getCurrentPage());
		payOrderListDTOPage.setTotalCount(payOrderBOPage.getTotalCount());
		if (payOrderBOPage.getRecords() == null) {
			payOrderListDTOPage.setRecords(new ArrayList<>());
			return successGet(payOrderListDTOPage);
		}
		List<MerchantPayOrderListDTO> merchantPayOrderListDTOS = PayOrderConverter.coverDTOS(payOrderBOPage.getRecords());
		payOrderListDTOPage.setRecords(merchantPayOrderListDTOS);
		return successGet(payOrderListDTOPage);
	}

	/**
	 * 用户买单详情
	 * 
	 * @param id
	 *            买单id
	 * @param memberId
	 *            会员id
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月11日
	 */
	@RequestMapping(value = "getOrderInfo", method = RequestMethod.GET)
	public Result<MemberPayOrderInfoDTO> getOrderInfo(@RequestParam("id") Long id, @RequestParam("memberId") Long memberId) {
		PayOrderBO payOrderBO = null;
		try {
			payOrderBO = payOrderService.getOrderInfo(id, memberId);
		} catch (DataNotExistException e) {
			return successGet(ResultCode.NOT_FOUND_DATA, e.getMessage());
		} catch (IllegalOperationException e) {
			return successGet(ResultCode.ILLEGAL_OPERATION, e.getMessage());
		}
		return successGet(PayOrderConverter.coverOrderInfoDTO(payOrderBO));
	}
}
