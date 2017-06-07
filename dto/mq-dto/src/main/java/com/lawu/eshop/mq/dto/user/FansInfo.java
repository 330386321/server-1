package com.lawu.eshop.mq.dto.user;

import java.io.Serializable;

/**
 * @author zhangyong
 * @date 2017/5/18.
 */
public class FansInfo implements Serializable {

	private static final long serialVersionUID = -3621898811051179446L;

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
