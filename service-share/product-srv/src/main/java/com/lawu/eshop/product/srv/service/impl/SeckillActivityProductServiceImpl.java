package com.lawu.eshop.product.srv.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.constant.ActivityProductStatusEnum;
import com.lawu.eshop.product.param.SeckillActivityProductPageQueryParam;
import com.lawu.eshop.product.srv.bo.SeckillActivityProductBO;
import com.lawu.eshop.product.srv.bo.SeckillActivityProductExtendBO;
import com.lawu.eshop.product.srv.converter.SeckillActivityProductConverter;
import com.lawu.eshop.product.srv.domain.SeckillActivityDO;
import com.lawu.eshop.product.srv.domain.SeckillActivityProductDO;
import com.lawu.eshop.product.srv.domain.SeckillActivityProductDOExample;
import com.lawu.eshop.product.srv.domain.SeckillActivityProductModelDO;
import com.lawu.eshop.product.srv.domain.SeckillActivityProductModelDOExample;
import com.lawu.eshop.product.srv.exception.DataNotExistException;
import com.lawu.eshop.product.srv.mapper.SeckillActivityDOMapper;
import com.lawu.eshop.product.srv.mapper.SeckillActivityProductDOMapper;
import com.lawu.eshop.product.srv.mapper.SeckillActivityProductModelDOMapper;
import com.lawu.eshop.product.srv.service.SeckillActivityProductService;

/**
 * 抢购活动商品服务接口
 * 
 * @author jiangxinjun
 * @createDate 2017年11月23日
 * @updateDate 2017年11月23日
 */
@Service
public class SeckillActivityProductServiceImpl implements SeckillActivityProductService {
    
    @Autowired
    private SeckillActivityDOMapper seckillActivityDOMapper;
    
    @Autowired
    private SeckillActivityProductDOMapper seckillActivityProductDOMapper;
    
    @Autowired
    private SeckillActivityProductModelDOMapper seckillActivityProductModelDOMapper;
    
    @Override
    public Page<SeckillActivityProductBO> page(Long id, SeckillActivityProductPageQueryParam param) {
        SeckillActivityProductDOExample example = new SeckillActivityProductDOExample();
        SeckillActivityProductDOExample.Criteria criteria = example.createCriteria();
        criteria.andActivityIdEqualTo(id);
        // 已审核
        criteria.andStatusEqualTo(ActivityProductStatusEnum.AUDITED.getValue());
        
        Page<SeckillActivityProductBO> rtn = new Page<>();
        rtn.setCurrentPage(param.getCurrentPage());
        Long count = seckillActivityProductDOMapper.countByExample(example);
        rtn.setTotalCount(count.intValue());
        if (count <= 0 || param.getOffset() >= count) {
            return rtn;
        }
        RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
        List<SeckillActivityProductDO> list = seckillActivityProductDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<SeckillActivityProductBO> seckillActivityProductBOList = SeckillActivityProductConverter.convertSeckillActivityProductBOList(list);
        rtn.setRecords(seckillActivityProductBOList);
        return rtn;
    }
    
    @Override
    public SeckillActivityProductExtendBO information(Long id) throws DataNotExistException {
        // 根据抢购商品id查询抢购商品信息
        SeckillActivityProductDO seckillActivityProductDO = seckillActivityProductDOMapper.selectByPrimaryKey(id);
        if (seckillActivityProductDO == null) {
            throw new DataNotExistException("抢购活动商品数据不存在");
        }
        // 根据抢购活动id查询活动信息
        SeckillActivityDO seckillActivityDO = seckillActivityDOMapper.selectByPrimaryKey(seckillActivityProductDO.getActivityId());
        if (seckillActivityDO == null) {
            throw new DataNotExistException("抢购活动数据不存在");
        }
        // 根据抢购商品id查询抢购商品型号的信息
        SeckillActivityProductModelDOExample example = new SeckillActivityProductModelDOExample();
        example.createCriteria().andActivityProductIdEqualTo(id);
        List<SeckillActivityProductModelDO> productModelList = seckillActivityProductModelDOMapper.selectByExample(example);
        return SeckillActivityProductConverter.convert(seckillActivityDO, seckillActivityProductDO, productModelList);
    }

}
