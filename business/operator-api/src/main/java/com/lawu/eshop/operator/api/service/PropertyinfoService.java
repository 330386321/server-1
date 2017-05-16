package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.param.BackagePropertyinfoDataParam;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年5月16日 下午2:32:29
 *
 */
@FeignClient(value = "property-srv")
public interface PropertyinfoService {


    @SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "propertyInfo/updateBalanceAndPoint")
    Result updateBalanceAndPoint(@RequestBody BackagePropertyinfoDataParam dparam);

}
