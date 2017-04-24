package com.lawu.eshop.user.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.user.constants.StatusEnum;
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

	@Deprecated
	@Override
	@Transactional
	public Integer save(Long userId, AddressParam address) {
		AddressDO addressDO = AddressConverter.convertDO(address);
		String reg = "^\\d(\\d|\\/)*\\d$";
		String regionPath = addressDO.getRegionPath();
		boolean tag = regionPath.matches(reg);
		if (!tag) {
			return 0;
		}
		addressDO.setUserId(userId);
		addressDO.setGmtCreate(new Date());
		addressDO.setGmtModified(new Date());
		addressDO.setIsDefault(false);
		addressDO.setStatus(new Byte("1"));
		Integer id = addressDOMapper.insert(addressDO);
		return id;
	}

	@Override
	@Transactional
	public Integer update(AddressParam address, Long id) {
		AddressDO addressDO = AddressConverter.convertDO(address);
		addressDO.setId(id);
		addressDO.setGmtModified(new Date());
		Integer i = addressDOMapper.updateByPrimaryKeySelective(addressDO);
		return i;
	}

	@Override
	public AddressBO get(Long id) {
		AddressDO address = addressDOMapper.selectByPrimaryKey(id);
		return AddressConverter.convertBO(address);
	}

	@Deprecated
	@Override
	public List<AddressBO> selectByUserId(Long userId) {
		AddressDOExample example = new AddressDOExample();
		Byte status = 1;
		example.setOrderByClause("gmt_create asc");
		example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(status);
		List<AddressDO> addressDOS = addressDOMapper.selectByExample(example);
		return addressDOS.isEmpty() ? null : AddressConverter.convertListBOS(addressDOS);
	}

	@Override
	@Transactional
	public Integer remove(Long id) {
		AddressDOExample example = new AddressDOExample();
		example.createCriteria().andIdEqualTo(id);
		AddressDO address = new AddressDO();
		address.setStatus(new Byte("0"));
		Integer i = addressDOMapper.updateByExampleSelective(address, example);
		return i;

	}

	@Override
	@Transactional
	public Integer updateDefault(Long id, Long userId) {
		AddressDO addressDO = new AddressDO();
		AddressDOExample example = new AddressDOExample();
		example.createCriteria().andUserIdEqualTo(userId);
		addressDO.setIsDefault(false);
		addressDO.setGmtModified(new Date());
		addressDOMapper.updateByExampleSelective(addressDO, example);
		addressDO.setIsDefault(true);
		AddressDOExample example2 = new AddressDOExample();
		example2.createCriteria().andIdEqualTo(id);
		Integer i = addressDOMapper.updateByExampleSelective(addressDO, example2);
		return i;

	}

	/**
	 * 根据用户编号 查询用户所有地址
	 * 
	 * @param userNum
	 *            用户编号
	 * @return
	 * @author Sunny
	 */
	@Override
	public List<AddressBO> selectByUserNum(String userNum) {
		AddressDOExample example = new AddressDOExample();
		example.createCriteria().andUserNumEqualTo(userNum).andStatusEqualTo(StatusEnum.VALID.getValue());

		List<AddressDO> list = addressDOMapper.selectByExample(example);

		return AddressConverter.convertListBOS(list);
	}

	/**
	 * 根据用户编号 添加收货地址
	 * 
	 * @param userNum
	 *            用户编号
	 * @param param
	 *            保存地址参数
	 * @param bindingResult
	 *            参数验证结果
	 * @author Sunny
	 */
	@Override
	@Transactional
	public int saveWithUserNum(String userNum, AddressParam param) {
		
		AddressDO addressDO = AddressConverter.convertDO(param);
		
		addressDO.setUserNum(userNum);
		addressDO.setStatus(StatusEnum.VALID.getValue());
		addressDO.setGmtCreate(new Date());
		addressDO.setGmtModified(new Date());
		
		int result = addressDOMapper.insertSelective(addressDO);
		
		if (result <= 0) {
			return ResultCode.SAVE_FAIL;
		}

		return ResultCode.SUCCESS;
	}

}
