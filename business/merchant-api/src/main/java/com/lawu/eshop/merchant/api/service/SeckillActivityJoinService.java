package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.dto.SeckillActivityJoinDTO;
import com.lawu.eshop.product.param.SeckillActivityJoinParam;


/**
 * 参加抢购活动服务api接口
 * @author zhangrc
 *
 */
@FeignClient(value= "product-srv")
public interface SeckillActivityJoinService {

	/**
	 * 专场活动列表
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "seckillActivityJoin/queryPage", method = RequestMethod.POST)
	Result<Page<SeckillActivityJoinDTO>> queryPage(@RequestBody SeckillActivityJoinParam param);
}
