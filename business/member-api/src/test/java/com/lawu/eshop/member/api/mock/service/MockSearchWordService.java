package com.lawu.eshop.member.api.mock.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.SearchWordDTO;
import com.lawu.eshop.mall.param.SearchWordParam;
import com.lawu.eshop.member.api.service.SearchWordService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;


@Service
public class MockSearchWordService implements SearchWordService {

    @Override
    public Result<Page<SearchWordDTO>> listSearchWord(@ModelAttribute SearchWordParam searchWordParam) {
        return null;
    }
}
