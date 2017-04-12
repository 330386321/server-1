package com.lawu.eshop.ad.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.param.RedPacketParam;
import com.lawu.eshop.ad.srv.service.RedPacketService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;

/**
 * 红包
 * @author zhangrc
 * @date 2017/4/8
 *
 */
@RestController
@RequestMapping(value = "redPacket/")
public class RedPacketController extends BaseController{
	
	@Autowired
	private RedPacketService redPacketService;
	
	/**
	 * 红包生成
	 * @param param
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(@RequestBody RedPacketParam param,@RequestParam  Long  merchantId,@RequestParam  String  num ) {
    	Integer i=redPacketService.save(param, merchantId,num);
    	if(i>0){
    		return successCreated(ResultCode.SUCCESS);
    	}else{
    		return successCreated(ResultCode.SAVE_FAIL);
    	}
    	
    }

}
