package com.lawu.eshop.order.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.mall.dto.CommentOrderDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExtendDetailDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExtendQueryDTO;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignParam;
import com.lawu.eshop.order.srv.bo.CommentOrderBO;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExpressBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendDetailBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendQueryBO;
import com.lawu.eshop.order.srv.converter.ShoppingOrderConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderExtendConverter;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;
import com.lawu.eshop.order.srv.strategy.ExpressStrategy;

/**
 * 购物订单
 * 
 * @author Sunny
 * @date 2017/04/06
 */
@RestController
@RequestMapping(value = "shoppingOrder/")
public class ShoppingOrderController extends BaseController {

	@Autowired
	private ShoppingOrderService shoppingOrderService;

	@Autowired
	@Qualifier("kDNiaoExpressStrategy")
	private ExpressStrategy expressStrategy;

	/**
	 * 保存订单
	 * 
	 * @param param
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result<List<Long>> save(@RequestBody List<ShoppingOrderSettlementParam> params) {

		// 参数验证
		if (params == null || params.isEmpty()) {
			successCreated(ResultCode.REQUIRED_PARM_EMPTY);
		}

		List<Long> ids = shoppingOrderService.save(params);

		if (ids == null || params.isEmpty()) {
			return successCreated(ResultCode.SAVE_FAIL);
		}

		return successCreated(ids);
	}

	/**
	 * 获取商品评价状态
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "getOrderCommentStatus/{orderId}", method = RequestMethod.GET)
	public Result<CommentOrderDTO> getOrderCommentStatus(@PathVariable("orderId") Long orderId) {
		// 查询订单商品评价状态
		CommentOrderBO commentOrderBO = shoppingOrderService.getOrderCommentStatusById(orderId);
		CommentOrderDTO commentOrderDTO = ShoppingOrderConverter.coverCommentStatusDTO(commentOrderBO);
		return successGet(commentOrderDTO);
	}

	/**
	 * 根据查询参数分页查询
	 * 
	 * @param memberId
	 *            会员id
	 * @param params
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "page/{memberId}", method = RequestMethod.POST)
	public Result<Page<ShoppingOrderExtendQueryDTO>> selectPageByMemberId(@PathVariable("memberId") Long memberId, @RequestBody ShoppingOrderQueryForeignParam param) {
		Page<ShoppingOrderExtendQueryBO> shoppingOrderExtendQueryBOPage = shoppingOrderService.selectPageByMemberId(memberId, param);

		Page<ShoppingOrderExtendQueryDTO> shoppingOrderExtendQueryDTOPage = new Page<ShoppingOrderExtendQueryDTO>();
		shoppingOrderExtendQueryDTOPage.setCurrentPage(shoppingOrderExtendQueryBOPage.getCurrentPage());
		shoppingOrderExtendQueryDTOPage.setTotalCount(shoppingOrderExtendQueryBOPage.getTotalCount());
		shoppingOrderExtendQueryDTOPage.setRecords(ShoppingOrderExtendConverter.convertShoppingOrderExtendQueryDTOList(shoppingOrderExtendQueryBOPage.getRecords()));

		return successCreated(shoppingOrderExtendQueryDTOPage);
	}

	/**
	 * 根据id查询订单详情
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public Result<ShoppingOrderExtendDetailDTO> get(@PathVariable("id") Long id) {

		if (id == null || id <= 0) {
			return successGet(ResultCode.ID_EMPTY);
		}

		ShoppingOrderExtendDetailBO shoppingOrderExtendDetailBO = shoppingOrderService.get(id);

		if (shoppingOrderExtendDetailBO == null || shoppingOrderExtendDetailBO.getId() == null || shoppingOrderExtendDetailBO.getId() <= 0 || shoppingOrderExtendDetailBO.getItems() == null || shoppingOrderExtendDetailBO.getItems().isEmpty()) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}

		return successGet(ShoppingOrderExtendConverter.convert(shoppingOrderExtendDetailBO));
	}

	/**
	 * 根据id查询订单物流信息
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@RequestMapping(value = "getExpressInfo/{id}", method = RequestMethod.GET)
	public Result<ShoppingOrderExpressDTO> getExpressInfo(@PathVariable("id") Long id) {

		if (id == null || id <= 0) {
			return successGet(ResultCode.ID_EMPTY);
		}

		ShoppingOrderExpressBO shoppingOrderExpressBO = shoppingOrderService.getExpressInfo(id);

		if (shoppingOrderExpressBO == null) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}

		ExpressInquiriesDetailBO expressInquiriesDetailBO = expressStrategy.inquiries(shoppingOrderExpressBO.getExpressCompanyCode(), shoppingOrderExpressBO.getWaybillNum());

		return successGet(ShoppingOrderConverter.covert(shoppingOrderExpressBO, expressInquiriesDetailBO));
	}
	
	/**
	 * 取消购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @param param
	 *            更新参数 
	 * @return
	 */
	@RequestMapping(value = "cancelOrder/{id}", method = RequestMethod.PUT)
	public Result cancelOrder(@PathVariable("id") Long id) {

		if (id == null || id <= 0) {
			return successCreated(ResultCode.ID_EMPTY);
		}
		
		ShoppingOrderBO shoppingOrderBO = shoppingOrderService.getShoppingOrder(id);
		
		if (shoppingOrderBO == null || shoppingOrderBO.getId() == null || shoppingOrderBO.getId() <= 0) {
			return successCreated(ResultCode.RESOURCE_NOT_FOUND);
		}
		
		// 被取消的订单必须要是待支付的状态
		if (!shoppingOrderBO.getOrderStatus().equals(ShoppingOrderStatusEnum.PENDING_PAYMENT.getValue())) {
			return successCreated(ResultCode.STATUS_NOT_CHANGED);
		}
		
		Integer result = shoppingOrderService.cancelOrder(id);

		return successCreated();
	}

}
