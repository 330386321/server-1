package com.lawu.eshop.order.srv.converter;

import com.lawu.eshop.order.constants.EvaluationEnum;
import com.lawu.eshop.order.dto.PayOrderDTO;
import com.lawu.eshop.order.srv.bo.PayOrderBO;
import com.lawu.eshop.order.srv.domain.PayOrderDO;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
public class PayOrderConverter {

    public static PayOrderBO coverBO(PayOrderDO payOrderDO) {
        if (payOrderDO == null) {
            return null;
        }
        PayOrderBO payOrderBO = new PayOrderBO();
        payOrderBO.setId(payOrderDO.getId());
        payOrderBO.setTotalAmount(payOrderDO.getTotalAmount());
        payOrderBO.setActualAmount(payOrderDO.getActualAmount());
        payOrderBO.setEvaluation(payOrderDO.getIsEvaluation());
        payOrderBO.setFavoredAmount(payOrderDO.getFavoredAmount());
        payOrderBO.setGmtCreate(payOrderDO.getGmtCreate());
        payOrderBO.setMerchantId(payOrderDO.getMerchantId());
        return payOrderBO;
    }

    public static PayOrderDTO coverDTO(PayOrderBO payOrderBO) {
        if (payOrderBO == null) {
            return null;
        }
        PayOrderDTO payOrderDTO = new PayOrderDTO();
        payOrderDTO.setId(payOrderBO.getId());
        payOrderDTO.setTotalAmount(payOrderBO.getTotalAmount());
        payOrderDTO.setActualAmount(payOrderBO.getActualAmount());
        payOrderDTO.setEvaluationEnum(EvaluationEnum.getEnum(payOrderBO.getEvaluation()));
        payOrderDTO.setFavoredAmount(payOrderBO.getFavoredAmount());
        payOrderDTO.setGmtCreate(payOrderBO.getGmtCreate());
        payOrderDTO.setMerchantId(payOrderBO.getMerchantId());
        return payOrderDTO;
    }
}
