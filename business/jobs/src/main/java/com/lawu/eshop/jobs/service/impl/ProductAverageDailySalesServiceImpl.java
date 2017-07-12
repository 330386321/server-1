package com.lawu.eshop.jobs.service.impl;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.jobs.service.ProductService;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.product.param.ListProductParam;
import com.lawu.eshop.jobs.service.ProductAverageDailySalesService;
import com.lawu.eshop.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/25.
 */
@Service
public class ProductAverageDailySalesServiceImpl implements ProductAverageDailySalesService {

    @Autowired
    private ProductService productService;

    @Override
    public void executeProductAverageDailySales() {
        ListProductParam listProductParam = new ListProductParam();
        listProductParam.setPageSize(1000);
        int currentPage = 0;

        Result<List<ProductInfoDTO>> result;
        while (true){
            currentPage ++;
            listProductParam.setCurrentPage(currentPage);
            result = productService.listProduct(listProductParam);
            if (result == null || result.getRet() != ResultCode.SUCCESS) {
                return;
            }

            for (ProductInfoDTO productInfoDTO : result.getModel()) {
                int days = DateUtil.daysOfTwo(productInfoDTO.getGmtCreate());
                int salesVolume = productInfoDTO.getTotalSalesVolume();
                double averageDailySales = 0;
                if (days > 0) {
                    averageDailySales = (double) salesVolume / days;
                }
                productService.updateAverageDailySalesById(productInfoDTO.getId(), BigDecimal.valueOf(averageDailySales));
            }
        }
    }
}