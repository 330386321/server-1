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
	
	/**
	 * 隐藏默认的构造器
	 */
	private PayOrderConverter() {
		throw new IllegalAccessError("Utility class");
	}

	public static PayOrderBO coverBO(PayOrderDO payOrderDO) {
    	PayOrderBO rtn = null;
        if (payOrderDO == null) {
            return rtn;
        }
        
        rtn = new PayOrderBO();
        rtn.setId(payOrderDO.getId());
        rtn.setTotalAmount(payOrderDO.getTotalAmount());
        rtn.setActualAmount(payOrderDO.getActualAmount());
        rtn.setEvaluation(payOrderDO.getIsEvaluation());
        rtn.setFavoredAmount(payOrderDO.getFavoredAmount());
        rtn.setGmtCreate(payOrderDO.getGmtCreate());
        rtn.setMerchantId(payOrderDO.getMerchantId());
        return rtn;
    }

    public static PayOrderDTO coverDTO(PayOrderBO payOrderBO) {
    	PayOrderDTO rtn = null;
        if (payOrderBO == null) {
            return null;
        }
        rtn = new PayOrderDTO();
        rtn.setId(payOrderBO.getId());
        rtn.setTotalAmount(payOrderBO.getTotalAmount());
        rtn.setActualAmount(payOrderBO.getActualAmount());
        rtn.setEvaluationEnum(EvaluationEnum.getEnum(payOrderBO.getEvaluation()));
        rtn.setFavoredAmount(payOrderBO.getFavoredAmount());
        rtn.setGmtCreate(payOrderBO.getGmtCreate());
        rtn.setMerchantId(payOrderBO.getMerchantId());
        return rtn;
    }
}
