package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.dto.PermissionListDTO;
import com.lawu.eshop.operator.dto.PerssionDTO;
import com.lawu.eshop.operator.param.PermissionParam;
import com.lawu.eshop.operator.param.PerssionParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
@FeignClient(value = "operator-srv")
public interface PermissonService {
    /**
     * 查询当前用户的权限信息
     * @param account
     * @return
     */
    @RequestMapping(value = "perssion/findPessionByAccount/{account}",method = RequestMethod.GET)
    Result<PerssionDTO> findPessionByAccount(@PathVariable("account") String account);

    /**
     * 增加权限记录
     * @param perssionParam
     * @return
     */
    @RequestMapping(value = "perssion/addPerssion",method = RequestMethod.POST)
    Result addPerssion(@ModelAttribute PerssionParam perssionParam);

    /**
     * 查询权限记录列表
     * @param param
     * @return
     */
    @RequestMapping(value = "perssion/findPerminnionList", method = RequestMethod.POST)
    Result<Page<PermissionListDTO>> findPerminnionList(@ModelAttribute PermissionParam param);
}
