package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.annotation.PageBody;
import com.lawu.eshop.mall.dto.SuggestionDTO;
import com.lawu.eshop.mall.param.SuggestionListParam;
import com.lawu.eshop.operator.api.service.SuggestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PageBody
    @ApiOperation(value = "意见记录列表", notes = "可以根据时间段查询已经反馈记录 [1004,1002]", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "suggestion/getSuggestionList", method = RequestMethod.GET)
    public Result<Page<SuggestionDTO>> getSuggestionList(@ModelAttribute SuggestionListParam pageParam) {
        if (pageParam == null) {
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        Result<Page<SuggestionDTO>> pageResult = suggestionService.getSuggestionList(pageParam);
        return pageResult;
    }
    
    @ApiOperation(value = "删除反馈意见", notes = "删除反馈意见 []（张荣成）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "delSuggestion/{id}", method = RequestMethod.DELETE)
    public Result delCommentProductInfo(@PathVariable("id") @ApiParam(value = "反馈ID",required = true) Long id){
        if(id == null){
            return successDelete(ResultCode.REQUIRED_PARM_EMPTY);
        }
        return  successDelete(suggestionService.delSuggestion(id));
    }
    
    @ApiOperation(value = "批量删除反馈意见", notes = "批量删除反馈意见 []（张荣成）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @RequestMapping(value = "batchelDelSuggestion/", method = RequestMethod.DELETE)
    public Result batchelDelSuggestion(@RequestParam @ApiParam(value = "反馈ID集合 以逗号隔开",required = true) String ids){
        if(ids == null){
            return successDelete(ResultCode.REQUIRED_PARM_EMPTY);
        }
        String[] commentIds=ids.split(",");
        for (String id : commentIds) {
        	 suggestionService.delSuggestion(Long.parseLong(id));
		}
       
        return  successDelete();
    }
}
