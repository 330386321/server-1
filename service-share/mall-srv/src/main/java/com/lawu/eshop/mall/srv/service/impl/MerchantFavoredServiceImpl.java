package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.mall.constants.StatusEnum;
import com.lawu.eshop.mall.param.MerchantFavoredParam;
import com.lawu.eshop.mall.srv.bo.MerchantFavoredBO;
import com.lawu.eshop.mall.srv.converter.MerchantFavoredConverter;
import com.lawu.eshop.mall.srv.domain.MerchantFavoredDO;
import com.lawu.eshop.mall.srv.domain.MerchantFavoredDOExample;
import com.lawu.eshop.mall.srv.mapper.MerchantFavoredDOMapper;
import com.lawu.eshop.mall.srv.service.MerchantFavoredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
@Service
public class MerchantFavoredServiceImpl implements MerchantFavoredService {
    @Autowired
    private MerchantFavoredDOMapper merchantFavoredDOMapper;

    @Override
    @Transactional
    public Integer saveMerchantFavoredInfo(Long merchantId, MerchantFavoredParam param) {
        MerchantFavoredDO merchantFavoredDO = new MerchantFavoredDO();
        merchantFavoredDO.setMerchantId(merchantId);
        merchantFavoredDO.setType(param.getTypeEnum().val);
        if (param.getReachAmount() != null) {
            merchantFavoredDO.setReachAmount(param.getReachAmount());
        }
        if (param.getFavoredAmount() != null) {
            merchantFavoredDO.setFavoredAmount(param.getFavoredAmount());
        }
        if (param.getDiscountRate() != null) {
            merchantFavoredDO.setDiscountRate(param.getDiscountRate());
        }
        merchantFavoredDO.setValidWeekTime(param.getValidWeekTime());
        merchantFavoredDO.setValidDayBeginTime(param.getValidDayBeginTime());
        merchantFavoredDO.setValidDayEndTime(param.getValidDayEndTime());
        merchantFavoredDO.setEntireBeginTime(param.getEntireBeginTime());
        merchantFavoredDO.setEntireEndTime(param.getEntireEndTime());
        merchantFavoredDO.setStatus(StatusEnum.STATUS_VALID.val);
        merchantFavoredDO.setGmtCreate(new Date());
        merchantFavoredDO.setGmtModified(new Date());
        int id = merchantFavoredDOMapper.insert(merchantFavoredDO);
        return id;
    }

    @Override
    public MerchantFavoredBO findFavoredByMerchantId(Long merchantId) {
        MerchantFavoredDOExample example = new MerchantFavoredDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId).andStatusEqualTo(StatusEnum.STATUS_VALID.val);
        List<MerchantFavoredDO> merchantFavoredDOS = merchantFavoredDOMapper.selectByExample(example);
        MerchantFavoredBO merchantFavoredBO = new MerchantFavoredBO();
        if (!merchantFavoredDOS.isEmpty()) {
            return MerchantFavoredConverter.coverBO(merchantFavoredDOS.get(0));
        }
        return null;
    }

    @Override
    @Transactional
    public void delMerchantFavoredInfoById(Long id) {
        MerchantFavoredDO merchantFavoredDO = new MerchantFavoredDO();
        merchantFavoredDO.setId(id);
        merchantFavoredDO.setStatus(StatusEnum.STATUS_INVALID.val);
        merchantFavoredDOMapper.updateByPrimaryKeySelective(merchantFavoredDO);
    }

    @Override
    public MerchantFavoredBO findFavoredById(Long id) {
        MerchantFavoredDOExample example = new MerchantFavoredDOExample();
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(StatusEnum.STATUS_VALID.val);
        List<MerchantFavoredDO> merchantFavoredDOS = merchantFavoredDOMapper.selectByExample(example);
        MerchantFavoredBO merchantFavoredBO = new MerchantFavoredBO();
        if (!merchantFavoredDOS.isEmpty()) {
            return MerchantFavoredConverter.coverBO(merchantFavoredDOS.get(0));
        }
        return null;
    }

    @Override
    public Integer updateMerchantFavoredInfo(Long merchantId, MerchantFavoredParam param) {
        MerchantFavoredDOExample example = new MerchantFavoredDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId);
        MerchantFavoredDO merchantFavoredDO = new MerchantFavoredDO();
        merchantFavoredDO.setType(param.getTypeEnum().val);
        if (param.getReachAmount() != null && param.getFavoredAmount() != null) {
            merchantFavoredDO.setReachAmount(param.getReachAmount());
            merchantFavoredDO.setFavoredAmount(param.getFavoredAmount());
            merchantFavoredDO.setDiscountRate(null);
        }
        if (param.getDiscountRate() != null) {
            merchantFavoredDO.setDiscountRate(param.getDiscountRate());
            merchantFavoredDO.setReachAmount(null);
            merchantFavoredDO.setFavoredAmount(null);
        }
        merchantFavoredDO.setValidWeekTime(param.getValidWeekTime());
        merchantFavoredDO.setValidDayBeginTime(param.getValidDayBeginTime());
        merchantFavoredDO.setValidDayEndTime(param.getValidDayEndTime());
        merchantFavoredDO.setEntireBeginTime(param.getEntireBeginTime());
        merchantFavoredDO.setEntireEndTime(param.getEntireEndTime());
        merchantFavoredDO.setGmtModified(new Date());
        Integer row = merchantFavoredDOMapper.updateByExampleSelective(merchantFavoredDO, example);
        return row;
    }
}
