package com.lawu.eshop.order.srv.service.impl;

import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.constants.StatusEnum;
import com.lawu.eshop.mall.param.PayOrderListParam;
import com.lawu.eshop.mall.param.PayOrderParam;
import com.lawu.eshop.order.srv.bo.PayOrderBO;
import com.lawu.eshop.order.srv.converter.PayOrderConverter;
import com.lawu.eshop.order.srv.domain.PayOrderDO;
import com.lawu.eshop.order.srv.domain.PayOrderDOExample;
import com.lawu.eshop.order.srv.mapper.PayOrderDOMapper;
import com.lawu.eshop.order.srv.service.PayOrderService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    @Qualifier("payOrderTransactionMainServiceImpl")
    private TransactionMainService payOrderTransactionMainService;

    @Override
    @Transactional
    public Integer savePayOrderInfo(Long memberId, PayOrderParam param) {
        PayOrderDO payOrderDO = new PayOrderDO();
        payOrderDO.setMemberId(memberId);
        payOrderDO.setMerchantId(param.getMerchantId());
        payOrderDO.setFavoredAmount(param.getFavoredAmount());
        payOrderDO.setActualAmount(param.getActualAmount());
        payOrderDO.setTotalAmount(param.getTotalAmount());
        payOrderDO.setPayType(param.getPayTypeEnum().val);
        payOrderDO.setGmtCreate(new Date());
        payOrderDO.setGmtModified(new Date());
        payOrderDO.setIsEvaluation(false);//未评
        payOrderDO.setStatus(StatusEnum.STATUS_VALID.val);
        int id = payOrderDOMapper.insert(payOrderDO);
        // 发消息更新门店买单笔数
        payOrderTransactionMainService.sendNotice(param.getMerchantId());
        return id;
    }

    @Override
    public Page<PayOrderBO> getpayOrderList(Long memberId, PayOrderListParam param) {
        PayOrderDOExample example = new PayOrderDOExample();
        if (param.getEvaluationEnum() == null) {
            example.createCriteria().andMemberIdEqualTo(memberId).andStatusEqualTo(StatusEnum.STATUS_VALID.val);
        } else {
            example.createCriteria().andMemberIdEqualTo(memberId).andIsEvaluationEqualTo(param.getEvaluationEnum().val).andStatusEqualTo(StatusEnum.STATUS_VALID.val);
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
        payOrderDO.setStatus(StatusEnum.STATUS_INVALID.val);
        payOrderDOMapper.updateByPrimaryKeySelective(payOrderDO);
    }
}