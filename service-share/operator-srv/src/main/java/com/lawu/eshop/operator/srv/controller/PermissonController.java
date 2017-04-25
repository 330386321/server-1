package com.lawu.eshop.operator.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.operator.dto.PermissionListDTO;
import com.lawu.eshop.operator.dto.PermissionDTO;
import com.lawu.eshop.operator.param.PermissionParam;
import com.lawu.eshop.operator.param.PerssionParam;
import com.lawu.eshop.operator.srv.bo.PermissionBO;
import com.lawu.eshop.operator.srv.bo.PerssionInfoListBO;
import com.lawu.eshop.operator.srv.bo.UserBO;
import com.lawu.eshop.operator.srv.converter.PermissionConverter;
import com.lawu.eshop.operator.srv.service.PermissonService;
import com.lawu.eshop.operator.srv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
@RestController
@RequestMapping(value = "permission")
public class PermissonController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissonService perssionService;

    /**
     * 查询权限信息
     *
     * @return
     */
    @RequestMapping(value = "findPermissionByAccount/{account}", method = RequestMethod.GET)
    public Result<List<PermissionDTO>> findPermissionByAccount(@PathVariable("account") String account) {
        UserBO userBO = userService.find(account);
        if (userBO == null) {
            return successGet(ResultCode.USER_WRONG_ID);
        }
        List<PerssionInfoListBO>  perssionInfoListBOS = userService.findRolePermissionList(userBO.getId());
        if(perssionInfoListBOS == null){
            return successGet(ResultCode.ROLE_HAS_NOPERMISSION);
        }
        List<PermissionDTO> perssionDTOS = PermissionConverter.coverDTOS(perssionInfoListBOS);
        return successGet(perssionDTOS);
    }

    @RequestMapping(value = "addPerssion", method = RequestMethod.POST)
    public Result addPerssion(@RequestBody PerssionParam perssionParam) {
        Integer id = perssionService.addPerssion(perssionParam);
        if (id == null || id <= 0) {
            return successCreated(ResultCode.SAVE_FAIL);
        }
        return successCreated(ResultCode.SUCCESS);
    }

    /**
     * 查询权限列表记录
     * @param param
     * @return
     */
    @RequestMapping(value = "findPerminnionList", method = RequestMethod.POST)
    public Result<Page<PermissionListDTO>> findPerminnionList(@RequestBody PermissionParam param) {

        Page<PermissionListDTO> pages = new Page<>();
        Page<PermissionBO> boPage = perssionService.findPerminnionList(param);
        if (boPage == null || boPage.getRecords().isEmpty()) {
            pages.setRecords(new ArrayList<>());
            return successGet(pages);
        }
        List<PermissionListDTO> listDTOList = new ArrayList<>();
        for (PermissionBO permissionBO : boPage.getRecords()) {
            PermissionListDTO permissionListDTO = PermissionConverter.coverDTO(permissionBO);
            listDTOList.add(permissionListDTO);
        }
        pages.setTotalCount(boPage.getTotalCount());
        pages.setCurrentPage(boPage.getCurrentPage());
        pages.setRecords(listDTOList);
        return successGet(pages);
    }


}
