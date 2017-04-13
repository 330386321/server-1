package com.lawu.eshop.ad.srv.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.constants.RedPacketStatusEnum;
import com.lawu.eshop.ad.dto.RedPacketDTO;
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
	
	
	/**
	 * 领取
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "getRedPacket", method = RequestMethod.GET)
    public Result<RedPacketDTO> getRedPacket(@RequestParam  Long  merchantId,@RequestParam  Long  memberId) {
    	BigDecimal point=redPacketService.getRedPacket(merchantId,memberId);
    	RedPacketDTO dto=new RedPacketDTO();
    	dto.setPoint(point);
    	if(point.compareTo(new BigDecimal(0))==1)
    		dto.setStatusEnum(RedPacketStatusEnum.RED_PACKET_SUCCESS);
    	else
    		dto.setStatusEnum(RedPacketStatusEnum.RED_PACKET_FAIL);
    	return successGet(dto);
    }

}
