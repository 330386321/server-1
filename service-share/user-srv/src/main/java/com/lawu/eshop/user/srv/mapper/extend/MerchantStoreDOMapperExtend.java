package com.lawu.eshop.user.srv.mapper.extend;

import com.lawu.eshop.user.param.ListMerchantStoreParam;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.extend.MerchantPushView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
public interface MerchantStoreDOMapperExtend {
    Integer  addMerchantStoreBuyNums(Long merchantId);

    List<MerchantPushView> selectPushInfo(@Param("area") String area);

    List<MerchantStoreDO> listMerchantStore(ListMerchantStoreParam listMerchantStoreParam);
}
