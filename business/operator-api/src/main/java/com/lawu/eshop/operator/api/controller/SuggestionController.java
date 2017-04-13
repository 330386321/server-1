package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.mall.dto.SuggestionDTO;
import com.lawu.eshop.mall.param.SuggestionListParam;
import com.lawu.eshop.operator.api.service.SuggestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
@Api(tags = "suggestionOperator")
@RestController
@RequestMapping(value = "suggestionOperator/")
public class SuggestionController extends BaseController {

    @Autowired
    private SuggestionService suggestionService;

    @ApiOperation(value = "意见记录列表", notes = "可以根据时间段查询已经反馈记录 [1004,1002]", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "suggestion/getSuggestionList", method = RequestMethod.POST)
    public Result<Page<SuggestionDTO>> getSuggestionList(@ModelAttribute SuggestionListParam pageParam) {
        if (pageParam == null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Result<Page<SuggestionDTO>> pageResult = suggestionService.getSuggestionList(pageParam);
        return pageResult;
    }
}
