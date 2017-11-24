package com.lawu.eshop.mall.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.param.LotteryRecordParam;
import com.lawu.eshop.mall.srv.service.LotteryRecordService;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
@RestController
@RequestMapping(value = "lotteryRecord/")
public class LotteryRecordController extends BaseController {

    @Autowired
    private LotteryRecordService lotteryRecordService;

    /**
     * 保存抽奖记录
     *
     * @param param
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "saveLotteryRecord", method = RequestMethod.POST)
    public Result listLotteryActivity(@RequestBody LotteryRecordParam param) {
        lotteryRecordService.saveLotteryRecord(param);
        return successCreated();
    }

}
