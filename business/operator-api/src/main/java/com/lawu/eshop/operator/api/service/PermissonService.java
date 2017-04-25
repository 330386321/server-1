package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.dto.PermissionDTO;
import com.lawu.eshop.operator.dto.PermissionListDTO;
import com.lawu.eshop.operator.param.PermissionParam;
import com.lawu.eshop.operator.param.PerssionParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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
    @RequestMapping(value = "permission/findPermissionByAccount/{account}",method = RequestMethod.GET)
    Result<List<PermissionDTO>>findPermissionByAccount(@PathVariable("account") String account);

    /**
     * 增加权限记录
     * @param perssionParam
     * @return
     */
    @RequestMapping(value = "permission/addPerssion",method = RequestMethod.POST)
    Result addPerssion(@ModelAttribute PerssionParam perssionParam);

    /**
     * 查询权限记录列表
     * @param param
     * @return
     */
    @RequestMapping(value = "permission/findPerminnionList", method = RequestMethod.POST)
    Result<Page<PermissionListDTO>> findPerminnionList(@ModelAttribute PermissionParam param);
}
