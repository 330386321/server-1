package com.lawu.eshop.statistics.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.param.UserRegAreaParam;
import com.lawu.eshop.statistics.srv.service.UserRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/6/29.
 */
@RestController
@RequestMapping(value = "userReg/")
public class UserRegController extends BaseController {

    @Autowired
    private UserRegService userRegService;

    /**
     * 按日统计用户注册量
     *
     * @param memberCount
     * @param merchantCount
     */
    @RequestMapping(value = "saveUserRegDaily", method = RequestMethod.POST)
    public Result saveUserRegDaily(@RequestParam Integer memberCount, @RequestParam Integer merchantCount) {
        userRegService.saveUserRegDaily(memberCount, merchantCount);
        return successCreated();
    }

    /**
     * 按月统计用户注册量
     *
     * @param memberCount
     * @param merchantCount
     */
    @RequestMapping(value = "saveUserRegMonth", method = RequestMethod.POST)
    public Result saveUserRegMonth(@RequestParam Integer memberCount, @RequestParam Integer merchantCount) {
        userRegService.saveUserRegMonth(memberCount, merchantCount);
        return successCreated();
    }

    /**
     * 按市级更新用户注册量
     *
     * @param param
     */
    @RequestMapping(value = "updateUserRegArea", method = RequestMethod.POST)
    public Result updateUserRegArea(@RequestBody UserRegAreaParam param) {
        userRegService.updateUserRegArea(param);
        return successCreated();
    }

}
