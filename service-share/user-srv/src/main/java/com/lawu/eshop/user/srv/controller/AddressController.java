package com.lawu.eshop.user.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.user.dto.AddressDTO;
import com.lawu.eshop.user.param.AddressParam;
import com.lawu.eshop.user.srv.bo.AddressBO;
import com.lawu.eshop.user.srv.converter.AddressConverter;
import com.lawu.eshop.user.srv.service.AddressService;

/**
 * 描述：收货地址管理
 * @author zhangrc
 * @date 2017/03/22
 *
 */
@RestController
@RequestMapping(value = "address/")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	 /**
	  * 收货地址列表
	  * @return
	  */
	@RequestMapping(value = "selectByUserId", method = RequestMethod.GET)
    public List<AddressDTO> selectByUserId(@RequestParam Long userId) {
		List<AddressBO> addressBOS = addressService.selectByUserId(userId);
        return AddressConverter.convertListDOTS(addressBOS);
    }
	
	
	 /**
	  * 增加收货地址
	  * @return
	  */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(@RequestBody  AddressParam  addressDO ) {
		addressService.save(addressDO);
    }
   
   
    /**
	 * 单个查询地址
	 * @return
	 */
    @RequestMapping(value = "get", method = RequestMethod.POST)
    public AddressDTO get(@RequestParam Long id) {
		AddressBO addressBO = addressService.get(id);
      return AddressConverter.convertDTO(addressBO);
    }
   
   /**
	 * 修改地址
	 * @return
	 */
   @RequestMapping(value = "update", method = RequestMethod.POST)
   public void update(@RequestBody AddressParam  addressParam,@RequestParam Long id) {
		addressService.update(addressParam,id);
   }
   
   /**
	 * 修改地址
	 * @return
	 */
   @RequestMapping(value = "remove", method = RequestMethod.GET)
   public void remove(@RequestParam Long id) {
		addressService.remove(id);
   }
   
   /**
	 * 修改默认地址
	 * @return
	 */
  @RequestMapping(value = "updateStatus", method = RequestMethod.GET)
  public void updateStatus(@RequestParam Long id,@RequestParam Long userId) {
		addressService.updateStatus(id, userId);
  }
	
	

}
