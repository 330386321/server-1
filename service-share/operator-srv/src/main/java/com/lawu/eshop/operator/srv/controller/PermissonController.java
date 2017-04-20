package com.lawu.eshop.operator.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.operator.dto.PerssionDTO;
import com.lawu.eshop.operator.param.PerssionParam;
import com.lawu.eshop.operator.srv.bo.PerssionInfoListBO;
import com.lawu.eshop.operator.srv.bo.UserBO;
import com.lawu.eshop.operator.srv.service.PermissonService;
import com.lawu.eshop.operator.srv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
@RestController
@RequestMapping(value = "perssion")
public class PermissonController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private PermissonService perssionService;
    /**
     * 查询权限信息
     *
     * @return
     */
    @RequestMapping(value = "findPessionByAccount/{account}", method = RequestMethod.GET)
    public Result<PerssionDTO> findPessionByAccount(@PathVariable("account") String account) {
        UserBO userBO = userService.find(account);
        if (userBO == null) {
            return successGet(ResultCode.USER_WRONG_ID);
        }
        PerssionInfoListBO perssionInfoListBO = perssionInfoListBO = userService.findRolePermissionList(userBO.getId());
        PerssionDTO perssionDTO = new PerssionDTO();
        perssionDTO.setPermissionInfo(perssionInfoListBO.getPerssionInfo());
        return successGet(perssionDTO);
    }

    @RequestMapping(value = "addPerssion",method = RequestMethod.POST)
    Result addPerssion(@ModelAttribute PerssionParam perssionParam){
       Integer id =  perssionService.addPerssion(perssionParam);
        return  successCreated();
    }
}
