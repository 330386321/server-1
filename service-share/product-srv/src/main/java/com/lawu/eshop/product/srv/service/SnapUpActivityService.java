package com.lawu.eshop.product.srv.service;

import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.param.SnapUpActivityPageQueryParam;

/**
 * 抢购活动服务接口
 * 
 * @author jiangxinjun
 * @createDate 2017年11月23日
 * @updateDate 2017年11月23日
 */
public interface SnapUpActivityService {
    
    /**
     * 根据查询参数分页查询抢购活动列表
     * 用于运营平台
     * 
     * @param param
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月23日
     * @updateDate 2017年11月23日
     */
    Page<?> page(SnapUpActivityPageQueryParam param);
    
    /**
     * 查询当天的抢购活动列表
     * 
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月23日
     * @updateDate 2017年11月23日
     */
     List<?> list();
    
    
    /**
     * 根据id查询活动资料
     * 用于会员端
     * 
     * @param id
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月23日
     * @updateDate 2017年11月23日
     */
    Object information(Long id);
}
