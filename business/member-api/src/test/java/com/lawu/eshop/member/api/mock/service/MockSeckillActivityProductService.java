package com.lawu.eshop.member.api.mock.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.SeckillActivityProductService;
import com.lawu.eshop.product.dto.SeckillActivityProductBuyPageDTO;
import com.lawu.eshop.product.dto.SeckillActivityProductInformationDTO;
import com.lawu.eshop.product.param.SeckillActivityProductPageQueryParam;

@Service
public class MockSeckillActivityProductService extends BaseController implements SeckillActivityProductService {

    @Override
    public Result<Page<SeckillActivityProductBuyPageDTO>> page(@PathVariable("id") Long id, @RequestBody SeckillActivityProductPageQueryParam param) {
        return null;
    }

    @Override
    public Result<SeckillActivityProductInformationDTO> information(@PathVariable("id") Long id, @RequestParam("memberId") Long memberId) {
        return null;
    }
}
