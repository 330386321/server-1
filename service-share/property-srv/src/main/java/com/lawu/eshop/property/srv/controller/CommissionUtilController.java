package com.lawu.eshop.property.srv.controller;

import javax.validation.Valid;
import java.math.BigDecimal;

import com.lawu.eshop.property.dto.AdCommissionResultDTO;
import com.lawu.eshop.property.param.CommissionResultParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.dto.CommissionUtilDTO;
import com.lawu.eshop.property.srv.bo.CommissionUtilBO;
import com.lawu.eshop.property.srv.service.CommissionUtilService;

/**
 * 
 * <p>
 * Description: 收益，爱心账户计算结果
 * </p>
 * @author Yangqh
 * @date 2017年5月24日 下午1:00:46
 *
 */
@RestController
@RequestMapping(value = "commissionUtil/")
public class CommissionUtilController extends BaseController {

	@Autowired
	private CommissionUtilService commissionUtilService;

	/**
	 * 获取用户点击广告后，自己所得余额和需要扣钱的爱心账户
	 * @param clickMoney
	 * @return
	 * @author yangqh
	 * @date 2017年5月24日 下午1:09:48
	 */
	@RequestMapping(value = "getClickAdMine", method = RequestMethod.PUT)
	public Result<CommissionUtilDTO> getClickAdMine(@RequestBody BigDecimal clickMoney)  {
		if(clickMoney == null || !(clickMoney.compareTo(BigDecimal.ZERO) == 1)){
			return successCreated(ResultCode.FAIL,"传入金额错误");
		}
		CommissionUtilBO bo = commissionUtilService.getClickAdMine(clickMoney);
		CommissionUtilDTO dto = new CommissionUtilDTO();
		dto.setActureLoveIn(bo.getActureLoveIn());
		dto.setActureMoneyIn(bo.getActureMoneyIn());
		return successCreated(dto);
	}
	
	/**
	 * 获取用户收入余额，按指定比例计算，计算爱心账户
	 * @param clickMoney
	 * @return
	 * @author yangqh
	 * @date 2017年5月24日 下午1:09:48
	 */
	@RequestMapping(value = "getIncomeMoney", method = RequestMethod.PUT)
	public Result<CommissionUtilDTO> getIncomeMoney(@RequestBody BigDecimal clickMoney)  {
		if(clickMoney == null || !(clickMoney.compareTo(BigDecimal.ZERO) == 1)){
			return successCreated(ResultCode.FAIL,"传入金额错误");
		}
		CommissionUtilBO bo = commissionUtilService.getIncomeMoney(clickMoney);
		CommissionUtilDTO dto = new CommissionUtilDTO();
		dto.setActureLoveIn(bo.getActureLoveIn());
		dto.setActureMoneyIn(bo.getActureMoneyIn());
		return successCreated(dto);
	}

	/**
	 * 提成计算结果
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "getCommissionResult", method = RequestMethod.PUT)
	public Result<AdCommissionResultDTO> getCommissionResult(@RequestBody @Valid CommissionResultParam param, BindingResult result) {
		String message = validate(result);
		if (message != null) {
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
		}
		BigDecimal actureMoneyIn;
		BigDecimal actureLoveIn = null;
		if (param.getDept().intValue() == 2) {
			actureMoneyIn = param.getBeforeMoney().multiply(param.getCommission0()).multiply(param.getCurrentCommission()).setScale(6, BigDecimal.ROUND_HALF_UP);// 第三级进积分，无爱心账户
		} else {
			actureMoneyIn = param.getBeforeMoney().multiply(param.getCommission0()).multiply(param.getCurrentCommission()).multiply(param.getActualCommissionScope()).setScale(6, BigDecimal.ROUND_HALF_UP);// 实际所得余额
			actureLoveIn = param.getBeforeMoney().multiply(param.getCommission0()).multiply(param.getCurrentCommission()).multiply(param.getLoveAccountScale()).setScale(6, BigDecimal.ROUND_HALF_UP);// 爱心账户

			//如果计算出爱心账户为0.000000时默认赋值0.000001
			if (actureLoveIn.compareTo(BigDecimal.ZERO) == 0) {
				actureLoveIn = new BigDecimal("0.000001");
			}
		}
		//如果计算出实际提成为0.000000时默认赋值0.000001
		if (actureMoneyIn.compareTo(BigDecimal.ZERO) == 0) {
			actureMoneyIn = new BigDecimal("0.000001");
		}
		AdCommissionResultDTO rtnDTO = new AdCommissionResultDTO();
		rtnDTO.setActureMoneyIn(actureMoneyIn);
		rtnDTO.setActureLoveIn(actureLoveIn);
		return successCreated(rtnDTO);
	}

}
