package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.dto.SeckillActivityThatDayDTO;

/**
 * 抢购活动服务接口
 * 
 * @author jiangxinjun
 * @createDate 2017年11月24日
 * @updateDate 2017年11月24日
 */
@FeignClient(value = "product-srv", path="seckillActivity/")
public interface SeckillActivityService {
    
    /**
     * 查询当天所有活动
     * 
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月24日
     * @updateDate 2017年11月24日
     */
    @RequestMapping(path = "thatday/list", method = RequestMethod.GET)
    Result<List<SeckillActivityThatDayDTO>> thatDayList();
    
    /**
     * 查询当天所有活动
     * 
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月24日
     * @updateDate 2017年11月24日
     */
    @RequestMapping(path = "recently/list", method = RequestMethod.GET)
    Result<List<SeckillActivityThatDayDTO>> recentlyList();
    
    
    
}
