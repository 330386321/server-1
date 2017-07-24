package com.lawu.eshop.order.srv.converter;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.order.constants.EvaluationEnum;
import com.lawu.eshop.order.dto.MemberPayOrderInfoDTO;
import com.lawu.eshop.order.dto.MerchantPayOrderListDTO;
import com.lawu.eshop.order.dto.OperatorPayOrderListDTO;
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
        if (payOrderDO == null) {
            return null;
        }
        PayOrderBO payOrderBO = new PayOrderBO();
        payOrderBO.setOrderNum(payOrderDO.getOrderNum());
        payOrderBO.setId(payOrderDO.getId());
        payOrderBO.setActualAmount(payOrderDO.getActualAmount());
        payOrderBO.setGmtCreate(payOrderDO.getGmtCreate());
        payOrderBO.setMemberId(payOrderDO.getMemberId());
        payOrderBO.setMerchantId(payOrderDO.getMerchantId());
        payOrderBO.setCommentTime(payOrderDO.getCommentTime());
        payOrderBO.setPayType(payOrderDO.getPayType());
        payOrderBO.setEvaluation(payOrderDO.getIsEvaluation());
        payOrderBO.setFavoredAmount(payOrderDO.getFavoredAmount());
        payOrderBO.setTotalAmount(payOrderDO.getTotalAmount());
        return payOrderBO;
    }

    public static PayOrderDTO coverDTO(PayOrderBO payOrderBO) {
    	PayOrderDTO rtn ;
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

    public static List<PayOrderBO> coverBOS(List<PayOrderDO> payOrderDOS) {
        if (payOrderDOS == null || payOrderDOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<PayOrderBO> payOrderBOS = new ArrayList<>();
        for (PayOrderDO payOrderDO : payOrderDOS) {
            payOrderBOS.add(coverBO(payOrderDO));
        }
        return payOrderBOS;
    }

    public static List<MerchantPayOrderListDTO> coverDTOS(List<PayOrderBO> payOrderBOS) {
        if(payOrderBOS == null){
            return  new ArrayList<>();
        }
        List<MerchantPayOrderListDTO> payOrderListDTOS = new ArrayList<>();
        MerchantPayOrderListDTO merchantPayOrderListDTO ;
        for(PayOrderBO payOrderBO :payOrderBOS){
            merchantPayOrderListDTO = new MerchantPayOrderListDTO();
            merchantPayOrderListDTO.setOrderNum(payOrderBO.getOrderNum());
            merchantPayOrderListDTO.setGmtCreate(payOrderBO.getGmtCreate());
            merchantPayOrderListDTO.setActualAmount(payOrderBO.getActualAmount());
            payOrderListDTOS.add(merchantPayOrderListDTO);
        }
        return payOrderListDTOS;
    }

    public static MemberPayOrderInfoDTO coverOrderInfoDTO(PayOrderBO payOrderBO) {
        if(payOrderBO == null){
            return  null;
        }
        MemberPayOrderInfoDTO infoDTO = new MemberPayOrderInfoDTO();
        infoDTO.setId(payOrderBO.getId());
        infoDTO.setMerchantId(payOrderBO.getMerchantId());
        infoDTO.setActualAmount(payOrderBO.getActualAmount());
        infoDTO.setEvaluationEnum(EvaluationEnum.getEnum(payOrderBO.getEvaluation()));
        infoDTO.setFavoredAmount(payOrderBO.getFavoredAmount());
        infoDTO.setGmtCreate(payOrderBO.getGmtCreate());
        infoDTO.setOrderNum(payOrderBO.getOrderNum());
        infoDTO.setTotalAmount(payOrderBO.getTotalAmount());
        return  infoDTO;
    }

    public static List<OperatorPayOrderListDTO> coverOperatorPayOrderListDTOS(List<PayOrderBO> records) {
        if (records.isEmpty()) {
            return new ArrayList<>();
        }
        List<OperatorPayOrderListDTO> payOrderListDTOS = new ArrayList<>();
        OperatorPayOrderListDTO payOrderListDTO = null;
        for (PayOrderBO payOrderBO : records) {
            payOrderListDTO = new OperatorPayOrderListDTO();
            payOrderListDTO.setId(payOrderBO.getId());
            payOrderListDTO.setOrderNum(payOrderBO.getOrderNum());
            payOrderListDTO.setActualAmount(payOrderBO.getActualAmount());
            payOrderListDTO.setGmtCreate(payOrderBO.getGmtCreate());
            payOrderListDTO.setMerchantId(payOrderBO.getMerchantId());
            payOrderListDTO.setMemberId(payOrderBO.getMemberId());
            payOrderListDTOS.add(payOrderListDTO);
        }
        return payOrderListDTOS;
    }
}
