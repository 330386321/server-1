package com.lawu.eshop.operator.api.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.mall.constants.LotteryActivityStatusEnum;
import com.lawu.eshop.mall.dto.LotteryActivityOperatorDTO;
import com.lawu.eshop.mall.param.LotteryActivityParam;
import com.lawu.eshop.mall.query.ListLotteryActivityQuery;
import com.lawu.eshop.operator.api.service.LotteryActivityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author meishuquan
 * @date 2017/11/24.
 */
@Api(tags = "lotteryActivity")
@RestController
@RequestMapping(value = "lotteryActivity/")
public class LotteryActivityController extends BaseController {

    @Autowired
    private LotteryActivityService lotteryActivityService;

    @ApiOperation(value = "抽奖活动列表", notes = "抽奖活动列表。（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("lottery:list")
    @PageBody
    @RequestMapping(value = "listOperatorLotteryActivity", method = RequestMethod.POST)
    public Result<Page<LotteryActivityOperatorDTO>> listOperatorLotteryActivity(@RequestBody @ApiParam ListLotteryActivityQuery query) {
        return lotteryActivityService.listOperatorLotteryActivity(query);
    }

    @ApiOperation(value = "发布活动", notes = "发布活动。[1002]（梅述全）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("lottery:publish")
    @RequestMapping(value = "publishLotteryActivity/{id}", method = RequestMethod.PUT)
    public Result publishLotteryActivity(@PathVariable @ApiParam(name = "id", required = true, value = "活动ID") Long id) {
        return lotteryActivityService.updateLotteryActivityStatusById(id, LotteryActivityStatusEnum.PUBLISHED);
    }

    @ApiOperation(value = "下架活动", notes = "下架活动。[1002]（梅述全）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("lottery:cancel")
    @RequestMapping(value = "cancelLotteryActivity/{id}", method = RequestMethod.PUT)
    public Result cancelLotteryActivity(@PathVariable @ApiParam(name = "id", required = true, value = "活动ID") Long id) {
        return lotteryActivityService.updateLotteryActivityStatusById(id, LotteryActivityStatusEnum.CANCEL);
    }

    @ApiOperation(value = "删除活动", notes = "删除活动。[1002]（梅述全）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("lottery:del")
    @RequestMapping(value = "delLotteryActivity/{id}", method = RequestMethod.PUT)
    public Result delLotteryActivity(@PathVariable @ApiParam(name = "id", required = true, value = "活动ID") Long id) {
        return lotteryActivityService.updateLotteryActivityStatusById(id, LotteryActivityStatusEnum.DELETE);
    }

    @ApiOperation(value = "根据id查询抽奖活动", notes = "根据id查询抽奖活动。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getOperatorLotteryActivity/{id}", method = RequestMethod.GET)
    public Result<LotteryActivityOperatorDTO> getOperatorLotteryActivity(@PathVariable @ApiParam(name = "id", required = true, value = "活动ID") Long id) {
        return lotteryActivityService.getOperatorLotteryActivity(id);
    }

    @ApiOperation(value = "新增抽奖活动", notes = "新增抽奖活动。（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequiresPermissions("lottery:add")
    @RequestMapping(value = "saveLotteryActivity", method = RequestMethod.POST)
    public Result saveLotteryActivity(@ModelAttribute LotteryActivityParam param) {
        return lotteryActivityService.saveLotteryActivity(param);
    }

}
