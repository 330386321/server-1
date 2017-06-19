package com.lawu.eshop.user.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.property.StoreStatusNotification;
import com.lawu.eshop.mq.dto.user.MerchantStatusEnum;
import com.lawu.eshop.solr.SolrUtil;
import com.lawu.eshop.user.srv.UserSrvConfig;
import com.lawu.eshop.user.srv.bo.MerchantStoreInfoBO;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangyong
 * @date 2017/6/7.
 */
@Service("handleDepositAuditCancelTransactionFollowServiceImpl")
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_PROPERTY_SRV, tags = MqConstant.TAG_HANDLE_DEPOSIT_AUDIT_CANCEL)
public class HandleDepositAuditCancelTransactionFollowServiceImpl extends AbstractTransactionFollowService<StoreStatusNotification, Reply> {
    @Autowired
    private MerchantStoreInfoService merchantStoreInfoService;
    @Autowired
    private UserSrvConfig userSrvConfig;

    /**
     *
     */
    @Transactional
    @Override
    public Reply execute(StoreStatusNotification notification) {
        Reply rtn = null;

        if (notification == null) {
            return rtn;
        }

        rtn = new Reply();
        MerchantStoreInfoBO storeInfoBO = merchantStoreInfoService.selectMerchantStoreByMId(notification.getMerchantId());
        if (storeInfoBO == null) {
            return null;
        }
        merchantStoreInfoService.updateMerchantStoreStatus(notification.getMerchantId(), MerchantStatusEnum.MERCHANT_STATUS_UNCHECK.val);

        //删除solr门店信息
        SolrUtil.delSolrDocsById(storeInfoBO.getMerchantStoreId(), userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore());
        return rtn;
    }
}