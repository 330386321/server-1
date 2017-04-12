package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.CommentMerchantService;
import com.lawu.eshop.member.api.service.NearStoreService;
import com.lawu.eshop.user.dto.NearStoreDTO;
import com.lawu.eshop.user.query.NearStoreParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@Api(tags = "nearStore")
@RestController
@RequestMapping(value = "nearStore/")
public class NearStoreController extends BaseController {

    @Autowired
    private NearStoreService nearStoreService;

    @Autowired
    private CommentMerchantService commentMerchantService;

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "猜你喜欢/更多商家", notes = "猜你喜欢/更多商家(查询同类型商家)，按距离查询门店信息。(梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "listNearStore", method = RequestMethod.GET)
    public Result<Page<NearStoreDTO>> sendSms(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                              @ModelAttribute @ApiParam(required = true, value = "查询条件") NearStoreParam nearStoreParam) {
        return nearStoreService.listNearStore(nearStoreParam);
    }
}
