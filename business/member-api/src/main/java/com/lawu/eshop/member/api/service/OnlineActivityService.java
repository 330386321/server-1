package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.mall.dto.OnlineActivityDTO;

/**
 * @author Sunny
 * @date 2017/3/23
 */
@FeignClient(value= "mall-srv")
public interface OnlineActivityService {

	/**
	 * 根据商户id查询商户的活动列表
	 * 
	 * @param merchantId
	 * @return
	 */
    @RequestMapping(method = RequestMethod.GET, value = "onlineActivity/list")
    List<OnlineActivityDTO> list(@RequestParam("merchantId") Long merchantId);
    
	/**
	 * 查看活动详情
	 * 
	 * @param id
	 * @return
	 */
    @RequestMapping(method = RequestMethod.GET, value = "onlineActivity/get")
    OnlineActivityDTO get(@RequestParam("id") Long id);
    
	/**
	 * 保存活动
	 * 
	 * @param onlineActivity
	 * @return
	 */
    @RequestMapping(method = RequestMethod.POST, value = "onlineActivity/save")
    void save(@ModelAttribute OnlineActivityDTO onlineActivityDTO);
    
	/**
	 * 删除活动
	 * 
	 * @param id
	 * @return
	 */
    @RequestMapping(method = RequestMethod.GET, value = "onlineActivity/delete")
    void delete(@RequestParam("id") Long id);
    
	/**
	 * 更新活动
	 * 
	 * @param onlineActivity
	 * @return
	 */
    @RequestMapping(method = RequestMethod.GET, value = "onlineActivity/update")
    void update(@ModelAttribute OnlineActivityDTO onlineActivityDTO);

}
