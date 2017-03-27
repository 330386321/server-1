package com.lawu.eshop.mall.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.mall.dto.OnlineActivityDTO;
import com.lawu.eshop.mall.srv.bo.OnlineActivityBO;
import com.lawu.eshop.mall.srv.converter.OnlineActivityConverter;
import com.lawu.eshop.mall.srv.service.OnlineActivityService;

/**
 * @author Sunny
 * @date 2017/3/22
 */
@RestController
@RequestMapping(value = "onlineActivity/")
public class OnlineActivityController {

    @Autowired
    private OnlineActivityService memberService;
    
    /**
     * 查询全部商户活动
     * 
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public List<OnlineActivityDTO> list() {
    	
        List<OnlineActivityBO> onlineActivityBOS = memberService.list();
        
        return OnlineActivityConverter.convertDTOS(onlineActivityBOS);
        
    }
    
    /**
     * 根据商户id查询当前商户的活动
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "listByMerchantId", method = RequestMethod.GET)
    public List<OnlineActivityDTO> listByMerchantId(@RequestParam Long merchantId) {
    	
        List<OnlineActivityBO> onlineActivityBOS = memberService.listByMerchantId(merchantId);
        
        return OnlineActivityConverter.convertDTOS(onlineActivityBOS);
        
    }
    
    /**
     * 根据id获取当个活动详情
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public OnlineActivityDTO get(@RequestParam Long id) {
        OnlineActivityBO onlineActivityBO = memberService.get(id);
        return OnlineActivityConverter.convertDTO(onlineActivityBO);
        
    }
    
    /**
     * 保存商户活动
     * merchantId属性不能为空
     * 
     * @param onlineActivityDTO
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(@ModelAttribute OnlineActivityDTO onlineActivityDTO) {
    	if(onlineActivityDTO == null || onlineActivityDTO.getMerchantId() == null){
    		return;
    	}
        memberService.save(OnlineActivityConverter.convertBO(onlineActivityDTO));
    }
    
    /**
     * 根据活动id删除
     * 
     * @param id
     */
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public void delete(@RequestParam Long id) {
    	if(id == null){
    		return;
    	}
        memberService.delete(id);
    }
    
    /**
     * 根据活动id更新
     * 
     * @param onlineActivityBO
     */
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public void update(@ModelAttribute OnlineActivityBO onlineActivityBO) {
    	if(onlineActivityBO == null || onlineActivityBO.getId() == null){
    		return;
    	}
        memberService.update(onlineActivityBO);
    }
}
