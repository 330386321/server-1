package com.lawu.eshop.order.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.order.constants.PayOrderStatusEnum;
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
        payOrderDO.setActualAmount(BigDecimal.valueOf(param.getTotalAmount()-param.getFavoredAmount()+param.getNotFavoredAmount()));
        payOrderDO.setNotFavoredAmount(BigDecimal.valueOf(param.getNotFavoredAmount()));
        payOrderDO.setTotalAmount(BigDecimal.valueOf(param.getTotalAmount()));
        String orderNum = StringUtil.getRandomNum("");
        payOrderDO.setOrderNum(orderNum);
        payOrderDO.setGmtCreate(new Date());
        payOrderDO.setGmtModified(new Date());
        payOrderDO.setMerchantNum(param.getMerchantNum());
        payOrderDO.setIsEvaluation(false);//未评
        payOrderDO.setStatus(PayOrderStatusEnum.STATUS_UNPAY.val);//待支付
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
            example.createCriteria().andMemberIdEqualTo(memberId).andStatusEqualTo(PayOrderStatusEnum.STATUS_PAY_SUCCESS.val);
        } else {
            example.createCriteria().andMemberIdEqualTo(memberId).andIsEvaluationEqualTo(param.getEvaluationEnum().val).andStatusEqualTo(PayOrderStatusEnum.STATUS_PAY_SUCCESS.val);
        }
        example.setOrderByClause("id desc");
        //分页
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
        payOrderDO.setStatus(PayOrderStatusEnum.STATUS_DEL.val);
        payOrderDOMapper.updateByPrimaryKeySelective(payOrderDO);
    }

	@Override
	public ThirdPayCallBackQueryPayOrderBO selectThirdPayCallBackPayOrder(String orderId) {
		PayOrderDO payDO = payOrderDOMapper.selectByPrimaryKey(Long.valueOf(orderId));
		ThirdPayCallBackQueryPayOrderBO bo = new ThirdPayCallBackQueryPayOrderBO();
		bo.setActualMoney(payDO.getActualAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		bo.setBusinessUserNum(payDO.getMerchantNum());
		bo.setPayOrderStatusEnum(PayOrderStatusEnum.getEnum(payDO.getStatus()));
		return bo;
	}
}
