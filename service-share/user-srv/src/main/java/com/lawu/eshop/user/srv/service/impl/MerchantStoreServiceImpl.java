package com.lawu.eshop.user.srv.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.user.param.MerchantStoreParam;
import com.lawu.eshop.user.srv.bo.MerchantStoreBO;
import com.lawu.eshop.user.srv.converter.MerchantStoreConverter;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDOExample;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.service.MerchantStoreService;

@Service
public class MerchantStoreServiceImpl implements MerchantStoreService {

    @Autowired
    private MerchantStoreDOMapper merchantStoreDOMapper;

    @Override
    public MerchantStoreBO selectMerchantStore(Long merchantId) {

        MerchantStoreDOExample example = new MerchantStoreDOExample();
        example.createCriteria().andMerchantIdEqualTo(merchantId);
        List<MerchantStoreDO> list = merchantStoreDOMapper.selectByExample(example);
        MerchantStoreDO merchantStoreDO = null;
        if (!list.isEmpty()) {
            merchantStoreDO = list.get(0);
        }
        MerchantStoreBO bo = MerchantStoreConverter.convertStoreBO(merchantStoreDO);
        return bo;
    }

    @Override
    @Transactional
    public void updateNoReasonReturn(Long id) {
        MerchantStoreDO merchantStoreDO = new MerchantStoreDO();
        merchantStoreDO.setId(id);
        merchantStoreDO.setIsNoReasonReturn(true);
        merchantStoreDOMapper.updateByPrimaryKey(merchantStoreDO);
    }

    @Override
    public MerchantStoreBO getMerchantStoreById(Long id) {
        MerchantStoreDO merchantStoreDO = merchantStoreDOMapper.selectByPrimaryKey(id);
        return MerchantStoreConverter.convertStoreBO(merchantStoreDO);
    }

	@Override
	public List<MerchantStoreBO> selectAllMerchantStore(MerchantStoreParam param) {
		 MerchantStoreDOExample example = new MerchantStoreDOExample();
	        example.createCriteria().andStatusEqualTo(new Byte("1"));
	        List<MerchantStoreDO> list = merchantStoreDOMapper.selectByExample(example);
	        List<MerchantStoreBO> boList=new ArrayList<>();
	        if (!list.isEmpty()) {
	        	for (MerchantStoreDO merchantStoreDO : list) {
	        		MerchantStoreBO bo=new MerchantStoreBO();
	        		bo.setId(merchantStoreDO.getId());
	        		bo.setName(merchantStoreDO.getName());
	        		boList.add(bo);
				}
	        }
		return boList;
	}

}
