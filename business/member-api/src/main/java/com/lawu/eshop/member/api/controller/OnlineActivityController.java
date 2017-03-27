package com.lawu.eshop.member.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.mall.dto.OnlineActivityDTO;
import com.lawu.eshop.member.api.service.OnlineActivityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Sunny 
 * @date 2017/3/23
 */
@Api(tags = "onlineActivity")
@RestController
@RequestMapping(value = "onlineActivity/")
public class OnlineActivityController {

    @Autowired
    private OnlineActivityService onlineActivityService;

    @ApiOperation(value = "商家活动列表", notes = "根据商家id获取活动列表", httpMethod = "POST")
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public List<OnlineActivityDTO> findMerchantOnlineActivity(@RequestParam @ApiParam(name = "merchantId", required = true, value = "商家ID") Long merchantId) {
        
    	List<OnlineActivityDTO> OnlineActivityDTOS = onlineActivityService.list(merchantId);
        
        return OnlineActivityDTOS;
    }
    
    @ApiOperation(value = "获取活动详情", notes = "根据id获取活动详情", httpMethod = "POST")
    @RequestMapping(value = "get", method = RequestMethod.POST)
    public OnlineActivityDTO get(@RequestParam @ApiParam(name = "id", required = true, value = "活动ID") Long id) {
        
    	OnlineActivityDTO OnlineActivityDTO = onlineActivityService.get(id);
        
        return OnlineActivityDTO;
    }
    
    @ApiOperation(value = "保存活动", notes = "保存活动", httpMethod = "POST")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(@ModelAttribute @ApiParam(required = true, value = "活动") OnlineActivityDTO onlineActivityDTO) {
    	
    	onlineActivityService.save(onlineActivityDTO);
    	
    }
    
    @ApiOperation(value = "更新活动", notes = "根据活动id更新活动", httpMethod = "POST")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public void update(@ModelAttribute @ApiParam(required = true, value = "活动") OnlineActivityDTO onlineActivityDTO) {
    	onlineActivityService.update(onlineActivityDTO);
    	
    }
    
    @ApiOperation(value = "删除活动", notes = "根据活动id删除活动", httpMethod = "POST")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(@RequestParam @ApiParam(name = "id", required = true, value = "活动id") Long id) {
    	onlineActivityService.delete(id);
    }

}
