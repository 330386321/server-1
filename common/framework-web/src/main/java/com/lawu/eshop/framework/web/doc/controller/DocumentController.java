package com.lawu.eshop.framework.web.doc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.BaseController;

/**
 * API生成
 * 
 * @author Sunny
 * @date 2017/03/31
 */
@Controller
public class DocumentController extends BaseController {

    @RequestMapping(value = "document", method = RequestMethod.GET)
    public String api() {
    	System.out.println("test");
    	
    	return "ApiDocument";
    }



}
