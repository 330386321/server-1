package com.lawu.eshop.mall.srv.converter;

import com.lawu.eshop.mall.dto.TradeDTO;
import com.lawu.eshop.mall.srv.bo.TradeBO;
import com.lawu.eshop.mall.srv.domain.TradeDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
public class TradeConverter {

    /**
     * BO转换
     *
     * @param tradeDO
     * @return
     */
    public static TradeBO convertBO(TradeDO tradeDO) {
        if (tradeDO == null) {
            return null;
        }

        TradeBO tradeBO = new TradeBO();
        tradeBO.setId(tradeDO.getId());
        tradeBO.setParentId(tradeDO.getParentId());
        tradeBO.setPath(tradeDO.getPath());
        tradeBO.setName(tradeDO.getName());
        return tradeBO;
    }

    /**
     * DTO转换
     *
     * @param tradeBO
     * @return
     */
    public static TradeDTO convertDTO(TradeBO tradeBO) {
        if (tradeBO == null) {
            return null;
        }

        TradeDTO tradeDTO = new TradeDTO();
        tradeDTO.setId(tradeBO.getId());
        tradeDTO.setParentId(tradeBO.getParentId());
        tradeDTO.setPath(tradeBO.getPath());
        tradeDTO.setName(tradeBO.getName());
        return tradeDTO;
    }

    /**
     * BO转换
     *
     * @param tradeDOS
     * @return
     */
    public static List<TradeBO> convertBO(List<TradeDO> tradeDOS) {
        if (tradeDOS == null) {
            return null;
        }

        List<TradeBO> tradeBOS = new ArrayList<TradeBO>(tradeDOS.size());
        for (TradeDO tradeDO : tradeDOS) {
            TradeBO tradeBO = new TradeBO();
            tradeBO.setId(tradeDO.getId());
            tradeBO.setParentId(tradeDO.getParentId());
            tradeBO.setPath(tradeDO.getPath());
            tradeBO.setName(tradeDO.getName());
            tradeBOS.add(tradeBO);
        }
        return tradeBOS;
    }

    /**
     * DTO转换
     *
     * @param tradeBOS
     * @return
     */
    public static List<TradeDTO> convertDTO(List<TradeBO> tradeBOS) {
        if (tradeBOS == null) {
            return null;
        }

        List<TradeDTO> tradeDTOS = new ArrayList<TradeDTO>(tradeBOS.size());
        for (TradeBO tradeBO : tradeBOS) {
            TradeDTO tradeDTO = new TradeDTO();
            tradeDTO.setId(tradeBO.getId());
            tradeDTO.setParentId(tradeBO.getParentId());
            tradeDTO.setPath(tradeBO.getPath());
            tradeDTO.setName(tradeBO.getName());
            tradeDTOS.add(tradeDTO);
        }
        return tradeDTOS;
    }
}
