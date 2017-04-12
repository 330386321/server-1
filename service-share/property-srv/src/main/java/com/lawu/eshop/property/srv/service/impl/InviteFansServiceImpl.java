package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitle;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.srv.service.InviteFansService;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
@Service
public class InviteFansServiceImpl implements InviteFansService {

    @Autowired
    private PointDetailService pointDetailService;

    @Autowired
    private PropertyInfoService propertyInfoService;

    @Override
    @Transactional
    public void inviteFans(String userNum, Integer consumePoint) {
        //插入积分明细
        PointDetailSaveDataParam pointDetailSaveDataParam = new PointDetailSaveDataParam();
        pointDetailSaveDataParam.setTitle(TransactionTitle.INVITE_FANS);
        pointDetailSaveDataParam.setPointNum(StringUtil.getRandomNum(""));
        pointDetailSaveDataParam.setUserNum(userNum);
        pointDetailSaveDataParam.setPointType(MerchantTransactionTypeEnum.INVITE_FANS.getValue());
        pointDetailSaveDataParam.setPoint(new BigDecimal(consumePoint));
        pointDetailService.save(pointDetailSaveDataParam);

        //更新用户资产
        BigDecimal decimal = new BigDecimal(consumePoint);
        propertyInfoService.updatePropertyNumbers(userNum, "P", "M", decimal);
    }
}
