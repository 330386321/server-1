package com.lawu.eshop.operator.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.dto.RoleDTO;
import com.lawu.eshop.operator.param.RoleParam;
import com.lawu.eshop.operator.srv.bo.RoleBO;
import com.lawu.eshop.operator.srv.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyong
 * @date 2017/4/21.
 */
@RestController
@RequestMapping(value = "role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询角色信息列表
     * @param param
     * @return
     */
    @RequestMapping(value = "findroleList",method = RequestMethod.POST)
    public Result<Page<RoleDTO>> findroleList(@RequestBody RoleParam param){

        Page<RoleBO> boPage = roleService.findroleList(param);
        return successGet();
    }


}
