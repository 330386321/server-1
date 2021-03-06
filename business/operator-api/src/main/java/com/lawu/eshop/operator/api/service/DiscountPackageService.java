package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.DiscountPackageQueryDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author meishuquan
 * @date 2017/8/1.
 */
@FeignClient(value = "mall-srv", path = "discountPackage/")
public interface DiscountPackageService {

    /**
     * 根据商家id查询商家的所有优惠套餐<p>
     * 用户端
     *
     * @param merchantId 商家id
     * @return
     * @author Sunny
     * @date 2017年6月26日
     */
    @RequestMapping(value = "member/list/{merchantId}", method = RequestMethod.GET)
    Result<Page<DiscountPackageQueryDTO>> listForMember(@PathVariable("merchantId") Long merchantId);

}
