package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.StoreSolrService;
import com.lawu.eshop.user.dto.NearStoreDTO;
import com.lawu.eshop.user.dto.param.StoreSearchWordDTO;
import com.lawu.eshop.user.param.StoreSolrParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/3/30.
 */
@Api(tags = "storeSolr")
@RestController
@RequestMapping(value = "storeSolr/")
public class StoreSolrController extends BaseController {

    @Autowired
    private StoreSolrService storeSolrService;

    @ApiOperation(value = "搜索门店", notes = "搜索门店。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listStore", method = RequestMethod.GET)
    public Result<Page<NearStoreDTO>> listStore(@ModelAttribute @ApiParam StoreSolrParam storeSolrParam) {
        return storeSolrService.listStore(storeSolrParam);
    }

    @ApiOperation(value = "搜索词关联推荐", notes = "根据搜索词推荐关联词搜索。[1100] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listStoreSearchWord", method = RequestMethod.GET)
    public Result<List<StoreSearchWordDTO>> listStoreSearchWord(@RequestParam @ApiParam(name = "name", required = true, value = "门店名称") String name) {
        return storeSolrService.listStoreSearchWord(name);
    }

}
