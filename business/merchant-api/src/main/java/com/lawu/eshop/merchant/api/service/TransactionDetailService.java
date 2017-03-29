package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.TransactionDetailDTO;
import com.lawu.eshop.property.param.TransactionDetailQueryParam;

/**
 * @author Sunny
 * @date 2017/3/29
 */
@FeignClient(value = "property-srv")
public interface TransactionDetailService {
	
	/**
     * 根据用户编号和查询参数查询交易明细
     * 
     * @param userNo 用户编号
     * @param transactionType 交易类型
     * @param transactionDetailQueryParam 查询参数
     * @return
     */
    @RequestMapping(value = "transactionDetail/findPageByUserNum/{userNum}", method = RequestMethod.POST)
    public Result<Page<TransactionDetailDTO>> findPageByUserNum(@PathVariable("userNum") String userNum, @RequestParam(name = "transactionType", required = false) Byte transactionType, @RequestBody TransactionDetailQueryParam transactionDetailQueryParam);
	
}
