package com.lawu.eshop.property.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.PayTypeEnum;
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.constants.ThirdPayStatusEnum;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.dto.BalanceAndPointListQueryDTO;
import com.lawu.eshop.property.dto.RechargeReportDTO;
import com.lawu.eshop.property.dto.RechargeSaveDTO;
import com.lawu.eshop.property.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.property.param.NotifyCallBackParam;
import com.lawu.eshop.property.param.RechargeQueryDataParam;
import com.lawu.eshop.property.param.RechargeReportParam;
import com.lawu.eshop.property.param.RechargeSaveDataParam;
import com.lawu.eshop.property.srv.bo.BalanceAndPointListQueryBO;
import com.lawu.eshop.property.srv.bo.PropertyInfoBO;
import com.lawu.eshop.property.srv.bo.RechargeReportBO;
import com.lawu.eshop.property.srv.service.PropertyInfoService;
import com.lawu.eshop.property.srv.service.PropertyService;
import com.lawu.eshop.property.srv.service.RechargeService;
import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.utils.BeanUtil;
import com.lawu.eshop.utils.PwdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>
 * Description:充值
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月12日 下午8:35:46
 *
 */
@RestController
@RequestMapping(value = "recharge/")
public class RechargeController extends BaseController {

	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private PropertyService propertySrevice;
	@Autowired
	private PropertyInfoService propertyInfoService;

	/**
	 * 用户商家第三方充值余额积分保存充值记录
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestBody @Valid RechargeSaveDataParam param, BindingResult result) {
		String message = validate(result);
		if (message != null) {
			return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
		}
		
		String value = "1";//充值余额
		if (PayTypeEnum.POINT.getVal().equals(param.getPayTypeEnum().getVal())) {
			// 获取第三方支付充值积分的比例
			String name = PropertyType.MEMBER_THIRD_PAY_POINT;
			if (param.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
				name = PropertyType.MERCHANT_THIRD_PAY_POINT;
			}
			value = propertySrevice.getValue(name);
		}
		param.setRechargeScale(value);
		
		// 余额支付时校验资产财产记录、余额、支付密码
		if(TransactionPayTypeEnum.BALANCE.getVal().equals(param.getTransactionPayTypeEnum().getVal())){
			PropertyInfoBO propertyInfoBO = propertyInfoService.getPropertyInfoByUserNum(param.getUserNum());
			if(propertyInfoBO == null){
				return successCreated(ResultCode.PAY_PWD_NULL);
			}else{
				// 校验支付密码
				if (!PwdUtil.verify(param.getPayPwd(), propertyInfoBO.getPayPassword())) {
					return successCreated(ResultCode.PAY_PWD_ERROR);
				}
			}
		}
		
		RechargeSaveDTO dto = rechargeService.save(param);
		if(dto == null){
			return successCreated(ResultCode.FAIL, "充值失败！");
		}
		return successCreated(dto);
	}
	
	/**
	 * 用户/商家微信/支付宝充值余额积分回调
	 * @param param
	 * @param result
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "doHandleRechargeNotify", method = RequestMethod.POST)
	public Result doHandleRechargeNotify(@RequestBody @Valid NotifyCallBackParam param, BindingResult result) {
		String message = validate(result);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
		
		return rechargeService.doHandleRechargeNotify(param);
	}

	/**
	 * 调用第三方时获取支付金额
	 * @param rechargeId
	 * @return
	 */
	@RequestMapping(value = "getRechargeMoney", method = RequestMethod.GET)
	public ThirdPayCallBackQueryPayOrderDTO getRechargeMoney(@RequestParam String rechargeId) {
		if(rechargeId == null || "".equals(rechargeId)){
			return null;
		}
		ThirdPayCallBackQueryPayOrderDTO recharge = rechargeService.getRechargeMoney(rechargeId);
		return recharge;
	}
	
	/**
	 * 运营平台充值
	 * @param dparam
	 * @return
	 * @throws Exception
	 * @author yangqh
	 * @date 2017年5月16日 下午3:58:09
	 */
	@RequestMapping(value = "selectPropertyinfoList", method = RequestMethod.POST)
	public Result<Page<BalanceAndPointListQueryDTO>> selectPropertyinfoList(@RequestBody RechargeQueryDataParam dparam) throws Exception {
		Page<BalanceAndPointListQueryBO> page = rechargeService.selectPropertyinfoList(dparam);
		List<BalanceAndPointListQueryBO> cbos = page.getRecords();
		List<BalanceAndPointListQueryDTO> dtos = new ArrayList<BalanceAndPointListQueryDTO>();
		for (BalanceAndPointListQueryBO bo : cbos) {
			BalanceAndPointListQueryDTO dto = new BalanceAndPointListQueryDTO();
			BeanUtil.copyProperties(bo, dto);
			dtos.add(dto);
		}
		Page<BalanceAndPointListQueryDTO> pageResult = new Page<BalanceAndPointListQueryDTO>();
		pageResult.setTotalCount(page.getTotalCount());
		pageResult.setCurrentPage(page.getCurrentPage());
		pageResult.setRecords(dtos);
		return successCreated(pageResult);
	}

	/**
	 * 根据ID查询充值支付方式
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getRechargePayType/{id}", method = RequestMethod.GET)
	public Result<String> getRechargePayType(@PathVariable Long id) {
		String payType = rechargeService.getRechargePayType(id);
		return successGet(payType);
	}

	/**
	 * 根据充值ID查询充值状态
	 *
	 * @param rechargeId
	 * @return
	 */
	@RequestMapping(value = "getRechargeById/{rechargeId}", method = RequestMethod.GET)
	public Result<ThirdPayStatusEnum> getRechargeById(@PathVariable Long rechargeId) {
		ThirdPayStatusEnum status = rechargeService.getRechargeById(rechargeId);
		return successGet(status);
	}

	// -------------------------------统计报表

		/**
		 * 查询某天平台用户商家充值余额成功的记录
		 * @param param
		 * @param result
		 * @return
		 * @throws Exception
		 * @author yangqh
		 * @date 2017年6月29日 下午5:26:31
		 */
		@RequestMapping(value = "selectWithdrawCashListByDateAndStatus", method = RequestMethod.POST)
		public Result<List<RechargeReportDTO>> selectWithdrawCashListByDateAndStatus(@RequestBody @Valid RechargeReportParam param, BindingResult result) throws Exception {
			String message = validate(result);
	    	if (message != null) {
	    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
	    	}
	    	List<RechargeReportDTO> dtos = new ArrayList<>();
	    	List<RechargeReportBO> rntList = rechargeService.selectWithdrawCashListByDateAndStatus(param);
			for(RechargeReportBO bo : rntList){
				RechargeReportDTO dto = new RechargeReportDTO();
				dto.setId(bo.getId());
				dto.setRechargeMoney(bo.getRechargeMoney());
				dto.setUserNum(bo.getUserNum());
				dtos.add(dto);
			}
			return successCreated(dtos);
		}
}
