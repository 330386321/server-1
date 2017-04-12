package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.param.PropertyInfoDataParam;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月12日 下午12:53:13
 *
 */
public interface PropertyInfoDataService {

    /**
     * 邀请粉丝消费积分
     *
     * @param userNum      商户编号
     * @param consumePoint 消费积分
     */
    void inviteFans(String userNum, Integer consumePoint);

    /**
     * 商家发广告
     * @param param
     * @return
     */
	@SuppressWarnings("rawtypes")
	int addAd(PropertyInfoDataParam param);
}
