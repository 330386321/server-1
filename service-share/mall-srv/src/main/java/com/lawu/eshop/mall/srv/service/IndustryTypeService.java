package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.mall.srv.bo.IndustryTypeBO;

import java.util.List;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
public interface IndustryTypeService {

    /**
     * 查询所有行业
     *
     * @return
     */
    List<IndustryTypeBO> listIndustryType();

}
