package com.lawu.eshop.jobs.service.impl;

import java.util.List;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.IncomeMsgService;
import com.lawu.eshop.jobs.service.MessageService;
import com.lawu.eshop.jobs.service.PropertyPointDetailService;
import com.lawu.eshop.jobs.service.PropertyTransactionDetailService;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.mall.param.MessageTempParam;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.dto.IncomeMsgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yangqh
 * @date 2017/7/3.
 */
@Service
public class IncomeMsgImpl implements IncomeMsgService {

    @Autowired
    private PropertyTransactionDetailService propertyTransactionDetailService;
    @Autowired
    private PropertyPointDetailService propertyPointDetailService;
    @Autowired
    private MessageService messageService;

    @Override
    public void execute() {

        //查询昨天+（推荐E友收益、推荐商家收益）数据
        Result<List<IncomeMsgDTO>> transactionDetailListResult = propertyTransactionDetailService.getIncomeMsgDataList();
        Result<List<IncomeMsgDTO>> pointDetailListResult = propertyPointDetailService.getIncomeMsgDataList();
        for(IncomeMsgDTO dto : transactionDetailListResult.getModel()){
            MessageInfoParam messageInfoParam = new MessageInfoParam();
            messageInfoParam.setRelateId(0L);
            MessageTempParam messageTempParam = new MessageTempParam();
            messageTempParam.setEarningAmount(dto.getMoney());
            if(dto.getUserNum().startsWith("B")){
                messageTempParam.setUserName("E店商家");

            } else if(dto.getUserNum().startsWith("M")){
                messageTempParam.setUserName("E店用户");
            }
            if(MerchantTransactionTypeEnum.SALES_COMMISSION.getValue().equals(dto.getType()) ||
                    MemberTransactionTypeEnum.SALES_COMMISSION.getValue().equals(dto.getType())){
                messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_RECOMMEND_MEMBER_BALANCE);
            }
            if(MerchantTransactionTypeEnum.VOLUME_COMMISSION.getValue().equals(dto.getType()) ||
                    MemberTransactionTypeEnum.VOLUME_COMMISSION.getValue().equals(dto.getType())){
                messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_RECOMMEND_MERCHANT_BALANCE);
            }
            messageInfoParam.setMessageParam(messageTempParam);
            messageService.saveMessage(dto.getUserNum(), messageInfoParam);
        }
        for(IncomeMsgDTO dto : pointDetailListResult.getModel()){
            MessageInfoParam messageInfoParam = new MessageInfoParam();
            messageInfoParam.setRelateId(0L);
            MessageTempParam messageTempParam = new MessageTempParam();
            messageTempParam.setEarningPoint(dto.getMoney());
            if(dto.getUserNum().startsWith("B")){
                messageTempParam.setUserName("E店商家");
            } else if(dto.getUserNum().startsWith("M")){
                messageTempParam.setUserName("E店用户");
            }
            if(MerchantTransactionTypeEnum.SALES_COMMISSION.getValue().equals(dto.getType()) ||
                    MemberTransactionTypeEnum.SALES_COMMISSION.getValue().equals(dto.getType())){
                messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_RECOMMEND_MEMBER_POINT);
            }
            if(MerchantTransactionTypeEnum.VOLUME_COMMISSION.getValue().equals(dto.getType()) ||
                    MemberTransactionTypeEnum.VOLUME_COMMISSION.getValue().equals(dto.getType())){
                messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_RECOMMEND_MERCHANT_POINT);
            }
            messageInfoParam.setMessageParam(messageTempParam);
            messageService.saveMessage(dto.getUserNum(), messageInfoParam);
        }
    }
}
