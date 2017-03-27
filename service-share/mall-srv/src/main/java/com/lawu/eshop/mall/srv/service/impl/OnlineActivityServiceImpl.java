package com.lawu.eshop.mall.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.mall.srv.bo.OnlineActivityBO;
import com.lawu.eshop.mall.srv.converter.OnlineActivityConverter;
import com.lawu.eshop.mall.srv.domain.OnlineActivityDO;
import com.lawu.eshop.mall.srv.domain.OnlineActivityDOExample;
import com.lawu.eshop.mall.srv.mapper.OnlineActivityDOMapper;
import com.lawu.eshop.mall.srv.service.OnlineActivityService;

/**
 * 商家活动服务实现
 *
 * @author Sunny
 * @date 2017/3/22
 */
@Service
public class OnlineActivityServiceImpl implements OnlineActivityService {

    @Autowired
    private OnlineActivityDOMapper onlineActivityDOMapper;

    @Override
    public List<OnlineActivityBO> list() {
        OnlineActivityDOExample example = new OnlineActivityDOExample();
        example.setOrderByClause("gmt_Modified desc,id asc");
        List<OnlineActivityDO> onlineActivityDOS = onlineActivityDOMapper.selectByExample(example);
        
        return OnlineActivityConverter.convertBOS(onlineActivityDOS);
    }
    
    @Override
    public List<OnlineActivityBO> listByMerchantId(Long merchantId) {
    	
        OnlineActivityDOExample example = new OnlineActivityDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId);
        
        List<OnlineActivityDO> onlineActivityDOS = onlineActivityDOMapper.selectByExample(example);

        return OnlineActivityConverter.convertBOS(onlineActivityDOS);
    }

	@Override
	public OnlineActivityBO get(Long id) {
		
		OnlineActivityDO onlineActivityDO = onlineActivityDOMapper.selectByPrimaryKey(id);

		return onlineActivityDO == null ? null : OnlineActivityConverter.convertBO(onlineActivityDO);
	}

	@Override
	public void save(OnlineActivityBO onlineActivityBO) {
		
		if (onlineActivityBO == null) {
			return;
		}
		
		OnlineActivityDO onlineActivityDO = OnlineActivityConverter.convertDO(onlineActivityBO);
		onlineActivityDO.setGmtModified(new Date());
		
		onlineActivityDOMapper.insert(onlineActivityDO);
	}

	@Override
	public void update(OnlineActivityBO onlineActivityBO) {
		
		if(onlineActivityBO == null || onlineActivityBO.getId() == null){
			return;
		}
		
		OnlineActivityDO onlineActivityDO =  OnlineActivityConverter.convertDO(onlineActivityBO);
		onlineActivityDO.setGmtModified(new Date());
		
		onlineActivityDOMapper.updateByPrimaryKey(onlineActivityDO);
		
	}

	@Override
	public void delete(Long id) {
		if(id == null){
			return;
		}
		onlineActivityDOMapper.deleteByPrimaryKey(id);
	}
}
