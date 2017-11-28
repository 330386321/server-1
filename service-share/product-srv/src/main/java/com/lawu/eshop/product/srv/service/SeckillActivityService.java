package com.lawu.eshop.product.srv.service;

import java.util.List;

import com.lawu.eshop.common.exception.DataNotExistException;
import com.lawu.eshop.common.exception.WrongOperationException;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.param.SeckillActivityPageQueryParam;
import com.lawu.eshop.product.param.SeckillActivityUpdateParam;
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
     * @updateDate 2017年11月27日
     */
    Page<SeckillActivityBO> page(SeckillActivityPageQueryParam param);

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
    
    /**
     * 根据id查询抢购活动详情
     * 
     * @param id 抢购活动id
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    SeckillActivityBO get(Long id) throws DataNotExistException;
    
    /**
     * 根据id删除抢购活动   
     * 
     * @param id 抢购活动id
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    void delete(Long id) throws DataNotExistException, WrongOperationException;
    
    /**
     * 根据id发布抢购活动   
     * 
     * @param id 抢购活动id
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    void release(Long id) throws DataNotExistException, WrongOperationException;
    
    /**
     * 根据id下架抢购活动
     * 下架之后抢购的活动的状态为已结束
     * 
     * @param id 抢购活动id
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    void down(Long id) throws DataNotExistException, WrongOperationException;
    
    /**
     * 根据id更新抢购活动
     * 
     * @param id 抢购活动id
     * @param param 抢购活动更新参数
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    void update(Long id, SeckillActivityUpdateParam param) throws DataNotExistException, WrongOperationException;
    
    /**
     * 根据id审核抢购活动
     * 
     * @param id 抢购活动id
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    void audit(Long id) throws DataNotExistException, WrongOperationException;
    
    /**
     * 抢购活动报名结束，修改抢购活动的状态为审核中
     * 
     * @return
     * @author jiangxinjun
     * @createDate 2017年11月27日
     * @updateDate 2017年11月27日
     */
    void seckillActivityRegistrationEnd();
}
