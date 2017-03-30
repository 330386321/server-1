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
	public Integer save(Long userId,AddressParam address) {
		AddressDO addressDO =AddressConverter.convertDO(address);
		String reg="^\\d(\\d|\\/)*\\d$";
		String regionPath=addressDO.getRegionPath();
		boolean tag=regionPath.matches(reg);
		if(!tag){
			return 0;
		}
		addressDO.setUserId(userId);
		addressDO.setGmtCreate(new Date());
		addressDO.setGmtModified(new Date());
		addressDO.setIsDefault(false);
		addressDO.setStatus(new Byte("1"));
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
		Byte status=1;
		example.setOrderByClause("gmt_create asc");
		example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(status);
		List<AddressDO> addressDOS= addressDOMapper.selectByExample(example);
		return addressDOS.isEmpty() ? null : AddressConverter.convertListBOS(addressDOS);
	}

	@Override
	public Integer remove(Long id) {
		AddressDOExample example = new AddressDOExample();
		example.createCriteria().andIdEqualTo(id);
		AddressDO address=new AddressDO();
		address.setStatus(new Byte("0"));
		Integer i=addressDOMapper.updateByExampleSelective(address, example);
		return i;
		
	}

	@Override
	public Integer updateDefault(Long id,Long userId) {
		AddressDO addressDO=new AddressDO();
		AddressDOExample example = new AddressDOExample();
		example.createCriteria().andUserIdEqualTo(userId);
		addressDO.setIsDefault(false);
		addressDO.setGmtModified(new Date());
		addressDOMapper.updateByExampleSelective(addressDO, example);
		addressDO.setIsDefault(true);
		AddressDOExample example2 = new AddressDOExample();
		example2.createCriteria().andIdEqualTo(id);
		Integer i =addressDOMapper.updateByExampleSelective(addressDO, example2);
		return i;
		
	}

}
