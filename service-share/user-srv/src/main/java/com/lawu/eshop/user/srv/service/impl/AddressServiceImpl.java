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
	public Integer save(AddressParam address) {
		AddressDO addressDO =AddressConverter.convertDO(address);
		addressDO.setGmtCreate(new Date());
		addressDO.setGmtModified(new Date());
		addressDO.setIsDefault(false);
		Integer id=addressDOMapper.insert(addressDO);
		return id;
	}

	@Override
	public Integer update(AddressParam address,Long id) {
		AddressDO addressDO= AddressConverter.convertDO(address);
		addressDO.setId(id);
		addressDO.setGmtModified(new Date());
		Integer i=addressDOMapper.updateByPrimaryKeySelective(addressDO);
		return i;
	}

	@Override
	public AddressBO get(Long id) {
        AddressDO address = addressDOMapper.selectByPrimaryKey(id);
		return AddressConverter.convertBO(address);
	}

	@Override
	public List<AddressBO> selectByUserId(Long userId) {
		AddressDOExample example = new AddressDOExample();
		example.setOrderByClause("gmt_create");
		example.createCriteria().andUserIdEqualTo(userId);
		List<AddressDO> addressDOS= addressDOMapper.selectByExample(example);
		return addressDOS.isEmpty() ? null : AddressConverter.convertListBOS(addressDOS);
	}

	@Override
	public Integer remove(Long id) {
		Integer i=addressDOMapper.deleteByPrimaryKey(id);
		return i;
		
	}

	@Override
	public Integer updateStatus(Long id,Long userId) {
		AddressDO addressDO=new AddressDO();
		addressDO.setIsDefault(false);
		addressDO.setUserId(userId);
		addressDO.setGmtModified(new Date());
		addressDOMapper.updateStatusById(addressDO);
		addressDO.setId(id);
		addressDO.setIsDefault(true);
		Integer i =addressDOMapper.updateByPrimaryKeySelective(addressDO);
		return i;
		
	}

}
