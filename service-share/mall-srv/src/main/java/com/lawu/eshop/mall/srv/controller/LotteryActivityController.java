package com.lawu.eshop.mall.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.LotteryActivityDTO;
import com.lawu.eshop.mall.param.LotteryActivityRealParam;
import com.lawu.eshop.mall.srv.bo.LotteryActivityBO;
import com.lawu.eshop.mall.srv.converter.LotteryActivityConverter;
import com.lawu.eshop.mall.srv.service.LotteryActivityService;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
@RestController
@RequestMapping(value = "lotteryActivity/")
public class LotteryActivityController extends BaseController {

    @Autowired
    private LotteryActivityService lotteryActivityService;

    /**
     * 抽奖活动列表
     *
     * @param param
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "listLotteryActivity", method = RequestMethod.POST)
    public Result<Page<LotteryActivityDTO>> listLotteryActivity(@RequestBody LotteryActivityRealParam param) {
        Page<LotteryActivityBO> activityBOPage = lotteryActivityService.listLotteryActivity(param);
        Page<LotteryActivityDTO> page = new Page<>();
        page.setCurrentPage(activityBOPage.getCurrentPage());
        page.setTotalCount(activityBOPage.getTotalCount());
        page.setRecords(LotteryActivityConverter.converDTOS(activityBOPage.getRecords()));
        return successCreated(page);
    }

}
