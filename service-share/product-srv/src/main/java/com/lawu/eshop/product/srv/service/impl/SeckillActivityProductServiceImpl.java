package com.lawu.eshop.product.srv.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.common.exception.DataNotExistException;
import com.lawu.eshop.common.exception.WrongOperationException;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.constant.ActivityProductStatusEnum;
import com.lawu.eshop.product.param.SeckillActivityProductNotPassedParam;
import com.lawu.eshop.product.param.SeckillActivityProductPageQueryParam;
import com.lawu.eshop.product.param.SeckillActivityProductPageSearchParam;
import com.lawu.eshop.product.srv.bo.SeckillActivityProductBO;
import com.lawu.eshop.product.srv.bo.SeckillActivityProductExtendBO;
import com.lawu.eshop.product.srv.converter.SeckillActivityProductConverter;
import com.lawu.eshop.product.srv.domain.SeckillActivityDO;
import com.lawu.eshop.product.srv.domain.SeckillActivityProductDO;
import com.lawu.eshop.product.srv.domain.SeckillActivityProductDOExample;
import com.lawu.eshop.product.srv.domain.SeckillActivityProductModelDO;
import com.lawu.eshop.product.srv.domain.SeckillActivityProductModelDOExample;
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
        if (productModelList == null || productModelList.isEmpty()) {
            throw new DataNotExistException("抢购活动商品型号数据不存在");
        }
        return SeckillActivityProductConverter.convert(seckillActivityDO, seckillActivityProductDO, productModelList);
    }
    
    @Override
    public Page<SeckillActivityProductBO> page(Long id, SeckillActivityProductPageSearchParam param) {
        SeckillActivityProductDOExample example = new SeckillActivityProductDOExample();
        SeckillActivityProductDOExample.Criteria criteria = example.createCriteria();
        criteria.andActivityIdEqualTo(id);
        if (param.getStatus() != null) {
            criteria.andStatusEqualTo(param.getStatus().getValue());
        }
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

    @Transactional
    @Override
    public void audit(Long id) throws DataNotExistException, WrongOperationException {
        SeckillActivityProductDO seckillActivityProductDO = seckillActivityProductDOMapper.selectByPrimaryKey(id);
        if (seckillActivityProductDO == null) {
            throw new DataNotExistException("抢购活动商品数据不存在");
        }
        if (!ActivityProductStatusEnum.NOT_AUDITED.getValue().equals(seckillActivityProductDO.getStatus())) {
            throw new WrongOperationException("抢购活动商品不是未审核状态");
        }
        SeckillActivityProductDO seckillActivityProductUpdateDO = new SeckillActivityProductDO();
        seckillActivityProductUpdateDO.setId(seckillActivityProductDO.getId());
        seckillActivityProductUpdateDO.setStatus(ActivityProductStatusEnum.AUDITED.getValue());
        seckillActivityProductDOMapper.updateByPrimaryKeySelective(seckillActivityProductUpdateDO);
    }

    @Transactional
    @Override
    public void notPassed(Long id, SeckillActivityProductNotPassedParam param) throws DataNotExistException, WrongOperationException {
        SeckillActivityProductDO seckillActivityProductDO = seckillActivityProductDOMapper.selectByPrimaryKey(id);
        if (seckillActivityProductDO == null) {
            throw new DataNotExistException("抢购活动商品数据不存在");
        }
        if (!ActivityProductStatusEnum.NOT_AUDITED.getValue().equals(seckillActivityProductDO.getStatus())) {
            throw new WrongOperationException("抢购活动商品不是未审核状态");
        }
        SeckillActivityProductDO seckillActivityProductUpdateDO = new SeckillActivityProductDO();
        seckillActivityProductUpdateDO.setId(seckillActivityProductDO.getId());
        seckillActivityProductUpdateDO.setStatus(ActivityProductStatusEnum.NOT_PASS.getValue());
        seckillActivityProductUpdateDO.setReasons(param.getReasons());
        seckillActivityProductDOMapper.updateByPrimaryKeySelective(seckillActivityProductUpdateDO);
    }

}
