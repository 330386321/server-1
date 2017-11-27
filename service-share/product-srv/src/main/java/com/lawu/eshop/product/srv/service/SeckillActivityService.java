package com.lawu.eshop.product.srv.service;

import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.param.SeckillActivityPageQueryParam;
import com.lawu.eshop.product.srv.bo.SeckillActivityBO;

/**
 * 抢购活动服务接口
 * 
 * @author jiangxinjun
 * @createDate 2017年11月23日
 * @updateDate 2017年11月27日
 */
public interface SeckillActivityService {

    /**
     * 根据查询参数分页查询抢购活动列表 用于运营平台
     * 
     * @param param
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月23日
     * @updateDate 2017年11月23日
     */
    Page<?> page(SeckillActivityPageQueryParam param);

    /**
     * 查询当天的抢购活动列表
     * 
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月23日
     * @updateDate 2017年11月23日
     */
    List<SeckillActivityBO> thatDayList();

    /**
     * 查询最近一天的抢购活动列表
     * 
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月23日
     * @updateDate 2017年11月23日
     */
    List<SeckillActivityBO> recentlyList();
    
    /**
     * 抢购活动开始，修改抢购活动的状态为进行中
     * 
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    void seckillActivityStart();
    
    /**
     * 抢购活动结束，修改抢购活动的状态为已结束
     * 
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    void seckillActivityEnd();
}