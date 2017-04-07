package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.constants.SearchWordTypeEnum;
import com.lawu.eshop.mall.dto.SearchWordDTO;
import com.lawu.eshop.mall.param.SearchWordParam;
import com.lawu.eshop.operator.api.service.SearchWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/4/7.
 */
@Api(tags = "searchWord")
@RestController
@RequestMapping(value = "searchWord/")
public class SearchWordController extends BaseController {

    @Autowired
    private SearchWordService searchWordService;

    @ApiOperation(value = "新增搜索词条", notes = "新增搜索词条。（梅述全）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "saveSearchWord", method = RequestMethod.POST)
    public Result saveSearchWord(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                 @RequestParam @ApiParam(name = "word", required = true, value = "词条") String word,
                                 SearchWordTypeEnum searchWordTypeEnum) {
        return searchWordService.saveSearchWord(word, searchWordTypeEnum);
    }

    @ApiOperation(value = "删除词条", notes = "根据ID词条。[1002]（梅述全）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @Authorization
    @RequestMapping(value = "deleteSearchWord/{id}", method = RequestMethod.DELETE)
    public Result deleteSearchWord(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                   @PathVariable @ApiParam(name = "id", required = true, value = "ID") Long id) {
        return searchWordService.deleteSearchWordById(id);
    }

    @ApiOperation(value = "查询词条", notes = "查询词条。（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "listSearchWord", method = RequestMethod.GET)
    public Result<Page<SearchWordDTO>> listSearchWord(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                      @ModelAttribute @ApiParam(required = true, value = "查询条件") SearchWordParam searchWordParam) {
        return searchWordService.listSearchWord(searchWordParam);
    }
}
