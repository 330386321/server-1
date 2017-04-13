package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.member.api.service.StoreSolrService;
import com.lawu.eshop.user.dto.NearStoreDTO;
import com.lawu.eshop.user.param.StoreSolrParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "商品详情为你推荐", notes = "商品详情为你推荐(同类别按销量排行)。[1002] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "listStore", method = RequestMethod.GET)
    public Result<Page<NearStoreDTO>> listStore(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                @ModelAttribute @ApiParam StoreSolrParam storeSolrParam) {
        return storeSolrService.listStore(storeSolrParam);
    }

}
