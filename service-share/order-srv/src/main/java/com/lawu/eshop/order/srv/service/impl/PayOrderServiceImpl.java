package com.lawu.eshop.order.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.order.constants.CommissionStatusEnum;
import com.lawu.eshop.order.constants.PayOrderStatusEnum;
import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;
import com.lawu.eshop.order.param.MerchantPayOrderListParam;
import com.lawu.eshop.order.param.PayOrderListParam;
import com.lawu.eshop.order.param.PayOrderParam;
import com.lawu.eshop.order.srv.bo.PayOrderBO;
import com.lawu.eshop.order.srv.bo.ThirdPayCallBackQueryPayOrderBO;
import com.lawu.eshop.order.srv.converter.PayOrderConverter;
import com.lawu.eshop.order.srv.domain.PayOrderDO;
import com.lawu.eshop.order.srv.domain.PayOrderDOExample;
import com.lawu.eshop.order.srv.mapper.PayOrderDOMapper;
import com.lawu.eshop.order.srv.service.PayOrderService;
import com.lawu.eshop.utils.StringUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
@Service
public class PayOrderServiceImpl implements PayOrderService {
	@Autowired
	private PayOrderDOMapper payOrderDOMapper;

    @Override
    @Transactional
    public PayOrderBO savePayOrderInfo(Long memberId, PayOrderParam param,String numNum) {
        PayOrderDO payOrderDO = new PayOrderDO();
        payOrderDO.setMemberId(memberId);
        payOrderDO.setMemberNum(numNum);
        payOrderDO.setMerchantId(param.getMerchantId());
        payOrderDO.setFavoredAmount(BigDecimal.valueOf(param.getFavoredAmount()));
        payOrderDO.setActualAmount(BigDecimal.valueOf(param.getTotalAmount()-param.getFavoredAmount()));
        payOrderDO.setNotFavoredAmount(BigDecimal.valueOf(param.getNotFavoredAmount()));
        payOrderDO.setTotalAmount(BigDecimal.valueOf(param.getTotalAmount()));
        String orderNum = StringUtil.getRandomNum("");
        payOrderDO.setOrderNum(orderNum);
        payOrderDO.setGmtCreate(new Date());
        payOrderDO.setGmtModified(new Date());
		payOrderDO.setCommissionStatus(CommissionStatusEnum.NOT_COUNTED.getValue());
        payOrderDO.setMerchantNum(param.getMerchantNum());
        payOrderDO.setIsEvaluation(false);//未评
        payOrderDO.setStatus(PayOrderStatusEnum.STATUS_UNPAY.val);//待支付
		payOrderDO.setOrderStatus(true);
        payOrderDOMapper.insert(payOrderDO);
        PayOrderBO payOrderBO = new PayOrderBO();
        payOrderBO.setOrderNum(orderNum);
        payOrderBO.setId(payOrderDO.getId());
        return payOrderBO;
    }

	@Override
	public Page<PayOrderBO> getpayOrderList(Long memberId, PayOrderListParam param) {
		PayOrderDOExample example = new PayOrderDOExample();
		if (param.getEvaluationEnum() == null) {
			example.createCriteria().andMemberIdEqualTo(memberId)
					.andStatusEqualTo(PayOrderStatusEnum.STATUS_PAY_SUCCESS.val).andOrderStatusEqualTo(true);
		} else {
			example.createCriteria().andMemberIdEqualTo(memberId).andIsEvaluationEqualTo(param.getEvaluationEnum().getVal())
					.andStatusEqualTo(PayOrderStatusEnum.STATUS_PAY_SUCCESS.val).andOrderStatusEqualTo(true);
		}
		example.setOrderByClause("id desc");
		// 分页
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		Page<PayOrderBO> page = new Page<>();
		page.setTotalCount(payOrderDOMapper.countByExample(example));
		page.setCurrentPage(param.getCurrentPage());

		List<PayOrderDO> payOrderDOS = payOrderDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		if (payOrderDOS == null) {
			return null;
		}
		List<PayOrderBO> payOrderBOS = new ArrayList<>();
		for (PayOrderDO payOrderDO : payOrderDOS) {
			PayOrderBO payOrderBO = PayOrderConverter.coverBO(payOrderDO);
			payOrderBOS.add(payOrderBO);
		}
		page.setRecords(payOrderBOS);
		return page;
	}

	@Override
	@Transactional
	public void delPayOrderInfo(Long id) {
		PayOrderDO payOrderDO = new PayOrderDO();
		payOrderDO.setId(id);
		payOrderDO.setOrderStatus(false);
		payOrderDOMapper.updateByPrimaryKeySelective(payOrderDO);
	}

	@Override
	public ThirdPayCallBackQueryPayOrderBO selectThirdPayCallBackPayOrder(String orderId) {
		PayOrderDO payDO = payOrderDOMapper.selectByPrimaryKey(Long.valueOf(orderId));
		ThirdPayCallBackQueryPayOrderBO bo = new ThirdPayCallBackQueryPayOrderBO();
		bo.setActualMoney(payDO.getActualAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		bo.setBusinessUserNum(payDO.getMerchantNum());
		bo.setPayOrderStatusEnum(PayOrderStatusEnum.getEnum(payDO.getStatus()));
		bo.setOrderNum(payDO.getOrderNum());
		return bo;
	}

	/**
	 * 查询未计算提成的买单
	 * 
	 * @return
	 * @throws Exception
	 * @author yangqh
	 */
	@Override
	public List<ShoppingOrderCommissionDTO> selectNotCommissionOrder() {
		PayOrderDOExample example = new PayOrderDOExample();
		example.createCriteria().andStatusEqualTo(PayOrderStatusEnum.STATUS_PAY_SUCCESS.val)
				.andCommissionStatusEqualTo(CommissionStatusEnum.NOT_COUNTED.getValue());
		List<PayOrderDO> dos = payOrderDOMapper.selectByExample(example);
		List<ShoppingOrderCommissionDTO> dtos = new ArrayList<ShoppingOrderCommissionDTO>();
		for(PayOrderDO orderDO : dos){
			ShoppingOrderCommissionDTO dto = new ShoppingOrderCommissionDTO();
			dto.setId(orderDO.getId());
			dto.setMemberNum(orderDO.getMemberNum());
			dto.setMerchantNum(orderDO.getMerchantNum());
			dto.setActualAmount(orderDO.getActualAmount());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public int updateCommissionStatus(List<Long> ids) {
		PayOrderDOExample example = new PayOrderDOExample();
		example.createCriteria().andIdIn(ids);
		
		PayOrderDO payOrder = new PayOrderDO();
		payOrder.setGmtCommission(new Date());
		payOrder.setCommissionStatus(CommissionStatusEnum.CALCULATED.getValue());
		payOrderDOMapper.updateByExampleSelective(payOrder, example);
		
		return ResultCode.SUCCESS;
	}

	@Override
	public Page<PayOrderBO> getMerchantPayOrderList(Long userId, MerchantPayOrderListParam param) {
		PayOrderDOExample example = new PayOrderDOExample();
		example.createCriteria().andMerchantIdEqualTo(userId).andStatusEqualTo(PayOrderStatusEnum.STATUS_PAY_SUCCESS.val);
		example.setOrderByClause("id desc");

		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		Page<PayOrderBO> page = new Page<>();
		page.setTotalCount(payOrderDOMapper.countByExample(example));
		page.setCurrentPage(param.getCurrentPage());

		List<PayOrderDO> payOrderDOS = payOrderDOMapper.selectByExampleWithRowbounds(example, rowBounds);

		List<PayOrderBO> payOrderBOS = PayOrderConverter.coverBOS(payOrderDOS);

		page.setRecords(payOrderBOS);
		return page;
	}
}
