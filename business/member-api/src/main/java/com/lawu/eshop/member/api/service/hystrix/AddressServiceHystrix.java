package com.lawu.eshop.member.api.service.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.member.api.service.AddressService;
import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.param.AddressParam;

/**
 * 收货地址接口异常处理类
 * @author zhangrc 
 * @date 2017/03/22
 *
 */
@Component
public class AddressServiceHystrix implements AddressService {


	@Override
	public List<AddressDTO> selectByUserId(@RequestParam Long userId) {
		
		return null;
	}

	@Override
	public AddressDTO get(@RequestParam Long id) {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setId(-1L);
	    return addressDTO;
	}

	@Override
	public void delete(@RequestParam Long userId) {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setId(-1L);
	}

	@Override
	public void save(@RequestBody  AddressParam address) {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setId(-1L);
	}

	@Override
	public void update(@RequestBody AddressParam address,@RequestParam Long id) {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setId(-1L);
	}

	@Override
	public void updateStatus(@RequestParam Long id,@RequestParam Long userId) {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setId(-1L);
	}
}
