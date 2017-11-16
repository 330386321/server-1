package com.lawu.eshop.jobs.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.dto.AdCommissionResultDTO;
import com.lawu.eshop.property.param.CommissionResultParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.param.CommissionJobParam;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.order.dto.ShoppingOrderCommissionDTO;
import com.lawu.eshop.property.constants.LoveTypeEnum;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.jobs.service.CommissionCommonService;
import com.lawu.eshop.jobs.service.CommonPropertyService;
import com.lawu.eshop.jobs.service.OrderSrvService;
import com.lawu.eshop.jobs.service.PropertySrvService;
import com.lawu.eshop.jobs.service.SaleAndVolumeCommissionService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.dto.CommissionInvitersUserDTO;

@Service
public class SaleAndVolumeCommissionServiceImpl implements SaleAndVolumeCommissionService {

    private static Logger logger = LoggerFactory.getLogger(SaleAndVolumeCommissionServiceImpl.class);

    @Autowired
    private CommonPropertyService commonPropertyService;
    @Autowired
    private CommissionCommonService userCommonService;
    @Autowired
    private PropertySrvService propertySrvService;
    @Autowired
    private OrderSrvService orderSrvService;
    @Autowired
    private CommissionUtilImpl commissionUtilImpl;

    @Override
    public void commission(ShoppingOrderCommissionDTO shoppingOrderCommissionDTO, int flag, String msg) {

        Map<String, BigDecimal> property = commonPropertyService.getSaleCommissionPropertys();
        BigDecimal saleCommissionAddScope = property.get(PropertyType.sale_commission_add_scope);// 每上升一个级别提成的幅度
        BigDecimal loveAccountScale = property.get(PropertyType.love_account_scale);// 爱心账户比例
        BigDecimal actualCommissionScope = property.get("acture_in_scale");// 实际提成比例=1-爱心账户(0.003)

        ShoppingOrderCommissionDTO order = shoppingOrderCommissionDTO;

        if ((order.getMemberNum() == null || "".equals(order.getMemberNum())) && (order.getMerchantNum() == null || "".equals(order.getMerchantNum()))) {
            logger.error("[{}]提成数据用户编号和商家编号均为空！orderId={}", msg, order.getId());
            throw new RuntimeException();
        }

        // 分别查询产生交易用户和商家的上3级推荐关系
        List<CommissionInvitersUserDTO> memberInviters = userCommonService.selectHigherLevelInviters(order.getMemberNum(), 3, true);
        List<CommissionInvitersUserDTO> merchantInviters = userCommonService.selectHigherLevelInviters(order.getMerchantNum(), 3, true);

        List<CommissionInvitersUserDTO> inviters = new ArrayList<>();
        inviters.addAll(memberInviters);
        inviters.addAll(merchantInviters);

        int retCode = ResultCode.SUCCESS;
        if (inviters != null && !inviters.isEmpty()) {
            int m = 0;
            for (int i = 0; i < inviters.size(); i++) {
                String userNum = inviters.get(i).getUserNum();
                CommissionJobParam param = new CommissionJobParam();
                param.setUserNum(userNum);
                param.setBizId(order.getId());
                param.setTempBidId(order.getId());
                BigDecimal actualMoney = order.getActualAmount();

                BigDecimal saleCommission = property.get(PropertyType.sale_commission_1);
                if (inviters.get(i).getDept() == 1) {
                    saleCommission = property.get(PropertyType.sale_commission_1);
                } else if (inviters.get(i).getDept() == 2) {
                    saleCommission = property.get(PropertyType.sale_commission_2);
                } else if (inviters.get(i).getDept() == 3) {
                    param.setLast(true);
                    saleCommission = property.get(PropertyType.sale_commission_3);
                }

                BigDecimal level = new BigDecimal(inviters.get(i).getLevel());
                BigDecimal actualCommission = saleCommission.add(saleCommissionAddScope.multiply(level.subtract(new BigDecimal("1"))));//没升一个级别+0.005
                CommissionResultParam commissionResultparam = new CommissionResultParam();
                commissionResultparam.setBeforeMoney(actualMoney);
                commissionResultparam.setCommission0(BigDecimal.valueOf(1));
                commissionResultparam.setCurrentCommission(actualCommission);
                commissionResultparam.setActualCommissionScope(actualCommissionScope);
                commissionResultparam.setLoveAccountScale(loveAccountScale);
                commissionResultparam.setDept(i);
                AdCommissionResultDTO rntDTO = commissionUtilImpl.getCommissionResult(commissionResultparam);
                param.setActureMoneyIn(rntDTO.getActureMoneyIn());
                param.setActureLoveIn(rntDTO.getActureLoveIn());

                if(inviters.get(i).getFlag() == 1){
                    if (userNum.startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
                        param.setTypeVal(MemberTransactionTypeEnum.SALES_COMMISSION.getValue());
                        param.setTypeName(MemberTransactionTypeEnum.SALES_COMMISSION.getName());
                    } else if (userNum.startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
                        param.setTypeVal(MerchantTransactionTypeEnum.SALES_COMMISSION.getValue());
                        param.setTypeName(MerchantTransactionTypeEnum.SALES_COMMISSION.getName());
                    }
                    param.setLoveTypeVal(LoveTypeEnum.SALES_COMMISSION.getValue());
                    param.setLoveTypeName(LoveTypeEnum.SALES_COMMISSION.getName());
                } else if(inviters.get(i).getFlag() == 2){
                    if (userNum.startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
                        param.setTypeVal(MemberTransactionTypeEnum.VOLUME_COMMISSION.getValue());
                        param.setTypeName(MemberTransactionTypeEnum.VOLUME_COMMISSION.getName());
                    } else if (userNum.startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
                        param.setTypeVal(MerchantTransactionTypeEnum.VOLUME_COMMISSION.getValue());
                        param.setTypeName(MerchantTransactionTypeEnum.VOLUME_COMMISSION.getName());
                    }
                    param.setLoveTypeVal(LoveTypeEnum.VOLUME_COMMISSION.getValue());
                    param.setLoveTypeName(LoveTypeEnum.VOLUME_COMMISSION.getName());
                }

                logger.info("[{}]订单ID={}，交易用户编号={}，交易商家编号={}，获得提成账号编号：{}；基础金额(a)：{}，提成基础金额比例(b)=1，实际提成比例(c)：{}（初始提成比例(c1)={}, 等级(c2)={},每上升一级幅度累加比例(c3)={}）；所得：实际提成金额：{}，爱心账户金额：{}", msg, order.getId(), order.getMemberNum(), order.getMerchantNum(), userNum, actualMoney, actualCommission, saleCommission, level, saleCommissionAddScope, rntDTO.getActureMoneyIn(),rntDTO.getActureLoveIn());

                try{
                    retCode = propertySrvService.calculation(param);
                    if (ResultCode.SUCCESS == retCode) {
                        m++;
                    }
                }catch (Exception e){
                    throw new RuntimeException();
                }
            }
            // 所有上线提成计算成功才算成功
            if (m != inviters.size()) {
                throw new RuntimeException();
            }
        }

        // 修改订单是否计算提成状态
        if (ResultCode.SUCCESS == retCode) {
            List<Long> successOrderIds = new ArrayList<Long>();
            successOrderIds.add(order.getId());
            if (flag == 1) {
                Result result = orderSrvService.updatePayOrderCommissionStatus(successOrderIds);
                if (result.getRet() != ResultCode.SUCCESS) {
                    logger.error("{}更新订单状态返回错误,retCode={},订单ID={}", msg, result.getRet(),order.getId());
                    throw new RuntimeException();
                }
            } else if (flag == 2) {
                Result result = orderSrvService.updateCommissionStatus(successOrderIds);
                if (result.getRet() != ResultCode.SUCCESS) {
                    logger.error("{}更新订单状态返回错误,retCode={},订单ID={}", msg, result.getRet(),order.getId());
                    throw new RuntimeException();
                }
            }
        }
    }

}
