package com.lawu.eshop.user.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;

/**
 * @author meishuquan
 * @date 2017/5/10.
 */
public class ListMerchantStoreParam extends AbstractPageParam{

    private Byte status;

    private Byte manageType;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getManageType() {
        return manageType;
    }

    public void setManageType(Byte manageType) {
        this.manageType = manageType;
    }
}
