package com.lawu.eshop.property.srv.controller;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.TransactionDetailBackageDTO;
import com.lawu.eshop.property.dto.TransactionDetailToMemberDTO;
import com.lawu.eshop.property.dto.TransactionDetailToMerchantDTO;
import com.lawu.eshop.property.param.TransactionDetailQueryForBackageParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForMemberParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForMerchantParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.bo.TransactionDetailBO;
import com.lawu.eshop.property.srv.converter.TransactionDetailConverter;
import com.lawu.eshop.property.srv.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sunny
 * @date 2017/3/29
 */
@RestController
@RequestMapping(value = "transactionDetail/")
public class TransactionDetailController extends BaseController {

	@Autowired
	private TransactionDetailService transactionDetailService;

	/**
	 * <p>
	 * 提供给用户
	 * <p>
	 * 根据用户编号和查询参数查询交易明细
	 * 
	 * @param userNum
	 *            用户编号
	 * @param param
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "findPageByUserNumForMember/{userNum}", method = RequestMethod.POST)
	public Result<Page<TransactionDetailToMemberDTO>> findPageByUserNumForMember(@PathVariable("userNum") String userNum, @RequestBody TransactionDetailQueryForMemberParam param) {
		Page<TransactionDetailBO> transactionDetailBOPage = transactionDetailService.findPageByUserNumForMember(userNum, param);
		
		return successCreated(TransactionDetailConverter.convertTransactionDetailToMemberDTOPage(transactionDetailBOPage));
	}

	/**
	 * <p>
	 * 提供给商家
	 * <p>
	 * 根据用户编号和查询参数查询交易明细
	 * 
	 * @param userNum
	 *            用户编号
	 * @param transactionDetailQueryParam
	 *            查询参数
	 * @return
	 */
	@RequestMapping(value = "findPageByUserNumForMerchant/{userNum}", method = RequestMethod.POST)
	public Result<Page<TransactionDetailToMerchantDTO>> findPageByUserNumForMerchant(@PathVariable("userNum") String userNum, @RequestBody TransactionDetailQueryForMerchantParam transactionDetailQueryParam) {
		Page<TransactionDetailBO> transactionDetailBOPage = transactionDetailService.findPageByUserNumForMerchant(userNum, transactionDetailQueryParam);
		
		return successCreated(TransactionDetailConverter.convertTransactionDetailToMerchantDTOPage(transactionDetailBOPage));
	}

	/**
	 * 保存交易明细记录
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestBody TransactionDetailSaveDataParam param) {
		return successCreated(transactionDetailService.save(param));
	}

	/**
	 * 查询运营后台充值记录
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "getBackageRechargePageList", method = RequestMethod.POST)
	public Result<Page<TransactionDetailBackageDTO>> getBackageRechargePageList(@RequestBody TransactionDetailQueryForBackageParam param) {
		Page<TransactionDetailBO> transactionDetailBOPage = transactionDetailService.getBackageRechargePageList(param);

		return successCreated(TransactionDetailConverter.convertTransactionDetailBackageDTOPage(transactionDetailBOPage));
	}

}
