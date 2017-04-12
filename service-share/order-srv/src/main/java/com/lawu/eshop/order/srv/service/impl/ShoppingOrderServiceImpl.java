package com.lawu.eshop.order.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.constants.ShoppingOrderItemRefundStatusEnum;
import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.mall.constants.ShoppingOrderStatusToMemberEnum;
import com.lawu.eshop.mall.constants.ShoppingRefundTypeEnum;
import com.lawu.eshop.mall.param.ShoppingOrderLogisticsInformationParam;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementItemParam;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderRequestRefundForeignParam;
import com.lawu.eshop.order.srv.bo.CommentOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExpressBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendDetailBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendQueryBO;
import com.lawu.eshop.order.srv.converter.ShoppingOrderConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderExtendConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemConverter;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDOExample;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDOExample.Criteria;
import com.lawu.eshop.order.srv.mapper.ShoppingCartDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingRefundDetailDOMapper;
import com.lawu.eshop.order.srv.mapper.extend.ShoppingOrderExtendDOMapper;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;

@Service
public class ShoppingOrderServiceImpl implements ShoppingOrderService {
	
	@Autowired
	private ShoppingCartDOMapper shoppingCartDOMapper;
	
	@Autowired
	private ShoppingOrderExtendDOMapper shoppingOrderDOExtendMapper;
	
	@Autowired
	private ShoppingOrderDOMapper shoppingOrderDOMapper;
	
	@Autowired
	private ShoppingOrderItemDOMapper shoppingOrderItemDOMapper;
	
	@Autowired
	private ShoppingRefundDetailDOMapper shoppingRefundDetailDOMapper;
	
    //@Autowired
    //private TransactionMainService transactionMainService;
	
	/**
	 * 
	 * @param params 多个订单参数
	 * @return 返回保存的订单id
	 */
	@Transactional
	@Override
	public List<Long> save(List<ShoppingOrderSettlementParam> params) {
		
		List<Long> rtn = new ArrayList<Long>();
		
		// 插入订单
		for (ShoppingOrderSettlementParam shoppingOrderSettlementParam : params) {
			ShoppingOrderDO shoppingOrderDO =  ShoppingOrderConverter.convert(shoppingOrderSettlementParam);
			shoppingOrderDOMapper.insertSelective(shoppingOrderDO);
			Long id = shoppingOrderDO.getId();
			
			// 把订单id放入list返回
			rtn.add(id);
			
			//插入订单项
			for (ShoppingOrderSettlementItemParam item : shoppingOrderSettlementParam.getItems()) {
				ShoppingOrderItemDO shoppingOrderItemDO = ShoppingOrderItemConverter.convert(id, item);
				shoppingOrderItemDOMapper.insertSelective(shoppingOrderItemDO);
				
				//删除购物车记录,保证在同一个事务中进行
				shoppingCartDOMapper.deleteByPrimaryKey(item.getShoppingCartId());
			}
			
			// TODO 事务补偿预留(减掉库存)
			//transactionMainService.sendNotice(id);
		}
		
		return rtn;
	}

	@Override
	public CommentOrderBO getOrderCommentStatusById(Long orderId) {
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(orderId);
		CommentOrderBO commentOrderBO = ShoppingOrderConverter.coverCommentStatusBO(shoppingOrderDO);
		return commentOrderBO;
	}
	
	@Override
	public Page<ShoppingOrderExtendQueryBO> selectPageByMemberId(Long memberId, ShoppingOrderQueryForeignParam param) {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		
		// 组装Criteria
		Criteria baseCriteria = shoppingOrderExtendDOExample.createCriteria();
		
		// 用户如果删除则不显示
		baseCriteria.andStatusEqualTo((byte)0x01);
		
		if (memberId != null) {
			baseCriteria.andMemberIdEqualTo(memberId);
		}
		
		if (param.getOrderStatus() != null) {
			baseCriteria.andOrderStatusEqualTo(param.getOrderStatus().getValue());
			
			// 查找待评价的订单
			if (param.getOrderStatus().equals(ShoppingOrderStatusToMemberEnum.BE_EVALUATED)) {
				baseCriteria.andIsEvaluationEqualTo(false);
			}
		}
		
		if (!StringUtils.isEmpty(param.getKeyword())) {
			shoppingOrderExtendDOExample.clear();
			
			Criteria orderNumCriteria = shoppingOrderExtendDOExample.or();
			orderNumCriteria.andOrderNumEqualTo(param.getKeyword());
			orderNumCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());
			
			Criteria paroductCriteria = shoppingOrderExtendDOExample.or();
			paroductCriteria.andProductNameLike("%"+ param.getKeyword() + "%");
			paroductCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());
		}
		
		// 过滤重复记录
		shoppingOrderExtendDOExample.setDistinct(true);
		
		// 查询总记录数
		Long count = shoppingOrderDOExtendMapper.countByExample(shoppingOrderExtendDOExample);
		
		Page<ShoppingOrderExtendQueryBO> shoppingOrderItemBOPage = new Page<ShoppingOrderExtendQueryBO>();
		shoppingOrderItemBOPage.setTotalCount(count.intValue());
		shoppingOrderItemBOPage.setCurrentPage(param.getCurrentPage());
		
		// 如果总记录为0，不再执行后续操作直接返回
		if (count == null || count <= 0) {
			return shoppingOrderItemBOPage;
		}
		
		// 分页参数
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		
		// 默认创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");
		
		// 如果参数中的keyword有值，查询结果的订单项会缺少，所有先找出所有购物订单id再通过去查找购物订单以及级联的购物订单项
		List<Long> shoppingOrderIdList = shoppingOrderDOExtendMapper.selectShoppingOrderIdByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
		
		shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.createCriteria().andIdIn(shoppingOrderIdList);
		
		// 默认创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");
		
		List<ShoppingOrderExtendDO> shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectShoppingOrderAssociationByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
		
		shoppingOrderItemBOPage.setRecords(ShoppingOrderExtendConverter.convert(shoppingOrderExtendDOList));
		
		return shoppingOrderItemBOPage;
	}
	
	/**
	 * 根据id获取购物订单以及订单项
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Override
	public ShoppingOrderExtendDetailBO get(Long id) {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.createCriteria().andIdEqualTo(id);
		
		ShoppingOrderExtendDO shoppingOrderExtendDO = shoppingOrderDOExtendMapper.getShoppingOrderAssociationByPrimaryKey(id);
		
		if (shoppingOrderExtendDO == null || shoppingOrderExtendDO.getId() == null || shoppingOrderExtendDO.getId() <= 0
				|| shoppingOrderExtendDO.getItems() == null || shoppingOrderExtendDO.getItems().isEmpty()) {
			return null;
		}
		
		return ShoppingOrderExtendConverter.convertShoppingOrderExtendDetailBO(shoppingOrderExtendDO);
	}
	
	/**
	 * 根据id获取购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Override
	public ShoppingOrderBO getShoppingOrder(Long id) {
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);
		
		return ShoppingOrderConverter.convertShoppingOrderBO(shoppingOrderDO);
	}
	
	/**
	 * 根据id获取购物订单物流信息
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Override
	public ShoppingOrderExpressBO getExpressInfo(Long id) {
		ShoppingOrderDOExample shoppingOrderDOExample = new ShoppingOrderDOExample();
		shoppingOrderDOExample.createCriteria().andIdEqualTo(id);
		
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(id);
		
		return ShoppingOrderConverter.covert(shoppingOrderDO);
	}
	
	/**
	 * 取消购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Transactional
	@Override
	public Integer cancelOrder(Long id) {
		// 更新购物订单的状态
		ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
		shoppingOrderDO.setId(id);
		shoppingOrderDO.setGmtModified(new Date());
		// 更新订单状态
		shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
		// 更新成交时间
		shoppingOrderDO.setGmtTransaction(new Date());
		Integer result = shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);

		// 更新购物订单项状态
		ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
		com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample.Criteria shoppingOrderItemDOExampleCriteria = shoppingOrderItemDOExample.createCriteria();
		shoppingOrderItemDOExampleCriteria.andShoppingOrderIdEqualTo(id);
		
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.CANCEL_TRANSACTION.getValue());
		
		shoppingOrderItemDOMapper.updateByExampleSelective(shoppingOrderItemDO, shoppingOrderItemDOExample);
		
		// TODO 事务补偿预留(释放库存)
		//transactionMainService.sendNotice(id);
		return result;
	}
	
	/**
	 * 删除购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Transactional
	@Override
	public Integer deleteOrder(Long id) {
		// 更新购物订单的状态
		ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
		shoppingOrderDO.setId(id);
		shoppingOrderDO.setGmtModified(new Date());
		// 更改数据状态为删除
		shoppingOrderDO.setStatus((byte)0x00);
		Integer result = shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);

		return result;
	}

	/**
	 * 支付成功之后
	 * 修改购物订单以及订单项状态为待发货
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Transactional
	@Override
	public Integer paymentSuccessful(Long id) {
		// 更新购物订单的状态
		ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
		shoppingOrderDO.setId(id);
		shoppingOrderDO.setGmtModified(new Date());
		// 更改订单状态为待发货
		shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.BE_SHIPPED.getValue());
		// 更新付款时间
		shoppingOrderDO.setGmtPayment(new Date());
		Integer result = shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);
		
		// 更新购物订单项状态
		ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
		com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample.Criteria shoppingOrderItemDOExampleCriteria = shoppingOrderItemDOExample.createCriteria();
		shoppingOrderItemDOExampleCriteria.andShoppingOrderIdEqualTo(id);
		
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.BE_SHIPPED.getValue());
		
		shoppingOrderItemDOMapper.updateByExampleSelective(shoppingOrderItemDO, shoppingOrderItemDOExample);
		
		return result;
	}
	
	/**
	 * 确认收货之后
	 * 修改购物订单以及订单项状态为交易成功
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
	@Transactional
	@Override
	public Integer tradingSuccess(Long id) {
		// 更新购物订单的状态
		ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
		shoppingOrderDO.setId(id);
		shoppingOrderDO.setGmtModified(new Date());
		// 更改订单状态为
		shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		// 更新成交时间交易成功
		shoppingOrderDO.setGmtTransaction(new Date());
		Integer result = shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);
		
		// 更新购物订单项状态
		ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
		com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample.Criteria shoppingOrderItemDOExampleCriteria = shoppingOrderItemDOExample.createCriteria();
		shoppingOrderItemDOExampleCriteria.andShoppingOrderIdEqualTo(id);
		
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.TRADING_SUCCESS.getValue());
		
		shoppingOrderItemDOMapper.updateByExampleSelective(shoppingOrderItemDO, shoppingOrderItemDOExample);
		return result;
	}
	
	/**
	 * 买家申请退款 
	 * 修改订单状态为退款中
	 * 修改订单项的退款状态为待商家确认
	 * 
	 * @param shoppingOrderitemId
	 *            购物订单项id
	 * @param shoppingOrderBO
	 *            购物订单
	 * @param param
	 *            退款参数
	 * @return
	 */
	@Transactional
	@Override
	public Integer requestRefund(Long shoppingOrderitemId, ShoppingOrderBO shoppingOrderBO, ShoppingOrderRequestRefundForeignParam param) {
		// 更新购物订单的状态
		ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
		shoppingOrderDO.setId(shoppingOrderBO.getId());
		shoppingOrderDO.setGmtModified(new Date());
		// 更改订单状态为待商家确认
		shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
		// 更新成交时间
		shoppingOrderDO.setGmtTransaction(new Date());
		Integer result = shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);
		
		// 更新购物订单项状态
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setId(shoppingOrderitemId);
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.REFUNDING.getValue());
		
		// 如果卖家支持七天无理由退货，跳过商家确认这个阶段
		if (shoppingOrderBO.getIsNoReasonReturn()) {
			// 系统内部自动判断买家是否需要退货
			if (shoppingOrderBO.getOrderStatus().equals(ShoppingOrderStatusEnum.BE_SHIPPED)) {
				shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.TO_BE_REFUNDED.getValue());
			} else {
				shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.FILL_RETURN_ADDRESS.getValue());
			}
		} else {
			shoppingOrderItemDO.setRefundStatus(ShoppingOrderItemRefundStatusEnum.TO_BE_CONFIRMED.getValue());
		}
		
		shoppingOrderItemDOMapper.updateByPrimaryKeySelective(shoppingOrderItemDO);
		
		// 保存退货详情记录
		ShoppingRefundDetailDO shoppingRefundDetailDO = new ShoppingRefundDetailDO();
		shoppingRefundDetailDO.setShoppingOrderItemId(shoppingOrderitemId);
		// 根据订单状态是否需要退货
		if (shoppingOrderBO.getOrderStatus().equals(ShoppingOrderStatusEnum.BE_SHIPPED)) {
			shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.REFUND.getValue());
		} else{
			shoppingRefundDetailDO.setType(ShoppingRefundTypeEnum.RETURN_REFUND.getValue());
		}
		shoppingRefundDetailDO.setAmount(shoppingOrderBO.getOrderTotalPrice());
		shoppingRefundDetailDO.setReason(param.getReason());
		shoppingRefundDetailDO.setGmtCreate(new Date());
		shoppingRefundDetailDO.setGmtModified(new Date());
		
		shoppingRefundDetailDOMapper.insertSelective(shoppingRefundDetailDO);
		
		return result;
	}
	
	/**
	 * 商家填写物流信息
	 * 更改购物订单的状态为待收货
	 * 
	 * @param id
	 *            购物订单id
	 * @param param
	 *            物流信息参数
	 * @return
	 */
	@Transactional
	@Override
	public Integer fillLogisticsInformation(Long id, ShoppingOrderLogisticsInformationParam param) {
		
		// 更新购物订单的状态
		ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
		shoppingOrderDO.setId(id);
		shoppingOrderDO.setGmtModified(new Date());
		// 更改订单状态为待商家确认
		shoppingOrderDO.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
		
		// 更新购物订单的物流信息
		shoppingOrderDO.setExpressCompanyId(param.getExpressCompanyId());
		shoppingOrderDO.setExpressCompanyCode(param.getExpressCompanyCode());
		shoppingOrderDO.setExpressCompanyName(param.getExpressCompanyName());
		shoppingOrderDO.setWaybillNum(param.getWaybillNum());
		
		Integer result = shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);
		
		// 更新购物订单项状态
		ShoppingOrderItemDOExample shoppingOrderItemDOExample = new ShoppingOrderItemDOExample();
		com.lawu.eshop.order.srv.domain.ShoppingOrderItemDOExample.Criteria shoppingOrderItemDOExampleCriteria = shoppingOrderItemDOExample.createCriteria();
		shoppingOrderItemDOExampleCriteria.andShoppingOrderIdEqualTo(id);
		
		ShoppingOrderItemDO shoppingOrderItemDO = new ShoppingOrderItemDO();
		shoppingOrderItemDO.setGmtModified(new Date());
		shoppingOrderItemDO.setOrderStatus(ShoppingOrderStatusEnum.TO_BE_RECEIVED.getValue());
		
		shoppingOrderItemDOMapper.updateByExampleSelective(shoppingOrderItemDO, shoppingOrderItemDOExample);
		
		return result;
	}
	
}
