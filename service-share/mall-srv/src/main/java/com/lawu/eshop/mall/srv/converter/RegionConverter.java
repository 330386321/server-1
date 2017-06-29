package com.lawu.eshop.mall.srv.converter;

import com.lawu.eshop.mall.constants.RegionLevelEnum;
import com.lawu.eshop.mall.dto.RegionDTO;
import com.lawu.eshop.mall.srv.bo.RegionBO;
import com.lawu.eshop.mall.srv.domain.RegionDO;
import com.lawu.eshop.mall.srv.domain.extend.RegionDOView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
public class RegionConverter {

    public static RegionBO coverBO(RegionDOView regionDOView) {
        if (regionDOView == null) {
            return null;
        }
        RegionBO regionBO = new RegionBO();
        regionBO.setId(regionDOView.getId());
        regionBO.setName(regionDOView.getName());
        regionBO.setParentId(regionDOView.getParentId());
        regionBO.setPath(regionDOView.getPath());
        regionBO.setLevelEnum(RegionLevelEnum.getEnum(regionDOView.getLevel()));
        return regionBO;
    }

    public static RegionDTO coverDTO(RegionBO regionBO) {
        if (regionBO == null) {
            return null;
        }
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(regionBO.getId());
        regionDTO.setName(regionBO.getName());
        regionDTO.setParentId(regionBO.getParentId());
        regionDTO.setPath(regionBO.getPath());
        regionDTO.setLevelEnum(regionBO.getLevelEnum());
        return regionDTO;
    }

    public static List<RegionBO> coverBO(List<RegionDO> regionDOList) {
        List<RegionBO> regionBOList = new ArrayList<>();
        if (regionDOList == null || regionDOList.isEmpty()) {
            return regionBOList;
        }
        for (RegionDO regionDO : regionDOList) {
            RegionBO regionBO = new RegionBO();
            regionBO.setId(regionDO.getId());
            regionBO.setName(regionDO.getName());
            regionBO.setParentId(regionDO.getParentId());
            regionBO.setPath(regionDO.getPath());
            regionBO.setLevelEnum(RegionLevelEnum.getEnum(regionDO.getLevel()));
            regionBOList.add(regionBO);
        }
        return regionBOList;
    }
}
