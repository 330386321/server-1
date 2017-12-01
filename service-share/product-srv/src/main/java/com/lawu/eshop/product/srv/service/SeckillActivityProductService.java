package com.lawu.eshop.product.srv.service;

import com.lawu.eshop.common.exception.DataNotExistException;
import com.lawu.eshop.common.exception.WrongOperationException;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.param.SeckillActivityProductAuditParam;
import com.lawu.eshop.product.param.SeckillActivityProductNotPassedParam;
import com.lawu.eshop.product.param.SeckillActivityProductPageQueryParam;
import com.lawu.eshop.product.param.SeckillActivityProductPageSearchParam;
import com.lawu.eshop.product.srv.bo.SeckillActivityProductBO;
import com.lawu.eshop.product.srv.bo.SeckillActivityProductExtendBO;

/**
 * 抢购活动商品服务接口
 * 
 * @author jiangxinjun
 * @createDate 2017年11月23日
 * @updateDate 2017年11月23日
 */
public interface SeckillActivityProductService {

    /**
     * 根据抢购活动id和查询参数分页查询抢购活动商品列表
     * 用于用户端
     * 
     * @param id 抢购活动id
     * @param param 分页查询参数
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月23日
     * @updateDate 2017年11月23日
     */
    Page<SeckillActivityProductBO> page(Long id, SeckillActivityProductPageQueryParam param);
    
    /**
     * 根据抢购活动商品id查询活动资料以及商品型号库存信息
     * 用于会员端
     * 
     * @param id
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月23日
     * @updateDate 2017年11月24日
     */
     SeckillActivityProductExtendBO information(Long id) throws DataNotExistException;
     
     /**
      * 根据抢购活动id和查询参数分页查询抢购活动商品列表
      * 用于用户端
      * 
      * @param id 抢购活动id
      * @param param 分页查询参数
      * @return
      * @author jiangxinjun
      * @createDate 2017年11月27日
      * @updateDate 2017年11月27日
      */
     Page<SeckillActivityProductBO> page(Long id, SeckillActivityProductPageSearchParam param);
     
     /**
      * 审核抢购活动商品
      * 
      * @param id 抢购活动商品id
      * @return
      * @author jiangxinjun
      * @createDate 2017年11月27日
      * @updateDate 2017年11月27日
      */
     void audit(Long id, SeckillActivityProductAuditParam param) throws DataNotExistException, WrongOperationException;
     
     /**
      * 抢购活动商品
      * 审核不通过
      * 
      * @param id 抢购活动商品id
      * @param param 反馈参数
      * @return
      * @author jiangxinjun
      * @createDate 2017年11月27日
      * @updateDate 2017年11月27日
      */
     void notPassed(Long id, SeckillActivityProductNotPassedParam param) throws DataNotExistException, WrongOperationException;
}
