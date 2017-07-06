package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.ExpressCompanyDTO;
import com.lawu.eshop.mall.dto.ExpressCompanyRetrieveDTO;

/**
 * @author Sunny
 * @date 2017/3/27
 */
@FeignClient(value= "mall-srv", path = "expressCompany/")
public interface ExpressCompanyService {

    /**
     * 查询全部快递公司，根据ordinal排序
     */
    @RequestMapping(method = RequestMethod.GET, value = "list")
    Result<List<ExpressCompanyDTO>> list();
 
	/**
	 * 根据id查询快递公司
	 * 
	 * @param id 快递公司id
	 * @return
	 */
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	Result<ExpressCompanyDTO> get(@PathVariable("id") Integer id);
	
	/**
	 * 根据关键字检索快递公司
	 * 
	 * @param keyWord 快递公司的name
	 * @return
	 * @author Sunny
	 * @date 2017年7月6日
	 */
	@RequestMapping(value = "keyWord", method = RequestMethod.GET)
	Result<ExpressCompanyRetrieveDTO> listByKeyWord(@RequestParam("keyWord") String keyWord);
}
