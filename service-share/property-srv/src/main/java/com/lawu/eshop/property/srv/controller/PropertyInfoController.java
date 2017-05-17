package com.lawu.eshop.property.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.PropertyinfoFreezeEnum;
import com.lawu.eshop.property.dto.*;
import com.lawu.eshop.property.param.BackagePropertyinfoDataParam;
import com.lawu.eshop.property.srv.bo.PropertyBalanceBO;
import com.lawu.eshop.property.srv.bo.PropertyInfoBO;
import com.lawu.eshop.property.srv.bo.PropertyPointAndBalanceBO;
import com.lawu.eshop.property.srv.bo.PropertyPointBO;
import com.lawu.eshop.property.srv.converter.PropertyBalanceConverter;
import com.lawu.eshop.property.srv.converter.PropertyPointConverter;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.utils.BeanUtil;
import com.lawu.eshop.utils.MD5;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author meishuquan
 * @date 2017/3/27
 */
@RestController
@RequestMapping(value = "propertyInfo/")
public class PropertyInfoController extends BaseController {

	@Autowired
	private PropertyInfoService propertyInfoService;

	/**
	 * 修改用户支付密码
	 *
	 * @param userNum
	 *            用户编号
	 * @param originalPwd
	 *            原始密码
	 * @param newPwd
	 *            新密码
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updatePayPwd/{userNum}", method = RequestMethod.PUT)
	public Result updatePayPwd(@PathVariable String userNum, @RequestParam String originalPwd,
			@RequestParam String newPwd) {
		PropertyInfoBO propertyInfoBO = propertyInfoService.getPropertyInfoByUserNum(userNum);
		if (propertyInfoBO == null) {
			return successGet(ResultCode.NOT_FOUND_DATA);
		}
		if (!MD5.MD5Encode(originalPwd).equals(propertyInfoBO.getPayPassword())) {
			return successGet(ResultCode.VERIFY_PWD_FAIL);
		}
		propertyInfoService.updatePayPwd(userNum, newPwd);
		return successCreated();
	}

	/**
	 * 重置用户支付密码
	 *
	 * @param userNum
	 *            用户编号
	 * @param newPwd
	 *            新密码
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "resetPayPwd/{userNum}", method = RequestMethod.PUT)
	public Result resetPayPwd(@PathVariable String userNum, @RequestParam String newPwd) {
		PropertyInfoBO propertyInfoBO = propertyInfoService.getPropertyInfoByUserNum(userNum);
		if (propertyInfoBO == null) {
			return successGet(ResultCode.NOT_FOUND_DATA);
		}
		propertyInfoService.updatePayPwd(userNum, newPwd);
		return successCreated();
	}

	/**
	 * 设置用户支付密码
	 *
	 * @param userNum
	 *            用户编号
	 * @param newPwd
	 *            新密码
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "setPayPwd/{userNum}", method = RequestMethod.PUT)
	public Result setPayPwd(@PathVariable String userNum, @RequestParam String newPwd) {
		PropertyInfoBO propertyInfoBO = propertyInfoService.getPropertyInfoByUserNum(userNum);
		if (propertyInfoBO == null) {
			return successGet(ResultCode.NOT_FOUND_DATA);
		}
		propertyInfoService.updatePayPwd(userNum, newPwd);
		return successCreated();
	}

	/**
	 * 查询用户是否设置支付密码
	 *
	 * @param userNum
	 *            用户编号
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "isSetPayPwd/{userNum}", method = RequestMethod.GET)
	public Result isSetPayPwd(@PathVariable String userNum) {
		PropertyInfoBO propertyInfoBO = propertyInfoService.getPropertyInfoByUserNum(userNum);
		if (propertyInfoBO == null) {
			return successGet(ResultCode.NOT_FOUND_DATA);
		}
		if (StringUtils.isEmpty(propertyInfoBO.getPayPassword())) {
			return successGet(false);
		}
		return successGet(true);
	}

	/**
	 * 校验支付密码
	 * 
	 * @param userNum
	 *            用户编号
	 * @param payPwd
	 *            明文
	 * @return
	 */
	@RequestMapping(value = "varifyPayPwd", method = RequestMethod.GET)
	public Result<Boolean> varifyPayPwd(@RequestParam String userNum, @RequestParam String payPwd) {
		PropertyInfoBO propertyInfoBO = propertyInfoService.getPropertyInfoByUserNum(userNum);
		if (propertyInfoBO == null) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}
		if (StringUtils.isEmpty(propertyInfoBO.getPayPassword())) {
			return successGet(ResultCode.PAY_PWD_NULL);
		}
		if (MD5.MD5Encode(payPwd).equals(propertyInfoBO.getPayPassword())) {
			return successGet(true);
		}
		return successGet(false);
	}

	/**
	 * 根据用户编号获取资产余额
	 *
	 * @param userNum
	 *            用户编号
	 * @return
	 */
	@RequestMapping(value = "propertyBalance/{userNum}", method = RequestMethod.GET)
	public Result<PropertyBalanceDTO> getPropertyBalance(@PathVariable("userNum") String userNum) {
		PropertyBalanceBO propertyBalanceBO = propertyInfoService.getPropertyBalanceByUserNum(userNum);
		return successCreated(PropertyBalanceConverter.convert(propertyBalanceBO));
	}

	/**
	 * 根据用户编号获取用户积分
	 *
	 * @param userNum
	 *            用户编号
	 * @return
	 */
	@RequestMapping(value = "propertyPoint/{userNum}", method = RequestMethod.GET)
	public Result<PropertyPointDTO> getPropertyPoint(@PathVariable("userNum") String userNum) {
		PropertyPointBO propertyPointBO = propertyInfoService.getPropertyPointByUserNum(userNum);
		return successCreated(PropertyPointConverter.convert(propertyPointBO));
	}

	/**
	 * 查询用户商家财产积分余额
	 * 
	 * @param userNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getPropertyInfoMoney/{userNum}", method = RequestMethod.GET)
	public Result<PropertyPointAndBalanceDTO> getPropertyInfoMoney(@PathVariable("userNum") String userNum)
			throws Exception {
		PropertyPointAndBalanceBO propertyPointBO = propertyInfoService.getPropertyInfoMoney(userNum);
		PropertyPointAndBalanceDTO dto = new PropertyPointAndBalanceDTO();
		BeanUtil.copyProperties(propertyPointBO, dto);
		return successCreated(dto);
	}

	/**
	 * 操作用户积分财产
	 *
	 * @param userNum
	 *            用户编号
	 * @param column
	 *            列名：B-余额，P-积分，L-爱心账户
	 * @param flag
	 *            标记:A-加,M-减
	 * @param number
	 *            余额、积分、爱心账户的数额
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updatePoint/{userNum}", method = RequestMethod.POST)
	public Result updatePoint(@PathVariable("userNum") String userNum, @RequestParam String column,
			@RequestParam String flag, @RequestParam BigDecimal number) {
		int retCode = propertyInfoService.updatePropertyNumbers(userNum, column, flag, number);
		return successCreated(retCode);
	}

	/**
	 * 查询爱心账户
	 * 
	 * @param userNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectLoveAccount/{userNum}", method = RequestMethod.GET)
	public Result<PropertyLoveAccountDTO> selectLoveAccount(@PathVariable("userNum") String userNum) throws Exception {
		BigDecimal selectLoveAccount = propertyInfoService.selectLoveAccount(userNum);
		PropertyLoveAccountDTO dto = new PropertyLoveAccountDTO();
		dto.setLoveAccount(selectLoveAccount);
		return successCreated(dto);
	}

	/**
	 * 运营平台余额积分处理
	 * 
	 * @param dparam
	 * @return
	 * @author yangqh
	 * @date 2017年5月16日 下午2:36:23
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updateBalanceAndPoint", method = RequestMethod.POST)
	public Result updateBalanceAndPoint(@RequestBody BackagePropertyinfoDataParam dparam) {
		int retCode = propertyInfoService.updateBalanceAndPoint(dparam);
		return successCreated(retCode);
	}

	/**
	 * 运营平台资金冻结操作
	 * @param userNum
	 * @param freeze
	 * @return
	 * @author yangqh
	 * @date 2017年5月16日 下午4:56:44
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "updatePropertyinfoFreeze", method = RequestMethod.POST)
	public Result updatePropertyinfoFreeze(@RequestParam String userNum, @RequestParam PropertyinfoFreezeEnum freeze) {
		int retCode = propertyInfoService.updatePropertyinfoFreeze(userNum,freeze);
		return successCreated(retCode);
	}
	
	/**
	 * 运营平台查询用户资金冻结情况
	 * @param userNum
	 * @return
	 * @author yangqh
	 * @date 2017年5月16日 下午4:56:25
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "getPropertyinfoFreeze", method = RequestMethod.GET)
	public Result<PropertyinfoFreezeInfoDTO> getPropertyinfoFreeze(@RequestParam("userNum") String userNum)  {
		PropertyinfoFreezeEnum freezeEnum = propertyInfoService.getPropertyinfoFreeze(userNum);
		PropertyinfoFreezeInfoDTO dto = new PropertyinfoFreezeInfoDTO();
		if(freezeEnum == null){
			return successCreated(ResultCode.FAIL,"用户资产记录为空！");
		}
		dto.setFreeze(freezeEnum);
		dto.setUserNum(userNum);
		return successCreated(dto);
	}
}
