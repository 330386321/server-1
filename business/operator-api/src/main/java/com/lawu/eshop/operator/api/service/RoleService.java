package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.dto.RoleDTO;
import com.lawu.eshop.operator.param.RoleParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhangyong
 * @date 2017/4/21.
 */
@FeignClient(value = "operator-srv")
public interface RoleService {

    @RequestMapping(value = "role/findroleList",method = RequestMethod.POST)
    Result<Page<RoleDTO>> findroleList(@ModelAttribute RoleParam param);
}
