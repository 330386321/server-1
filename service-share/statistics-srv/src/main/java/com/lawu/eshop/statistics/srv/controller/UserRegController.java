package com.lawu.eshop.statistics.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.ReportUserRegAreaDTO;
import com.lawu.eshop.statistics.dto.ReportUserRegDTO;
import com.lawu.eshop.statistics.param.UserRegAreaParam;
import com.lawu.eshop.statistics.param.UserRegParam;
import com.lawu.eshop.statistics.srv.bo.ReportUserRegAreaBO;
import com.lawu.eshop.statistics.srv.converter.ReportUserRegConverter;
import com.lawu.eshop.statistics.srv.domain.extend.ReportUserRegDOView;
import com.lawu.eshop.statistics.srv.service.UserRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 查询日统计列表
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "getReportUserRegDaily", method = RequestMethod.POST)
    public Result<List<ReportUserRegDTO>> getReportUserRegDaily(@RequestBody UserRegParam param) {
        List<ReportUserRegDOView> regDOViews = userRegService.getReportUserRegDaily(param);
        return successGet(ReportUserRegConverter.convertDTO(regDOViews));
    }

    /**
     * 查询月统计列表
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "getReportUserRegMonth", method = RequestMethod.POST)
    public Result<List<ReportUserRegDTO>> getReportUserRegMonth(@RequestBody UserRegParam param) {
        List<ReportUserRegDOView> regDOViews = userRegService.getReportUserRegMonth(param);
        return successGet(ReportUserRegConverter.convertDTO(regDOViews));
    }

    /**
     * 查询区域统计列表
     *
     * @return
     */
    @RequestMapping(value = "getReportUserRegArea", method = RequestMethod.GET)
    public Result<List<ReportUserRegAreaDTO>> getReportUserRegArea() {
        List<ReportUserRegAreaBO> regAreaBOList = userRegService.getReportUserRegArea();
        return successGet(ReportUserRegConverter.convertAreaDTO(regAreaBOList));
    }

}
