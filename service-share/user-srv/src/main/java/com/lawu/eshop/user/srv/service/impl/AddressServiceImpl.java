package com.lawu.eshop.user.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.user.param.AddressParam;
import com.lawu.eshop.user.srv.bo.AddressBO;
import com.lawu.eshop.user.srv.converter.AddressConverter;
import com.lawu.eshop.user.srv.domain.AddressDO;
import com.lawu.eshop.user.srv.domain.AddressDOExample;
import com.lawu.eshop.user.srv.mapper.AddressDOMapper;
import com.lawu.eshop.user.srv.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressDOMapper addressDOMapper; 
 
	@Override
	public void insert(AddressParam address) {
		AddressDO addressDO =AddressConverter.convertDO(address);
		addressDO.setGmtCreate(new Date());
		addressDOMapper.insert(addressDO);
	}

	@Override
	public void update(AddressParam address) {
		AddressDO addressDO= AddressConverter.convertDO(address);
		addressDO.setGmtModified(new Date());
		addressDOMapper.updateByPrimaryKeySelective(addressDO);
	}

	@Override
	public AddressBO find(Long id) {
        AddressDO address = addressDOMapper.selectByPrimaryKey(id);
		return AddressConverter.convertBO(address);
	}

	@Override
	public List<AddressBO> findAll(Long userId) {
		AddressDOExample example = new AddressDOExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<AddressDO> addressDOS= addressDOMapper.selectByExample(example);
		return addressDOS.isEmpty() ? null : AddressConverter.convertListBOS(addressDOS);
	}

	@Override
	public void delete(Long id) {
		addressDOMapper.deleteByPrimaryKey(id);
	}

}
