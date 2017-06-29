package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.mall.constants.RegionLevelEnum;
import com.lawu.eshop.mall.srv.bo.RegionBO;
import com.lawu.eshop.mall.srv.converter.RegionConverter;
import com.lawu.eshop.mall.srv.domain.RegionDO;
import com.lawu.eshop.mall.srv.domain.RegionDOExample;
import com.lawu.eshop.mall.srv.domain.extend.RegionDOView;
import com.lawu.eshop.mall.srv.mapper.RegionDOMapper;
import com.lawu.eshop.mall.srv.mapper.extend.RegionDOMMapperExtend;
import com.lawu.eshop.mall.srv.service.RegionService;
import org.apache.commons.lang.StringUtils;
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

    @Autowired
    private RegionDOMapper regionDOMapper;

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

    @Override
    public String getRegionFullName(Integer id) {
        String regionFullName = "";
        RegionDO regionDO = regionDOMapper.selectByPrimaryKey(id);
        if (regionDO == null) {
            return regionFullName;
        }
        int cnt = 0;
        regionFullName = regionDO.getName();
        do {
            if (regionDO.getParentId() > 0) {
                regionDO = regionDOMapper.selectByPrimaryKey(regionDO.getParentId());
                if (regionDO == null) {
                    return regionFullName;
                }
                regionFullName = regionDO.getName() + regionFullName;
            }
            cnt++;
        } while (cnt < 2);
        return regionFullName;
    }

    @Override
    public String getAreaName(String regionPath) {
        String areaName = "";
        if (StringUtils.isEmpty(regionPath)) {
            return areaName;
        }
        String[] paths = regionPath.split("/");
        if (paths.length < 3) {
            return areaName;
        }
        RegionDO regionDO = regionDOMapper.selectByPrimaryKey(Integer.valueOf(paths[2]));
        if (regionDO == null || StringUtils.isEmpty(regionDO.getName())) {
            return areaName;
        }
        return regionDO.getName();
    }

    @Override
    public List<RegionBO> getRegionLevelTwo() {
        RegionDOExample example = new RegionDOExample();
        example.createCriteria().andLevelEqualTo(RegionLevelEnum.REGION_LEVEL_TWO.val);
        List<RegionDO> regionDOS = regionDOMapper.selectByExample(example);
        return RegionConverter.coverBO(regionDOS);
    }
}
