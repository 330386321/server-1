package com.lawu.eshop.user.srv.service;

import com.lawu.eshop.user.dto.MerchantProfileDTO;
import com.lawu.eshop.user.dto.param.MerchantProfileParam;
import com.lawu.eshop.user.srv.bo.MerchantProfileBO;

/**
 * Created by Administrator on 2017/3/23.
 */
public interface MerchantProfileService {

    /**
     * 设置相关网站链接
     * @param merchantProfileParamd
     */
    void updateMerchantSizeLink(MerchantProfileParam merchantProfileParamd);

    /**
     * 查询商家主页
     * @param merchantProfileId
     * @return
     */
    MerchantProfileBO findMerchantProfileInfo(Long merchantProfileId);
}
