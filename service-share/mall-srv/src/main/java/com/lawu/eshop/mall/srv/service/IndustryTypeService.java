package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.mall.srv.bo.IndustryTypeBO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@Service
public interface IndustryTypeService {

    /**
     * 查询行业
     *
     * @return
     */
    List<IndustryTypeBO> listIndustryType();
}
