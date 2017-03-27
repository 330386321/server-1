package com.lawu.eshop.mall.srv.service;

import java.util.List;

import com.lawu.eshop.mall.srv.bo.OnlineActivityBO;

/**
 * 商户活动服务接口
 *
 * @author Sunny
 * @date 2017/3/22
 */
public interface OnlineActivityService {
	
	/**
	 * 查询全部商户活动
	 * 
	 * @param merchantId
	 * @return
	 */
    List<OnlineActivityBO> list();
	
	/**
	 * 根据商户id查询当前商户的活动列表
	 * 
	 * @param merchantId
	 * @return
	 */
    List<OnlineActivityBO> listByMerchantId(Long merchantId);
    
    /**
     * 查询活动详情
     * 
     * @param id
     * @return
     */
    OnlineActivityBO get(Long id);
    
    /**
     * 添加活动
     * 
     * @param bo
     * @return
     */
    void save(OnlineActivityBO onlineActivityBO);
    
    /**
     * 更新活动
     * 
     * @param bo
     * @return
     */
    void update(OnlineActivityBO onlineActivityBO);
    
    /**
     * 删除活动
     * 
     * @param id
     * @return
     */
    void delete(Long id);
    
}
