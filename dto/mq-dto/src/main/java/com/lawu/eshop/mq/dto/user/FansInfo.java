package com.lawu.eshop.mq.dto.user;

/**
 * @author zhangyong
 * @date 2017/5/18.
 */
public class FansInfo {

    private Long memberId;

    private Long merchantId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
}