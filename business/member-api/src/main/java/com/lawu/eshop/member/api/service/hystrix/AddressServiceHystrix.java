package com.lawu.eshop.member.api.service.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
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
public class AddressServiceHystrix  implements AddressService{

	@Override
	public Result save(Long id,AddressParam address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result update(AddressParam address, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<AddressDTO> get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result selectByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result delete(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result updateDefault(Long id, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
