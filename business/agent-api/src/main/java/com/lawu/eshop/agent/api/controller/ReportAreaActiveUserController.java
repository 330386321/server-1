package com.lawu.eshop.agent.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;

import io.swagger.annotations.Api;

/**
 * @author zhangyong
 * @date 2017/8/14.
 */
@Api(tags = "activeUser")
@RestController
@RequestMapping(value = "activeUser/")
public class ReportAreaActiveUserController extends BaseController{

}
