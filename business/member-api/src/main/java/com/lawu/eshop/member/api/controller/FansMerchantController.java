package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.FansMerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
@Api(tags = "fansMerchant")
@RestController
@RequestMapping(value = "fansMerchant/")
public class FansMerchantController extends BaseController {

    @Autowired
    private FansMerchantService fansMerchantService;

    @Audit(date = "2017-04-21", reviewer = "孙林青")
    @ApiOperation(value = "成为商家粉丝", notes = "成为商家粉丝。 (梅述全)", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "saveFansMerchant", method = RequestMethod.PUT)
    public Result saveFansMerchant(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                   @RequestParam @ApiParam(required = true, value = "商家ID") Long merchantId) {
        long memberId = UserUtil.getCurrentUserId(getRequest());
        return fansMerchantService.saveFansMerchant(merchantId, memberId);
    }

}
