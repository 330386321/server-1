package com.lawu.eshop.statistics.srv.converter;

import com.lawu.eshop.statistics.dto.UserActiveDTO;
import com.lawu.eshop.statistics.srv.bo.UserActiveBO;
import com.lawu.eshop.statistics.srv.domain.extend.UserActiveDOView;

/**
 * @author zhangyong
 * @date 2017/6/30.
 */
public class UserActiveConverter {
    public static UserActiveBO coverBO(UserActiveDOView userActiveDOView) {
        if (userActiveDOView == null) {
            return null;
        }
        UserActiveBO userActiveBO = new UserActiveBO();
        userActiveBO.setCityId(userActiveDOView.getCityId());
        userActiveBO.setCityName(userActiveDOView.getCityName());
        userActiveBO.setVisitDate(userActiveDOView.getVisitDate());
        userActiveBO.setUserCount(userActiveDOView.getUserCount());
        return userActiveBO;
    }

    public static UserActiveDTO coverDTO(UserActiveBO userActiveBO) {
        if (userActiveBO == null) {
            return null;
        }
        UserActiveDTO userActiveDTO = new UserActiveDTO();
        userActiveDTO.setCityId(userActiveBO.getCityId());
        userActiveDTO.setCityName(userActiveBO.getCityName());
        userActiveDTO.setUserCount(userActiveBO.getUserCount());
        userActiveDTO.setVisitDate(userActiveBO.getVisitDate());
        return userActiveDTO;
    }
}
