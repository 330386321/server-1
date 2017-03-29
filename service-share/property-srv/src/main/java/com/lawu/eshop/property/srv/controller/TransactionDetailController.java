package com.lawu.eshop.property.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.TransactionDetailDTO;
import com.lawu.eshop.property.param.TransactionDetailQueryParam;
import com.lawu.eshop.property.srv.bo.TransactionDetailBO;
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
     * 根据用户编号和查询参数查询交易明细
     * 
     * @param userNo 用户编号
     * @param transactionType 交易类型
     * @param transactionDetailQueryParam 查询参数
     * @return
     */
    @RequestMapping(value = "findPageByUserNum/{userNum}", method = RequestMethod.POST)
    public Result<Page<TransactionDetailDTO>> findPageByUserNum(@PathVariable("userNum") String userNum, @RequestParam(name = "transactionType", required = false) Byte transactionType, @RequestBody TransactionDetailQueryParam transactionDetailQueryParam) {
    	
    	Page<TransactionDetailBO> transactionDetailBOPage = transactionDetailService.findPageByUserNum(userNum, transactionType, transactionDetailQueryParam);
    	
    	Page<TransactionDetailDTO> transactionDetailDTOPage = new Page<TransactionDetailDTO>();
    	transactionDetailDTOPage.setCurrentPage(transactionDetailBOPage.getCurrentPage());
    	transactionDetailDTOPage.setTotalCount(transactionDetailBOPage.getTotalCount());
    	transactionDetailDTOPage.setRecords(TransactionDetailConverter.convertDTOS(transactionDetailBOPage.getRecords()));
    	
        return successCreated(transactionDetailDTOPage);
    }
}
