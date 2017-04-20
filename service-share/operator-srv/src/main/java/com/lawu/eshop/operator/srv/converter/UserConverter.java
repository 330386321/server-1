package com.lawu.eshop.operator.srv.converter;

import com.lawu.eshop.operator.srv.bo.UserBO;
import com.lawu.eshop.operator.srv.domain.UserDO;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
public class UserConverter {

    public static UserBO convertBO(UserDO userDO) {
        if(userDO == null){
            return null;
        }
        UserBO userBO = new UserBO();
        userBO.setId(userDO.getId());
        userBO.setAccount(userDO.getAccount());
        userBO.setPassword(userDO.getPassword());
        userBO.setName(userDO.getName());
        return userBO;
    }
}
