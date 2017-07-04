package com.lawu.eshop.property.srv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.TotalSalesDTO;
import com.lawu.eshop.property.dto.TransactionDetailBackageDTO;
import com.lawu.eshop.property.dto.TransactionDetailToMemberDTO;
import com.lawu.eshop.property.dto.TransactionDetailToMerchantDTO;
import com.lawu.eshop.property.param.TotalSalesQueryParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForBackageParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForMemberParam;
import com.lawu.eshop.property.param.TransactionDetailQueryForMerchantParam;
import com.lawu.eshop.property.param.TransactionDetailSaveDataParam;
import com.lawu.eshop.property.srv.bo.TotalSalesBO;
import com.lawu.eshop.property.srv.bo.TransactionDetailBO;
import com.lawu.eshop.property.srv.converter.TotalSalesConverter;
import com.lawu.eshop.property.srv.converter.TransactionDetailConverter;
import com.lawu.eshop.property.srv.service.TransactionDetailService;

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
	
	/**
	 * 查询指定日期的平台销量
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "totalSales", method = RequestMethod.PUT)
	public Result<TotalSalesDTO> selectTotalSales(@RequestBody TotalSalesQueryParam param) {
		List<TotalSalesBO> totalSalesBOList = transactionDetailService.selectTotalSales(param);
		TotalSalesDTO rtn = TotalSalesConverter.convertTotalSalesDTO(totalSalesBOList);
		return successCreated(rtn);
	}

}
