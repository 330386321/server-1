package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.dto.PerssionDTO;
import com.lawu.eshop.operator.param.PerssionParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
@FeignClient(value = "operator-srv")
public interface PermissonService {
    @RequestMapping(value = "perssion/findPessionByAccount/{account}",method = RequestMethod.GET)
    Result<PerssionDTO> findPessionByAccount(@PathVariable("account") String account);

    @RequestMapping(value = "perssion/addPerssion",method = RequestMethod.POST)
    Result addPerssion(@ModelAttribute PerssionParam perssionParam);
}
