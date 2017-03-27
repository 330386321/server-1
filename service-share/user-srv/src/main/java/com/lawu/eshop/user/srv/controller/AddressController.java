package com.lawu.eshop.user.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
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
public class AddressController extends BaseController{
	
	@Autowired
	private AddressService addressService;
	
	 /**
	  * 收货地址列表
	  * @return
	  */
	@RequestMapping(value = "selectByUserId", method = RequestMethod.GET)
    public Result selectByUserId(@RequestParam Long userId) {
		List<AddressBO> addressBOS = addressService.selectByUserId(userId);
		return  successAccepted(AddressConverter.convertListDOTS(addressBOS));
    }
	
	
	 /**
	  * 增加收货地址
	  * @return
	  */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(@RequestBody  AddressParam  addressDO ) {
    	Integer i=addressService.save(addressDO);
    	if(i>0){
    		return successCreated();
    	}else{
    		return successCreated(ResultCode.USER_WRONG_ID);
    	}
    	
    }
   
   
    /**
	 * 单个查询地址
	 * @return
	 */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public Result<AddressDTO> get(@RequestParam Long id) {
		AddressBO addressBO = addressService.get(id);
        return successGet(AddressConverter.convertDTO(addressBO));
    }
   
   /**
	 * 修改地址
	 * @return
	 */
   @RequestMapping(value = "update", method = RequestMethod.POST)
   public Result update(@RequestBody AddressParam  addressParam,@RequestParam Long id) {
	   Integer i=addressService.update(addressParam,id);
	   if(i>0){
		    return successCreated();
	   }else{
	   		return successCreated(ResultCode.USER_WRONG_ID);
	   }
   }
   
   /**
	 * 修改地址
	 * @return
	 */
   @RequestMapping(value = "remove", method = RequestMethod.DELETE)
   public Result remove(@RequestParam Long id) {
	   Integer i=addressService.remove(id);
	   if(i>0){
   			return successCreated();
   	   }else{
   		    return successCreated(ResultCode.USER_WRONG_ID);
   	   }
   }
   
   /**
	 * 修改默认地址
	 * @return
	 */
  @RequestMapping(value = "updateDefault", method = RequestMethod.GET)
  public Result updateDefault(@RequestParam Long id,@RequestParam Long userId) {
	  Integer i=addressService.updateDefault(id, userId);
	  if(i>0){
 			return successCreated();
 	   }else{
 		    return successCreated(ResultCode.USER_WRONG_ID);
 	   }
  }
	
	

}
