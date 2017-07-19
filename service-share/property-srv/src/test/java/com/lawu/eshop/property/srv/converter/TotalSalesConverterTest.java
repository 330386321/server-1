package com.lawu.eshop.property.srv.converter;

import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.dto.TotalSalesDTO;
import com.lawu.eshop.property.param.TotalSalesQueryParam;
import com.lawu.eshop.property.srv.bo.TotalSalesBO;
import com.lawu.eshop.property.srv.domain.extend.TotalSalesDO;
import com.lawu.eshop.property.srv.domain.extend.TotalSalesQueryExample;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangqh
 * @date 2017/7/19
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TotalSalesConverterTest {

    @Test
    public void convert(){
        TotalSalesQueryParam param = new TotalSalesQueryParam();
        param.setDate(new Date());
        TotalSalesQueryExample bo = TotalSalesConverter.convert(param);
        Assert.assertNotNull(bo);
    }

    @Test
    public void convert1(){
        TotalSalesDO totalSalesDO = new TotalSalesDO();
        totalSalesDO.setAmount(new BigDecimal("1"));
        totalSalesDO.setTransactionType(new Byte("1"));
        TotalSalesBO bo = TotalSalesConverter.convert(totalSalesDO);
        Assert.assertNotNull(bo);
    }

    @Test
    public void convertTotalSalesBOList(){
        List<TotalSalesDO> totalSalesDOList = new ArrayList<>();
        TotalSalesDO totalSalesDO = new TotalSalesDO();
        totalSalesDO.setAmount(new BigDecimal("1"));
        totalSalesDO.setTransactionType(new Byte("1"));
        totalSalesDOList.add(totalSalesDO);
        List<TotalSalesBO> bos = TotalSalesConverter.convertTotalSalesBOList(totalSalesDOList);
        Assert.assertNotNull(bos);
    }

    @Test
    public void convertTotalSalesDTO(){
        List<TotalSalesBO> totalSalesBOList = new ArrayList<>();
        TotalSalesBO totalSalesBO = new TotalSalesBO();
        totalSalesBO.setAmount(new BigDecimal("1"));
        totalSalesBO.setTransactionType(MerchantTransactionTypeEnum.AD_RETURN_POINT);
        totalSalesBOList.add(totalSalesBO);
        TotalSalesDTO dto = TotalSalesConverter.convertTotalSalesDTO(totalSalesBOList);
        Assert.assertNotNull(dto);
    }
}
