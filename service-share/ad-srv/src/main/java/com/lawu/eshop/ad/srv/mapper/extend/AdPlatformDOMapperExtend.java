package com.lawu.eshop.ad.srv.mapper.extend;

import com.lawu.eshop.ad.param.AdPlatformExtendParam;
import com.lawu.eshop.ad.srv.domain.extend.AdPlatformDOView;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/6/16.
 */
public interface AdPlatformDOMapperExtend {

    List<AdPlatformDOView> getAdPlatformByTypePosition(AdPlatformExtendParam param);
}
