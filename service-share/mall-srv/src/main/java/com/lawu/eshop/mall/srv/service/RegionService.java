package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.mall.srv.bo.RegionBO;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
public interface RegionService {


    List<RegionBO> getRegionList();

    /**
     * 根据区域ID查询区域完整名称
     *
     * @param id
     * @return
     */
    String getRegionFullName(Integer id);

    /**
     * 根据区域路径查询区域名称
     *
     * @param regionPath
     * @return
     */
    String getAreaName(String regionPath);
}
