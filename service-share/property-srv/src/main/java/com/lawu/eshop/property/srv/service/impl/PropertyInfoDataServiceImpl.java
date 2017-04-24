package com.lawu.eshop.property.srv.service.impl;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.PropertyInfoDirectionEnum;
import com.lawu.eshop.property.param.PointDetailSaveDataParam;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.property.srv.domain.FansInviteDetailDO;
import com.lawu.eshop.property.srv.mapper.FansInviteDetailDOMapper;
import com.lawu.eshop.property.srv.service.PointDetailService;
import com.lawu.eshop.property.srv.service.PropertyInfoDataService;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Description:与积分相关业务处理(加、减)
 * </p>
 *
 * @author Yangqh
 * @date 2017年4月12日 下午12:54:03
 */
@Service
public class PropertyInfoDataServiceImpl implements PropertyInfoDataService {

    @Autowired
    private PointDetailService pointDetailService;

    @Autowired
    private PropertyInfoService propertyInfoService;

    @Autowired
    private FansInviteDetailDOMapper fansInviteDetailDOMapper;

    @Override
    @Transactional
    public int doHanlderMinusPoint(PropertyInfoDataParam param) {
        int retCode = propertyInfoService.validatePoint(param.getUserNum(), param.getPoint());
        if (retCode != ResultCode.SUCCESS) {
            return retCode;
        }
        String pointNum = StringUtil.getRandomNum("");
        // 插入积分明细
        PointDetailSaveDataParam pointDetailSaveDataParam = new PointDetailSaveDataParam();
        pointDetailSaveDataParam.setTitle(param.getTransactionTitleEnum().val);
        pointDetailSaveDataParam.setPointNum(pointNum);
        pointDetailSaveDataParam.setUserNum(param.getUserNum());
        if (param.getMemberTransactionTypeEnum() != null) {
            pointDetailSaveDataParam.setPointType(param.getMemberTransactionTypeEnum().getValue());
        } else if (param.getMerchantTransactionTypeEnum() != null) {
            pointDetailSaveDataParam.setPointType(param.getMerchantTransactionTypeEnum().getValue());
        } else {
            return ResultCode.BIZ_TYPE_NULL;
        }
        pointDetailSaveDataParam.setPoint(new BigDecimal(param.getPoint()));
        pointDetailSaveDataParam.setDirection(PropertyInfoDirectionEnum.OUT.val);
        pointDetailService.save(pointDetailSaveDataParam);

        //插入邀请粉丝记录
        if (param.getMerchantTransactionTypeEnum() != null && param.getMerchantTransactionTypeEnum().getValue() == MerchantTransactionTypeEnum.INVITE_FANS.getValue()) {
            FansInviteDetailDO fansInviteDetailDO = new FansInviteDetailDO();
            fansInviteDetailDO.setMerchantId(param.getMerchantId());
            fansInviteDetailDO.setPointNum(pointNum);
            fansInviteDetailDO.setRegionName(param.getRegionName());
            fansInviteDetailDO.setInviteFansCount(param.getInviteFansCount());
            fansInviteDetailDO.setConsumePoint(new BigDecimal(param.getPoint()));
            fansInviteDetailDO.setGmtCreate(new Date());
            fansInviteDetailDOMapper.insertSelective(fansInviteDetailDO);
        }

        // 更新用户资产
        BigDecimal point = new BigDecimal(param.getPoint());
        propertyInfoService.updatePropertyNumbers(param.getUserNum(), "P", "M", point);

        return ResultCode.SUCCESS;
    }

    @Override
    @Transactional
    public int doHanlderAddPoint(PropertyInfoDataParam param) {

        // 插入积分明细
        PointDetailSaveDataParam pointDetailSaveDataParam = new PointDetailSaveDataParam();
        pointDetailSaveDataParam.setTitle(param.getTransactionTitleEnum().val);
        pointDetailSaveDataParam.setPointNum(StringUtil.getRandomNum(""));
        pointDetailSaveDataParam.setUserNum(param.getUserNum());
        if (param.getMemberTransactionTypeEnum() != null) {
            pointDetailSaveDataParam.setPointType(param.getMemberTransactionTypeEnum().getValue());
        } else if (param.getMerchantTransactionTypeEnum() != null) {
            pointDetailSaveDataParam.setPointType(param.getMerchantTransactionTypeEnum().getValue());
        } else {
            return ResultCode.BIZ_TYPE_NULL;
        }
        pointDetailSaveDataParam.setPoint(new BigDecimal(param.getPoint()));
        pointDetailSaveDataParam.setDirection(PropertyInfoDirectionEnum.IN.val);
        pointDetailService.save(pointDetailSaveDataParam);

        // 更新用户资产
        BigDecimal point = new BigDecimal(param.getPoint());
        propertyInfoService.updatePropertyNumbers(param.getUserNum(), "P", "A", point);

        return ResultCode.SUCCESS;
    }
}
