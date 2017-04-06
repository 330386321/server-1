package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.mall.srv.bo.TradeBO;
import com.lawu.eshop.mall.srv.converter.TradeConverter;
import com.lawu.eshop.mall.srv.domain.TradeDO;
import com.lawu.eshop.mall.srv.domain.TradeDOExample;
import com.lawu.eshop.mall.srv.mapper.TradeDOMapper;
import com.lawu.eshop.mall.srv.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeDOMapper tradeDOMapper;

    @Override
    public List<TradeBO> listTrade() {
        TradeDOExample tradeDOExample = new TradeDOExample();
        List<TradeDO> tradeDOS = tradeDOMapper.selectByExample(tradeDOExample);
        return tradeDOS.isEmpty() ? null : TradeConverter.convertBO(tradeDOS);
    }
}
