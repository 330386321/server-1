package com.lawu.eshop.product.srv.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.product.srv.domain.SeckillActivityAttentionDO;
import com.lawu.eshop.product.srv.domain.SeckillActivityProductDO;
import com.lawu.eshop.product.srv.exception.DataNotExistException;
import com.lawu.eshop.product.srv.mapper.SeckillActivityAttentionDOMapper;
import com.lawu.eshop.product.srv.mapper.SeckillActivityProductDOMapper;
import com.lawu.eshop.product.srv.mapper.extend.SeckillActivityProductDOExtendMapper;
import com.lawu.eshop.product.srv.service.SeckillActivityAttentionService;

/**
 * 抢购活动关注服务接口实现类
 * 
 * @author jiangxinjun
 * @createDate 2017年11月24日
 * @updateDate 2017年11月24日
 */
@Service
public class SeckillActivityAttentionServiceImpl implements SeckillActivityAttentionService {
    
    @Autowired
    private SeckillActivityProductDOMapper seckillActivityProductDOMapper;
    
    @Autowired
    private SeckillActivityProductDOExtendMapper seckillActivityProductDOExtendMapper;
    
    @Autowired
    private SeckillActivityAttentionDOMapper seckillActivityAttentionDOMapper;
    
    @Override
    public void attention(Long activityProductId, Long memberId) throws DataNotExistException {
        // 查询商品信息
        SeckillActivityProductDO seckillActivityProductDO = seckillActivityProductDOMapper.selectByPrimaryKey(activityProductId);
        if (seckillActivityProductDO == null) {
            throw new DataNotExistException("抢购活动商品数据不存在");
        }
        SeckillActivityAttentionDO seckillActivityAttentionDO = new SeckillActivityAttentionDO();
        seckillActivityAttentionDO.setActivityId(seckillActivityProductDO.getActivityId());
        seckillActivityAttentionDO.setGmtCreate(new Date());
        seckillActivityAttentionDO.setGmtModified(new Date());
        seckillActivityAttentionDO.setIsRemind(false);
        seckillActivityAttentionDO.setMemberId(memberId);
        seckillActivityAttentionDO.setProductId(seckillActivityProductDO.getProductId());
        seckillActivityAttentionDOMapper.insert(seckillActivityAttentionDO);
        
        // 增加关注人数
        seckillActivityProductDOExtendMapper.increaseAttentionCount(activityProductId);
    }
    
}
