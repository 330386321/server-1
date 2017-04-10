package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.mall.srv.bo.RegionBO;
import com.lawu.eshop.mall.srv.converter.RegionConverter;
import com.lawu.eshop.mall.srv.domain.extend.RegionDOView;
import com.lawu.eshop.mall.srv.mapper.extend.RegionDOMMapperExtend;
import com.lawu.eshop.mall.srv.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionDOMMapperExtend regionDOMMapperExtend;

    @Override
    public List<RegionBO> getRegionList() {
        List<RegionDOView> viewList = regionDOMMapperExtend.getRegionList();
        if (viewList == null) {
            return null;
        }
        List<RegionBO> regionBOS = new ArrayList<RegionBO>();
        for (RegionDOView regionDOView : viewList) {
            RegionBO regionBO = RegionConverter.coverBO(regionDOView);
            regionBOS.add(regionBO);
        }
        return regionBOS;
    }
}
