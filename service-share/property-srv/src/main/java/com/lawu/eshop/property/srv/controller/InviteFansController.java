package com.lawu.eshop.property.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.srv.service.InviteFansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
@RestController
@RequestMapping(value = "inviteFans/")
public class InviteFansController extends BaseController {

    @Autowired
    private InviteFansService inviteFansService;

    @RequestMapping(value = "inviteFans/{userNum}", method = RequestMethod.POST)
    public Result inviteFans(@PathVariable("userNum") String userNum, @RequestParam Integer consumePoint) {
        inviteFansService.inviteFans(userNum, consumePoint);
        return successCreated();
    }

}
