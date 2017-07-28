package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.StoreSolrService;
import com.lawu.eshop.user.dto.StoreSearchWordDTO;
import com.lawu.eshop.user.dto.StoreSolrDTO;
import com.lawu.eshop.user.param.DiscountStoreParam;
import com.lawu.eshop.user.param.StoreSolrParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
class MockStoreSolrService implements StoreSolrService {

    @Override
    public Result<Page<StoreSolrDTO>> listStore(@ModelAttribute StoreSolrParam storeSolrParam) {
        return null;
    }

    @Override
    public Result<List<StoreSearchWordDTO>> listStoreSearchWord(@RequestParam("name") String name) {
        return null;
    }

    @Override
    public Result<Page<StoreSolrDTO>> discountStore(@ModelAttribute DiscountStoreParam discountStoreParam) {
        return null;
    }
}
